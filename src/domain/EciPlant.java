package domain;

public class EciPlant extends Plant {

    public EciPlant(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
    }

    @Override
    public void act()  {

    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.addPlantToRemove(this);
            return;
        }
        act();
    }
}
