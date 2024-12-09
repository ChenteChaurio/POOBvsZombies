package domain;

public class PotatoMine extends Plant{
    private boolean active;
    private static final int COOLDOWN = 14000;
    private long lastShotTime = System.currentTimeMillis();
    private int damage;
    private int explosiveHealth = 101;

    public PotatoMine(int x, int y,PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 25;
        this.health = 100;
        this.active = false;
    }

    @Override
    public void act() throws PoobVsZombiesException {
        if(active){
            health = explosiveHealth;
            damage = Integer.MAX_VALUE;
            explode();
        }
    }

    public void explode() throws PoobVsZombiesException {
       if (health < Integer.MAX_VALUE) {
           if(!poobVsZombies.getBoard()[x][y+1].isEmpty()){
                Zombie zombie = (Zombie) poobVsZombies.getBoard()[x][y+1].getFirst();
                zombie.takeDamage(damage);
                poobVsZombies.addPlantToRemove(this);
           }
       }
    }



    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.addPlantToRemove(this);
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
