package domain;

public interface Thing {
    void update() throws PoobVsZombiesException;
    boolean isAlive();
    void takeDamage(int damage);
}
