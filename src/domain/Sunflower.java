package domain;

public class Sunflower extends Plant{
    private static final int COOLDOWN = 20000;
    private long lastShotTime = System.currentTimeMillis();


    /**
     * The constructor of Sunflower Plant
     * @param x the position in x
     * @param y the position in y
     * @param poobVsZombies the mainGame
     */
    public Sunflower(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 50;
        this.health = 300;
    }

    /**
     * The action to be taken (in this case the Sunflower generate a sun)
     */
    @Override
    public void act() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastShotTime > COOLDOWN) {
            Sun sun = new Sun(x,y,25);
            lastShotTime = currentTime;
        }
    }

    /**
     * Method that updates the status of the plant every moment
     */
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.addPlantToRemove(this);
            return;
        }
        act();
    }
}
