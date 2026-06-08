package pl.pwr.galnal.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.pwr.galnal.engine.Simulation;

public class SimulationApp extends Application {

    private Simulation simulation;
    private ControlPanel controlPanel;
    private BoardRender boardRenderer;
    private final SimulationFactory factory = new SimulationFactory();

    @Override
    public void start(Stage primaryStage) {
        boardRenderer = new BoardRender();
        
        // Przekazujemy do panelu instrukcje, co ma się stać po kliknięciu przycisków
        controlPanel = new ControlPanel(this::generateSimulation, this::performStep);

        BorderPane root = new BorderPane();
        root.setCenter(boardRenderer);
        root.setRight(controlPanel);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Symulacja Ewakuacji - Architektura SRP");
        primaryStage.setScene(scene);
        primaryStage.show();

        generateSimulation(); // Inicjalne wygenerowanie
    }

    private void generateSimulation() {
        simulation = factory.createSimulation(
                controlPanel.getBoardWidth(),
                controlPanel.getBoardHeight(),
                controlPanel.getCivCount(),
                controlPanel.getFireCount(),
                controlPanel.getFirefighterCount(),
                controlPanel.getFireChance(),
                controlPanel.getEvacCount()
        );
        boardRenderer.draw(simulation.getBoard());
    }

    private void performStep() {
        if (simulation != null) {
            simulation.updateBoard();
            boardRenderer.draw(simulation.getBoard());
        }
    }
}