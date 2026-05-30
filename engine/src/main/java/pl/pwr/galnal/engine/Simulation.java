package pl.pwr.galnal.engine;

public class Simulation {
    private Board board;
    private int stepCount;
    private boolean isRunning;

    public Simulation(Board board){
        this.board = board;
        this.stepCount = 0;
        this.isRunning = false;
    }

    public void run(int steps){
        this.isRunning = true;
        for(int i=0; i<steps; i++){
            if(isRunning==false){
                break;
            }
            updateBoard();
        }
    }

    public void updateBoard(){
        stepCount++;
        for(Agent agent: board.getAgents()){
            agent.step();
        }
    }

    public void exportIndicators(){}
}
