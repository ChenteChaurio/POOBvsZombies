package domain;

import java.util.ArrayList;

public class PoobVsZombies {
    public static int width = 10;
    public static int height = 5;
    private Thing[][] board = new Thing[height][width];
    private boolean loadGame;
    public ArrayList<Zombie> zombies = new ArrayList<>();
    public ArrayList<Plant> plants = new ArrayList<>();
    private ArrayList<Pea> peas = new ArrayList<>();
    private int elapsedTime = 0;

    public PoobVsZombies() {
        loadGame = true;
    }

    public Thing[][] getBoard() {
        return board;
    }

    public void setThing(int x, int y, Thing thing) throws PoobVsZombiesException {
        board[x][y] = thing;
        if(thing instanceof Zombie) {
            Zombie z = (Zombie) thing;
            zombies.add(z);
        }else if(thing instanceof Plant) {
            Plant p = (Plant) thing;
            plants.add(p);
        }
    }

    public void addPea(Pea pea) {
        peas.add(pea);
    }

    public ArrayList<Pea> getPeas() {
        return peas;
    }

    public void updatePeas() {
        for (Pea pea : peas) {
            pea.update();
            if(checkPeaCollision(pea)){
                break;
            };
        }
    }

    public void updateZombies() throws PoobVsZombiesException {
        for (Zombie zombie : zombies) {
            zombie.update();
            if(!zombie.isAlive()){
                break;
            }
        }
    }

    public void updatePlants() throws PoobVsZombiesException {
        for (Plant plant : plants) {
            plant.update();
            if(!plant.isAlive()){
                break;
            }
        }
    }

    private boolean checkPeaCollision(Pea pea) {
        for (int j = 0; j < board[pea.getX()].length; j++) {
            if (board[pea.getX()][j] instanceof Zombie) {
                Zombie zombie = (Zombie) board[pea.getX()][j];
                if (pea.checkCollision(zombie)) {
                    zombie.takeDamage(pea.getDamage());
                    peas.remove(pea);
                    return true;
                }
            }
        }
        return false;
    }

    public void removeThing(Thing thing) throws PoobVsZombiesException {
        if (thing instanceof Zombie) {
            Zombie zombie = (Zombie) thing;
            setThing(zombie.getX(), zombie.getY(), null);
            zombies.remove(zombie);
        }else if (thing instanceof Plant) {
            Plant plant = (Plant) thing;
            setThing(plant.getX(), plant.getY(), null);
            plants.remove(plant);
        }
    }

    public boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX < height && newY >= 0 && newY < width;
    }

}
