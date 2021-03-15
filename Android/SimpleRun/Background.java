package com.example.simplerun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.ContextCompat;

// 배경
public class Background {
    private Bitmap image0, image1;
    private int x;
    private Rect src0, src1, dst0, dst1;
    private Paint paint;
    private int currImage;
    private int width, height;

    public Background(Context context) {
        BitmapDrawable d0 = (BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.back0);
        image0 = d0.getBitmap();
        BitmapDrawable d1 = (BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.back1);
        image1 = d1.getBitmap();
        x = 0;
        src0 = new Rect();
        src1 = new Rect();
        dst0 = new Rect();
        dst1 = new Rect();
        paint = new Paint();
    }

    // 초기화
    public void initialize(int width, int height) {
        this.width = width;
        this.height = height;
        currImage = 0;
        src0.set(0, 0, width, image0.getHeight());
        dst0.set(0, 0, width, height);
    }

    // 배경 이미지를 그린다
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image0, src0, dst0, paint);
        canvas.drawBitmap(image1, src1, dst1, paint);
    }

    public void move() {
        x += 10; // 10픽셀 이동
        if (currImage == 0) { //배경 이미지가 image0인 경우
            if (x >= image0.getWidth()) {
                // 배경이 image1로 넘어간다
                x -= image0.getWidth();
                currImage = 1;
                src0.set(0, 0, 0, 0);  // image0은 사용하지 않는다.
                dst0.set(0, 0, 0, 0);  // image0은 사용하지 않는다.
                src1.set(x, 0, x + width, image1.getHeight());
                dst1.set(0, 0, width, height);
            } else if (x + width > image0.getWidth()) {
                // 배경에 image0과 image1이 동시에 그려진다.
                src0.set(x, 0, image0.getWidth(), image0.getHeight());
                dst0.set(0, 0, src0.width(), height); // 화면 왼쪽
                src1.set(0, 0, width - src0.width(), image1.getHeight());
                dst1.set(dst0.right, 0, width, height); // 화면 오른쪽
            } else {
                // 배경이 image0이다
                src0.offsetTo(x, 0);  // 왼쪽 위 꼭지점만 지정한다. 크기는 변하지 않는다.
            }
        } else { //배경 이미지가 image1인 경우
            if (x >= image1.getWidth()) {
                // 배경이 image0으로 넘어간다
                x -= image1.getWidth();
                currImage = 0;
                src1.set(0, 0, 0, 0);  // image1은 사용하지 않는다.
                dst1.set(0, 0, 0, 0);  // image1은 사용하지 않는다.
                src0.set(x, 0, x + width, image0.getHeight());
                dst0.set(0, 0, width, height);
            } else if (x + width > image1.getWidth()) {
                // 배경에 image1과 image0이 동시에 그려진다.
                src1.set(x, 0, image1.getWidth(), image1.getHeight());
                dst1.set(0, 0, src1.width(), height); // 화면 왼쪽
                src0.set(0, 0, width - src1.width(), image0.getHeight());
                dst0.set(dst1.right, 0, width, height); // 화면 오른쪽
            } else {
                // 배경이 image1이다
                src1.offsetTo(x, 0);  // 왼쪽 위 꼭지점만 지정한다. 크기는 변하지 않는다.
            }
        }
    }
}
