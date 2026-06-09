package pl.pwr.galnal.gui;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {

    private final Slider widthSlider;
    private final Slider heightSlider;
    private final Slider civSlider;
    private final Slider fireSlider;
    private final Slider firefighterSlider;
    private final Slider fireChanceSlider;
    private final Slider evacPointSlider;
    private final Slider speedSlider;

    public ControlPanel(Runnable onGenerate, Runnable onStart, Runnable onStop) {
        setSpacing(7);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-width: 0 0 0 1;");
        setPrefWidth(250);

        widthSlider = createSlider(5, 50, 15, true, 10);
        heightSlider = createSlider(5, 50, 15, true, 10);
        civSlider = createSlider(0, 100, 10, true, 10);
        fireSlider = createSlider(0, 30, 2, true, 10);
        firefighterSlider = createSlider(0, 20, 2, true, 10);
        fireChanceSlider = createSlider(0.0, 1.0, 0.5, false, 0.2);
        evacPointSlider = createSlider(1, 20, 2, true, 10);
        speedSlider = createSlider(50,1000,300,true,20);

        Button generateBtn = new Button("Generuj nową planszę");
        generateBtn.setMaxWidth(Double.MAX_VALUE);
        generateBtn.setStyle("-fx-font-weight: bold; -fx-base: #e0e0e0;");
        generateBtn.setOnAction(e -> onGenerate.run());

        Button startButton = new Button("Start");
        startButton.setMaxWidth(Double.MAX_VALUE);
        startButton.setMinHeight(40); 
        startButton.setStyle("-fx-base: #a4c639; -fx-font-weight: bold; -fx-font-size: 14px;"); 
        startButton.setOnAction(e -> onStart.run());

        Button stopButton = new Button("Stop");
        stopButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setMinHeight(40); 
        stopButton.setStyle("-fx-base: #ff6b6b; -fx-font-weight: bold; -fx-font-size: 14px;");
        stopButton.setOnAction(e -> onStop.run());

        HBox playbackControls = new HBox(10, startButton, stopButton);
        
        HBox.setHgrow(startButton, Priority.ALWAYS);
        HBox.setHgrow(stopButton, Priority.ALWAYS);

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

private Slider createSlider(double min, double max, double defaultVal, boolean isInteger, double tickUnit) {
        Slider slider = new Slider(min, max, defaultVal);
        slider.setMajorTickUnit(tickUnit);
        
        // Poprawne ustawianie slidera nieważne od pozycji
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

    private VBox buildSliderBox(String name, Slider slider, boolean isInteger) {
        Label label = new Label();
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            label.setText(name + ": " + (isInteger ? newVal.intValue() : String.format("%.2f", newVal.doubleValue())));
        });
        label.setText(name + ": " + (isInteger ? (int)slider.getValue() : String.format("%.2f", slider.getValue())));
        VBox box = new VBox(2, label, slider);
        box.setPadding(new Insets(0, 0, 10, 0));
        return box;
    }

    public int getBoardWidth() {
         return (int) widthSlider.getValue(); 
        }
    public int getBoardHeight() { 
        return (int) heightSlider.getValue(); 
    }
    public int getCivCount() { 
        return (int) civSlider.getValue(); }
    public int getFireCount() { return (int) fireSlider.getValue(); 

    }
    public int getFirefighterCount() {
         return (int) firefighterSlider.getValue(); 
        }
    public double getFireChance() { 
        return fireChanceSlider.getValue(); 
    }
    public int getEvacCount() { 
        return (int) evacPointSlider.getValue(); 
    }
    public DoubleProperty speedProperty(){ 
        return speedSlider.valueProperty();
    }
}