package pl.pwr.galnal.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {

    private final Slider widthSlider;
    private final Slider heightSlider;
    private final Slider civSlider;
    private final Slider fireSlider;
    private final Slider firefighterSlider;
    private final Slider fireChanceSlider;
    private final Slider evacPointSlider;

    public ControlPanel(Runnable onGenerate, Runnable onStep) {
        setSpacing(10);
        setPadding(new Insets(15));
        setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-width: 0 0 0 1;");
        setPrefWidth(250);

        widthSlider = createSlider(5, 50, 15, true, 10);
        heightSlider = createSlider(5, 50, 15, true, 10);
        civSlider = createSlider(0, 100, 10, true, 10);
        fireSlider = createSlider(0, 30, 2, true, 10);
        firefighterSlider = createSlider(0, 10, 2, true, 10);
        fireChanceSlider = createSlider(0.0, 1.0, 0.5, false, 0.2);
        evacPointSlider = createSlider(1, 20, 2, true, 10);

        Button generateBtn = new Button("Generuj nową planszę");
        generateBtn.setMaxWidth(Double.MAX_VALUE);
        generateBtn.setStyle("-fx-font-weight: bold; -fx-base: #e0e0e0;");
        generateBtn.setOnAction(e -> onGenerate.run());

        Button stepButton = new Button("Następny krok (Tick)");
        stepButton.setMaxWidth(Double.MAX_VALUE);
        stepButton.setOnAction(e -> onStep.run());

        getChildren().addAll(
                new Label("WYMIARY PLANSZY"),
                buildSliderBox("Szerokość", widthSlider, true),
                buildSliderBox("Wysokość", heightSlider, true),
                new Label("AGENTY (SPAWN)"),
                buildSliderBox("Cywile", civSlider, true),
                buildSliderBox("Pożary", fireSlider, true),
                buildSliderBox("Strażacy", firefighterSlider, true),
                buildSliderBox("Wyjścia", evacPointSlider, true),
                new Label("FIZYKA POŻARU"),
                buildSliderBox("Szansa rozprzestrzeniania", fireChanceSlider, false),
                generateBtn,
                stepButton
        );
    }

    private Slider createSlider(double min, double max, double defaultVal, boolean isInteger, double tickUnit) {
        Slider slider = new Slider(min, max, defaultVal);
        slider.setMajorTickUnit(tickUnit);
        slider.setMinorTickCount(isInteger ? 4 : 1);
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

    // Bezpieczne gettery do wyciągania wartości przez główną aplikację
    public int getBoardWidth() { return (int) widthSlider.getValue(); }
    public int getBoardHeight() { return (int) heightSlider.getValue(); }
    public int getCivCount() { return (int) civSlider.getValue(); }
    public int getFireCount() { return (int) fireSlider.getValue(); }
    public int getFirefighterCount() { return (int) firefighterSlider.getValue(); }
    public double getFireChance() { return fireChanceSlider.getValue(); }
    public int getEvacCount() { return (int) evacPointSlider.getValue(); }
}