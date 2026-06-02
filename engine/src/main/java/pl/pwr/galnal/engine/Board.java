package pl.pwr.galnal.engine;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int width;
    private final int height;
    private final Cell[][] grid;
    private final List<Agent> agents;

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
        Cell top = getCell(x,y-1);
        Cell right = getCell(x+1,y);
        Cell bottom = getCell(x,y+1);
        Cell left = getCell(x-1,y);  
        
        if(top != null){
            neighbors.add(top);
        }
        if(right != null){
            neighbors.add(right);
        }
        if(bottom != null){
            neighbors.add(bottom);
        }
        if(left != null){
            neighbors.add(left);
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
            if(c != null) {
                if(agent instanceof Fire fire) {
                    c.setFire(fire);
                } else if(agent instanceof EvacuationPoint evacuationPoint){
                    c.setEvacuationPoint(evacuationPoint);
                } else{
                    c.setPhysicalEntity(agent);
                }
            }
        }
    }

    public void removeAgent(Agent agent){
        if(agents.contains(agent)){
            agents.remove(agent);
            Cell c = getCell(agent.getX(), agent.getY());
            if(c != null) {
                if (agent instanceof Fire) {
                    c.setFire(null);
                } else if(agent instanceof EvacuationPoint){
                    c.setEvacuationPoint(null);
                } else if (c.getPhysicalEntity() == agent) {
                    c.setPhysicalEntity(null);
                }
            }
        }
    }

    public List<Agent> getAgents(){
        return agents;
    }
}