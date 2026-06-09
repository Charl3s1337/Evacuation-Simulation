package pl.pwr.galnal.engine.agents;

import java.util.List;
import java.util.Random;

import pl.pwr.galnal.engine.Board;
import pl.pwr.galnal.engine.Cell;

public class Firefighter extends Agent {
    private int extinguishedCount;
    private final Random random;

    public Firefighter(int x, int y, Board board){
        super(x, y, board);
        this.extinguishedCount = 0;
        this.random = new Random();
    }

    @Override
    public void step(){
        Cell currentCell = board.getCell(x, y);
        if(currentCell != null && currentCell.getFire() != null){
            board.removeAgent(currentCell.getFire());
            extinguishedCount++;
            board.incExtinguished();
            return;
        }

        List<Cell> neighbors = board.getNeighbors(x, y);
        boolean extinguished = false;

        for (Cell neighbor : neighbors) {
            if (neighbor.getFire() != null) {
                board.removeAgent(neighbor.getFire());
                extinguishedCount++;
                board.incExtinguished();
                extinguished = true;
                break;
            }
        }

        if (!extinguished) {
            List<Cell> available = board.getAvailableNeighbors(x, y);
            if (!available.isEmpty()) {
                Cell nextCell = available.get(random.nextInt(available.size()));

                if (currentCell != null && currentCell.getPhysicalEntity() == this) {
                    currentCell.setPhysicalEntity(null);
                }

                this.x = nextCell.getX();
                this.y = nextCell.getY();
                nextCell.setPhysicalEntity(this);
            }
        }
    }
    public int getExtinguishedCount(){
        return extinguishedCount;
    }
}