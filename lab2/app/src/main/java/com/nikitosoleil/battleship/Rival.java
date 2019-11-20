package com.nikitosoleil.battleship;

public interface Rival {
    public Board initial();

    public Pair<Integer> nextMove(Board board);
}
