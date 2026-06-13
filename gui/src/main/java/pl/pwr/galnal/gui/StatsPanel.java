package pl.pwr.galnal.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Boczny panel statystyk.
 * <p>
 * Służy do wyświetlania bieżących statystyk symulacji.
 * </p>
 */
public class StatsPanel extends VBox {
    private final Label timeLabel;
    private final Label deadLabel;
    private final Label savedLabel;
    private final Label extinguishedLabel;

    /**
     * Konstruktor panelu statystyk.
     * Inicjalizuje i układa etykiety tekstowe oraz nadaje im kolory.
     */
    public StatsPanel() {
        // Odstępy i marginesy.
        setSpacing(10);
        setPadding(new Insets(15));
        setPrefWidth(200);

        // Białe tło i szara linia po prawej stronie (oddzielenie od centrum).
        setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 0 1 0 0;");

        // Tworzenie etykiet.
        Label title = new Label("BIEŻĄCE STATYSTYKI");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #333333; ");

        timeLabel = new Label("Liczba kroków (ticki): 0");
        timeLabel.setStyle("-fx-font-size: 12px");

        // Dodanie ładnych kolorów dla głównych statystyk.
        deadLabel = new Label("Liczba ofiar: 0");
        deadLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 12px;");

        savedLabel = new Label("Liczba uratowanych: 0");
        savedLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 12px");

        extinguishedLabel = new Label("Ugaszone pożary: 0");
        extinguishedLabel.setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-font-size: 12px");

        // Dodanie wszystkich etykiet do kontenera VBox.
        getChildren().addAll(title, timeLabel, deadLabel, savedLabel, extinguishedLabel);
    }

    /**
     * Nadpisuje teksty w etykietach nowymi wartościami.
     * @param ticks Obecny krok symulacji.
     * @param dead Liczba martwych cywilów.
     * @param saved Liczba uratowanych cywilów.
     * @param extinguished Liczba ugaszonych ognisk pożaru.
     */
    
    public void updateStats(int ticks, int dead, int saved, int extinguished) {
        timeLabel.setText("Liczba kroków (ticki): "+ticks);
        deadLabel.setText("Liczba ofiar: "+dead);
        savedLabel.setText("Liczba uratowanych: "+saved);
        extinguishedLabel.setText("Ugaszone pożary: "+extinguished);
    }   
    /**
     * Resetowanie liczników.
     * Używane np. przy generowaniu nowej planszy.
     */
    public void resetStats() {
        updateStats(0, 0, 0, 0);
    } 
}
