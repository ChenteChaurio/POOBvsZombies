package domain;

public class NormalZombie extends Zombie {


    public NormalZombie(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.health = 100;
        this.speed = 1;
        this.cost = 100;

    }


    @Override
    public void move() throws PoobVsZombiesException {
        if (y > 0) {
            poobVsZombies.removeThing(this);
            y -= speed;
            poobVsZombies.setThing(x,y,this);
        }
    }

    @Override
    public void attack(Plant plant) {
        
    }


    public boolean attackMode() {
        if(poobVsZombies.getBoard()[x][y-1] instanceof Plant) {
            return true;
        }
        return false;
    }


    private boolean isValidMovement(int x, int y){
        return(poobVsZombies.getBoard()[x][y] == null);
    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.removeThing(this);
            return;
        }
        if(!attackMode()){
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastShotTime) >= COOLDOWN && poobVsZombies.isValidMove(x,y-speed)) {
                move();
                lastShotTime = currentTime;
        }else{

            }

        }
    }
}