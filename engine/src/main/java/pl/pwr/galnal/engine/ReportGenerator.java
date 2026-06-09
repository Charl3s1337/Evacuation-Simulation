package pl.pwr.galnal.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import pl.pwr.galnal.engine.agents.Agent;
import pl.pwr.galnal.engine.agents.EvacuationPoint;
import pl.pwr.galnal.engine.agents.Firefighter;

public class ReportGenerator{
    public static void exportData(Simulation simulation, String fileName){
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            Board board = simulation.getBoard();

            writer.println("- RAPORT SYMULACJI -");
            writer.println("Powod zakonczenia:,"+simulation.getEndCause());
            writer.println("Liczba krokow (ticki):,"+simulation.getStepCount());
            writer.println("Zabici cywile:,"+board.getDeadCiv());
            writer.println("Uratowani cywile:,"+board.getSavedCiv());
            writer.println("Liczba ugaszonych pozarow:,"+board.getExtinguished());
            writer.println();

            writer.println("- STATYSTYKI STRAZAKOW -");
            writer.println("ID Strazaka,Liczba ugaszonych pozarow");

            int id = 1;
            for(Agent a : board.getAgents()){
                if(a instanceof Firefighter fight){
                    writer.println("Strazak nr."+ id +","+ fight.getExtinguishedCount());
                    id++;
                }
            }

            writer.println();

            writer.println("- STATYSTYKI PUNKTOW EWAKUACYJNYCH -");
            writer.println("ID Punktu,Liczba ewakuowanych");
            int idPunktu = 1;
            for(Agent p : board.getAgents()){
                if(p instanceof EvacuationPoint point){
                    writer.println("Punkt nr."+ idPunktu +","+point.getSavedCount());
                    idPunktu++;
                }
            }
            System.out.println("Zapisano: Plik " + fileName + " zostal pomyslnie wygenerowany");
        } catch(IOException e){
            System.out.println("Blad: Nie udalo sie zapisac pliku.");
        }
    }
}