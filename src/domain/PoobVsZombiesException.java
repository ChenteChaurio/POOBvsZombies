package domain;

public class PoobVsZombiesException extends Exception{
    public static final String PLANT_IN_SPACE = "Ya hay una planta en esta casilla en esa posición";
    public static final String NULL_OBJECT = "No se puede establecer un objeto nulo";

    public PoobVsZombiesException(String message){
        super(message);
    }
}
