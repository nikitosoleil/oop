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

    public static final int[] ships = {4, 3, 2, 1};
    public static final List<Coordinates<Integer>> directions = new ArrayList<Coordinates<Integer>>(Arrays.asList(
            new Coordinates<Integer>(1, 0),
            new Coordinates<Integer>(-1, 0),
            new Coordinates<Integer>(0, 1),
            new Coordinates<Integer>(0, -1)));

    private CellState[][] state;

    public Board() {
        state = new CellState[Game.n][Game.n];
        for (CellState[] row : state)
            Arrays.fill(row, CellState.EMPTY);
    }

    public static Board fromMask(int[][] mask) {
        Board board = new Board();
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                Board.CellState state = Board.CellState.values()[mask[i][j]];
                board.setState(i, j, state);
            }
        }
        return board;
    }


    public CellState getState(int x, int y) {
        return state[x][y];
    }

    public CellState getState(Coordinates<Integer> point) {
        return getState(point.x, point.y);
    }

    public void setState(int x, int y, CellState value) {
        state[x][y] = value;
    }

    public void setState(Coordinates<Integer> point, CellState value) {
        setState(point.x, point.y, value);
    }

    public static boolean inBound(int x, int y) {
        return 0 <= x && x < Game.n && 0 <= y && y < Game.n;
    }

    public static boolean inBound(Coordinates<Integer> point) {
        return inBound(point.x, point.y);
    }

    public boolean moveValid(int x, int y) {
        return state[x][y] == CellState.EMPTY || state[x][y] == CellState.PRESENT;
    }

    public boolean moveValid(Coordinates<Integer> point) {
        return moveValid(point.x, point.y);
    }

    public Board getHidden() {
        Board hidden = new Board();
        for (int i = 0; i < Game.n; ++i)
            for (int j = 0; j < Game.n; ++j) {
                if (getState(i, j) != CellState.PRESENT)
                    hidden.setState(i, j, getState(i, j));
                else
                    hidden.setState(i, j, CellState.EMPTY);
            }
        return hidden;
    }

    public Board clone() {
        Board newBoard = new Board();
        for (int i = 0; i < Game.n; ++i)
            for (int j = 0; j < Game.n; ++j)
                newBoard.setState(i, j, getState(i, j));
        return newBoard;
    }

    void shipCoords(Coordinates<Integer> point, Coordinates<Integer> topLeft, Coordinates<Integer> bottomRight,
                    boolean[][] visited) {
        visited[point.x][point.y] = true;
        topLeft.x = Math.min(topLeft.x, point.x);
        topLeft.y = Math.min(topLeft.y, point.y);
        bottomRight.x = Math.max(bottomRight.x, point.x);
        bottomRight.y = Math.max(bottomRight.y, point.y);

        for (Coordinates<Integer> direction : directions) {
            Coordinates<Integer> next = new Coordinates<Integer>(point.x + direction.x, point.y + direction.y);
            if (inBound(next) && !visited[next.x][next.y] && isShip(getState(next)))
                shipCoords(next, topLeft, bottomRight, visited);
        }
    }

    private boolean itsFreeRealEstate(Coordinates<Integer> U, Coordinates<Integer> V, boolean itself) {
        for (int i = Math.max(0, U.x - 1); i <= Math.min(Game.n - 1, V.x + 1); ++i)
            for (int j = Math.max(0, U.y - 1); j <= Math.min(Game.n - 1, V.y + 1); ++j) {
                if (!itself && U.x <= i && i <= V.x && U.y <= j && j <= V.y)
                    continue;
                if (isShip(getState(i, j)))
                    return false;
            }
        return true;
    }

    public int stateInvalid() {
        boolean[][] visited = new boolean[Game.n][Game.n];
        int[] shipsLeft = ships.clone();
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                if (isShip((getState(i, j))) && !visited[i][j]) {
                    Coordinates<Integer> topLeft = new Coordinates<Integer>(i, j);
                    Coordinates<Integer> bottomRight = new Coordinates<Integer>(i, j);
                    shipCoords(new Coordinates<Integer>(i, j), topLeft, bottomRight, visited);

                    int dimensions = 0, size = 0;
                    if (topLeft.x != bottomRight.x) {
                        size = bottomRight.x - topLeft.x;
                        dimensions++;
                    }
                    if (topLeft.y != bottomRight.y) {
                        size = bottomRight.y - topLeft.y;
                        dimensions++;
                    }

                    if (dimensions == 2)
                        return 1;
                    if (!itsFreeRealEstate(topLeft, bottomRight, false))
                        return 2;
                    if (size >= ships.length)
                        return 3;

                    shipsLeft[size]--;
                }
            }
        }
        for (int i = 0; i < shipsLeft.length; ++i)
            if (shipsLeft[i] != 0)
                return 4;
        return 0;
    }

    public void randomize() {
        Random rand = new Random();
        for (int size = 0; size < ships.length; ++size) {
            for (int iter = 0; iter < ships[size]; ++iter) {
                Coordinates<Integer> U, V;
                while (true) {
                    U = new Coordinates<Integer>(rand.nextInt(Game.n), rand.nextInt(Game.n));
                    V = U.clone();
                    if (rand.nextBoolean() && U.x + size < Game.n)
                        V.x += size;
                    else if (U.y + size < Game.n)
                        V.y += size;
                    else
                        continue;
                    if (itsFreeRealEstate(U, V, true))
                        break;
                }
                for (int i = U.x; i <= V.x; ++i)
                    for (int j = U.y; j <= V.y; ++j)
                        setState(i, j, CellState.PRESENT);
            }
        }
    }

    private boolean shipStatus(Coordinates<Integer> point, Coordinates<Integer> previous) {
        boolean destroyed = true;
        for (Coordinates<Integer> direction : directions) {
            Coordinates<Integer> next = new Coordinates<Integer>(point.x + direction.x, point.y + direction.y);
            if (inBound(next) && !next.equals(previous) && isShip(getState(next)))
                destroyed = destroyed && shipStatus(next, point);
        }
        return destroyed && (getState(point) != CellState.PRESENT);
    }

    private void destroyShip(Coordinates<Integer> point, Coordinates<Integer> previous) {
        setState(point, CellState.DESTROYED);
        for (Coordinates<Integer> direction : directions) {
            Coordinates<Integer> next = new Coordinates<Integer>(point.x + direction.x, point.y + direction.y);
            if (inBound(next) && !next.equals(previous) && isShip(getState(next)))
                destroyShip(next, point);
        }
    }

    public void updateShip(Coordinates<Integer> point) {
        boolean destroyed = shipStatus(point, point);
        if (destroyed)
            destroyShip(point, point);
    }

    public boolean theEnd() {
        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                if (getState(i, j) == CellState.PRESENT)
                    return false;
            }
        }
        return true;
    }
}
