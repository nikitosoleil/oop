package com.nikitosoleil.battleship;

public interface Rival {
    public Board initial();

    public Pair<Integer> move(Board board);
}
