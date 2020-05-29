package com.nikitosoleil.battleship;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void isWater() {
        assertTrue(Board.isWater(Board.CellState.EMPTY));
        assertTrue(Board.isWater(Board.CellState.TRIED));
        assertFalse(Board.isWater(Board.CellState.PRESENT));
        assertFalse(Board.isWater(Board.CellState.FOUND));
        assertFalse(Board.isWater(Board.CellState.DESTROYED));
    }

    @Test
    public void isShip() {
        assertFalse(Board.isShip(Board.CellState.EMPTY));
        assertFalse(Board.isShip(Board.CellState.TRIED));
        assertTrue(Board.isShip(Board.CellState.PRESENT));
        assertTrue(Board.isShip(Board.CellState.FOUND));
        assertTrue(Board.isShip(Board.CellState.DESTROYED));
    }

    @Test
    public void ships() {
        assertTrue(Board.ships.length > 1);
        for (Integer count : Board.ships) {
            assertTrue(count >= 0);
        }
    }

    @Test
    public void directions() {
        assertEquals(4, Board.directions.size());
    }

    @Test
    public void creation() {
        Board board = new Board();
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                assertTrue(Board.inBound(i, j));
                assertTrue(Board.inBound(new Coordinates<Integer>(i, j)));

                assertEquals(board.getState(i, j), Board.CellState.EMPTY);
                assertEquals(board.getState(new Coordinates<Integer>(i, j)), Board.CellState.EMPTY);

                board.setState(i, j, Board.CellState.TRIED);
                assertEquals(board.getState(i, j), Board.CellState.TRIED);
                board.setState(new Coordinates<Integer>(i, j), Board.CellState.EMPTY);
                assertEquals(board.getState(i, j), Board.CellState.EMPTY);
            }
        }
        assertFalse(Board.inBound(-1, 0));
        assertFalse(Board.inBound(0, -1));
        assertFalse(Board.inBound(Game.n, 0));
        assertFalse(Board.inBound(0, Game.n));
    }

    @Test
    public void validMoves() {
        Board board = new Board();
        assertTrue(board.moveValid(1, 1));
        board.setState(1, 1, Board.CellState.TRIED);
        assertFalse(board.moveValid(1, 1));
        board.setState(2, 2, Board.CellState.PRESENT);
        assertTrue(board.moveValid(new Coordinates<Integer>(2, 2)));
    }

    @Test
    public void getHidden() {
        Board board = new Board();
        board.setState(0, 0, Board.CellState.TRIED);
        board.setState(0, 1, Board.CellState.PRESENT);
        board.setState(1, 0, Board.CellState.FOUND);
        board.setState(1, 1, Board.CellState.DESTROYED);
        Board hiddenBoard = board.getHidden();
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                if (board.getState(i, j) == Board.CellState.PRESENT)
                    assertEquals(Board.CellState.EMPTY, hiddenBoard.getState(i, j));
                else
                    assertEquals(board.getState(i, j), hiddenBoard.getState(i, j));
            }
        }
    }

    @Test
    public void cloning() {
        Board board = new Board();
        Board hiddenBoard = board.getHidden();
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j)
                assertEquals(board.getState(i, j), hiddenBoard.getState(i, j));
        }

    }

    @Test
    public void invalidness2dshipPresent() {
        int[][] mask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        assertEquals(1, board.stateInvalid());
    }

    @Test
    public void invalidnessShipsAdjacent() {
        int[][] mask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 2, 2, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        assertEquals(2, board.stateInvalid());
    }

    @Test
    public void invalidnessShipsTooBig() {
        int[][] mask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 2, 2, 2, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        assertEquals(3, board.stateInvalid());
    }

    @Test
    public void invalidnessWrongSetOfShips() {
        int[][] mask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 2, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 2, 2, 2, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        assertEquals(4, board.stateInvalid());
    }

    @Test
    public void invalidnessEverythingIsCorrect() {
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
        assertEquals(0, board.stateInvalid());
    }

    @Test
    public void randomness() {
        for (int t = 0; t < 10; ++t) {
            Board board = Board.random();
            assertEquals(0, board.stateInvalid());
        }
    }

    @Test
    public void updateShip() {
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

        List<Coordinates<Integer>> fireCells = new ArrayList<Coordinates<Integer>>();
        fireCells.add(new Coordinates<Integer>(1, 2));
        fireCells.add(new Coordinates<Integer>(3, 0));
        fireCells.add(new Coordinates<Integer>(1, 9));
        fireCells.add(new Coordinates<Integer>(2, 9));

        int[][] expectedNewMask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 2, 0, 0, 4},
                {0, 0, 0, 0, 2, 0, 0, 0, 0, 4},
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 2, 2, 0, 0, 0},
                {2, 0, 0, 2, 0, 0, 0, 0, 2, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 2, 2, 2, 0},
                {0, 2, 2, 0, 0, 0, 0, 0, 0, 0}};
        Board expectedNewBoard = Board.fromMask(expectedNewMask);

        Board newBoard = board.clone();
        for (Coordinates<Integer> coords : fireCells) {
            newBoard.setState(coords, Board.CellState.FOUND);
            newBoard.updateShip(coords);
        }

        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                assertEquals(expectedNewBoard.getState(i, j), newBoard.getState(i, j));
            }
        }
    }

    @Test
    public void theEnd() {
        int[][] mask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 4, 0, 0, 0, 4, 0, 0, 4},
                {0, 0, 0, 0, 4, 0, 0, 0, 0, 4},
                {4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 4, 4, 0, 0, 0},
                {4, 0, 0, 4, 0, 0, 0, 0, 4, 0},
                {0, 0, 0, 4, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 4, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 4, 4, 4, 0},
                {0, 2, 4, 0, 0, 0, 0, 0, 0, 0}};
        Board board = Board.fromMask(mask);
        assertFalse(board.theEnd());
        board.setState(9, 1, Board.CellState.DESTROYED);
        assertTrue(board.theEnd());
    }
}
