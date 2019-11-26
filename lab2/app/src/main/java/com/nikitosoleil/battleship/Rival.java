package com.nikitosoleil.battleship;

public interface Rival {
    public Coordinates<Integer> nextMove(Board board);
}
