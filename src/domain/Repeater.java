package domain;

import presentation.RepeaterA;

public class Repeater extends Plant {
    private static final int INITIAL_COOLDOWN = 1500;
    private static final int THREEPEATER_COOLDOWN = 1200;
    private static final int GATLING_COOLDOWN = 800;
    private static final int EVOLUTION_TIME_TO_THREEPEATER = 10000;
    private static final int EVOLUTION_TIME_TO_GATLING = 15000;

    private long lastShotTime = 0;
    private long evolutionStartTime = 0;
    private int evolutionStage = 0; // 0: Repeater, 1: Threepeater, 2: Gatling

    //animacion
    private RepeaterA repeaterAnimation;

    public Repeater(int x, int y, PoobVsZombies poobVsZombies) {
        super(x, y, poobVsZombies);
        this.cost = 100;
        this.health = 300;
        this.evolutionStartTime = System.currentTimeMillis();
    }

    //animacion
    public void setRepeaterAnimation(RepeaterA animation) {
        this.repeaterAnimation = animation;
    }

    private boolean modeAttack() {
        for (int j = 0; j < poobVsZombies.getBoard()[x].length; j++) {
            for (Thing thing : poobVsZombies.getBoard()[x][j]) {
                if (thing instanceof Zombie) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void act() {
        long currentTime = System.currentTimeMillis();

        if (evolutionStage == 0 && (currentTime - evolutionStartTime) >= EVOLUTION_TIME_TO_THREEPEATER) {
            evolveToThreepeater();
        } else if (evolutionStage == 1 && (currentTime - evolutionStartTime) >= EVOLUTION_TIME_TO_GATLING) {
            evolveToGatling();
        }

        if (modeAttack() && (currentTime - lastShotTime) >= getCooldown()) {
            Pea pea = new Pea(x, y + 1);
            poobVsZombies.addPea(pea);
            lastShotTime = currentTime;

            // Animación de ataque
            if (repeaterAnimation != null) {
                repeaterAnimation.animateAttack();
            }
        } else {
            // Animación idle
            if (repeaterAnimation != null) {
                repeaterAnimation.animateIdle();
            }
        }
    }

    private void evolveToThreepeater() {
        evolutionStage++;
        evolutionStartTime = System.currentTimeMillis();

        //animation
        if (repeaterAnimation != null) {
            repeaterAnimation.evolveToThreepeater();
        }
    }

    private void evolveToGatling() {
        evolutionStage++;
        evolutionStartTime = System.currentTimeMillis();

        //animation
        if (repeaterAnimation != null) {
            repeaterAnimation.evolveToGatling();
        }
    }

    private int getCooldown() {
        switch (evolutionStage) {
            case 0: return INITIAL_COOLDOWN;
            case 1: return THREEPEATER_COOLDOWN;
            case 2: return GATLING_COOLDOWN;
            default: return INITIAL_COOLDOWN;
        }
    }

    @Override
    public void update() throws PoobVsZombiesException {
        if (!isAlive()) {
            poobVsZombies.addPlantToRemove(this);

            if (repeaterAnimation != null) {
                repeaterAnimation.stopAnimation();
            }
            return;
        }
        act();
    }

}