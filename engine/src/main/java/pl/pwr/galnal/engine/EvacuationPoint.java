package pl.pwr.galnal.engine;

public class EvacuationPoint extends Agent {
    private int savedCount;

    public EvacuationPoint(int x, int y, Board board){
        super(x, y, board);
        this.savedCount = 0;
    }

    @Override
    public void step(){
        // Agent statyczny
    }

    public void incrementSaved() {
        savedCount++;
    }

    public int getSavedCount() {
        return savedCount;
    }
}