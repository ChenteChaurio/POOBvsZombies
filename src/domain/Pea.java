package domain;

import presentation.PeaA;

public class Pea {
    private final int x;
    private int y;
    private int damage = 20;
    private static final int COOLDOWN = 400;
    private long lastShotTime = System.currentTimeMillis();

    //ani
    private PeaA peaAnimation;

    /**
     * The constructor of the object Pea
     * @param x the position in x
     * @param y the position in y
     */
    public Pea(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Move the pea 1 on the right
     */
    public void move() {
        this.y += 1;
    }


    /**
     * Checks if the zombie is in position to receive damage from the pea
     * @param zombie the zombie to checks
     * @return boolean
     */
    public boolean checkCollision(Zombie zombie) {
        return this.x == zombie.getX() && this.y == zombie.getY()|| this.x == zombie.getX() && this.y-1 == zombie.getY();
    }


    /**
     * Method that updates the status of the plant every moment
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastShotTime) >= COOLDOWN) {
            move();
            lastShotTime = currentTime;
        }
    }


    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //ani
    public void setPeaAnimation(PeaA peaAnimation) {
        this.peaAnimation = peaAnimation;
    }

}

