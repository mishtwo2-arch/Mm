package com.example.mm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {

    private float playerX = 200;
    private float playerY = 200;
    private float playerRadius = 50;
    public int moveDir = 0;
    private float playerSpeed = 600;
    private float fallSpeed = 0;
    private float jumpPower = 1200;
    private float gravity = 2000;
    private boolean onGround = false;

    private final Paint paint = new Paint();

    public void jump() {
        if (onGround) {
            fallSpeed = -jumpPower;
            onGround = false;
        }
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawCircle(playerX, playerY, playerRadius, paint);
    }

    public void update(float deltaTime, Platform[] platforms, float screenWidth) {
        if (!onGround) {
            fallSpeed += gravity * deltaTime;
            playerY += fallSpeed * deltaTime;

            for (Platform p : platforms) {
                if (
                        fallSpeed < 0 &&
                                playerX + playerRadius > p.x &&
                                playerX - playerRadius < p.x + p.width &&
                                playerY - playerRadius < p.y + p.height &&
                                playerY + playerRadius > p.y + p.height
                ) {
                    playerY = p.y + p.height + playerRadius;
                    fallSpeed = 0;
                }
            }
        }

        if (moveDir != 0) {
            playerX += moveDir * playerSpeed * deltaTime;
        }

        if (playerX < playerRadius) {
            playerX = playerRadius;
        }

        if (playerX > screenWidth - playerRadius) {
            playerX = screenWidth - playerRadius;
        }

        onGround = false;

        for (Platform pg : platforms) {
            if (
                    fallSpeed >= 0 &&
                            playerY + playerRadius >= pg.y &&
                            playerY - playerRadius < pg.y &&
                            playerX + playerRadius > pg.x &&
                            playerX - playerRadius < pg.x + pg.width
            ) {
                playerY = pg.y - playerRadius;
                fallSpeed = 0;
                onGround = true;
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



    }
}
