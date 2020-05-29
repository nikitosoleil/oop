package com.nikitosoleil.battleship;

import java.util.Random;

public class Bot implements Rival {
    Random rand;

    public Bot() {
        init();
    }

    public void init() {
        rand = new Random();
    }

    private boolean makeSense(Board board, Coordinates<Integer> move) {
        for (int i = Math.max(0, move.x - 1); i <= Math.min(Game.n - 1, move.x + 1); ++i)
            for (int j = Math.max(0, move.y - 1); j <= Math.min(Game.n - 1, move.y + 1); ++j)
                if (Board.isShip(board.getState(i, j)))
                    return false;
        return true;
    }

    public Coordinates<Integer> nextMove(Board board) {
        Coordinates<Integer> move = null;
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                if (board.getState(i, j) == Board.CellState.FOUND) {
                    Logger.log(String.format("Bot found point of interest: %s %s", i, j));
                    for (Coordinates<Integer> direction : Board.directions) {
                        Coordinates<Integer> candidate = new Coordinates<Integer>(i + direction.x, j + direction.y);
                        if (board.moveValid(candidate)) {
                            move = candidate;
                            Logger.log(String.format("Bot found optimal move: %s %s", move.x, move.y));
                        }
                    }
                }
            }
        }
        if (move == null) {
            move = new Coordinates<Integer>(-1, -1);
            do {
                move.x = rand.nextInt(Game.n);
                move.y = rand.nextInt(Game.n);
            }
            while (!board.moveValid(move) || !makeSense(board, move));
            Logger.log(String.format("Bot took random move: %s %s", move.x, move.y));
        }
        return move;
    }
}
