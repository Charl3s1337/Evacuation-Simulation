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
        return this.physicalEntity == null || this.physicalEntity instanceof EvacuationPoint;
    }

    public void setPhysicalEntity(Agent agent){
        this.physicalEntity = agent;
    }

    public Agent getPhysicalEntity() {
        return physicalEntity;
    }

    public void setFire(Fire fire){
        this.burning = fire;
    }

    public Fire getFire() {
        return burning;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}