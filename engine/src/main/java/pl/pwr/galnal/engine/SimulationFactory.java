package pl.pwr.galnal.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.pwr.galnal.engine.agents.Civilian;
import pl.pwr.galnal.engine.agents.EvacuationPoint;
import pl.pwr.galnal.engine.agents.Fire;
import pl.pwr.galnal.engine.agents.Firefighter;
import pl.pwr.galnal.engine.agents.Obstacle;

public class SimulationFactory {
    private final Random random = new Random();

    public Simulation createSimulation(int width, int height, int civs, int fires, int firefighters, double fireChance, int evacs) {
        Board board = new Board(width, height);

        spawnEvacuationPoints(board, evacs);
        spawnRandomAgents(board, Obstacle.class, (width * height) / 10);
        spawnRandomAgents(board, Civilian.class, civs);
        spawnRandomFires(board, fires, fireChance);
        spawnRandomAgents(board, Firefighter.class, firefighters);

        return new Simulation(board);
    }

    private void spawnEvacuationPoints(Board board, int count) {
        List<int[]> edges = new ArrayList<>();
        int w = board.getWidth(), h = board.getHeight();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (x == 0 || x == w - 1 || y == 0 || y == h - 1) edges.add(new int[]{x, y});
            }
        }
        Collections.shuffle(edges, random);
        int actual = Math.min(count, edges.size());
        for (int i = 0; i < actual; i++) {
            board.addAgent(new EvacuationPoint(edges.get(i)[0], edges.get(i)[1], board));
        }
    }

    private void spawnRandomAgents(Board board, Class<?> type, int count) {
        int placed = 0, attempts = 0;
        while (placed < count && attempts < 1000) {
            int rx = random.nextInt(board.getWidth());
            int ry = random.nextInt(board.getHeight());
            Cell cell = board.getCell(rx, ry);

            if (cell.isPassable() && cell.getFire() == null && cell.getEvacuationPoint() == null) {
                if (type == Civilian.class) board.addAgent(new Civilian(rx, ry, board));
                else if (type == Firefighter.class) board.addAgent(new Firefighter(rx, ry, board));
                else if (type == Obstacle.class) board.addAgent(new Obstacle(rx, ry, board));
                placed++;
            }
            attempts++;
        }
    }

    private void spawnRandomFires(Board board, int count, double chance) {
        int placed = 0, attempts = 0;
        while (placed < count && attempts < 1000) {
            int rx = random.nextInt(board.getWidth());
            int ry = random.nextInt(board.getHeight());
            Cell cell = board.getCell(rx, ry);
            if (cell.isPassable() && cell.getFire() == null && cell.getEvacuationPoint() == null) {
                board.addAgent(new Fire(rx, ry, board, chance));
                placed++;
            }
            attempts++;
        }
    }
}