package domain;

import presentation.WallnutA;

public class Wallnut extends Plant{

    private WallnutA wallnutAnimation;

    /**
     * The constructor of WallNut Plant
     * @param x the position in x
     * @param y the position in y
     * @param poobVsZombies the mainGame
     */
    public Wallnut(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 50;
        this.health = 4000;
    }

    /**
     * The action to be taken (in this case the WallNut does nothing)
     */
    @Override
    public void act() {
    }

    /**
     * Method that updates the status of the plant every moment
     */
    @Override
    public void update()  {
        if(!isAlive()){
            if (wallnutAnimation != null) {
                wallnutAnimation.removeLabel();
            }
            poobVsZombies.addPlantToRemove(this);
        }
    }

    public void setWallnutAnimation(WallnutA wallnutAnimation) {
        this.wallnutAnimation = wallnutAnimation;
    }
}
