package com.nikitosoleil.battleship;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

class TestBot implements Rival {
    private Coordinates<Integer> nextMove;
    public boolean restarted;

    public TestBot() {
        init();
    }

    public void init() {
        nextMove = new Coordinates<Integer>(0, 0);
        restarted = true;
    }

    public void setNextMove(Coordinates<Integer> nextMove) {
        this.nextMove = nextMove;
    }

    public Coordinates<Integer> nextMove(Board board) {
        return nextMove;
    }
}

public class GameTest {
    private Game game;
    private TestBot bot;

    @Before
    public void createGame() {
        bot = new TestBot();
        game = new Game(null, bot);
        game.delay = game.finalDelay = 0;
    }

    void initWithDummyBoard() {
        int[][] mask = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 2}};
        Board botBoard = Board.fromMask(mask);
        game.setBotBoard(botBoard);
        Board playerBoard = Board.fromMask(mask);
        game.setPlayerBoard(playerBoard);
        bot.restarted = false;
    }

    @Test
    public void noFinish() {
        initWithDummyBoard();
        game.playerMove(0, 0);
        assertEquals(Board.CellState.TRIED, game.getPlayerBoard().getState(0, 0));
        assertEquals(Board.CellState.TRIED, game.getBotBoard().getState(0, 0));
        assertFalse(bot.restarted);
    }

    @Test
    public void playerFinishes() {
        initWithDummyBoard();
        game.playerMove(9, 9);
        assertTrue(bot.restarted);
    }

    @Test
    public void botFinishes() {
        initWithDummyBoard();
        bot.setNextMove(new Coordinates<Integer>(9, 9));
        game.playerMove(0, 0);
        assertTrue(bot.restarted);
    }
}
