package pl.pwr.galnal.engine;

public class Obstacle extends Agent {
    public Obstacle(int x, int y, Board board){
        super(x, y, board);
    }

    @Override
    public void step(){
        // Agent statyczny, pozostaje w spoczynku
    }
}