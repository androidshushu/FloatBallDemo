package com.example.sy0317.floatballdemo;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/8/22.
 */

public class FloatMenuView extends LinearLayout{
    private LinearLayout ll;
    private TranslateAnimation animation;
    public FloatMenuView(Context context) {
        super(context);
        View root = View.inflate(getContext(),R.layout.float_menu,null);
         ll = root.findViewById(R.id.ll);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(500);
        animation.setFillAfter(true);
        ll.setAnimation(animation);
        root.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                FloatViewManager manager = FloatViewManager.getInstance(getContext());
                manager.hideFloatMenuView();
                manager.showFloatCircleView();
                return false;
            }
        });
        addView(root);
    }
    public void startAnimation(){
        animation.start();


    }
}
