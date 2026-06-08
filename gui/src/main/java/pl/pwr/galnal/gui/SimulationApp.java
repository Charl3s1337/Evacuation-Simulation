package pl.pwr.galnal.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.pwr.galnal.engine.Simulation;
import pl.pwr.galnal.engine.agents.Civilian;
import pl.pwr.galnal.engine.agents.Fire;

public class SimulationApp extends Application {

    private Simulation simulation;
    private ControlPanel controlPanel;
    private BoardRender boardRenderer;
    private final SimulationFactory factory = new SimulationFactory();
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        boardRenderer = new BoardRender();

        timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> performStep()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        controlPanel = new ControlPanel(this::generateSimulation, this::startSimulation, this::stopSimulation);

        BorderPane root = new BorderPane();
        root.setCenter(boardRenderer);
        root.setRight(controlPanel);

        Scene scene = new Scene(root, 1100, 850);
        primaryStage.setTitle("Symulacja Ewakuacji");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        generateSimulation();
    }

    private void generateSimulation() {
        stopSimulation();
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

    private void startSimulation() {
        if (simulation != null) {
            timeline.play();
        }
    }

    private void stopSimulation() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void performStep() {
        if (simulation != null) {
            simulation.updateBoard();
            boardRenderer.draw(simulation.getBoard());
            checkEndConditions();
        }
    }

    private void checkEndConditions() {
        boolean isFirePresent = false;
        boolean isCivilianPresent = false;

        for (Object agent : simulation.getBoard().getAgents()) {
            if (agent instanceof Fire) {
                isFirePresent = true;
            } else if (agent instanceof Civilian) {
                isCivilianPresent = true;
            }
        }

        if (!isFirePresent || !isCivilianPresent) {
            stopSimulation();
            System.out.println("Symulacja zatrzymana. Powód: " +
                    (!isFirePresent ? "brak pożaru." : "brak cywilów."));
        }
    }
}