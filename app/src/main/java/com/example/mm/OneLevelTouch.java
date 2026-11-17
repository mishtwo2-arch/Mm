package com.example.mm;

import android.view.MotionEvent;

public class OneLevelTouch extends Touch {

    public OneLevelTouch(GameView gameView, Player player) {
        super(gameView, player);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, float quarter) {
        int action = event.getActionMasked();
        int index = event.getActionIndex();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                float x = event.getX(index);
                int id = event.getPointerId(index);

                gameView.resetFrameTime();

                if (x < quarter * 2 && movePointerId == -1) {

                    movePointerId = id;

                    if (x < quarter) {
                        player.moveDir = -1;
                        gameView.changeLevel(101);
                    } else {
                        player.moveDir = 1;
                        gameView.changeLevel(102);
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
                            gameView.changeLevel(101);
                        } else if (moveX < quarter * 2) {
                            player.moveDir = 1;
                            gameView.changeLevel(102);
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
        return true;
    }
    }
