package pl.pwr.galnal.engine;

public class Cell {
    private final int x;
    private final int y;
    private Agent physicalEntity;
    private EvacuationPoint evacuationPoint;
    private Fire burning;

    // Konstruktor - domyślnie komórka pusta
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.physicalEntity = null;
        this.evacuationPoint = null;
        this.burning = null;
    }

    public boolean isPassable(){
        return this.physicalEntity == null;
    }

    public void setPhysicalEntity(Agent agent){
        this.physicalEntity = agent;
    }

    public Agent getPhysicalEntity() {
        return physicalEntity;
    }
    public void setEvacuationPoint(EvacuationPoint point){
        this.evacuationPoint = point;
    }

    public EvacuationPoint getEvacuationPoint(){
        return evacuationPoint;
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