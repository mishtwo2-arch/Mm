package com.example.mm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MineMenu extends Level{
    private GameView gameView;
    private Paint paint = new Paint();

    public MineMenu(GameView gameView) {
        this.gameView = gameView;
    }
        @Override
        public void draw(Canvas canvas, float quarter) {
            paint.setColor(Color.GREEN);
            canvas.drawRect(quarter, 300, quarter * 3, 550, paint);

            paint.setColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize(150);
            canvas.drawText("Играть", quarter*2, 460, paint);
        }

        @Override
        public void onTouch(float x, float y, float quarter) {
            if (x >= quarter && x <= quarter*3 &&
                    y >= 300 && y <= 450) {

                gameView.changeLevel(101);
            }
        }
    }

