package pl.pwr.galnal.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import pl.pwr.galnal.engine.Board;
import pl.pwr.galnal.engine.Cell;
import pl.pwr.galnal.engine.agents.Agent;
import pl.pwr.galnal.engine.agents.Civilian;
import pl.pwr.galnal.engine.agents.Firefighter;
import pl.pwr.galnal.engine.agents.Obstacle;

/**
 * Główny widok planszy tzw. "View" w architekturze MVC.
 * <p>
 * Klasa odpowiedzialna za rysowanie obecnego stanu symulacji na ekranie.
 * Nie wykonuje żadnych obliczeń, tylko "zamienia" obiekty na piksele na ekranie.
 * </p>
 */
public class BoardRender extends StackPane {
    private final Canvas canvas;
    private Board currentBoard;
    private double tileSize = 40;
    private Image imgCivilian;
    private Image imgFirefighter;
    private Image imgFire;
    private Image imgObstacle;
    private Image imgEvac;

    /**
     * Konstruktor inicjalizujący Canvas (płótno) i wczytujący ikony z dysku.
     */
    public BoardRender() {
        canvas = new Canvas();
        getChildren().add(canvas);
        setStyle("-fx-background-color: #2b2b2b;");
        
        // Próba wczytania grafik z folderu.
        try {
            imgCivilian = new Image("file:icons/civilian.png");
            imgFirefighter = new Image("file:icons/firefighter.png");
            imgFire = new Image("file:icons/fire.png");
            imgObstacle = new Image("file:icons/obstacle.png");
            imgEvac = new Image("file:icons/evac.png");
        } catch (Exception e) {
            System.out.println("Nie udało się załadować ikon! Upewnij się, że masz folder 'icons' z odpowiednimi plikami PNG.");
        }

        // Automatyczne przerysowanie planszy przy zmianie rozmiaru okna.
        widthProperty().addListener((obs, oldVal, newVal) -> draw(currentBoard));
        heightProperty().addListener((obs, oldVal, newVal) -> draw(currentBoard));
    }

    /**
     * Główna metoda rysująca, wywoływana w każdym kroku symulacji.
     * Skaluje komórki do rozmiaru okna i rysuje odpowiednie ikony.
     * @param board Aktualny stan planszy.
     */
    public void draw(Board board) {
        this.currentBoard = board;

        // Zabezpieczenie przed rysowaniem pustej planszy lub zminimalizowanej planszy.
        if(board == null || getWidth() == 0 || getHeight() == 0) return;

        // Maksymalna liczba pikseli w komórce planszy
        double tileW = getWidth() / board.getWidth();
        double tileH = getHeight() / board.getHeight();

        // Minimalna wartość, żeby kafelki były kwadratami, mieszczącymi się na ekranie.
        tileSize = Math.min(tileW, tileH) - 1;

        // Ustawiamy rozmiar płótna na nasz rozmiar planszy (tyle kafelków ile ustalił użytkownik).
        canvas.setWidth(tileSize * board.getWidth());
        canvas.setHeight(tileSize * board.getHeight());

        // Narzędzie pędzla do "malowania".
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Wyczyszczenie poprzedniej klatki symulacji.
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Przejście przez komórki i renderowanie ich zawartości.
        for(int y = 0; y < board.getHeight(); y++) {
            for(int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCell(x, y);
                if(cell == null) continue;

                Agent entity = cell.getPhysicalEntity();
                boolean isBurning = (cell.getFire() != null);

                // Przeliczenie indeksów tablicy na piksele widoczne na ekranie.
                double drawX = x * tileSize;
                double drawY = y * tileSize;

                // Logika rysowania - sprawdzenie typu obiektu na danym polu i dobór jego grafiki.
                // Zabezpieczenie przed błędem pobierania ikon - wtedy kolory zamiast grafik.
                if(isBurning) {
                    if (imgFire != null && !imgFire.isError()) {
                        gc.drawImage(imgFire, drawX, drawY, tileSize, tileSize);
                    } else {
                        gc.setFill(Color.RED);
                        gc.fillRect(drawX, drawY, tileSize, tileSize);
                    }
                } else if(entity instanceof Obstacle) {
                    if (imgObstacle != null && !imgObstacle.isError()) {
                        gc.drawImage(imgObstacle, drawX, drawY, tileSize, tileSize);
                    } else {
                        gc.setFill(Color.GRAY);
                        gc.fillRect(drawX, drawY, tileSize, tileSize);
                    }
                } else if(entity instanceof Civilian) {
                    if (imgCivilian != null && !imgCivilian.isError()) {
                        gc.drawImage(imgCivilian, drawX, drawY, tileSize, tileSize);
                    } else {
                        gc.setFill(Color.CYAN);
                        gc.fillRect(drawX, drawY, tileSize, tileSize);
                    }
                } else if(entity instanceof Firefighter) {
                    if (imgFirefighter != null && !imgFirefighter.isError()) {
                        gc.drawImage(imgFirefighter, drawX, drawY, tileSize, tileSize);
                    } else {
                        gc.setFill(Color.BLUE);
                        gc.fillRect(drawX, drawY, tileSize, tileSize);
                    }
                } else if(cell.getEvacuationPoint() != null) {
                    if (imgEvac != null && !imgEvac.isError()) {
                        gc.drawImage(imgEvac, drawX, drawY, tileSize, tileSize);
                    } else {
                        gc.setFill(Color.GREEN);
                        gc.fillRect(drawX, drawY, tileSize, tileSize);
                    }
                }
            }
        }
    }
}