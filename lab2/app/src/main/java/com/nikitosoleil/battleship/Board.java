package com.nikitosoleil.battleship;

import java.util.Arrays;

public class Board {
    public enum cellState {
        UNKNOWN, TRIED, FOUND, DESTROYED
    }
    public cellState[][] userState;

    public int n;
    public boolean hiddenState[][];

    public Board(int n) {
        this.n = n;
        userState = new cellState[n][n];
        for (cellState[] row: userState)
            Arrays.fill(row, cellState.UNKNOWN);
        Logger.log("Board initialized, n: " + n);
    }

    public void action(int i, int j) {
        userState[i][j] = cellState.TRIED;
    }
}
