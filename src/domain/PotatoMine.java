package domain;

public class PotatoMine extends Plant{
    private boolean active;
    private static final int COOLDOWN = 14000;
    private long lastShotTime;
    private int damage;

    public PotatoMine(int x, int y,PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 25;
        this.health = 100;
        this.active = false;
    }

    @Override
    public void act() throws PoobVsZombiesException {
        if(active){
            damage = Integer.MAX_VALUE;
            explode();
        }
    }

    public void explode() throws PoobVsZombiesException {
        for (int j = 0; j < poobVsZombies.getBoard()[x].length; j++) {
            if (poobVsZombies.getBoard()[x][j] instanceof Zombie) {
                Zombie zombie = (Zombie) poobVsZombies.getBoard()[x][j];
                zombie.takeDamage(damage);
                poobVsZombies.removeThing(this);
            }
        }
    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.removeThing(this);
            return;
        }
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastShotTime) >= COOLDOWN) {
            active = true;
            lastShotTime = currentTime;
        }
        act();

    }
}
