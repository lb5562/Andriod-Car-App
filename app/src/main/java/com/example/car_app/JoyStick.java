package com.example.car_app;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

public class JoyStick extends SurfaceView implements SurfaceHolder.Callback {
    public JoyStick(Context context){
        super(context);
    }

    public JoyStick(Context context,AttributeSet attributes,int style){
        super(context,attributes,style);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
