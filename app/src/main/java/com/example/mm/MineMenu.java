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
        public void draw(Canvas canvas) {
            paint.setColor(Color.GREEN);
            canvas.drawRect(200, 300, 600, 450, paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("Играть", 300, 390, paint);
        }

        @Override
        public void onTouch(float x, float y) {
            if (x >= 200 && x <= 600 &&
                    y >= 300 && y <= 450) {

                gameView.changeLevel(101);
            }
        }
    }

