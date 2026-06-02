package pl.pwr.galnal.engine;

public abstract class Agent {
    protected int x;
    protected int y;
    protected Board board;

    public Agent(int x, int y, Board board){
        this.x = x;
        this.y = y;
        this.board = board;
    }

    public abstract void step();

    public int getX() { return x; }
    public int getY() { return y; }
}