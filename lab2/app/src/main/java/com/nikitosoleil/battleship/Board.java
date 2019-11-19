package com.nikitosoleil.battleship;

import java.util.Arrays;
import java.util.Random;

public class Board {
    public enum CellState {
        EMPTY, TRIED, PRESENT, FOUND, DESTROYED
    }

    private CellState[][] state;

    public Board(int n) {
        state = new CellState[n][n];
        for (CellState[] row : state)
            Arrays.fill(row, CellState.EMPTY);
    }


    public CellState getState(int x, int y) {
        return state[x][y];
    }

    public CellState getState(Pair<Integer> coords) {
        return getState(coords.x, coords.y);
    }

    public void setState(int x, int y, CellState value) {
        state[x][y] = value;
    }

    public void setState(Pair<Integer> coords, CellState value) {
        setState(coords.x, coords.y, value);
    }

    public boolean moveValid(int x, int y) {
        if (state[x][y] == CellState.EMPTY || state[x][y] == CellState.PRESENT)
            return true;
        else
            return false;
    }

    public boolean moveValid(Pair<Integer> coords) {
        return moveValid(coords.x, coords.y);
    }

    public boolean stateValid() {
        return true;
    }

    public void randomize() {
        Random rand = new Random();
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                if (rand.nextFloat() < 0.3)
                    state[i][j] = CellState.PRESENT;
                else
                    state[i][j] = CellState.EMPTY;
            }
        }
    }

    public boolean theEnd() {
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                if (state[i][j] == CellState.PRESENT)
                    return false;
            }
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
    }
}
