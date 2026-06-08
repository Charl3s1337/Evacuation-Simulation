package pl.pwr.galnal.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import pl.pwr.galnal.engine.Board;
import pl.pwr.galnal.engine.Cell;
import pl.pwr.galnal.engine.agents.Agent;
import pl.pwr.galnal.engine.agents.Civilian;
import pl.pwr.galnal.engine.agents.Firefighter;
import pl.pwr.galnal.engine.agents.Obstacle;

public class BoardRender extends StackPane {
    private final Canvas canvas;
    private Board currentBoard;
    private double tileSize = 40;

    public BoardRender() {
        canvas = new Canvas();
        getChildren().add(canvas);
        setStyle("-fx-background-color: #2b2b2b;");

        widthProperty().addListener((obs, oldVal, newVal) -> draw(currentBoard));
        heightProperty().addListener((obs, oldVal, newVal) -> draw(currentBoard));
    }

    public void draw(Board board) {
        this.currentBoard = board;
        if (board == null || getWidth() == 0 || getHeight() == 0) return;

        double tileW = getWidth() / board.getWidth();
        double tileH = getHeight() / board.getHeight();
        tileSize = Math.min(tileW, tileH) - 1;

        canvas.setWidth(tileSize * board.getWidth());
        canvas.setHeight(tileSize * board.getHeight());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Cell cell = board.getCell(x, y);
                if (cell == null) continue;

                Agent entity = cell.getPhysicalEntity();
                boolean isBurning = (cell.getFire() != null);

                if (isBurning) gc.setFill(Color.RED);
                else if (entity instanceof Obstacle) gc.setFill(Color.GRAY);
                else if (entity instanceof Civilian) gc.setFill(Color.CYAN);
                else if (entity instanceof Firefighter) gc.setFill(Color.BLUE);
                else if (cell.getEvacuationPoint() != null) gc.setFill(Color.GREEN);
                else gc.setFill(Color.DARKGRAY);

                gc.fillRect(x * tileSize + 1, y * tileSize + 1, tileSize - 2, tileSize - 2);
            }
        }
    }
}