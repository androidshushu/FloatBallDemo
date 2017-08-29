package com.example.sy0317.floatballdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyFloatService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        FloatViewManager manager = FloatViewManager.getInstance(this);
        manager.showFloatCircleView();
        super.onCreate();
    }
}
