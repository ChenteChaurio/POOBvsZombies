package domain;

public class Sunflower extends Plant{
    private static final int COOLDOWN = 20000;
    private long lastShotTime;

    public Sunflower(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 50;
        this.health = 300;
    }

    @Override
    public void act() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime > COOLDOWN) {
            Sun sun = new Sun(x,y,25);
            lastShotTime = currentTime;
        }
    }

    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.removeThing(this);
            return;
        }
        act();
    }
}
