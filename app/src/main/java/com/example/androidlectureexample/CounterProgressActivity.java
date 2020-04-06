package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CounterProgressActivity extends AppCompatActivity {
    TextView tv_01;
    Button btnStart;
    Button btnSecond;
    ProgressBar pbCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_progress);

        pbCounter=findViewById(R.id.pbCounter);
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
                    Thread counterRunnable = new Thread(new CounterRunnable());
                    counterRunnable.start();
                    break;
                case R.id.btnSecond:
                    Log.v("CounterLogActivity","onClick btnSecond====="+btnSecond);
                    Toast.makeText(getApplicationContext(),"엽엽",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public class CounterRunnable implements  Runnable{
        @Override
        public void run() {
            Log.v("CounterRunnable","???????????");
            //숫자를 더해가면서 progressBar를 진행
            long sum = 0;
            for(long i=0; i<10000000000L ; i++){
                sum += i;
                if(i % 100000000 == 0) {
                    Log.v("CounterRunnable","ifififififififi");
                    long loop = i / 100000000;
                    pbCounter.setProgress((int)loop);
                }
            }
            tv_01.setText("합계="+sum);
            Log.v("CounterLogActivity","sum====="+sum);
        }
    }
}
