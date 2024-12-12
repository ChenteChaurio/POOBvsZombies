package domain;

public class PlantPlayer extends Player {
    public PlantPlayer(String name, String type, PoobVsZombies poobVsZombies) {
        super(name,type,poobVsZombies);

    }


    private void createScore(){
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
    void makeScore() {
        score = resource;
        for(int i=0;i<poobVsZombies.getBoard().length;i++){
            for (int j = 0; j < poobVsZombies.getBoard()[i].length; j++) {
                for(Thing thing : poobVsZombies.getBoard()[i][j]){
                    if (thing instanceof Plant){
                        Plant plant = (Plant) thing;
                        score += plant.getCost();
                    }
                }
            }
        }
        score = score*3/2;
    }

    @Override
    void play(int x,int y,Thing thing) throws PoobVsZombiesException {
        if(x == 0) throw new PoobVsZombiesException(PoobVsZombiesException.LAWN_MOVER_SPACE);
        if(x == 9) throw new PoobVsZombiesException(PoobVsZombiesException.ZOMBIE_SPACE);
        if(thing instanceof Plant){
            Plant plant = (Plant) thing;
            poobVsZombies.setThing(x,y,thing);
        }
    }
}
