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
        if (poobVsZombies.getBoard()[x][y+1] instanceof Zombie) {
            Zombie zombie = (Zombie) poobVsZombies.getBoard()[x][y+1];
            zombie.takeDamage(damage);
            poobVsZombies.removeThing(this);
        }
    }

    private void isAtaccked(){

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
