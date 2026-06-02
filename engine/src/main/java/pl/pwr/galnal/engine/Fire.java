package pl.pwr.galnal.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fire extends Agent {
    private final double spreadProbability;
    private final Random random;

    public Fire(int x, int y, Board board, double spreadProbability){
        super(x, y, board);
        this.spreadProbability = spreadProbability;
        this.random = new Random();
    }

    @Override
    public void step(){
        if (random.nextDouble() < spreadProbability) {
            List<Cell> neighbors = board.getNeighbors(x, y);
            List<Cell> emptyNeighbors = new ArrayList<>();

            for (Cell c : neighbors) {
                if (c.getFire() == null && !(c.getPhysicalEntity() instanceof Obstacle)) {
                    emptyNeighbors.add(c);
                }
            }

            if (!emptyNeighbors.isEmpty()) {
                Cell target = emptyNeighbors.get(random.nextInt(emptyNeighbors.size()));
                Fire newFire = new Fire(target.getX(), target.getY(), board, spreadProbability);
                board.addAgent(newFire);
            }
        }
    }
}