package com.example.sy0317.floatballdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/8/21.
 */

public class FloatViewManager {
    private Context context;
    private WindowManager windowManager;//通过WindowManager来操控浮窗体的现实与隐藏以及位置变化
    private FloatCircleView circleView;
    private WindowManager.LayoutParams params;
    private boolean drag = false;
    private FloatMenuView floatMenuView;

    /**
     * 私有的构造函数，传入一个上下文,构造方法用于初始化参数
     * @param context
     */
    private FloatViewManager(final Context context){

        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        circleView = new FloatCircleView(context);

        circleView.setOnTouchListener(circleviewTouchListener);

        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"onclick",0).show();
                //点击的时候就隐藏cricleview,显示菜单栏
                windowManager.removeView(circleView);
                showFloatMenuView();
                floatMenuView.startAnimation();
//                floatMenuView = new FloatMenuView(context);
            }


        });
        floatMenuView = new FloatMenuView(context);
    }


    private View.OnTouchListener circleviewTouchListener = new View.OnTouchListener() {
        private  float startx;
        private float starty;
        private float x0;
        private float y0;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                     startx = motionEvent.getRawX();
                     x0 = motionEvent.getRawX();
                     y0 = motionEvent.getRawY();
                     starty = motionEvent.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = motionEvent.getRawX();
                    float y = motionEvent.getRawY();
                    float dx = x-startx;
                    float dy = y-starty;
                    params.x+=dx;
                    params.y+=dy;
                    circleView.setDragState(true);
                    windowManager.updateViewLayout(circleView,params);
                    startx = x;
                    starty = y;
                    break;
                case MotionEvent.ACTION_UP:
                    float x1 = motionEvent.getRawX();
                    if (x1>getScreenWidth()/2){
                        params.x = getScreenWidth()-circleView.width;
                    }else {
                        params.x = 0;
                    }
                    circleView.setDragState(false);
                    windowManager.updateViewLayout(circleView,params);
                    if (Math.abs(x1-x0)>6){
                        return true;
                    }else {
                        return false;
                    }

                default:
                    break;
            }
            return false;
        }
    };

    public int getScreenWidth(){
        return  windowManager.getDefaultDisplay().getWidth();
    }
    public int getScreenHeight(){
        return windowManager.getDefaultDisplay().getHeight();
    }
    public int getStatusHeight(){
        int height = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            height = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {

        }
        return height;
    }


    private void showFloatMenuView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width =getScreenWidth();
        params.height = getScreenHeight()-getStatusHeight();
        params.gravity = Gravity.BOTTOM|Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.format = PixelFormat.RGBA_8888;

        windowManager.addView(floatMenuView,params);
    }

    //创建一个实例
    private static FloatViewManager instance;

    //浮窗管理是通过上下文来管理的，所以此处要传入一个上下文
    public static FloatViewManager getInstance(Context context){
        if (instance == null){
            //同步代码块
            synchronized (FloatViewManager.class){
                if (instance == null){
                    instance = new FloatViewManager(context);
                }
            }
        }

        return instance;
    }

    /**
     * 显示浮窗小球到窗口
     */
    public void showFloatCircleView(){

        if (params == null){
            params = new WindowManager.LayoutParams();
            params.width = circleView.width;
            params.height = circleView.height;
            params.gravity = Gravity.TOP|Gravity.LEFT;
            params.x = 0;
            params.y = 0;
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
            //不抢焦点
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            params.format = PixelFormat.RGBA_8888;
        }


        windowManager.addView(circleView,params);

    }




    public void hideFloatMenuView() {
        windowManager.removeView(floatMenuView);

    }



}
