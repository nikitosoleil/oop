package com.nikitosoleil.battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class Board {
    public enum CellState {
        EMPTY, TRIED, PRESENT, FOUND, DESTROYED
    }

    public static boolean isWater(CellState cs) {
        return cs == CellState.EMPTY || cs == CellState.TRIED;
    }

    public static boolean isShip(CellState cs) {
        return cs == CellState.PRESENT || cs == CellState.FOUND || cs == CellState.DESTROYED;
    }

    public final int[] ships = {4, 3, 2, 1};
    public final List<Pair<Integer>> directions = new ArrayList<Pair<Integer>>(Arrays.asList(
            new Pair<Integer>(1, 0),
            new Pair<Integer>(-1, 0),
            new Pair<Integer>(0, 1),
            new Pair<Integer>(0, -1)));

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

    public boolean inBound(int x, int y) {
        return 0 <= x && x < Game.n && 0 <= y && y < Game.n;
    }

    public boolean inBound(Pair<Integer> coords) {
        return inBound(coords.x, coords.y);
    }

    public boolean moveValid(int x, int y) {
        return state[x][y] == CellState.EMPTY || state[x][y] == CellState.PRESENT;
    }

    public boolean moveValid(Pair<Integer> coords) {
        return moveValid(coords.x, coords.y);
    }

    public boolean stateValid() {
        return true;
    }

    private boolean itsFreeRealEstate(Pair<Integer> U, Pair<Integer> V) {
        for (int i = Math.max(0, U.x - 1); i <= Math.min(Game.n - 1, V.x + 1); ++i)
            for (int j = Math.max(0, U.y - 1); j <= Math.min(Game.n - 1, V.y + 1); ++j)
                if (state[i][j] == CellState.PRESENT)
                    return false;
        return true;
    }

    public void randomize() {
        Random rand = new Random();
        for (int size = 0; size < ships.length; ++size) {
            for (int iter = 0; iter < ships[size]; ++iter) {
                Pair<Integer> U, V;
                while (true) {
                    U = new Pair<Integer>(rand.nextInt(Game.n), rand.nextInt(Game.n));
                    V = U.clone();
                    if (rand.nextBoolean() && U.x + size < Game.n)
                        V.x += size;
                    else if (U.y + size < Game.n)
                        V.y += size;
                    else
                        continue;
                    if (itsFreeRealEstate(U, V))
                        break;
                }
                for (int i = U.x; i <= V.x; ++i)
                    for (int j = U.y; j <= V.y; ++j)
                        state[i][j] = CellState.PRESENT;
                System.out.println(U.x + " " + U.y + " " + V.x + " " + V.y);
            }
        }
    }

    private boolean shipStatus(Pair<Integer> point, Pair<Integer> previous) {
        boolean destroyed = true;
        for (Pair<Integer> direction : directions) {
            Pair<Integer> next = new Pair<Integer>(point.x + direction.x, point.y + direction.y);
            if (inBound(next) && !next.equals(previous) && isShip(getState(next)))
                destroyed = destroyed && shipStatus(next, point);
        }
        return destroyed && (getState(point) != CellState.PRESENT);
    }

    private void destroyShip(Pair<Integer> point, Pair<Integer> previous) {
        setState(point, CellState.DESTROYED);
        for (Pair<Integer> direction : directions) {
            Pair<Integer> next = new Pair<Integer>(point.x + direction.x, point.y + direction.y);
            if (inBound(next) && !next.equals(previous) && isShip(getState(next)))
                destroyShip(next, point);
        }
    }

    public void updateShip(Pair<Integer> point) {
        boolean destroyed = shipStatus(point, point);
        if (destroyed)
            destroyShip(point, point);
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
}
