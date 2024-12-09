package domain;

public class NormalZombie extends Zombie {
    private boolean hasWaitedTwoSeconds = false;
    private long lastWaitTime = System.currentTimeMillis();

    public NormalZombie(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.health = 100;
        this.speed = 1;
        this.cost = 100;
        this.lastMoveTime = System.currentTimeMillis();
        this.lastAttackTime = 0;
    }

    @Override
    public void move() throws PoobVsZombiesException {
        if (y > 0) {
            poobVsZombies.addZombieToMove(this);
        }
    }

    @Override
    public void attack() throws PoobVsZombiesException {
        Plant plant = (Plant) poobVsZombies.getBoard()[x][y-1].getFirst();
        if (plant == null) throw new PoobVsZombiesException(PoobVsZombiesException.NULL_OBJECT);
        plant.takeDamage(damage);
    }


    private boolean attackMode() {
        for (Thing thing : poobVsZombies.getBoard()[x][y-1]) {
            if (thing instanceof Plant) {
                return true;
            }
        }
        return false;
    }



    private boolean isValidMovement(int x, int y) {
        return poobVsZombies.isValidMove(x, y);
    }

    private void checkPlant() throws PoobVsZombiesException {
        if (poobVsZombies.getBoard()[x][y-1].isEmpty()){
            move();
            lastMoveTime = System.currentTimeMillis();
        }
    }



    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.addZombieToRemove(this);
            return;
        }
        if(isValidMovement(x,y-1)){
            long currentTime = System.currentTimeMillis();
            System.out.println(currentTime - lastMoveTime);
            if ((currentTime - lastMoveTime) >= COOLDOWN && !attackMode()) {
                move();
                lastAttackTime = currentTime;
                lastMoveTime = currentTime;
                lastWaitTime = currentTime;
                hasWaitedTwoSeconds = false;
            }else if(attackMode()){
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
            }
        }
    }
}