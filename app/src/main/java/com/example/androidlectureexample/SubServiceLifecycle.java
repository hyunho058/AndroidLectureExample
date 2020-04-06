package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SubServiceLifecycle extends Service {
    String TAG = "SubServiceLifecycle";
    private  Thread thread;

    public SubServiceLifecycle() {
    }
    //Service객체가 생성될떄 호출
    @Override
    public void onCreate() {
        Log.v(TAG,"onCreate()==Service객체가 생성될떄 호출");
        super.onCreate();
    }
    //실제 서비스 동작을 수행하는 부분  conCreate() -> onStartCommend()
    //Service가 하는 일은 1초간격으로 1부터 시작해서 10까지 숫자를 Log로 출력 하는 로직 작성
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG,"onStartCommand()==실제 서비스 동작을 수행하는 부분");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread가 시작되면 1초 동작하고 sleep 하고 log를 이용해 숫자 출력
                for(int i=0; i<100; i++){
                    try {
                        Thread.sleep(1000);
                        //sleep을 하려고 할때 interrup가 걸려있으면 Exception 이 발생한다
                        //Exception 이 발생하면 catch로 넘어가서 break를 만나면 for Loop가 종료 된다
                        Log.v(TAG,"현재 숫자=="+i);
                    }catch (Exception e){
                        Log.v(TAG,e.toString());
                        break;
                    }
                }
            }
        });
        //Thread가 가지고 있는 run() method가 호출
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }
    //Service객체가 메모리에서 사라질때 호출
    //stopService()가 호출되면 onDestroy()가 호출
    @Override
    public void onDestroy() {
        Log.v(TAG,"onDestroy()==Service객체가 메모리에서 사라질때 호출");
        if(thread != null && thread.isAlive()){
            thread.interrupt();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
