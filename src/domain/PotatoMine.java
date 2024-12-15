package domain;

import presentation.PotatoMineA;

public class PotatoMine extends Plant{
    private boolean active;
    private static final int COOLDOWN = 14000;
    private long lastShotTime = System.currentTimeMillis();
    private int damage;
    private int explosiveHealth = 101;
    private PotatoMineA potatoMineAnimation;

    /**
     * The constructor of PotatoMine Plant
     * @param x the position in x
     * @param y the position in y
     * @param poobVsZombies the mainGame
     */
    public PotatoMine(int x, int y,PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 25;
        this.health = 100;
        this.active = false;
    }

    /**
     * The action to be taken (in this case the PotatoMine activates
     * and explodes under the given conditions)
     */
    @Override
    public void act()  {
        if(active){
            health = explosiveHealth;
            damage = Integer.MAX_VALUE;
            explode();
        }
    }

    /**
     * The PotatoMine explodes in case the zombie gets to it
     */
    public void explode()  {
       if (health < Integer.MAX_VALUE) {
           if(!poobVsZombies.getBoard()[x][y+1].isEmpty()){
                Zombie zombie = (Zombie) poobVsZombies.getBoard()[x][y+1].getFirst();
                zombie.takeDamage(damage);
                poobVsZombies.addPlantToRemove(this);
           }
       }
    }

    /**
     * Method that updates the status of the plant every moment
     */
    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            if (potatoMineAnimation != null) {
                potatoMineAnimation.removeLabel();
            }
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

    public void setPotatoMineAnimation(PotatoMineA potatoMineAnimation) {
        this.potatoMineAnimation = potatoMineAnimation;
    }

}
