package com.example.mm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private final Platform[] platforms;

    private float playerX = 200;
    private float playerY = 200;
    private float playerRadius = 50;
    private int moveDir = 0;
    private long lastFTime = 0;
    private float playerSpeed = 600;
    private float fallSpeed = 800;
    private boolean onGround = false;
    private boolean isJumping = false;
    private float startJump = 0;
    private float jumpHeight = 300;
    private float jumpSpeed = 800;
    private float third;



    private final Paint paint = new Paint();
    private final Paint buttonPaint = new Paint();

    public GameView(Context context) {
        super(context);

        platforms = new Platform[] {
                new Platform(0, 700, 1000, 500),
                new Platform(1000, 900,700,1000),
                new Platform(1700, 800, 1000, 500),
                new Platform(0, 300, 200, 200)
        };
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        third = getWidth() / 3f;

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

        if (isJumping){
            playerY -=jumpSpeed * deltaTime;

            for(Platform p : platforms){
                if(
                   playerX + playerRadius > p.x &&
                   playerX - playerRadius < p.x+p.width &&
                           playerY - playerRadius < p.y + p.height &&
                           playerY + playerRadius > p.y + p.height
                ){
                    playerY = p.y+p.height+playerRadius;
                    isJumping = false;
                }
            }

            if(playerY <= startJump - jumpHeight){
                playerY = startJump - jumpHeight;
                isJumping = false;
            }
        }
        else if(!onGround) {
            playerY+=fallSpeed*deltaTime;
        }



        if (moveDir != 0) {
            playerX += moveDir * playerSpeed * deltaTime;
            postInvalidateOnAnimation();
        }


        for(Platform p : platforms) {
            p.rend(canvas);
        }

        if (playerX < 0+playerRadius) {
            playerX = 0+playerRadius;
        }
        if (playerX > getWidth()-playerRadius){
            playerX = getWidth()-playerRadius;
        }

        onGround = false;

        for(Platform pg : platforms){
            if(
                playerY+playerRadius >= pg.y &&
                playerY-playerRadius < pg.y &&
                playerX+playerRadius > pg.x &&
                playerX-playerRadius < pg.x+pg.width
            ) {
                playerY=pg.y-playerRadius;
                onGround=true;
                postInvalidateOnAnimation();
            }
        }

        for (Platform pv : platforms) {
            if (
                    playerX - playerRadius < pv.x + pv.width &&
                            playerX - playerRadius > pv.x &&
                            playerY + playerRadius > pv.y &&
                            playerY - playerRadius < pv.y + pv.height
            ) {
                playerX = pv.x + pv.width + playerRadius;
            }
        }

        for (Platform pv : platforms) {
            if (
                    playerX + playerRadius > pv.x &&
                            playerX - playerRadius < pv.x + pv.width &&
                            playerY + playerRadius > pv.y &&
                            playerY - playerRadius < pv.y + pv.height
            ) {
                playerX = pv.x - playerRadius;
            }
        }

            if(onGround==false){
                postInvalidateOnAnimation();
            }



        paint.setColor(Color.GREEN);

        canvas.drawCircle(playerX, playerY, playerRadius, paint);

        buttonPaint.setColor(Color.MAGENTA);

        Path lPath = new Path();

        lPath.moveTo (200, 900);
        lPath.lineTo(300, 850);
        lPath.lineTo(600, 850);
        lPath.lineTo(600, 950);
        lPath.lineTo(300, 950);
        lPath.close();

        Path  pPath = new Path();

        pPath.moveTo(third*2-200, 900);
        pPath.lineTo(third*2-300, 850);
        pPath.lineTo(third*2-600, 850);
        pPath.lineTo(third*2-600, 950);
        pPath.lineTo(third*2-300, 950);
        pPath.close();

        canvas.drawPath(lPath, buttonPaint);
        canvas.drawPath(pPath, buttonPaint);

        lastFTime = currentTime;

    }

    public void jump() {
        if(onGround && !isJumping) {
            isJumping = true;
            startJump = playerY;
            onGround = false;
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastFTime = System.nanoTime();

                if (event.getX() < third) {
                    moveDir = -1;
                } else if (event.getX() < third * 2) {
                    moveDir = 1;
                }
                else if (event.getX() < third * 3){
                    jump();
                }
                break;

            case MotionEvent.ACTION_MOVE:


                if (event.getX() < third) {
                    moveDir = -1;
                } else if (event.getX() < third * 2) {
                    moveDir = 1;
                }
                else if (event.getX() < third * 3){
                    jump();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                moveDir = 0;
                break;
            default:
                moveDir = 0;

        }
        postInvalidateOnAnimation();
        return true;

    }
}