package com.nikitosoleil.battleship;

public interface Rival {
    public void init();

    public Coordinates<Integer> nextMove(Board board);
}
