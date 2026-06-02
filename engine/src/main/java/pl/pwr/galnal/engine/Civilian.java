package pl.pwr.galnal.engine;

import java.util.List;
import java.util.Random;

public class Civilian extends Agent {
    private String state; // "escaping", "evacuated", "dead"
    private int evacuationTime;
    private Random random;

    public Civilian(int x, int y, Board board){
        super(x, y, board);
        this.state = "escaping";
        this.evacuationTime = 0;
        this.random = new Random();
    }

    @Override
    public void step(){
        if (!state.equals("escaping")) return;

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

    public void checkEvacuation() {
        if (!state.equals("escaping")) return;

        // Sprawdzamy, czy na naszej obecnej pozycji istnieje zarejestrowany EvacuationPoint
        for (Agent a : board.getAgents()) {
            if (a instanceof EvacuationPoint && a.getX() == this.x && a.getY() == this.y) {
                this.state = "evacuated";
                ((EvacuationPoint) a).incrementSaved();
                board.removeAgent(this);
                break;
            }
        }
    }

    public void checkDeath() {
        if (!state.equals("escaping")) return;
        Cell currentCell = board.getCell(x, y);
        if (currentCell != null && currentCell.getFire() != null) {
            this.state = "dead";
            board.removeAgent(this);
        }
    }

    public String getState() { return state; }
}