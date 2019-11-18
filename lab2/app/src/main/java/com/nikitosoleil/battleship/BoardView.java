package com.nikitosoleil.battleship;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.lang.Math;

public class BoardView {
    static final float margin = 100;
    float length, step;
    float lineWidth = 10;
    Coordinates topLeft, bottomRight;
    Board board;

    public BoardView(GameView gameView, Board board) {
        this.board = board;

        float width = gameView.getWidth(), height = gameView.getHeight();
        length = Math.min(width, height) - 2 * margin;
        step = length / board.n;
        topLeft = new Coordinates((width - length) / 2, (height - length) / 2);
        bottomRight = new Coordinates(width - topLeft.x, height - topLeft.y);

        Logger.log(String.format("boardView initialized, length: %s, step: %s, topLeft.x: %s, topLeft.y: %s, bottomRight.x: %s, bottomRight.y: %s", length, step, topLeft.x, topLeft.y, bottomRight.x, bottomRight.y));
    }

    public void draw(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(lineWidth);

        for (int i = 0; i <= board.n; ++i) {
            canvas.drawLine(topLeft.x + i * step, topLeft.y, bottomRight.x + i * step, bottomRight.y, linePaint);
            canvas.drawLine(topLeft.x, topLeft.y + i * step, bottomRight.x, bottomRight.y + i * step, linePaint);
        }

        Paint cellPaint = new Paint();

        for (int i = 0; i < board.n; ++i) {
            for (int j = 0; j < board.n; ++j) {
                if (board.userState[i][j] == Board.cellState.TRIED)
                    cellPaint.setColor(Color.RED);
                else
                    cellPaint.setColor(Color.GREEN);

                canvas.drawRect(topLeft.x + i * step + lineWidth / 2,
                        topLeft.y + j * step + lineWidth / 2,
                        topLeft.x + (i + 1) * step - lineWidth / 2,
                        topLeft.y + (j + 1) * step - lineWidth / 2,
                        cellPaint);
            }
        }
    }

}
