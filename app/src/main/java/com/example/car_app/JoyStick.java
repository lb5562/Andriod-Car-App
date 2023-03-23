package com.example.car_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class JoyStick extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
     private float centerX;
     private float centerY;
    private float baseRad;
    private float hatRad;
    public JoyStick(Context context){
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public JoyStick(Context context,AttributeSet attributes,int style){
        super(context,attributes,style);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }
    public JoyStick(Context context,AttributeSet set){
        super(context,set);
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    private void drawJoyStick(float cordX,float cordY){
        //checks if holder is in a valid state
        if(getHolder().getSurface().isValid()){
            //Opens canvas
            Canvas canvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            //creating a clear canvus
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            colors.setARGB(255,50,50,50); //drawing the base
            canvas.drawCircle(centerX,centerY,baseRad,colors);

            colors.setARGB(255,0,255,0); //drawing hat
            canvas.drawCircle(cordX,cordY,hatRad,colors);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void setUp(){
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        baseRad = Math.min(getWidth(),getHeight())/3;
        hatRad = Math.min(getWidth(),getHeight())/5;
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        setUp();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    //changes the display of the joycon based upon touch position
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
         if(view.equals(this)){
             float displayment = (float) Math.sqrt((Math.pow(motionEvent.getX()-centerX,2))+Math.pow(motionEvent.getY()-centerY,2));
             if(displayment < baseRad){
                 float r = baseRad/displayment;
                 float conX = centerX +(motionEvent.getX()-centerX)*r;
                 float conY = centerY +(motionEvent.getY()-centerY)*r;
                 drawJoyStick(conX, conY);
             }else {
                 //set boundries
                 drawJoyStick(centerX,centerY);
             }
        }
        return  true;
    }
}
