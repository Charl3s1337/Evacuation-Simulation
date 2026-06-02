package pl.pwr.galnal.engine;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int width;
    private int height;
    private Cell[][] grid;
    private List<Agent> agents;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        this.agents = new ArrayList<>();

        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                grid[i][j] = new Cell(i,j);
            }
        }
    }

    public Cell getCell(int x, int y){
        if(x>=0 && x<width && y>=0 && y<height){
            return grid[x][y];
        }
        return null;
    }

    public List<Cell> getNeighbors(int x, int y) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] d : dirs) {
            Cell c = getCell(x + d[0], y + d[1]);
            if (c != null) neighbors.add(c);
        }
        return neighbors;
    }

    public List<Cell> getAvailableNeighbors(int x, int y){
        List<Cell> available = new ArrayList<>();
        for (Cell c : getNeighbors(x, y)) {
            if (c.isPassable()) {
                available.add(c);
            }
        }
        return available;
    }

    public void addAgent(Agent agent){
        if(!agents.contains(agent)){
            agents.add(agent);
            Cell c = getCell(agent.getX(), agent.getY());
            if (c != null) {
                if (agent instanceof Fire) {
                    c.setFire((Fire) agent);
                } else {
                    c.setPhysicalEntity(agent);
                }
            }
        }
    }

    public void removeAgent(Agent agent){
        if(agents.contains(agent)){
            agents.remove(agent);
            Cell c = getCell(agent.getX(), agent.getY());
            if (c != null) {
                if (agent instanceof Fire) {
                    c.setFire(null);
                } else if (c.getPhysicalEntity() == agent) {
                    c.setPhysicalEntity(null);
                    restoreStaticEntity(c);
                }
            }
        }
    }

    public void restoreStaticEntity(Cell cell) {
        for (Agent a : agents) {
            if (a instanceof EvacuationPoint && a.getX() == cell.getX() && a.getY() == cell.getY()) {
                cell.setPhysicalEntity(a);
                return;
            }
        }
    }

    public List<Agent> getAgents(){
        return agents;
    }
}