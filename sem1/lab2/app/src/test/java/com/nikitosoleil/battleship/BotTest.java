package com.nikitosoleil.battleship;

import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {
    @Test
    public void randomMoveAtStart() {
        Bot bot = new Bot();
        int[][] mask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 2, 2, 0, 0, 0},
                {2, 0, 0, 2, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 2, 2, 2, 0},
                {0, 2, 2, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        for (int i = 0; i < 10; ++i) {
            Coordinates<Integer> move = bot.nextMove(board);
            assertTrue(board.moveValid(move));
        }
    }

    @Test
    public void randomMoveDuringGame() {
        Bot bot = new Bot();
        int[][] mask = {{0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                {0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 4, 4, 0, 0, 0},
                {2, 0, 0, 2, 0, 0, 0, 0, 4, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 2, 2, 2, 0},
                {0, 2, 2, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        for (int i = 0; i < 10; ++i) {
            Coordinates<Integer> move = bot.nextMove(board);
            assertTrue(board.moveValid(move));
        }
    }

    @Test
    public void randomMoveWhenFound() {
        Bot bot = new Bot();
        int[][] mask = {{0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                {0, 0, 2, 0, 0, 0, 2, 0, 0, 2},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 4, 4, 0, 0, 0},
                {2, 0, 0, 2, 0, 0, 0, 0, 4, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 3, 3, 2, 2, 0},
                {0, 2, 2, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        for (int t = 0; t < 10; ++t) {
            Coordinates<Integer> move = bot.nextMove(board);
            assertTrue(board.moveValid(move));
            boolean foundNearby = false;
            for (int i = Math.max(0, move.x - 1); i <= Math.min(Game.n - 1, move.x + 1); ++i) {
                for (int j = Math.max(0, move.y - 1); j <= Math.min(Game.n - 1, move.y + 1); ++j) {
                    if (board.getState(i, j) == Board.CellState.PRESENT)
                        foundNearby = true;
                }
            }
            assertTrue(foundNearby);
        }
    }
}
