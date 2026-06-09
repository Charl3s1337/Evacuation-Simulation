package pl.pwr.galnal.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatsPanel extends VBox{
    private final Label timeLabel;
    private final Label deadLabel;
    private final Label savedLabel;
    private final Label extinguishedLabel;

    public StatsPanel(){
        setSpacing(10);
        setPadding(new Insets(15));
        setPrefWidth(200);

        setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 0 1 0 0;");

        Label title = new Label("BIEŻĄCE STATYSTYKI");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #333333; ");

        timeLabel = new Label("Liczba kroków (ticki): 0");
        timeLabel.setStyle("-fx-font-size: 12px");

        deadLabel = new Label("Liczba ofiar: 0");
        deadLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 12px;");

        savedLabel = new Label("Liczba uratowanych: 0");
        savedLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 12px");

        extinguishedLabel = new Label("Ugaszone pożary: 0");
        extinguishedLabel.setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-font-size: 12px");

        getChildren().addAll(title, timeLabel, deadLabel, savedLabel, extinguishedLabel);
    }
    public void updateStats(int ticks, int dead, int saved, int extinguished){
        timeLabel.setText("Liczba kroków (ticki): "+ticks);
        deadLabel.setText("Liczba ofiar: "+dead);
        savedLabel.setText("Liczba uratowanych: "+saved);
        extinguishedLabel.setText("Ugaszone pożary: "+extinguished);
    }   
    public void resetStats(){
        updateStats(0, 0, 0, 0);
    } 
}
