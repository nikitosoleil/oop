package com.nikitosoleil.battleship;

public class InputManager {
    private Drawer drawer;
    private Game game;

    public InputManager(Drawer drawer, Game game) {
        this.drawer = drawer;
        this.game = game;
    }

    private static int getIndex(float start, float end, int count, float x) {
        if (x > start && x < end)
            return (int) Math.floor((x - start) * count / (end - start));
        else
            return -1;
    }

    public void onTouch(float x, float y) {
        Logger.log("onTouch: " + x + " " + y);
        int i = getIndex(drawer.topLeft.x, drawer.bottomRight.x, Game.n, x);
        int j = getIndex(drawer.topLeft.y, drawer.bottomRight.y, Game.n, y);
        if (i >= 0 && j >= 0) {
            Logger.log("action: " + i + " " + j);
            game.playerMove(i, j);
        }
    }
}
