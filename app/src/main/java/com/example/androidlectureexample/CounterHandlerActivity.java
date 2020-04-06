package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CounterHandlerActivity extends AppCompatActivity {
    TextView tv_01;
    Button btnStart;
    Button btnSecond;
    ProgressBar pbCounter;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_handler);

        pbCounter=findViewById(R.id.pbCounter);
        tv_01=findViewById(R.id.tv_01);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(mClick);
        btnSecond = findViewById(R.id.btnSecond);
        btnSecond.setOnClickListener(mClick);
        //데이터를 주고받는 역할을 수행하는 Handler객체 생성
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //받은 데이터를 이용해 화면 처리
                Bundle bundle = msg.getData();
                String count = bundle.getString("count");
                pbCounter.setProgress(Integer.parseInt(count));
            }
        };
        //연산시작이라는 버튼을 클릭했을 때 로직처리하는 Thread를 생성
    }

    TextView.OnClickListener mClick = new TextView.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnStart:
                    Log.v("CounterLogActivity","onClick btnStart====="+btnStart);
                    //Thread 생성
                    //Thread에게 Activity와 데이터 통신을 할 수 있는 handler객체를 전달.

                    Thread handlerThread = new Thread(new HandlerThread(handler));
                    handlerThread.start();
                    break;
                case R.id.btnSecond:
                    Log.v("CounterLogActivity","onClick btnSecond====="+btnSecond);
                    Toast.makeText(getApplicationContext(),"엽엽",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
//로직 처리를 담당하는 Thread를 위한 Runnable interface구현한 class
class HandlerThread implements Runnable{
    private  Handler handler;

    HandlerThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        long sum = 0;
        for(long i=0; i<10000000000L ; i++){
            sum += i;
            if(i % 100000000 == 0) {
                Log.v("CounterRunnable","ifififififififi");
                long loop = i / 100000000;
                Bundle bundle = new Bundle();
                bundle.putString("count",String.valueOf(loop));
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }
    }
}
