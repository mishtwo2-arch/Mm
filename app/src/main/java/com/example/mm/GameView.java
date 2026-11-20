package com.example.mm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    private static final float GAME_WIDTH = 1920f;
    private static final float GAME_HEIGHT = 1080f;
    private float scale;
    private float offsetX;
    private float offsetY;


    private Player player = new Player();
    private Touch touch;

    private long lastFTime = 0;

    private float quarter;
    private boolean buttonBoolean;
    int levelNum = 0;

    private TestLevel testLevel;
    private Level currentLevel;

    private final Paint buttonPaint = new Paint();


    public void resetFrameTime() {
        lastFTime = System.nanoTime();
    }

    public GameView(Context context) {
        super(context);

        testLevel = new TestLevel();

        changeLevel(levelNum);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        updateScale();

        canvas.drawColor(Color.LTGRAY);

        canvas.save();

        canvas.translate(offsetX, offsetY);
        canvas.scale(scale, scale);

        canvas.clipRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        buttonBoolean = testLevel.button;

        quarter = GAME_WIDTH / 4f;

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

        currentLevel.draw(canvas, quarter);

        if (levelNum >= 100) {
            player.update(deltaTime, currentLevel.platforms, GAME_WIDTH);

            for (Platform p : currentLevel.platforms) {
                p.rend(canvas);
            }

            player.draw(canvas);
        }

        buttonPaint.setColor(Color.MAGENTA);

        if (buttonBoolean && levelNum >= 100) {

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

        canvas.restore();

        lastFTime = currentTime;
        postInvalidateOnAnimation();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (scale <= 0f) {
            return true;
        }

        Matrix matrix = new Matrix();
        matrix.setTranslate(-offsetX, -offsetY);
        matrix.postScale(1f / scale, 1f / scale);

        MotionEvent gameEvent = MotionEvent.obtain(event);
        gameEvent.transform(matrix);

        try {

            if (levelNum == 0 || levelNum == 1) {
                if (gameEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    currentLevel.onTouch(
                            gameEvent.getX(),
                            gameEvent.getY(),
                            quarter
                    );
                }
                return true;
            }

            if (levelNum >= 100 &&
                    gameEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {

                float x = gameEvent.getX();
                float y = gameEvent.getY();

                if (x >= quarter * 4 - 150 &&
                        x <= quarter * 4 - 50 &&
                        y >= 50 && y <= 150) {

                    exitLevel();
                    return true;
                }
            }

            return touch.onTouchEvent(gameEvent, quarter);

        } finally {
            gameEvent.recycle();
        }
    }

    public void changeLevel(int newLevelNum) {
        if (newLevelNum == 0){
            currentLevel = new MineMenu(this);
        } else if (newLevelNum==1) {
            currentLevel = new LevelMenu(this);
        } else if (newLevelNum==100) {
            currentLevel = new ZeroLevel();
        } else if (newLevelNum == 101) {
            currentLevel = new OneLevel();
        } else if (newLevelNum == 102) {
            currentLevel = new TwoLevel();
        } else {
            return;
        }

        levelNum = newLevelNum;
        postInvalidateOnAnimation();

        if (newLevelNum == 102 || newLevelNum == 101) {
            if (!(touch instanceof TwoLevelTouch)) {
                touch = new TwoLevelTouch(this, player);
            }
        } else {
            touch = new Touch(this, player);
        }
    }

    public void exitLevel() {
        changeLevel(0);
        player.resetPosition();
    }

    private void updateScale() {
     float scaleX = getWidth() / GAME_WIDTH;
     float scaleY = getHeight() / GAME_HEIGHT;

     scale = Math.min(scaleX, scaleY);

     offsetX = (getWidth() - GAME_WIDTH*scale)/2f;
     offsetY = (getHeight() - GAME_HEIGHT*scale)/2f;

    }
}
