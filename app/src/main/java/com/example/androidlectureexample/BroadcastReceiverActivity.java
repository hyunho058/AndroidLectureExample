package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BroadcastReceiverActivity extends AppCompatActivity {
    String TAG= "BroadcastReceiverActivity";
    Button btnBroadcastRegister;
    Button btnBroadcastunRegister;
    Button btnSendBroadcast;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        btnBroadcastRegister=findViewById(R.id.btnBroadcastRegister);
        btnBroadcastRegister.setOnClickListener(mClick);
        btnBroadcastunRegister=findViewById(R.id.btnBroadcastunRegister);
        btnBroadcastunRegister.setOnClickListener(mClick);
        btnSendBroadcast=findViewById(R.id.btnSendBroadcast);
        btnSendBroadcast.setOnClickListener(mClick);
    }

    View.OnClickListener mClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnBroadcastRegister:
                    Log.v(TAG,"onClick=="+btnBroadcastRegister.getText());
                    //Broadcast Receiver 호출
                    //Broadcast Receiver 객체를 만들어 IntentFilter 와 함께 시스템에 등록
                    //1. IntentFilter 생성
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("MY_BROADCAST_SIGNAL");
                    //2.Broadcast Receiver 객체 생성
                    broadcastReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Log.v(TAG,"onReceive()");
                            //Receiver 가 신호를 받게 되면 이 부분이 호출
                            //해당 신호를 받게 되면 Logic 처리
                            if(intent.getAction().equals("MY_BROADCAST_SIGNAL")){
                                Toast.makeText(getApplicationContext(),"신호 받음",
                                        Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"신호 XX",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    //3. Filter와 함께 Broadcast Receiver를 등록
                    registerReceiver(broadcastReceiver,intentFilter);
                    break;
                case R.id.btnBroadcastunRegister:
                    Log.v(TAG,"onClick=="+btnBroadcastunRegister.getText());
                    //등록 해제
                    unregisterReceiver(broadcastReceiver);
                    break;
                case R.id.btnSendBroadcast:
                    Log.v(TAG,"onClick=="+btnSendBroadcast.getText());
                    //onClick 시 Broadcast 를 임의로 발생
                    Intent intent = new Intent("MY_BROADCAST_SIGNAL");
                    sendBroadcast(intent);
                    break;
            }
        }
    };
}
