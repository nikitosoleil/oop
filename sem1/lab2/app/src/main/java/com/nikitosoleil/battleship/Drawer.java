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
    Coordinates<Float> topLeft, bottomRight, topTextCorner;
    SurfaceHolder holder;

    public Drawer(GameView gameView, SurfaceHolder holder) {
        initialize(gameView, holder);
    }

    public void initialize(GameView gameView, SurfaceHolder holder) {
        this.holder = holder;

        float width = gameView.getWidth(), height = gameView.getHeight();
        length = Math.min(width, height) - 2 * margin;
        step = length / Game.n;
        topLeft = new Coordinates<Float>((width - length) / 2, (height - length) / 2);
        bottomRight = new Coordinates<Float>(width - topLeft.x, height - topLeft.y);

        topTextCorner = new Coordinates<Float>(margin, topLeft.y / 2 + fontSize / 2);
    }

    public void draw(Board board, boolean permission, String topText) {
        Canvas canvas = holder.lockCanvas();
        draw(canvas, board, permission, topText);
        holder.unlockCanvasAndPost(canvas);
    }

    private void draw(Canvas canvas, Board board, boolean permission, String topText) {
        canvas.drawColor(Color.BLACK);
        drawText(canvas, topText);
        drawCells(canvas, board, permission);
        drawGrid(canvas);
    }

    private void drawText(Canvas canvas, String topText) {
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(fontSize);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(topText, topTextCorner.x, topTextCorner.y, textPaint);
    }

    private void drawCells(Canvas canvas, Board board, boolean permission) {
        Paint cellPaint = new Paint();

        for (int i = 0; i < Game.n; ++i) {
            for (int j = 0; j < Game.n; ++j) {
                cellPaint.setColor(getColor(board.getState(i, j), permission));

                canvas.drawRect(topLeft.x + i * step,
                        topLeft.y + j * step,
                        topLeft.x + (i + 1) * step,
                        topLeft.y + (j + 1) * step,
                        cellPaint);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(lineWidth);

        for (int i = 0; i <= Game.n; ++i) {
            canvas.drawLine(topLeft.x + i * step, topLeft.y, topLeft.x + i * step, bottomRight.y, linePaint);
            canvas.drawLine(topLeft.x, topLeft.y + i * step, bottomRight.x, topLeft.y + i * step, linePaint);
        }
    }

    private int getColor(Board.CellState cellState, boolean permission) {
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
