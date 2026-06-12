package pl.pwr.galnal.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.pwr.galnal.engine.ReportGenerator;
import pl.pwr.galnal.engine.Simulation;
import pl.pwr.galnal.engine.SimulationFactory;

/**
 * Główna klasa aplikacji i główny kontroler w architekturze MVC.
 * <p>
 * Odpowiada za inicjalizację interfejsu graficznego (JavaFX),
 * integrację z silnikiem symulacji (moduł engine) i zarządzanie pętlą czasu.
 * </p>
 */
public class SimulationApp extends Application {

    private Simulation simulation;
    private ControlPanel controlPanel;
    private BoardRender boardRenderer;
    private final SimulationFactory factory = new SimulationFactory();
    private Timeline timeline;
    private StatsPanel statsPanel;

    /**
     * Główna metoda startowa aplikacji w JavaFX.
     * Inicjalizuje wszystkie panele i przypina do nich logikę obliczeniową.
     * @param primaryStage Główne okno aplikacji dostarczane przez framework JavaFx.
     */
    @Override
    public void start(Stage primaryStage) {
        boardRenderer = new BoardRender();

        // Inicjalizacja stopera, który działa w nieskończoność.
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        controlPanel = new ControlPanel(this::generateSimulation, this::startSimulation, this::stopSimulation);
        statsPanel = new StatsPanel();

        updateTimelineSpeed(controlPanel.speedProperty().get());

        // Nasłuchiwanie zmian na suwaku czasu (szybkości) i aktualizacja stopera (zmiana ilości ticków).
        controlPanel.speedProperty().addListener((obs, oldVal, newVal) -> {
            updateTimelineSpeed(newVal.doubleValue());
        });

        // Główny układ okna (BorderPane daje układ lewo,środek,prawo).
        BorderPane root = new BorderPane();

        // Ustawienie wygenerowanej planszy na środku okna.
        root.setCenter(boardRenderer);

        //Dodanie paska przewijania panelu konfiguracyjnego (w przypadku za małego okna).
        ScrollPane scrollPane = new ScrollPane(controlPanel);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: white");
        
        // Ustawienie panelu sterowania (razem z paskiem przewijania) po prawej stronie okna.
        root.setRight(scrollPane);

        // Ustawienie panelu statystyk po lewej stronie okna.
        root.setLeft(statsPanel);

        // Konfiguracja okna - rozmiar, tytuł, rozmiar minimalny (mniej się nie da).
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Symulacja Ewakuacji");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        // Generowanie pierwszej planszy od razu po uruchomieniu programu.
        generateSimulation();
    }

    /**
     * Pobiera parametry z panelu sterowania, tworzy instancję symulacji i wymusza jej pierwsze narysowanie w oknie.
     */
    private void generateSimulation() {
        stopSimulation();
        // Delegacja tworzenia planszy do fabryki w engine.
        simulation = factory.createSimulation(
                controlPanel.getBoardWidth(),
                controlPanel.getBoardHeight(),
                controlPanel.getCivCount(),
                controlPanel.getFireCount(),
                controlPanel.getFirefighterCount(),
                controlPanel.getFireChance(),
                controlPanel.getEvacCount()
        );
        // Czyszczenie panelu statystyk na start symulacji.
        statsPanel.resetStats();
        boardRenderer.draw(simulation.getBoard());
    }
    /**
     * Uruchamia pętlę symulacji (lub wznawia w razie zatrzymania przez Stop).
     */
    private void startSimulation() {
        if (simulation != null) {
            timeline.play();
        }
    }

    /**
     * Wstrzymuje pętlę symulacji
     */
    private void stopSimulation() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Wykonuje pojedynczny krok (tick) symulacji.
     * Metoda jest wywoływana automatycznie przez obiekt Timeline.
     */
    private void performStep() {
        if (simulation != null) {
            // 1. Aktualizacja planszy (obliczenia w engine).
            simulation.updateBoard();
            // 2. Narysowanie zaktualizowanego stanu planszy na ekranie.
            boardRenderer.draw(simulation.getBoard());
            // 3. Sprawdzenie warunków końcowych
            checkEndConditions();

            // 4. Przekazanie danych do panelu statystyk.
            statsPanel.updateStats(simulation.getStepCount(), simulation.getBoard().getDeadCiv(), simulation.getBoard().getSavedCiv(), simulation.getBoard().getExtinguished());
        }
    }

    /**
     * Sprawdza, czy symulacja zgłosiła status zakończenia.
     * Jeśli tak, zatrzymuje pętlę i eksportuje dane do pliku statystyk.
     */
    private void checkEndConditions() {
        if(simulation.isFinished()){
            stopSimulation();
            // Wypisanie powodu zatrzymania.
            System.out.println("Symulacja zatrzymana. Powod: "+simulation.getEndCause());
            //Eksport statystyk do pliku.
            ReportGenerator.exportData(simulation,"raport.csv");
        }
    }

    /**
     * Modyfikuje czas trwania pojedynczego kroku (ile dana klatka jest na ekranie).
     * @param time Czas (w milisekundach), określający czas pomiędzy kolejnymi klatkami.
     */
    private void updateTimelineSpeed(double time){
        // Sprawdzenie czy symulacja nadal trwa.
        boolean isRunning = (timeline.getStatus() == Animation.Status.RUNNING);
        // Zatrzymanie i wyczyszczenie stopera symulacji
        timeline.stop();
        timeline.getKeyFrames().clear();
        // Umieszczenie nowego czasu pomiedzy kolejnymi krokami symulacji
        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(time), e -> performStep()));
        // Jeśli przed zmianą czasu symulacja trwała, to wznawiamy ją.
        if(isRunning){
            timeline.play();
        }
    }
}