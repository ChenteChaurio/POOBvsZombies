package domain;

public class LawnMover implements Thing{
    private int x;
    private int y;
    private PoobVsZombies poobVsZombies;
    private int damage= Integer.MAX_VALUE;
    private static final int COOLDOWN = 200;
    private long lastDeathTime = System.currentTimeMillis();

    public LawnMover(int x, int y,PoobVsZombies poobVsZombies) {
        this.x = x;
        this.y = y;
        this.poobVsZombies = poobVsZombies;
    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.addLawnMoverToRemove(this);
            return;
        }
        long currentTime = System.currentTimeMillis();
        if(modeAtack()){
            System.out.println(currentTime - lastDeathTime);
            if ((currentTime - lastDeathTime) >= COOLDOWN) {
                deleteZombies();
                move();
                lastDeathTime = currentTime;
            }
        }
        if (isActive()&&(currentTime - lastDeathTime) >= COOLDOWN) {
            System.out.println(currentTime - lastDeathTime);
            move();
            lastDeathTime = currentTime;
        }
    }


    private void deleteZombies(){
        for (Thing thing: poobVsZombies.getBoard()[x][y]){
            if(thing instanceof Zombie){
                Zombie zombie = (Zombie) thing;
                zombie.takeDamage(damage);
            }
        }
    }

    private void move(){
        if(y<poobVsZombies.getBoard()[0].length){
            poobVsZombies.addLawnMoverToMove(this);
        }
    }

    public boolean modeAtack(){
        for(Thing thing :poobVsZombies.getBoard()[x][y]){
            if(thing instanceof Zombie){
                return true;
            }
        }
        return false;
    }

    public boolean isActive(){
        return y != 0;
    }


    @Override
    public boolean isAlive() {
        if(y==poobVsZombies.getBoard()[0].length-1){
            return false;
        }
        return true;
    }

    @Override
    public void takeDamage(int damage) {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

}
