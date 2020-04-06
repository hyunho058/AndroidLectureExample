package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SwipeActivity extends AppCompatActivity {
    LinearLayout linealLayout;
    double x1, x2;
    String TAG = "SwipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        linealLayout = findViewById(R.id.linealLayout);
        linealLayout.setOnTouchListener(onTouchListener);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.v("SwipeActivity","onTouch");

            if(event.getAction() ==MotionEvent.ACTION_DOWN){
                x1 = event.getX();
            }
            if(event.getAction() ==MotionEvent.ACTION_UP){
                x2 = event.getX();
                if(x1<x2){
                    Log.v(TAG,"onTouchListener_Right");
                }else{
                    Log.v(TAG,"onTouchListener_Left");
                }
            }
            return true;
        }
    };
}
