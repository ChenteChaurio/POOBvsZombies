package domain;

public class Pea {
    private final int x;
    private int y;
    private int damage = 20;
    private static final int COOLDOWN = 400;
    private long lastShotTime = System.currentTimeMillis();


    public Pea(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void move() {
        this.y += 1;
    }


    public boolean checkCollision(Zombie zombie) {
        return this.x == zombie.getX() && this.y == zombie.getY()|| this.x == zombie.getX() && this.y-1 == zombie.getY();
    }


    public void update() {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime - lastShotTime);
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

}

