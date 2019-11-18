package com.nikitosoleil.battleship;

public class InputManager {
    private BoardView boardView;
    private Board board;

    public InputManager(BoardView boardView, Board board) {
        this.boardView = boardView;
        this.board = board;
    }

    private static int getIndex(float start, float end, int count, float x) {
        if (x > start && x < end)
            return (int) Math.floor((x - start) * count / (end - start));
        else
            return -1;
    }

    public void onTouch(float x, float y) {
        Logger.log("onTouch: " + x + " " + y);
        int i = getIndex(boardView.topLeft.x, boardView.bottomRight.x, board.n, x);
        int j = getIndex(boardView.topLeft.y, boardView.bottomRight.y, board.n, y);
        if (i >= 0 && j >= 0) {
            Logger.log("action: " + i + " " + j);
            board.action(i, j);
        }
    }
}
