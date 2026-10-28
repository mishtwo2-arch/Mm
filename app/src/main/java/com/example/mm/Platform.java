package com.example.mm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Platform {
    float x;
    float y;
    float width;
    float height;

    private final Paint paint = new Paint();

    public Platform(float x, float y, float width, float height){

        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

        paint.setColor(Color.BLACK);
    }

    public void rend(Canvas canvas) {
        canvas.drawRect(
                x,
                y,
                x+width,
                y+height,
                paint
        );
    }
}
