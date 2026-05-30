package pl.pwr.galnal.engine;

public class Cell {
    private int x;
    private int y;
    private Agent physicalEntity;
    private Fire burning;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.physicalEntity = null;
        this.burning = null;
    }

    public boolean isPassable(){
        return this.physicalEntity == null;
    }

    public void setPhysicalEntity(Agent agent){
        this.physicalEntity = agent;
    }

    public void setFire(Fire fire){
        this.burning = fire;
    }
}
