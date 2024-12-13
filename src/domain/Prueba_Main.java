package domain;

import java.util.ArrayList;

public class Prueba_Main {
    public static void main(String[] args) throws PoobVsZombiesException {
        PoobVsZombies game = new PoobVsZombies(null);
        int elapsedTime = 0;
        //PeaShooter peaShooter = new PeaShooter(0, 2, game);
        //game.setThing(0, 2, peaShooter);
        LawnMover lawnMover = new LawnMover(0, 0, game);
        game.setThing(0, 0, lawnMover);
        NormalZombie normalZombie = new NormalZombie(0, 2, game);
        game.setThing(0, 2, normalZombie);
        NormalZombie normalZombie1 = new NormalZombie(0, 3, game);
        game.setThing(0, 3, normalZombie1);
        NormalZombie normalZombie2 = new NormalZombie(0, 4, game);
        game.setThing(0, 4, normalZombie2);
        NormalZombie normalZombie3 = new NormalZombie(0, 4, game);
        game.setThing(0, 4, normalZombie3);
//        PeaShooter peaShooter2 = new PeaShooter(0, 5, game);
//        game.setThing(0, 5, peaShooter2);

        for (int turn = 0; turn < 500; turn++) {
            System.out.println("Turno: " + turn);
            System.out.println("Estado del juego:");
            for (Zombie zombie : game.zombies) {
                System.out.println("Zombi en (" + zombie.getX() + ", " + zombie.getY() + ") - Salud: " + zombie.getHealth() + " - Vivo: " + zombie.isAlive());
            }

            for (Plant plant : game.plants) {
                System.out.println("Planta en (" + plant.x + ", " + plant.y + ") - Salud: " + plant.health + " - Vivo: " + plant.isAlive());
            }

            for (Pea pea : game.getPeas()) {
                System.out.println("Pea en (" + pea.getX() + ", " + pea.getY() + ")");

            }
            for (LawnMover mover :game.lawnMovers){
                System.out.println("Mover en (" + mover.getX() + ", " + mover.getY() + ")" + mover.isAlive()+" "+mover.modeAtack()) ;
            }
            int row = 0;
            for (int col = 0; col < game.getBoard()[0].length; col++) {
                System.out.print("[");
                ArrayList<Thing> cell = game.getBoard()[row][col];
                if (!cell.isEmpty()) {
                    for (Thing thing : cell) {
                        System.out.print(thing.getClass().getSimpleName() + " ");
                    }
                } else {
                    System.out.print("Empty");
                }
                System.out.print("] ");
            }
            System.out.println();
                System.out.println();
                System.out.println();
                game.updateLawnMover();
                game.updatePlants();
                game.updatePeas();
                game.updateZombies();
                elapsedTime++;

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

