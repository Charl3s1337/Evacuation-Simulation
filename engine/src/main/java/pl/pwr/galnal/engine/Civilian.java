package pl.pwr.galnal.engine;

import java.util.List;
import java.util.Random;

public class Civilian extends Agent {
    private CivilianState state;
    private int evacuationTime;
    private final Random random;

    public Civilian(int x, int y, Board board){
        super(x, y, board);
        this.state = CivilianState.EVACUATING;
        this.evacuationTime = 0;
        this.random = new Random();
    }

    @Override
    public void step(){
        if (state != CivilianState.EVACUATING) return;

        List<Cell> available = board.getAvailableNeighbors(x, y);
        if (!available.isEmpty()) {
            Cell currentCell = board.getCell(x, y);
            Cell nextCell = available.get(random.nextInt(available.size()));

            if (currentCell.getPhysicalEntity() == this) {
                currentCell.setPhysicalEntity(null);
            }

            this.x = nextCell.getX();
            this.y = nextCell.getY();
            nextCell.setPhysicalEntity(this);
            this.evacuationTime++;
        }
    }

    public void checkEvacuation() {
        if (state != CivilianState.EVACUATING) return;

        Cell currentCell = board.getCell(x, y);
        if(currentCell != null && currentCell.getEvacuationPoint() != null){
            this.state = CivilianState.EVACUATED;
            currentCell.getEvacuationPoint().incrementSaved();
            board.removeAgent(this);
        }
    }

    public void checkDeath() {
        if (state != CivilianState.EVACUATING) return;
        Cell currentCell = board.getCell(x, y);
        if (currentCell != null && currentCell.getFire() != null) {
            this.state = CivilianState.DEAD;
            board.removeAgent(this);
        }
    }

    public CivilianState getState() { return state; }
}