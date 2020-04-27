package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class SerialPortClientActivity extends AppCompatActivity {
    String TAG = "SerialPortClientActivity";
    //Compon
    TextView tvStatus;
    Button btnOn;
    Button btnOff;
    //Connection Variable
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    final SharedObject sharedObject = new SharedObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port_client);
        //공용 객체 생성


        /**
         * Java Network Server Connection
         * 'Activity(UI Thread)'에서 Network 처리코드를 사용할수 없다.*
         * 별도의 Thread를 이용해서 Connection 처리
         * 1.Runnable Object Create
         * 2.Thread 객체를 생성후 실행
         */
        //1Runnable Object Create
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("70.12.60.100",1234);
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    printWriter = new PrintWriter(socket.getOutputStream());
                    Log.v(TAG,"Connection Socket== "+socket.isConnected());
                    //데이터를 넘겨주는 걸 반복적으로 진행
                    while (true){
                        String msg = sharedObject.pop();
                        printWriter.println(msg);
                        printWriter.flush();
                    }

                } catch (IOException e) {
                    Log.v(TAG,"Connection Socket_IOException=="+e);
                    e.printStackTrace();
                }
            }
        });
        //2Thread 객체를 생성후 실행
        thread.start();

        tvStatus = findViewById(R.id.tvStatus);
        btnOn = findViewById(R.id.btnOn);
        btnOn.setOnClickListener(mClick);
        btnOff = findViewById(R.id.btnOff);
        btnOff.setOnClickListener(mClick);

    }
    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(TAG, "onClick==" + v.getId());
            switch (v.getId()) {
                case R.id.btnOn:
                    //Thread가 사용하는 곡용객체를 이용해서 메시지 전달
                    //공용객체에 데이터 입력
                    sharedObject.put("ON");
                    break;
                case R.id.btnOff:
                    sharedObject.put("OFF");
                    break;
            }
        }
    };
    //Thread가 사용할 공용객체를 만들기 위한 class
    class SharedObject{
        Object monitor = new Object();
        LinkedList<String>list = new LinkedList<>();

        public void put(String line){
            synchronized (monitor){
                list.addLast(line); // LinkedList 끝에 데이터를 넣는다
                Log.v(TAG,"SharedObject_put()=="+line);
                monitor.notify(); //가지고 있는 모니터를 놔주고 wait()이 풀리면서 코드 진행
            }
        }
        public String pop(){
            String result = "";
            synchronized (monitor){
                if (list.isEmpty()){
                    //list안에 문자열이 없으니까 일시 대기 (데이터가 들어올떄까지 대기)
                    //Monitor 를 이용한 wait()을 이용해 일시정지(Locking)
                    try {
                        monitor.wait(); //모니터를 잡고있다고 wait()이 걸리면 모니터를 놓고 모니터에 대해 notify() 가 올떄까지 기다린다
                        result = list.removeFirst(); // 큐구조이기 때문에 앞에 데이터를 빼간다
                    }catch (InterruptedException e){
                        Log.v(TAG,"InterruptedException=="+e);
                    }
                }else {
                    result = list.removeFirst();
                    Log.v(TAG,"SharedObject_put()=="+list);
                }
            }
            return result;
        }
    }

    class CommunicationRunnable implements Runnable{
        String line = "";
        CommunicationRunnable(String line){
            this.line=line;
        }
        @Override
        public void run() {
            printWriter.println(line);
            printWriter.flush();
        }
    }
}
