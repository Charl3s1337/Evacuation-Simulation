package pl.pwr.galnal.engine;

import java.util.ArrayList;
import java.util.List;

import pl.pwr.galnal.engine.agents.Agent;
import pl.pwr.galnal.engine.agents.Civilian;
import pl.pwr.galnal.engine.agents.Fire;
import pl.pwr.galnal.engine.agents.Firefighter;

public class Simulation {
    private final Board board;
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
            if(!isRunning){
                break;
            }
            updateBoard();
        }
    }

    public void updateBoard(){
        stepCount++;
        List<Agent> currentAgents = new ArrayList<>(board.getAgents());

        // 1. Strażacy wykonują ruch
        for(Agent agent: currentAgents){
            if(agent instanceof Firefighter && board.getAgents().contains(agent)) {
                agent.step();
            }
        }

        // 2. Cywile wykonują ruch
        for(Agent agent: currentAgents){
            if(agent instanceof Civilian && board.getAgents().contains(agent)) {
                agent.step();
            }
        }

        // 3. Sprawdzenie warunku ewakuacji
        for(Agent agent: new ArrayList<>(board.getAgents())){
            if(agent instanceof Civilian civilian) {
                civilian.checkEvacuation();
            }
        }

        // 4. Ogień próbuje się rozprzestrzenić
        for(Agent agent: currentAgents){
            if(agent instanceof Fire && board.getAgents().contains(agent)) {
                agent.step();
            }
        }

        // 5. Sprawdzenie warunku śmierci
        for(Agent agent: new ArrayList<>(board.getAgents())){
            if(agent instanceof Civilian civilian) {
                civilian.checkDeath();
            }
        }
    }

    public void exportIndicators(){
    }
    public Board getBoard(){
        return board;
    }
}