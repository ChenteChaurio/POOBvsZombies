package domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PoobVsZombiesTest {
    private PoobVsZombies game;

    @Before
    public void setUp() throws PoobVsZombiesException {
        game = new PoobVsZombies(100); // Inicializa el juego con un tiempo de 100
    }

    @Test
    public void testCreateCompleteMatriz() {
        assertNotNull(game.getBoard());
        assertEquals(PoobVsZombies.height, game.getBoard().length);
        assertEquals(PoobVsZombies.width, game.getBoard()[0].length);
    }

    @Test
    public void testSetThing() throws PoobVsZombiesException {
        Zombie zombie = new NormalZombie(1, 1, game);
        game.setThing(1, 1, zombie);
        assertTrue(game.getBoard()[1][1].contains(zombie));
        assertTrue(game.zombies.contains(zombie));
    }

    @Test(expected = PoobVsZombiesException.class)
    public void testSetThingWithNull() throws PoobVsZombiesException {
        game.setThing(1, 1, null);
    }

    @Test(expected = PoobVsZombiesException.class)
    public void testSetThingWithPlantInSpace() throws PoobVsZombiesException {
        Plant plant = new Sunflower(1, 1, game);
        game.setThing(1, 1, plant);
        Zombie zombie = new NormalZombie(1, 1, game);
        game.setThing(1, 1, zombie);
    }

    @Test
    public void testIsValidMove() {
        assertTrue(game.isValidMove(0, 0));
        assertFalse(game.isValidMove(-1, 0));
        assertFalse(game.isValidMove(5, 0));
        assertFalse(game.isValidMove(0, 10));
    }

    @Test
    public void testUpdateZombies() throws PoobVsZombiesException {
        Zombie zombie = new NormalZombie(1, 1, game);
        game.setThing(1, 1, zombie);
        zombie.takeDamage(100);
        game.updateZombies();
        assertFalse(game.zombies.contains(zombie));
    }

    @Test
    public void testRemoveThing() throws PoobVsZombiesException {
        Zombie zombie = new NormalZombie(1, 1, game);
        game.setThing(1, 1, zombie);
        game.removeThing(1, 1, zombie);
        assertFalse(game.getBoard()[1][1].contains(zombie));
        assertFalse(game.zombies.contains(zombie));
    }

    @Test
    public void testSunflowerAct() throws PoobVsZombiesException {
        Sunflower sunflower = new Sunflower(1, 1, game);
        game.setThing(1, 1, sunflower);
        sunflower.act();
        assertTrue(sunflower.isAlive());
    }

    @Test
    public void testGetPeas() {
        Pea pea = new Pea(2, 2);
        game.addPea(pea);
        ArrayList<Pea> peas = game.getPeas();
        assertEquals(1, peas.size());
        assertEquals(pea, peas.get(0));
    }

    @Test
    public void testUpdatePlants() throws PoobVsZombiesException {
        Plant plant = new Sunflower(1, 1, game);
        game.setThing(1, 1, plant);
        game.updatePlants();
        assertTrue(plant.isAlive());
    }

    @Test
    public void testCheckPeaCollision() throws PoobVsZombiesException {
        Zombie zombie = new NormalZombie(2, 3, game);
        game.setThing(2, 3, zombie);
        Pea pea = new Pea(2, 3);
        game.addPea(pea);
        assertTrue(game.checkPeaCollision(pea));
        assertEquals(80, zombie.getHealth());
    }

    @Test
    public void testMoveLawnMover() throws PoobVsZombiesException {
        LawnMover lawnMover = new LawnMover(4, 0, game);
        game.setThing(4, 0, lawnMover);
        game.addLawnMoverToMove(lawnMover);
        game.moveLawnMovers();

        assertTrue(game.getBoard()[4][1].contains(lawnMover));
        assertFalse(game.getBoard()[4][0].contains(lawnMover));
    }

    @Test
    public void testRemoveThingForPlant() throws PoobVsZombiesException {
        Plant plant = new Sunflower(1, 1, game);
        game.setThing(1, 1, plant);
        game.removeThing(1, 1, plant);

        assertFalse(game.getBoard()[1][1].contains(plant));
        assertFalse(game.plants.contains(plant));
    }

    @Test
    public void testRemoveThingForLawnMover() throws PoobVsZombiesException {
        LawnMover lawnMover = new LawnMover(2, 2, game);
        game.setThing(2, 2, lawnMover);
        game.removeThing(2, 2, lawnMover);

        assertFalse(game.getBoard()[2][2].contains(lawnMover));
        assertFalse(game.lawnMovers.contains(lawnMover));
    }


    @Test
    public void testUpdatePeas() throws PoobVsZombiesException {
        Zombie zombie = new NormalZombie(2, 3, game);
        game.setThing(2, 3, zombie);
        Pea pea = new Pea(2, 2);
        game.addPea(pea);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        game.updatePeas();
        assertEquals(2, pea.getX());
        assertEquals(3, pea.getY());
        assertEquals(80, zombie.getHealth());
        pea.move();
        pea.move();
        game.updatePeas();
        assertFalse(game.getPeas().contains(pea));
    }

    @Test
    public void testMoveZombies() throws PoobVsZombiesException {
        Zombie zombie = new NormalZombie(2, 1, game);
        game.setThing(2, 1, zombie);
        game.addZombieToMove(zombie);
        game.moveZombies();
        assertTrue(game.getBoard()[2][0].contains(zombie));
        assertFalse(game.getBoard()[2][1].contains(zombie));
        assertEquals(2, zombie.getX());
        assertEquals(0, zombie.getY());
    }

    @Test
    public void testRemoveMarkedPlant() throws PoobVsZombiesException {
        // Paso 1: Inicializar el juego
        PoobVsZombies game = new PoobVsZombies(100);

        // Paso 2: Agregar una planta al tablero
        Plant plant = new PeaShooter(1, 1, game);
        game.setThing(1, 1, plant);

        // Verificar que la planta se haya agregado correctamente
        assertTrue(game.getBoard()[1][1].contains(plant)); // La planta debe estar en el tablero
        assertTrue(game.plants.contains(plant)); // La planta debe estar en la lista de plantas

        // Paso 3: Marcar la planta para eliminación
        game.addPlantToRemove(plant);

        // Simular un ciclo de actualización donde se llama a removeMarkedThings
        // Llamar a removeMarkedThings en un contexto donde no se esté iterando sobre las listas
        game.removeMarkedThings(); // Llamar aquí para eliminar las plantas marcadas

        // Paso 5: Verificar que la planta haya sido eliminada
        assertFalse(game.getBoard()[1][1].contains(plant)); // La planta no debe estar en el tablero
        assertFalse(game.plants.contains(plant)); // La planta no debe estar en la lista de plantas
    }

    @Test
    public void testCheckLoseGameZombieReachesBase() throws PoobVsZombiesException {
        // Paso 1: Inicializar el juego
        PoobVsZombies game = new PoobVsZombies(100);

        // Paso 2: Agregar un Zombie en la fila 0
        Zombie zombie = new NormalZombie(0, 1, game); // Suponiendo que NormalZombie es una subclase de Zombie
        game.setThing(0, 1, zombie);

        // Paso 3: Ejecutar la verificación de pérdida
        game.checkLoseGame();

        // Paso 4: Verificar que el juego se haya detenido
        assertFalse(game.loadGame); // El juego debe estar marcado como no cargado
    }
}