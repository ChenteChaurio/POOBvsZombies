package domain;

public class NormalZombie extends Zombie {
    private boolean hasWaitedTwoSeconds = false;
    private long lastWaitTime = 0;

    public NormalZombie(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.health = 100;
        this.speed = 1;
        this.cost = 100;
        this.lastMoveTime = System.currentTimeMillis();
        this.lastAttackTime = System.currentTimeMillis();


    }


    @Override
    public void move() throws PoobVsZombiesException {
        if (y > 0) {
            poobVsZombies.removeThing(this);
            this.y -= speed;
            poobVsZombies.setThing(x,y,this);
        }
    }

    @Override
    public void attack() {
        Plant plant = (Plant) poobVsZombies.getBoard()[x][y-1];
        plant.takeDamage(damage);
    }


    public boolean attackMode() {
        return poobVsZombies.getBoard()[x][y - 1] instanceof Plant;
    }


    private boolean isValidMovement(int x, int y) {
        return poobVsZombies.isValidMove(x, y);
    }

    @Override
    public void update() throws PoobVsZombiesException {
        if(!isAlive()){
            poobVsZombies.removeThing(this);
            return;
        }
        if(isValidMovement(x,y-1)){
            long currentTime = System.currentTimeMillis();
            System.out.println(currentTime - lastMoveTime);
            if ((currentTime - lastMoveTime) >= COOLDOWN && !attackMode()) {
                move();
                lastMoveTime = currentTime;
                hasWaitedTwoSeconds = false;
            }else if(attackMode()){
                if (!hasWaitedTwoSeconds) {
                    this.lastWaitTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - lastWaitTime;
                    if (elapsedTime >= 2500) {
                        attack();
                        hasWaitedTwoSeconds = true;
                        lastMoveTime = currentTime;
                    }
                }else {
                    long elapsedActionTime = currentTime - lastAttackTime;

                    if (elapsedActionTime >= ATTACK_COOLDOWN) {
                        attack();
                        lastAttackTime = currentTime;
                    }
                }
            }
        }
    }
}