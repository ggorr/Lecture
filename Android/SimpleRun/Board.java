package com.example.simplerun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View implements Runnable, View.OnTouchListener {
    private Paint paint;
    private Background back;
    private boolean started;
    private Thread thread;
    private boolean run;
    private Runner runner;

    public Board(Context context) {
        super(context);
        back = new Background(context);
        started = false;
        thread = new Thread(this);
        runner = new Runner(context);
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!started) {
            started = true;
            back.initialize(getWidth(), getHeight());
            run = true;
            thread.start();
        }
        back.draw(canvas);
        runner.draw(canvas);
    }

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            back.move();
            runner.move();
            postInvalidate();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        runner.startJump();
        return false;
    }
}
