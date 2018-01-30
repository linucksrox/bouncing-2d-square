package com.dalydays.blog.a2dgametutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Owner on 1/30/2018.
 */

class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int xVel = 20;
    private int yVel = 10;
    private int size = 150;
    private int screenWidth;
    private int screenHeight;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        x1 = 10;
        y1 = 10;
        x2 = x1 + size;
        y2 = y1 + size;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        screenWidth = getWidth();
        screenHeight = getHeight();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        if (x2 > screenWidth || x1 < 0) {
            xVel *= -1;
        }
        if (y2 > screenHeight || y1 < 0) {
            yVel *= -1;
        }

        x1 += xVel;
        x2 += xVel;
        y1 += yVel;
        y2 += yVel;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setColor(Color.rgb(250, 0, 0));
            canvas.drawRect(x1, y1, x2, y2, paint);
        }
    }
}
