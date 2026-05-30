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
    // public List<Cell> getAvailableNeighbors(int x, int y){}

    public void addAgent(Agent agent){
        if(!agents.contains(agent)){
            agents.add(agent);
        }
    }
    public void removeAgent(Agent agent){
        if(agents.contains(agent)){
            agents.remove(agent);
        }
    }

    public List<Agent> getAgents(){
        return agents;
    }
}
