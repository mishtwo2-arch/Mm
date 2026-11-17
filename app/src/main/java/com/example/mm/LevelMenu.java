package com.example.mm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LevelMenu extends Level{
    private GameView gameView;
    private Paint paint = new Paint();

    public LevelMenu(GameView gameView) {
        this.gameView = gameView;
    }
    @Override
    public void draw(Canvas canvas, float quarter) {
        paint.setColor(Color.GREEN);
        canvas.drawRect(100, 200, quarter * 2 - 100, 600, paint);
        canvas.drawRect(quarter*2 +100, 200, quarter*4-100, 600, paint);

        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(300);

        canvas.drawText("1", quarter, 460, paint);
        canvas.drawText("2", quarter*3, 460, paint);
    }

    @Override
    public void onTouch(float x, float y, float quarter) {
        if (x >= 100 && x <= quarter * 2 - 100 &&
                y >= 200 && y <= 600) {
            gameView.changeLevel(100);
        }
        if (x >= quarter*2 +100 && x <= quarter*3 &&
                y >= 200 && y <= 600) {
            gameView.changeLevel(101);
        }

    }
}
