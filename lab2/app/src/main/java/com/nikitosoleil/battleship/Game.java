package com.nikitosoleil.battleship;

public class Game {
    public static final int n = 10;
    private Drawer drawer;
    private Rival bot;
    private Board playerBoard, botBoard;
    private final long delay = 2000;
    public Thread animationThread;

    public Game(Drawer drawer) {
        this.drawer = drawer;
        animationThread = new Thread();
        init();
    }

    public void init() {
        playerBoard = new Board();
        playerBoard.randomize();

        botBoard = new Board();
        playerBoard.randomize();

        bot = new Bot();
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getBotBoard() {
        return botBoard;
    }

    private boolean step(Board board, Coordinates<Integer> move) {
        if (board.getState(move) == Board.CellState.PRESENT) {
            board.setState(move, Board.CellState.FOUND);
            board.updateShip(move);
        } else if (!board.theEnd()) {
            board.setState(move, Board.CellState.TRIED);
            return true;
        }
        return false;
    }


    public void playerMove(Coordinates<Integer> playerMove) {
        Logger.log(String.format("Got player move: %s, %s", playerMove.x, playerMove.y));
        if (botBoard.moveValid(playerMove) && !botBoard.theEnd()) {
            boolean botTurn = step(botBoard, playerMove);
            if (botTurn) {
                updateView(botBoard, false, "YOUR TURN", delay);

                Coordinates<Integer> botMove;
                while (!playerBoard.theEnd()) {
                    updateView(playerBoard, true, "BOT TURN", delay);

                    Board hiddenPlayerBoard = playerBoard.getHidden();
                    botMove = bot.nextMove(hiddenPlayerBoard);

                    boolean playerTurn = step(playerBoard, botMove);
                    if (playerTurn)
                        break;
                }
                updateView(playerBoard, true, "BOT TURN", delay);
            }
        }
        if (botBoard.theEnd()) {
            updateView(botBoard, true, "YOU WON", delay);
            updateView(playerBoard, true, "YOU WON", delay);
            init();
        } else if (playerBoard.theEnd()) {
            updateView(playerBoard, true, "BOT WON", delay);
            updateView(botBoard, true, "BOT WON", delay);
            init();
        }
        updateView(botBoard, false, "YOUR TURN", 0);
    }

    public void updateView(Board board, boolean permission, String topText, long delay) {
        drawer.draw(board, permission, topText);
        freeze(delay);
    }

    public void playerMove(int x, int y) {
        playerMove(new Coordinates<Integer>(x, y));
    }

    private void freeze(final long n) {
        animationThread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(n);
                } catch (InterruptedException e) {
                    Logger.log("Animation interrupted");
                }
            }
        };
        animationThread.start();
        try {
            animationThread.join();
        } catch (InterruptedException e) {
        }
    }

}
