package domain;

abstract class Player {
    protected String name;
    protected int resource;
    protected int score;
    protected String type;
    protected PoobVsZombies poobVsZombies;
    public Player(String name, String type, PoobVsZombies poobVsZombies) {
        this.name = name;
        this.type = type;
        this.poobVsZombies = poobVsZombies;
    }

    public String getName() {
        return name;
    }

    public void addResource(int resource){
        this.resource += resource;
    }

    public int getResource() {
        return resource;
    }

    public int getScore() {
        return score;
    }

    abstract void makeScore();

    abstract void play(int x, int y, Thing thing) throws PoobVsZombiesException;
}
