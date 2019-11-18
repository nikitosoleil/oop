package com.nikitosoleil.battleship;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    Board board;
    BoardView boardView;
    InputManager inputManager;

    public GameView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

        board = new Board(10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputManager.onTouch(event.getX(), event.getY());
        draw();
        return super.onTouchEvent(event);
    }


    public void draw() {
        Canvas c = holder.lockCanvas();
        draw(c);
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        boardView.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        boardView = new BoardView(this, board);
        inputManager = new InputManager(boardView, board);
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        boardView = new BoardView(this, board);
        inputManager = new InputManager(boardView, board);
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}