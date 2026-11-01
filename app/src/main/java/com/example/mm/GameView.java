
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

    private final Platform[] platforms;

    private long lastFTime = 0;

    private float quarter;

    private int movePointerId = -1;
    private int jumpPointerId = -1;

    private final Paint buttonPaint = new Paint();

    public GameView(Context context) {
        super(context);

        platforms = new Platform[] {
                new Platform(0, 700, 1000, 500),
                new Platform(1000, 900, 700, 1000),
                new Platform(1700, 800, 1000, 500),
                new Platform(0, 300, 200, 200)
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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

        player.draw(canvas);

        Path jPath = new Path();

        jPath.moveTo(quarter * 4 - 350, 950);
        jPath.lineTo(quarter * 4 - 600, 950);
        jPath.lineTo(quarter * 4 - 350, 850);
        jPath.lineTo(quarter * 4 - 100, 950);
        jPath.close();

        canvas.drawPath(lPath, buttonPaint);
        canvas.drawPath(pPath, buttonPaint);
        canvas.drawPath(jPath, buttonPaint);

        lastFTime = currentTime;
        postInvalidateOnAnimation();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();
        int index = event.getActionIndex();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                float x = event.getX(index);
                int id = event.getPointerId(index);

                lastFTime = System.nanoTime();

                if (x < quarter * 2 && movePointerId == -1) {

                    movePointerId = id;

                    if (x < quarter) {
                        player.moveDir = -1;
                    } else {
                        player.moveDir = 1;
                    }

                }

                if (x >= quarter * 2 && jumpPointerId == -1) {
                    jumpPointerId = id;

                    player.jump();

                }



                break;

            case MotionEvent.ACTION_MOVE:

                if (movePointerId != -1) {

                    int moveIndex = event.findPointerIndex(movePointerId);

                    if (moveIndex != -1) {
                        float moveX = event.getX(moveIndex);

                        if (moveX < quarter) {
                            player.moveDir = -1;
                        } else if (moveX < quarter * 2) {
                            player.moveDir = 1;
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                int releasedId = event.getPointerId(index);

                if (releasedId == movePointerId) {
                    movePointerId = -1;

                    player.moveDir = 0;
                }

                if (releasedId == jumpPointerId) {
                    jumpPointerId = -1;

                }

                break;

            case MotionEvent.ACTION_CANCEL:

                movePointerId = -1;
                jumpPointerId = -1;

                player.moveDir = 0;

                break;

            default:
                break;
        }

        postInvalidateOnAnimation();
        return true;
    }
}
