package com.nikitosoleil.battleship;

public class Player implements Rival {
    public Board initial() {
        Board board = new Board(Game.n);

        // TODO: implement
        board.randomize();

        assert board.stateValid();
        return board;
    }

    public Pair<Integer> move(Board board) {
        // NOTE: not used in current implementation
        return new Pair<Integer>(-1, -1);
    }
}
