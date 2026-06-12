package pl.pwr.galnal.gui;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Boczny panel sterowania interfejsu
 * <p>
 * Klasa dziedziczy po VBox i zawiera wszystkie suwaki konfiguracyjne
 * oraz przyciski używane do zarządzania parametrami początkowymi
 * oraz przyciski sterowania czasem symulacji.
 * </p>
 */
public class ControlPanel extends VBox {

    private final Slider widthSlider;
    private final Slider heightSlider;
    private final Slider civSlider;
    private final Slider fireSlider;
    private final Slider firefighterSlider;
    private final Slider fireChanceSlider;
    private final Slider evacPointSlider;
    private final Slider speedSlider;

    /**
     * Konstruktor tworzący i układający elementy panelu sterowania.
     * @param onGenerate Akcja wywoływana po wciśnięciu przycisku "Generuj nową planszę".
     * @param onStart Akcja wywoływana po wciśnięciu przycisku "Start".
     * @param onStop Akcja wywoływana po wciśnięciu przycisku "Stop".
     */
    public ControlPanel(Runnable onGenerate, Runnable onStart, Runnable onStop) {

        // Style panelu opisywane podobnie jak w CSS
        setSpacing(3);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-width: 0 0 0 1;");
        setPrefWidth(250);

        // Wywoływanie metody createSlider, aby utworzyć każdy z suwaków
        widthSlider = createSlider(5, 50, 15, true, 5);
        heightSlider = createSlider(5, 50, 15, true, 5);
        civSlider = createSlider(0, 100, 10, true, 10);
        fireSlider = createSlider(0, 30, 2, true, 10);
        firefighterSlider = createSlider(0, 20, 2, true, 10);
        fireChanceSlider = createSlider(0.0, 1.0, 0.5, false, 0.2);
        evacPointSlider = createSlider(1, 20, 2, true, 10);
        speedSlider = createSlider(50,1000,300,true,50);

        // Konstruktor przycisku generowania i ustawianie jego styli
        Button generateBtn = new Button("Generuj nową planszę");
        generateBtn.setMaxWidth(Double.MAX_VALUE);
        generateBtn.setStyle("-fx-font-weight: bold; -fx-base: #e0e0e0;");
        generateBtn.setOnAction(e -> onGenerate.run());

        // Konstruktor przycisku Start i ustawianie jego styli
        Button startButton = new Button("Start");
        startButton.setMaxWidth(Double.MAX_VALUE);
        startButton.setMinHeight(30); 
        startButton.setStyle("-fx-base: #a4c639; -fx-font-weight: bold; -fx-font-size: 14px;"); 
        startButton.setOnAction(e -> onStart.run());

        // Konstruktor przycisku Stop i ustawianie jego styli
        Button stopButton = new Button("Stop");
        stopButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMinHeight(30); 
        stopButton.setStyle("-fx-base: #ff6b6b; -fx-font-weight: bold; -fx-font-size: 14px;");
        stopButton.setOnAction(e -> onStop.run());

        // Grupowanie przycisków Start i Stop w jednym rzędzie z wymuszeniem równomiernego rozciągania się.
        HBox playbackControls = new HBox(10, startButton, stopButton);
        
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);

        // Umieszczenie etykiet i suwaków w panelu
        getChildren().addAll(
                new Label("SZYBKOŚĆ SYMULACJI"),
                buildSliderBox("Tick",speedSlider, true),
                new Label("WYMIARY PLANSZY"),
                buildSliderBox("Szerokość", widthSlider, true),
                buildSliderBox("Wysokość", heightSlider, true),
                new Label("LICZBA AGENTÓW"),
                buildSliderBox("Cywile", civSlider, true),
                buildSliderBox("Pożary", fireSlider, true),
                buildSliderBox("Strażacy", firefighterSlider, true),
                buildSliderBox("Wyjścia ewakuacyjne", evacPointSlider, true),
                new Label("WŁASNOŚCI POŻARU"),
                buildSliderBox("Szansa rozprzestrzenienia", fireChanceSlider, false),
                generateBtn,
                playbackControls
        );
    }

    /**
     * Metoda pomocniczna, tworząca ustandaryzowany suwak.
     * @param min Minimalna wartość suwaka.
     * @param max Maksymalna wartość suwaka.
     * @param defaultVal Początkowa (domyślna) wartość suwaka.
     * @param isInteger Czy suwak ma przechodzić tylko po liczbach całkowitych
     * @param tickUnit Wielkość skoku widoczna na podziałce
     * @return Skonfigurowany obiekt Slider.
     */
    private Slider createSlider(double min, double max, double defaultVal, boolean isInteger, double tickUnit) {
        Slider slider = new Slider(min, max, defaultVal);
        slider.setMajorTickUnit(tickUnit);
        
        // Poprawne ustawianie wartości slidera nieważne od pozycji
        if (isInteger) {
            slider.setMinorTickCount((int) tickUnit - 1); 
        } else {
            slider.setMinorTickCount(4); 
        }
        
        slider.setSnapToTicks(isInteger);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        return slider;
    }

    /**
     * Metoda, która grupuje suwak i jego etykietę w jeden kontener.
     * Etykieta automatycznie odświeża się podczas przesuwania suwaka.
     * @param name Nazwa wyświetlana nad suwakiem
     * @param slider Suwak, który ma być połączony z etykietą
     * @param isInteger Czy formatować wartość jako int czy double.
     * @return Kontener VBox z etykietą i suwakiem
     */
    private VBox buildSliderBox(String name, Slider slider, boolean isInteger) {
        Label label = new Label();
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            label.setText(name + ": " + (isInteger ? newVal.intValue() : String.format("%.2f", newVal.doubleValue())));
        });
        label.setText(name + ": " + (isInteger ? (int)slider.getValue() : String.format("%.2f", slider.getValue())));
        VBox box = new VBox(2, label, slider);
        box.setPadding(new Insets(0, 0, 2, 0));
        return box;
    }

    /**
     * @return Wybrana przez użytkownika szerokość planszy.
     */
    public int getBoardWidth() {
         return (int) widthSlider.getValue(); 
    }

    /**
     * @return Wybrana przez użytkownika wysokość planszy.
     */        
    public int getBoardHeight() { 
        return (int) heightSlider.getValue(); 
    }

    /**
     * @return Wybrana przez użytkownika początkowa liczba cywilów.
     */    
    public int getCivCount() { 
        return (int) civSlider.getValue(); 
    }

    /**
     * @return Wybrana przez użytkownika początkowa liczba ognisk pożaru.
     */    
    public int getFireCount() { 
        return (int) fireSlider.getValue(); 
    }

    /**
     * @return Wybrana przez użytkownika liczba strażaków.
     */   
    public int getFirefighterCount() {
         return (int) firefighterSlider.getValue(); 
    }

    /**
     * @return Wybrana przez użytkownika szansa na rozprzestrzenienie się ognia.
     */
    public double getFireChance() { 
        return fireChanceSlider.getValue(); 
    }

     /**
     * @return Wybrana przez użytkownika liczba punktów ewakuacyjnych.
     */   
    public int getEvacCount() { 
        return (int) evacPointSlider.getValue(); 
    }

     /**
     * Przekazuje właściwość szybkości symulacji (ticki).
     * Umożliwia głównemu kontrolerowi bieżące nasłuchiwanie zmian.
     * @return DoubleProperty przechowujące czas trwania kroku symulacji (w milisekundach). 
     */   
    public DoubleProperty speedProperty(){ 
        return speedSlider.valueProperty();
    }
}