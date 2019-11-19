package com.nikitosoleil.battleship;

public class Game {
    public static final int n = 8;
    private Drawer drawer;
    private Player player;
    private Bot bot;
    private Board playerBoard, botBoard;
    private final long delay = 2000;
    public Thread animationThread;

    public Game(Drawer drawer) {
        this.drawer = drawer;
        init();
    }

    public void init() {
        player = new Player();
        playerBoard = player.initial();
        bot = new Bot();
        botBoard = bot.initial();
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getBotBoard() {
        return botBoard;
    }

    public void playerMove(Pair<Integer> playerMove) {
        Logger.log(String.format("Got player move: %s, %s", playerMove.x, playerMove.y));
        if (botBoard.moveValid(playerMove) && !botBoard.theEnd()) {
            if (botBoard.getState(playerMove) == Board.CellState.PRESENT) {
                botBoard.setState(playerMove, Board.CellState.FOUND);
            } else if (!botBoard.theEnd()) {
                botBoard.setState(playerMove, Board.CellState.TRIED);
                updateView(botBoard, false, "YOUR TURN", delay);

                Pair<Integer> botMove;
                while (!playerBoard.theEnd()) {
                    updateView(playerBoard, true, "BOT'S TURN", delay);

                    botMove = bot.move(playerBoard);
                    if (playerBoard.getState(botMove) == Board.CellState.PRESENT)
                        playerBoard.setState(botMove, Board.CellState.FOUND);
                    else {
                        playerBoard.setState(botMove, Board.CellState.TRIED);
                        break;
                    }
                }
                updateView(playerBoard, true, "BOT'S TURN", delay);
            }
        }
        if (botBoard.theEnd()) {
            updateView(botBoard, true, "YOU WON", delay);
            init();
        } else if (playerBoard.theEnd()) {
            updateView(playerBoard, true, "BOT WON", delay);
            init();
        }
        updateView(botBoard, false, "YOUR TURN", 0);
    }

    public void updateView(Board board, boolean permission, String topText, long delay) {
        drawer.update(board, permission, topText);
        drawer.draw();
        freeze(delay);
    }

    public void playerMove(int x, int y) {
        playerMove(new Pair<Integer>(x, y));
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
