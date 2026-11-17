package com.example.mm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Level {

    public Platform[] platforms;

    public void draw(Canvas canvas, float quarter) {
        drawExitButton(canvas, quarter);
    }

    public void onTouch(float x, float y, float quarter) {
    }

    protected void drawExitButton(Canvas canvas, float quarter) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setStrokeCap(Paint.Cap.ROUND);

        // Положение кнопки
        float right = quarter * 4 - 50;
        float top = 50;
        float left = right - 100;

        // Дверь
        canvas.drawLine(left + 60, top + 20, left + 80, top + 20, paint);
        canvas.drawLine(left + 80, top + 20, left + 80, top + 80, paint);
        canvas.drawLine(left + 80, top + 80, left + 60, top + 80, paint);

        // Стрелка влево
        canvas.drawLine(left + 65, top + 50, left + 15, top + 50, paint);
        canvas.drawLine(left + 15, top + 50, left + 30, top + 35, paint);
        canvas.drawLine(left + 15, top + 50, left + 30, top + 65, paint);
    }
}