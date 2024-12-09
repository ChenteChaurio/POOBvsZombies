package domain;

abstract class Plant implements Thing{
    protected int health;
    protected int cost;
    protected boolean alive = true;
    protected int x, y;
    protected PoobVsZombies poobVsZombies;

    public Plant(int x, int y,PoobVsZombies poobVsZombies){
        this.x = x;
        this.y = y;
        this.poobVsZombies = poobVsZombies;
    }

    public abstract void act() throws PoobVsZombiesException;

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

    public int getHealth(){
        return health;
    }

}
