package com.example.car_app;

public interface JoyStickListener {

    //Percents allows to see how much motor power should be used
    //source - if two or more joysticks are added, this allows what joystick the movement is comming from
    void onJoystickMoved(float xPercent,float yPercent,int source);
}
