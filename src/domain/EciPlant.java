package domain;

import presentation.ECIplantA;

public class EciPlant extends Plant {
    private ECIplantA eciPlantAnimation;

    public EciPlant(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
    }

    @Override
    public void act()  {

    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            if (eciPlantAnimation != null) {
                eciPlantAnimation.removeLabel();
            }
            poobVsZombies.addPlantToRemove(this);
            return;
        }
        act();
    }

    public void setEciPlantAnimation(ECIplantA eciPlantAnimation) {
        this.eciPlantAnimation = eciPlantAnimation;
    }
}
