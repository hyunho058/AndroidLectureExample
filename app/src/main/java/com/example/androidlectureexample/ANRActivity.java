package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ANRActivity extends AppCompatActivity {
    TextView tv_01;
    Button btnStart;
    Button btnSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);

        tv_01=findViewById(R.id.tv_01);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(mClic);
        btnSecond = findViewById(R.id.btnSecond);
        btnSecond.setOnClickListener(mClic);
    }

    View.OnClickListener mClic = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnStart:
                    //오랜시간 연산이 수행됨
                    long sum = 0;
                    for(long i=0; i<100000000000L ; i++){
                        sum += i;
                    }
                    Log.v("ANRActivity","sum====="+sum);
                    break;
                case R.id.btnSecond:
                    //Toast Massage 호출
                    Toast.makeText(getApplicationContext(),"얍얍",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
