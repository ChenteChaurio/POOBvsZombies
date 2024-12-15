package domain;

import presentation.PeaShooterA;

public class PeaShooter extends Plant {
    private static final int COOLDOWN = 1500;
    private long lastShotTime = 0;

    private PeaShooterA peaShooterAnimation;

    /**
     * The constructor of Peashooter Plant
     * @param x the position in x
     * @param y the position in y
     * @param poobVsZombies the mainGame
     */
    public PeaShooter(int x, int y,PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 100;
        this.health = 300;

    }

    /**
     * Checks the case where there is a zombie in the same row
     * @return boolean
     */
    private boolean modeAttack(){
        for (int j = 0; j < poobVsZombies.getBoard()[x].length; j++) {
            for (Thing thing : poobVsZombies.getBoard()[x][j]) {
                if(thing instanceof Zombie){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The action to be taken (in this case the Peashooter generates a pea in case it is
     * in attack mode and has already completed its cooldown.)
     */
    @Override
    public void act() {
        long currentTime = System.currentTimeMillis();

        if (modeAttack() && (currentTime - lastShotTime) >= COOLDOWN) {
            Pea pea = new Pea(x, y+1);
            poobVsZombies.addPea(pea);
            lastShotTime = currentTime;

            //animation
            if (peaShooterAnimation != null) {
                peaShooterAnimation.animateAttack();
            }
        } else {
            //idle
            if (peaShooterAnimation != null) {
                peaShooterAnimation.animateIdle();
            }
        }
    }


    /**
     * Method that updates the status of the plant every moment
     */
    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            if (peaShooterAnimation != null) {
                peaShooterAnimation.removeLabel();
            }
            poobVsZombies.addPlantToRemove(this);
            return;
        }
        act();
    }

    //ani
    public void setPeaShooterAnimation(PeaShooterA peaShooterAnimation) {
        this.peaShooterAnimation = peaShooterAnimation;
    }

}
