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
        if(modeAtack()){
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastDeathTime) >= COOLDOWN) {
                deleteZombies();
                move();
                lastDeathTime = currentTime;
            }
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
            y += 1;
        }
    }

    private boolean modeAtack(){
        for(Thing thing :poobVsZombies.getBoard()[x][y]){
            if(thing instanceof Zombie){
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void takeDamage(int damage) {

    }
}
