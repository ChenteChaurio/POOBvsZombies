package domain;

public class Sun {
    private int x;
    private int y;
    private int value;

    /**
     * The constructor of object Sun
     * @param x the position in x
     * @param y the position in y
     * @param value the value of the sun
     */
    public Sun(int x, int y,int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }


}
