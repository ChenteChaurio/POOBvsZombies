package domain;

public class ZombiePlayer extends Player {

    public ZombiePlayer(String name, String type, PoobVsZombies poobVsZombies) {
        super(name, type, poobVsZombies);
    }

    @Override
    void makeScore() {
        score = resource;
        if(type.equals("Zombie")){
            for(int i=0;i<poobVsZombies.getBoard().length;i++){
                for (int j = 0; j < poobVsZombies.getBoard()[i].length; j++) {
                    for(Thing thing : poobVsZombies.getBoard()[i][j]){
                        if (thing instanceof Zombie){
                            Zombie zombie = (Zombie) thing;
                            score += zombie.getCost();
                        }
                    }
                }
            }
        }
    }

    @Override
    void play(int x, int y, Thing thing) throws PoobVsZombiesException {
        if(y == 0) throw new PoobVsZombiesException(PoobVsZombiesException.LAWN_MOVER_SPACE);
        if(y != 9) throw new PoobVsZombiesException(PoobVsZombiesException.PLANT_SPACE);
        if(thing instanceof Zombie zombie){
            poobVsZombies.setThing(x,y,zombie);
        }
    }
}
