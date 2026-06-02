package pl.pwr.galnal.engine;

import java.util.List;
import java.util.Random;

public class Firefighter extends Agent {
    private int extinguishedCount;
    private Random random;

    public Firefighter(int x, int y, Board board){
        super(x, y, board);
        this.extinguishedCount = 0;
        this.random = new Random();
    }

    @Override
    public void step(){
        List<Cell> neighbors = board.getNeighbors(x, y);
        boolean extinguished = false;

        for (Cell neighbor : neighbors) {
            if (neighbor.getFire() != null) {
                board.removeAgent(neighbor.getFire());
                extinguishedCount++;
                extinguished = true;
                break;
            }
        }

        if (!extinguished) {
            List<Cell> available = board.getAvailableNeighbors(x, y);
            if (!available.isEmpty()) {
                Cell currentCell = board.getCell(x, y);
                Cell nextCell = available.get(random.nextInt(available.size()));

                if (currentCell.getPhysicalEntity() == this) {
                    currentCell.setPhysicalEntity(null);
                    board.restoreStaticEntity(currentCell);
                }

                this.x = nextCell.getX();
                this.y = nextCell.getY();
                nextCell.setPhysicalEntity(this);
            }
        }
    }
}