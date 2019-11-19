package com.nikitosoleil.battleship;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    Game game;
    Drawer drawer;
    InputManager inputManager;

    public GameView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        drawer = new Drawer(this, getHolder());
        game = new Game(drawer);
        inputManager = new InputManager(drawer, game);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputManager.onTouch(event.getX(), event.getY());
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawer.initialize(this, holder);
        drawer.draw(game.getBotBoard(), false, "YOUR TURN");
        inputManager = new InputManager(drawer, game);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}