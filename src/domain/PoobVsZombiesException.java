package domain;

public class PoobVsZombiesException extends Exception{
    public static final String PLANT_IN_SPACE = "Ya hay una planta en esta casilla en esa posición";
    public static final String NULL_OBJECT = "No se puede establecer un objeto nulo";
    public static final String LAWN_MOVER_SPACE = "En este lugar solo se pueden colocar podadoras";
    public static final String ZOMBIE_SPACE = "En esta casilla solo se pueden colocar zombies";
    public static final String PLANT_SPACE = "En esta casilla solo se pueden colocar plantas";

    public PoobVsZombiesException(String message){
        super(message);
    }
}
