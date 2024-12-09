package domain;

public class Prueba_Main {
    public static void main(String[] args) throws PoobVsZombiesException {
        PoobVsZombies game = new PoobVsZombies();
        int elapsedTime = 0;
        //PeaShooter peaShooter = new PeaShooter(0, 2, game);
        //game.setThing(0, 2, peaShooter);
        //PeaShooter peaShooter1 = new PeaShooter(0, 1, game);
        //game.setThing(0, 1, peaShooter1);
        PeaShooter peaShooter2 = new PeaShooter(0, 3, game);
        game.setThing(0, 3, peaShooter2);
        NormalZombie normalZombie = new NormalZombie(0, 5, game);
        game.setThing(0, 5,normalZombie);

        for (int turn = 0; turn < 200; turn++) {
            System.out.println("Turno: " + turn);
            System.out.println("Estado del juego:");
            for (Zombie zombie : game.zombies) {
                System.out.println("Zombi en (" + zombie.getX() + ", " + zombie.getY() + ") - Salud: " + zombie.getHealth() + " - Vivo: " + zombie.isAlive());
            }

            for (Plant plant : game.plants) {
                System.out.println("Planta en (" + plant.x + ", " + plant.y + ") - Salud: " + plant.health + " - Vivo: " + plant.isAlive());
            }

            for (Pea pea : game.getPeas()){
                System.out.println("Pea en (" + pea.getX() + ", " + pea.getY() + ")");

            }
            System.out.println();
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
