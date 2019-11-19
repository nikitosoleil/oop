package com.nikitosoleil.battleship;

public class Player implements Rival {
    public Board initial() {
        // NOTE: not used in current implementation
        Board board = new Board(Game.n);

        // TODO: implement
        board.randomize();

        assert board.stateValid();
        return board;
    }

    public Pair<Integer> move(Board board) {
        // NOTE: not used in current implementation
        // TODO: implement
        return new Pair<Integer>(-1, -1);
    }
}
