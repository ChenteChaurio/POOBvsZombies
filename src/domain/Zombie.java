package domain;

abstract class Zombie implements Thing {
    protected int health;
    protected int speed;
    protected boolean alive = true;
    protected int x, y;
    protected int damage = 100;
    protected PoobVsZombies poobVsZombies;
    protected static final int COOLDOWN = 2500;
    protected long lastMoveTime = 0;
    protected static final long ATTACK_COOLDOWN = 500;
    protected long lastAttackTime = 0;
    protected int cost;

    public Zombie(int x, int y,PoobVsZombies poobVsZombies) {
        this.x = x;
        this.y = y;
        this.poobVsZombies = poobVsZombies;
    }

    public abstract void move() throws PoobVsZombiesException;

    public abstract void attack() throws PoobVsZombiesException;

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public int getHealth(){
        return health;
    }

}
