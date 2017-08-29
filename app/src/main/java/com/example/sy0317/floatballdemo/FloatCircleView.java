package com.example.sy0317.floatballdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.R.attr.bitmap;
import static android.R.attr.src;

/**
 * Created by Administrator on 2017/8/21.
 */

public class FloatCircleView extends View {
    //宽高写死，就是悬浮球的大小
    public int width = 100;
    public int height = 100;
    private Paint circlePaint;
    private Paint textPaint;
    private String text = "60%";
    private Boolean drag = false;
    private Bitmap bitmap;
    //动态代码通过创建对象的时候
    public FloatCircleView(Context context) {
        super(context);
        /**
         *  如果没有用到自定义属性的话那么画笔的初始化必须放在只有一个参数的方法里面，
         *  不然就会报错的,这里会报空指针异常。
         */
        initPaints();
    }

    //构造函数被配置到布局文件里面
    public FloatCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //构造函数被配置到布局文件里面，并且里面有stylel属性
    public FloatCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initPaints() {
        //设置画圆的画笔的属性
        circlePaint = new Paint();
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAntiAlias(true);

        //设置写字的画笔属性
        textPaint = new Paint();
        textPaint.setTextSize(25);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);

        Bitmap src =  BitmapFactory.decodeResource(getResources(),R.drawable.cat);
        bitmap = Bitmap.createScaledBitmap(src,width,height,true);


    }


    /**
     * 在onMeasure设置绘制的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       setMeasuredDimension(width,height);
    }

    /**
     * 在onDraw里面绘制园
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //测量出文字的（文本框）位置的x，y坐标
        if (drag){
            canvas.drawBitmap(bitmap, 0, 0, null);
        }else {
            canvas.drawCircle(width/2,height/2,height/2,circlePaint);
            float textWidth = textPaint.measureText(text);
            float x = width/2-textWidth/2;
            //测量出文字的y坐标，通过文字的字体测量可以得出。获取getFontMetrics()文字规格
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            float dy = -(metrics.descent+metrics.ascent)/2;
            float y = height/2 + dy;

            canvas.drawText(text,x,y,textPaint);
        }

    }


    public void setDragState(boolean b) {
        drag = b;
        //刷新当前的view
        invalidate();
    }
}
