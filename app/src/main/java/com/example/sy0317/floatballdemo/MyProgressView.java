package com.example.sy0317.floatballdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/21.
 */

public class MyProgressView extends View{
    private int width = 150;
    private int height = 150;
    private Paint circlePaint;
    private Paint progressPaint;
    private Paint textPaint;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private Path path = new Path();
    private int progress = 50;
    private int max = 100;
    private int currentProgress = 0;
    private int count = 50;
    private Boolean isSingleTap = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public MyProgressView(Context context) {
        super(context);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.argb(0xff,0x8c,0x6c,0x6c));

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(Color.argb(0xff,0x4e,0xc9,0x63));
        progressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25);

        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);

        // TODO: 2017/8/21 编写监听器，监听点击·时间
        final GestureDetector detector = new GestureDetector(new MyGestureDetectorListener());
                setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return detector.onTouchEvent(motionEvent);
                    }
                });
        setClickable(true);

    }
    class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isSingleTap = true;
            startDoubleTapAnimation();
            return super.onDoubleTap(e);
        }

        private void startDoubleTapAnimation() {
            handler.postDelayed(doubleTapRunbale,50);

        }

        private DoubleTapRunnble doubleTapRunbale = new DoubleTapRunnble();
        class DoubleTapRunnble implements Runnable{
            @Override
            public void run() {
                currentProgress++;
                if (currentProgress<=progress){
                    invalidate();
                    handler.postDelayed(doubleTapRunbale,50);
                }else {
                    handler.removeCallbacks(doubleTapRunbale);
                    currentProgress = 0;
                }

            }
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            startSingleTapAnimation();
            isSingleTap = true;
            currentProgress = progress;
            return super.onSingleTapConfirmed(e);
        }

        private SingleTapRunnble singleTaprunnble = new SingleTapRunnble();
        class SingleTapRunnble implements Runnable{
            @Override
            public void run() {

                count --;
                if (count>=0){
                    invalidate();
                    handler.postDelayed(singleTaprunnble,200);
                }else {
                    handler.removeCallbacks(singleTaprunnble);
                    count = 50;
                }
            }
        }
        private void startSingleTapAnimation() {
            handler.postDelayed(singleTaprunnble,200);

        }
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bitmapCanvas.drawCircle(width/2,height/2,width/2,circlePaint);
        path.reset();
        float y =(1 - (float) currentProgress / max)*height;
        path.moveTo(width,y);
        super.onDraw(canvas);
        path.lineTo(width,height);
        path.lineTo(0,height);
        path.lineTo(0,y);
        if (!isSingleTap){
            float d =((float)currentProgress/progress)*10;
            for (int i=0;i<5;i++){
                path.rQuadTo(10,-d,20,0);
                path.rQuadTo(10,d,20,0);
        }
        }else {
            float d = (float)count/50*10;
            if (count %2==0){
                for (int i = 0;i<5;i++){
                    path.rQuadTo(20,-d,40,0);
                    path.rQuadTo(20,d,40,0);
                }
            }else {
                for (int i = 0;i<5;i++){
                    path.rQuadTo(20,d,40,0);
                    path.rQuadTo(20,-d,40,0);

                }

            }
        }
        path.close();
        bitmapCanvas.drawPath(path,progressPaint);
        String text = (int)(((float) currentProgress / max)*100)+"%";
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float textwidth = textPaint.measureText(text);
        float baseLine=height/2-(metrics.ascent+metrics.descent)/2;
        bitmapCanvas.drawText(text,width/2-textwidth/2,baseLine,textPaint);
        canvas.drawBitmap(bitmap,0,0,null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width,height);
    }
}
