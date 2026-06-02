package pl.pwr.galnal.engine;
public class Main {
    public static void main(String[] args) {
        int width = 7;
        int height = 7;
        Board board = new Board(width, height);

        System.out.println("--- RĘCZNA KONFIGURACJA SYMULACJI ---");

        board.addAgent(new EvacuationPoint(0, 0, board));
        board.addAgent(new EvacuationPoint(6, 6, board));

        board.addAgent(new Obstacle(3, 1, board));
        board.addAgent(new Obstacle(3, 2, board));
        board.addAgent(new Obstacle(3, 3, board));

        board.addAgent(new Civilian(1, 1, board));
        board.addAgent(new Civilian(2, 2, board));

        board.addAgent(new Firefighter(5, 1, board));

        board.addAgent(new Fire(4, 4, board, 0.5));

        Simulation simulation = new Simulation(board);
        int simulationSteps = 5;

        System.out.println("\nStan poczatkowy (Krok 0):");
        printBoard(board, width, height);

        for (int step = 1; step <= simulationSteps; step++) {
            System.out.println("\n--- KROK SYMULACJI: " + step + " ---");
            simulation.updateBoard();
            printBoard(board, width, height);
        }
    }

    private static void printBoard(Board board, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = board.getCell(x, y);
                if (cell == null) {
                    System.out.print("[?]");
                    continue;
                }

                Agent entity = cell.getPhysicalEntity();
                boolean isBurning = (cell.getFire() != null);

                if (isBurning) {
                    if (entity != null) System.out.print("[!]");
                    else System.out.print("[*]");
                } else if (entity instanceof Obstacle) {
                    System.out.print("[W]");
                } else if (entity instanceof EvacuationPoint) {
                    System.out.print("[E]");
                } else if (entity instanceof Civilian) {
                    System.out.print("[C]");
                } else if (entity instanceof Firefighter) {
                    System.out.print("[F]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }
}