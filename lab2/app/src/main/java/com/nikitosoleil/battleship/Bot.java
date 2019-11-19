package com.nikitosoleil.battleship;

import java.util.Random;

public class Bot implements Rival {
    Random rand;

    public Bot() {
        rand = new Random();
    }

    public Board initial() {
        Board board = new Board(Game.n);
        board.randomize();
        return board;
    }

    public Pair<Integer> move(Board board) {
        int x, y;
        do {
            x = rand.nextInt(Game.n);
            y = rand.nextInt(Game.n);
        }
        while (!board.moveValid(x, y));
        Logger.log(String.format("Bot move is: %s %s", x, y));
        return new Pair<Integer>(x, y);
    }
}
