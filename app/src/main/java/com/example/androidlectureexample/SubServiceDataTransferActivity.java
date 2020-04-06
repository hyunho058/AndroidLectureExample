package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

//Service 실행할때 Service가 존재하지 않는다면 Service를 생성하고 생성하기 위해 생성자가 호출
// onCreate() -> onStartCommand()
//Service 객체가 이미 존재하면
// onStartCommand() 만 호출
public class SubServiceDataTransferActivity extends Service {
    String activityData = "";
    String resultData ="";
    public SubServiceDataTransferActivity() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //로직 처리

        //Activicy로 부터 전달된 intent가 이 method의 인자로 전달
        activityData =intent.getStringExtra("ActivityData");

        //전달받은 데이터를 이ㅛㅇ해서 일반적인 로직처를 진행
        //만약 로직처리가 길어지면 activity가 block된다.

        //결과데이터를 Service에서 생성
        resultData = activityData+"를 받았다";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();

        Intent intent1 = new Intent(getApplicationContext(),ServiceDataTransferActivity.class);
        intent1.putExtra("resultData",resultData);
        //Service에서 Activity 호출, 화면이 없는 Service에서 화면을 가지고 있는 Activity를 호출
        //TASK필요
        //Activity를 새로 생성하는게 아니고 메모리에 존재하는 이전 Activity를 찾아서 실행 =>Flag 2개 추가
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
