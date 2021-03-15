package com.example.simplerun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.ContextCompat;

public class Runner {
    private static final float Y = 600.0f; // 그라운드: 점프하고 난 후 제자리로 돌아오기 위하여 저장
    private static final float V = 50.0f; // 점프 속도
    private static final float G = 3.0f; // 중력 가속도


    private Bitmap image;
    private int count; // animation 0 ~ 3
    private Paint paint;
    private Rect src;
    private RectF dst;
    private boolean jump;
    private float v;  // 속도: 점프에 적용
    private float y;  // y-좌표: 점프에 적용

    public Runner(Context context) {
        BitmapDrawable d = (BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.runner);
        image = d.getBitmap();
        paint = new Paint();
        count = 0;
        src = new Rect(0, 0, 100, 100);  // 이미지 크기가 100x100 이다
        dst = new RectF(200, Y, 300, Y+100);  // 러너가 그려질 위치
        jump = false;
        y = Y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, src, dst, paint);
    }

    public void move() {
        count += 1;
        if (count == 12) // 너무 빨라서 세 번에 한 번만 애니메이션 적용
            count = 0;
        src.offsetTo(100 * (count / 3), 0); // 너무 빨라서 세 번에 한 번만 애니메이션 적용
        if (jump) {
            v -= G;
            y -= v;
            if(y > Y) {
                y = Y;
                jump = false;
            }
            dst.offsetTo(200, y);  // 왼쪽 위 꼭지점만 지정한다. 크기는 변하지 않는다.
        }
    }

    public void startJump() {
        jump = true;
        v = V;
    }
}
