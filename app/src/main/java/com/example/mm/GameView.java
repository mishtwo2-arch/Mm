package com.example.mm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private Player player = new Player();
    private Touch touch;

    private final Platform[] platforms;

    private long lastFTime = 0;

    private float quarter;
    private boolean buttonBoolean;

    private TestLevel testLevel;

    private final Paint buttonPaint = new Paint();


    public void resetFrameTime() {
        lastFTime = System.nanoTime();
    }

    public GameView(Context context) {
        super(context);

        testLevel = new TestLevel();

        platforms = testLevel.platforms;

        touch = new Touch(this, player);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        testLevel= new TestLevel();

        buttonBoolean = testLevel.button;

        quarter = getWidth() / 4f;

        long currentTime = System.nanoTime();

        long fTime = currentTime - lastFTime;
        float deltaTime = fTime / 1_000_000_000f;

        if (lastFTime == 0) {
            deltaTime = 0;
        }

        if (deltaTime > 0.016f) {
            deltaTime = 0.016f;
        }

        canvas.drawColor(Color.WHITE);

        player.update(deltaTime, platforms, getWidth());

        for (Platform p : platforms) {
            p.rend(canvas);
        }

        buttonPaint.setColor(Color.MAGENTA);

        if (buttonBoolean == true) {

            Path lPath = new Path();

            lPath.moveTo(200, 900);
            lPath.lineTo(300, 850);
            lPath.lineTo(550, 850);
            lPath.lineTo(550, 950);
            lPath.lineTo(300, 950);
            lPath.close();

            Path pPath = new Path();

            pPath.moveTo(quarter * 2 - 200, 900);
            pPath.lineTo(quarter * 2 - 300, 850);
            pPath.lineTo(quarter * 2 - 550, 850);
            pPath.lineTo(quarter * 2 - 550, 950);
            pPath.lineTo(quarter * 2 - 300, 950);
            pPath.close();

            Path jPath = new Path();

            jPath.moveTo(quarter * 4 - 350, 950);
            jPath.lineTo(quarter * 4 - 600, 950);
            jPath.lineTo(quarter * 4 - 350, 850);
            jPath.lineTo(quarter * 4 - 100, 950);
            jPath.close();

            canvas.drawPath(lPath, buttonPaint);
            canvas.drawPath(pPath, buttonPaint);
            canvas.drawPath(jPath, buttonPaint);
        }

        player.draw(canvas);

        lastFTime = currentTime;
        postInvalidateOnAnimation();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        postInvalidateOnAnimation();

        return touch.onTouchEvent(event, quarter);
    }
}
