package domain;

import java.util.ArrayList;
import java.util.Iterator;

public class PoobVsZombies {
    public static int width = 10;
    public static int height = 5;
    private ArrayList<Thing>[][] board = new ArrayList[height][width];
    private boolean loadGame;
    public ArrayList<Zombie> zombies = new ArrayList<>();
    public ArrayList<Zombie> zombiesToRemove = new ArrayList<>();
    private ArrayList<Zombie> zombiesToMove = new ArrayList<>();
    public ArrayList<Plant> plants = new ArrayList<>();
    public ArrayList<Plant> plantsToRemove = new ArrayList<>();
    private ArrayList<Pea> peas = new ArrayList<>();

    public PoobVsZombies() {
        loadGame = true;
        createCompleteMatriz();
    }

    private void createCompleteMatriz() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new ArrayList<Thing>();
            }
        }
    }


    public ArrayList<Thing>[][] getBoard() {
        return board;
    }

    public void setThing(int x, int y, Thing thing) throws PoobVsZombiesException {
        if (thing == null) throw new PoobVsZombiesException(PoobVsZombiesException.NULL_OBJECT);
        if(containsPlant(x,y)) throw new PoobVsZombiesException(PoobVsZombiesException.PLANT_IN_SPACE);
        board[x][y].add(thing);
        if (thing instanceof Zombie) {
            zombies.add((Zombie) thing);
        } else if (thing instanceof Plant) {
            plants.add((Plant) thing);
        }
    }


    private boolean containsPlant(int x, int y) {
        for (Thing thing : board[x][y]) {
            if (thing instanceof Plant) {
                return true;
            }
        }
        return false;
    }

    public void addPea(Pea pea) {
        peas.add(pea);
    }

    public ArrayList<Pea> getPeas() {
        return peas;
    }

    public void updatePeas() {
        Iterator<Pea> peaIterator = peas.iterator();
        while (peaIterator.hasNext()) {
            Pea pea = peaIterator.next();
            pea.update();
            if (checkPeaCollision(pea)||!isValidMove(pea.getX(),pea.getY())) {
                peaIterator.remove();
            }
        }
    }

    public void updateZombies() throws PoobVsZombiesException {

        Iterator<Zombie> zombieIterator = zombies.iterator();
        ArrayList<Zombie> zombiesToRemove = new ArrayList<>();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            zombie.update();
            if (!zombie.isAlive()) {
                zombiesToRemove.add(zombie);
            }
        }
        moveZombies();
        zombies.removeAll(zombiesToRemove);
        removeMarkedThings();
    }


    public void updatePlants() throws PoobVsZombiesException {
        Iterator<Plant> plantIterator = plants.iterator();
        ArrayList<Plant> plantsToRemove = new ArrayList<>();
        while (plantIterator.hasNext()) {
            Plant plant = plantIterator.next();
            plant.update();
            if (!plant.isAlive()) {
                plantsToRemove.add(plant);
            }
        }
        plants.removeAll(plantsToRemove);
        removeMarkedThings();
    }

    public void addZombieToMove(Zombie zombie){
        zombiesToMove.add(zombie);
    }

    public void addZombieToRemove(Zombie zombie) {
        zombiesToRemove.add(zombie);  // Marcar el zombie para eliminación
    }

    public void addPlantToRemove(Plant plant) {
        plantsToRemove.add(plant);  // Marcar la planta para eliminación
    }


    private boolean checkPeaCollision(Pea pea) {
        for (int j = 0; j < board[pea.getX()].length; j++) {
            for(Thing things : board[pea.getX()][j]){
                if(things instanceof Zombie){
                    Zombie zombie = (Zombie) things;
                    if (pea.checkCollision(zombie)) {
                        zombie.takeDamage(pea.getDamage());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void moveZombies() throws PoobVsZombiesException {
        for (Zombie zombie : zombiesToMove) {
            int targetX = zombie.getX();
            int targetY = zombie.getY() - 1;
            if (isValidMove(targetX, targetY)) {
                board[targetX][targetY].add(zombie);
                board[zombie.getX()][zombie.getY()].remove(zombie);
                zombie.setX(targetX);
                zombie.setY(targetY);
            }
        }
        zombiesToMove.clear();
    }


    public void removeThing(int x, int y, Thing thing) throws PoobVsZombiesException {
        if (thing == null) throw new PoobVsZombiesException(PoobVsZombiesException.NULL_OBJECT);
        if (thing instanceof Zombie) {
            Iterator<Zombie> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
                Zombie zombie = zombieIterator.next();
                if (zombie.equals(thing)) {
                    zombieIterator.remove();
                    break;
                }
            }
        }
        else if (thing instanceof Plant) {
            Iterator<Plant> plantIterator = plants.iterator();
            while (plantIterator.hasNext()) {
                Plant plant = plantIterator.next();
                if (plant.equals(thing)) {
                    plantIterator.remove();
                    break;
                }
            }
        }
        board[x][y].remove(thing);
    }

    public void removeMarkedThings() throws PoobVsZombiesException {
        for (Zombie zombie : zombiesToRemove) {
            removeThing(zombie.getX(), zombie.getY(), zombie);
        }

        for (Plant plant : plantsToRemove) {
            removeThing(plant.getX(), plant.getY(), plant);
        }
        zombiesToRemove.clear();
        plantsToRemove.clear();
    }

    public boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX < height && newY >= 0 && newY < width;
    }

}
