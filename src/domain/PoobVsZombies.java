package domain;

import java.util.ArrayList;
import java.util.Iterator;

public class PoobVsZombies {
    public static int width = 10;
    public static int height = 5;
    private ArrayList<Thing>[][] board = new ArrayList[height][width];
    private PlantPlayer player1;
    private PlantPlayer player2;
    private boolean loadGame;
    public ArrayList<Zombie> zombies = new ArrayList<>();
    public ArrayList<Zombie> zombiesToRemove = new ArrayList<>();
    private ArrayList<Zombie> zombiesToMove = new ArrayList<>();
    public ArrayList<Plant> plants = new ArrayList<>();
    public ArrayList<Plant> plantsToRemove = new ArrayList<>();
    private ArrayList<Pea> peas = new ArrayList<>();
    private ArrayList<LawnMover> lawnMovers = new ArrayList<>();

    /**
     * Constructor of the main Game
     */
    public PoobVsZombies() {
        loadGame = true;
        createCompleteMatriz();
    }

    /**
     * Fills the matrix and turns it into a matrix of matrices
     */
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

    /**
     * Set a thing in a position of the board
     * @param x the X position
     * @param y the Y position
     * @param thing the thing to set
     * @throws PoobVsZombiesException
     */
    public void setThing(int x, int y, Thing thing) throws PoobVsZombiesException {
        if (thing == null) throw new PoobVsZombiesException(PoobVsZombiesException.NULL_OBJECT);
        if(containsPlant(x,y)) throw new PoobVsZombiesException(PoobVsZombiesException.PLANT_IN_SPACE);
        board[x][y].add(thing);
        if (thing instanceof Zombie) {
            zombies.add((Zombie) thing);
        } else if (thing instanceof Plant) {
            plants.add((Plant) thing);
        } else if (thing instanceof LawnMover) {
            lawnMovers.add((LawnMover) thing);
        }
    }


    /**
     * Checks if there is a plant in the position
     * @param x the position in x
     * @param y the position in y
     * @return boolean
     */
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


    private void updateLawnMover() throws PoobVsZombiesException {
        Iterator<LawnMover> it = lawnMovers.iterator();
        while(it.hasNext()){
            LawnMover mover = it.next();
            mover.update();
            if(!mover.isAlive()){
                it.remove();
            }
        }
    }

    /**
     * Update all peas that are in the matrix
     */
    private void updatePeas() {
        Iterator<Pea> peaIterator = peas.iterator();
        while (peaIterator.hasNext()) {
            Pea pea = peaIterator.next();
            pea.update();
            if (checkPeaCollision(pea)||!isValidMove(pea.getX(),pea.getY())) {
                peaIterator.remove();
            }
        }
    }

    /**
     * Update all Zombies that are in the matrix
     * @throws PoobVsZombiesException
     */
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


    /**
     * Update all Plants that are in the matrix
     * @throws PoobVsZombiesException
     */
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

    /**
     * Auxiliary method to move zombies from the array without generating exceptions.
     * @param zombie the zombie to move
     */
    public void addZombieToMove(Zombie zombie){
        zombiesToMove.add(zombie);
    }

    /**
     * Auxiliary method to remove zombies from the array without generating exceptions.
     * @param zombie the zombie to remove
     */
    public void addZombieToRemove(Zombie zombie) {
        zombiesToRemove.add(zombie);
    }

    /**
     * Auxiliary method to remove plants from the array without generating exceptions.
     * @param plant the plant to remove
     */
    public void addPlantToRemove(Plant plant) {
        plantsToRemove.add(plant);
    }


    /**
     * Checks the collision between a pea and the possible zombies in the matrix
     * @param pea the pea to be verified
     * @return
     */
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

    /**
     * Auxiliary method moving zombies
     * @throws PoobVsZombiesException
     */
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


    /**
     * Method to remove a thing from the matrix
     * @param x the X position
     * @param y the Y position
     * @param thing the Thing to remove
     * @throws PoobVsZombiesException
     */
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

    /**
     * Auxiliary method to remove things without causing exceptions
     * @throws PoobVsZombiesException
     */
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

    /**
     * Check if the movement is valid
     * @param newX the newX position
     * @param newY the newY position
     * @return boolean
     */
    public boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX < height && newY >= 0 && newY < width;
    }

}
