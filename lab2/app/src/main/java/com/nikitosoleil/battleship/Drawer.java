package com.nikitosoleil.battleship;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;

import java.lang.Math;

public class Drawer {
    static final float margin = 50;
    static final float fontSize = 96;
    float length, step;
    float lineWidth = 10;
    Pair<Float> topLeft, bottomRight, topTextCorner, bottomTextCorner;
    SurfaceHolder holder;
    Board board;
    boolean permission;
    String topText;

    public Drawer(GameView gameView, SurfaceHolder holder) {
        initialize(gameView, holder);
    }

    public void initialize(GameView gameView, SurfaceHolder holder) {
        this.holder = holder;

        float width = gameView.getWidth(), height = gameView.getHeight();
        length = Math.min(width, height) - 2 * margin;
        step = length / Game.n;
        topLeft = new Pair<Float>((width - length) / 2, (height - length) / 2);
        bottomRight = new Pair<Float>(width - topLeft.x, height - topLeft.y);

        topTextCorner = new Pair<Float>(margin, topLeft.y / 2 + fontSize / 2);
        bottomTextCorner = new Pair<Float>(margin, height - margin);

        Logger.log(String.format("boardView initialized, length:    %s, step: %s, topLeft.x: %s, topLeft.y: %s, bottomRight.x: %s, bottomRight.y: %s", length, step, topLeft.x, topLeft.y, bottomRight.x, bottomRight.y));
    }

    public void update(Board board, boolean permission, String topText) {
        this.board = board;
        this.permission = permission;
        this.topText = topText;
    }

    public void draw() {
        Canvas c = holder.lockCanvas();
        draw(c);
        holder.unlockCanvasAndPost(c);
    }

    private void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(fontSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        System.out.println(topTextCorner.x + " " + topTextCorner.y);
        canvas.drawText(topText, topTextCorner.x, topTextCorner.y, textPaint);

        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(lineWidth);

        for (int i = 0; i <= Game.n; ++i) {
            canvas.drawLine(topLeft.x + i * step, topLeft.y, bottomRight.x + i * step, bottomRight.y, linePaint);
            canvas.drawLine(topLeft.x, topLeft.y + i * step, bottomRight.x, bottomRight.y + i * step, linePaint);
        }

        Paint cellPaint = new Paint();

        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                cellPaint.setColor(getColor(board.getState(i, j)));

                canvas.drawRect(topLeft.x + i * step + lineWidth / 2,
                        topLeft.y + j * step + lineWidth / 2,
                        topLeft.x + (i + 1) * step - lineWidth / 2,
                        topLeft.y + (j + 1) * step - lineWidth / 2,
                        cellPaint);
            }
        }
    }

    private int getColor(Board.CellState cellState) {
        if (cellState == Board.CellState.TRIED)
            return Color.GRAY;
        else if (cellState == Board.CellState.FOUND)
            return Color.GREEN;
        else if (cellState == Board.CellState.DESTROYED)
            return Color.RED;
        else if (cellState == Board.CellState.PRESENT && permission)
            return Color.BLUE;
        return Color.WHITE;
    }

}