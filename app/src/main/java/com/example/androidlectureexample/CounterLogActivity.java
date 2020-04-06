package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CounterLogActivity extends AppCompatActivity {
    TextView tv_01;
    Button btnStart;
    Button btnSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_log);

        tv_01=findViewById(R.id.tv_01);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(mClick);
        btnSecond = findViewById(R.id.btnSecond);
        btnSecond.setOnClickListener(mClick);
    }

    View.OnClickListener mClick = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnStart:
                    Log.v("CounterLogActivity","onClick btnStart====="+btnStart);
                    //Thread만들어 실행
//                    MyRunnable myRunnable = new MyRunnable();
//                    Thread thread = new Thread(myRunnable);
//                    thread.start();
                    Thread myRunnable = new Thread(new MyRunnable());
                    myRunnable.start();

                    break;
                case R.id.btnSecond:
                    Log.v("CounterLogActivity","onClick btnSecond====="+btnSecond);
                    Toast.makeText(getApplicationContext(),"엽엽",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public class MyRunnable implements  Runnable{
        @Override
        public void run() {
            long sum = 0;
            for(long i=0; i<10000000000L ; i++){
                sum += i;
            }
            Log.v("CounterLogActivity","sum====="+sum);
        }
    }
}

