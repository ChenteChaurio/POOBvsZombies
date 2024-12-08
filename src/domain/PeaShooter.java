package domain;

public class PeaShooter extends Plant {
    private static final int COOLDOWN = 1500;
    private long lastShotTime;

    public PeaShooter(int x, int y,PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 100;
        this.health = 300;
    }


    private boolean modeAttack(){
        for (int j = 0; j < poobVsZombies.getBoard()[x].length; j++) {
            if(poobVsZombies.getBoard()[x][j] instanceof Zombie){
                return true;
            }
        }
        return false;
    }


    @Override
    public void act() {
        long currentTime = System.currentTimeMillis();
        if (modeAttack() && (currentTime - lastShotTime) >= COOLDOWN) {
            Pea pea = new Pea(x, y);
            poobVsZombies.addPea(pea);
            lastShotTime = currentTime;
        }
    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.removeThing(this);
            return;
        }
        act();
    }
}
