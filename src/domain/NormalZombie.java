package domain;

import presentation.BasicZombieA;

import java.util.NoSuchElementException;

public class NormalZombie extends Zombie {
    private boolean hasWaitedTwoSeconds = false;
    private long lastWaitTime = System.currentTimeMillis();

    private BasicZombieA animation;
    /**
     * The constructor of NormalZombie Zombie
     * @param x the position in x
     * @param y the position in y
     * @param poobVsZombies the mainGame
     */
    public NormalZombie(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.health = 100;
        this.speed = 1;
        this.cost = 100;
        this.lastMoveTime = System.currentTimeMillis();
        this.lastAttackTime = 0;
    }

    /**
     * The action to moves the zombie to the left
     * @throws PoobVsZombiesException
     */
    @Override
    public void move() throws PoobVsZombiesException {
        if (y > 0) {
            poobVsZombies.addZombieToMove(this);
            animation.updatePosition(x, y);
        }
    }

    /**
     * The method to attack in case you have already found the plant,
     * or if it is in the same position as you have found it
     */
    @Override
    public void attack() {
        Plant plant = null;
        try {
            Thing thing = poobVsZombies.getBoard()[x][y-1].getFirst();
            if (thing instanceof Plant) {
                plant = (Plant) thing;
            }
        } catch (NoSuchElementException e) {
            for (Thing thing : poobVsZombies.getBoard()[x][y]) {
                if (thing instanceof Plant) {
                    plant = (Plant) thing;
                    break;
                }
            }
        }
        if (plant != null) {
            plant.takeDamage(damage);
            animation.animateAttack();
        }
    }


    /**
     * Checks if the zombie goes into attack mode
     * @return
     */
    private boolean attackMode() {
        for (Thing thing : poobVsZombies.getBoard()[x][y-1]) {
            if (thing instanceof Plant) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check for a plant in the same position
     * @return boolean
     */
    private boolean plantInSamePosition(){
        for (Thing thing : poobVsZombies.getBoard()[x][y]) {
            if (thing instanceof Plant) {
                return true;
            }
        }
        return false;
    }


    /**
     * Validates zombie movement
     * @param x the new x position
     * @param y the new y position
     * @return boolean
     */
    private boolean isValidMovement(int x, int y) {
        return poobVsZombies.isValidMove(x, y);
    }

    private void checkPlant() throws PoobVsZombiesException {
        if (poobVsZombies.getBoard()[x][y-1].isEmpty()){
            move();
            lastMoveTime = System.currentTimeMillis();
        }
    }


    /**
     * Method that updates the status of the plant every moment(Decides if move or attack in respect times)
     * @throws PoobVsZombiesException
     */
    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.addZombieToRemove(this);
            return;
        }
        if(isValidMovement(x,y-1)){
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastMoveTime) >= COOLDOWN && !attackMode()) {
                move();
                lastAttackTime = currentTime;
                lastMoveTime = currentTime;
                lastWaitTime = currentTime;
                hasWaitedTwoSeconds = false;
            }else if(attackMode()&&!plantInSamePosition()){
                if (!hasWaitedTwoSeconds) {
                    if (currentTime - lastWaitTime >= 2500) {
                        attack();
                        checkPlant();
                        lastAttackTime = currentTime;
                        lastMoveTime = currentTime;
                        lastWaitTime = currentTime;
                        hasWaitedTwoSeconds = true;
                    }
                }else {
                    if (currentTime - lastAttackTime >= ATTACK_COOLDOWN) {
                        attack();
                        checkPlant();
                        lastAttackTime = currentTime;
                        lastMoveTime = currentTime;
                        lastWaitTime = currentTime;
                    }
                }
            }else if(plantInSamePosition()){
                if (currentTime - lastAttackTime >= ATTACK_COOLDOWN) {
                    attack();
                    lastAttackTime = currentTime;
                    lastWaitTime = currentTime;
                }
                long timeSinceLastMove = currentTime - lastMoveTime;
                //lastMoveTime = timeSinceLastMove;
                System.out.println(timeSinceLastMove);
            }
        }
    }
}