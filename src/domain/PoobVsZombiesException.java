package domain;

public class PoobVsZombiesException extends Exception{
    public static final String OCCUPIED_SPACE = "Ya hay un elemento en esa posición";

    public PoobVsZombiesException(String message){
        super(message);
    }
}
