package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class TouchEvent extends AppCompatActivity {
    String TAG = "TouchEvent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(this, "짜장면", Toast.LENGTH_SHORT).show();
//        return super.onTouchEvent(event);
//    }
    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.v(TAG,"onTouch");
            return false;
        }
    };
    View.OnLongClickListener longClickListener = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            Log.v(TAG,"onLongClick");
            return false;
        }
    };
}
