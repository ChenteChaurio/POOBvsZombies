package domain;

public class Wallnut extends Plant{
    public Wallnut(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 50;
        this.health = 4000;
    }

    @Override
    public void act() {
    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.addPlantToRemove(this);
        }
    }
}
