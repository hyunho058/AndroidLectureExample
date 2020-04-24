package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SerialPortService extends Service {
    Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    String situation="";

    public SerialPortService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        try {
            socket = new Socket("70.12.60.1", 7777);
            printWriter = new PrintWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


        //Activity로 부터 전달된 intent를 통해 keyword를 얻어옴
        String keyword = intent.getStringExtra("keyword");

        //Network연결을 통해 Open API를 호출하는 시간이 걸리는 작업을 수항하는 Thread를 이용해서 처리

        SerialRunnable runnable = new SerialRunnable(situation);
        Thread thread = new Thread(runnable);
        thread.start();

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

    class SerialRunnable implements Runnable{
        String situation;

        SerialRunnable(String situation){
            this.situation = situation;
        }

        @Override
        public void run() {
            Intent intent1 = new Intent(getApplicationContext(), KakaoBookSearchActivity.class);
            intent1.putExtra("situation",situation);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent1);
        }
    }
}
