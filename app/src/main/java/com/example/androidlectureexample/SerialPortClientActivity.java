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
import java.net.Socket;
import java.net.UnknownHostException;

public class SerialPortClientActivity extends AppCompatActivity {
    String TAG = "SerialPortClientActivity";
    TextView tvLED;
    Button btnOn;
    Button btnOff;
    Button btnConn;

    Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    Receiverunnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port_client);
        tvLED = findViewById(R.id.tvLED);
        btnConn=findViewById(R.id.btnConn);
        btnConn.setOnClickListener(mClick);
        btnOn = findViewById(R.id.btnOn);
        btnOn.setOnClickListener(mClick);
        btnOff = findViewById(R.id.btnOff);
        btnOff.setOnClickListener(mClick);

    }
    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnConn:
                    Log.v(TAG,"onClick()== "+btnConn.getText());
                    Thread thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                socket = new Socket("70.12.60.100",5566);
                                printWriter = new PrintWriter(socket.getOutputStream());
                                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력
                                Log.v(TAG,"onClick()== ConnSuccess");
                                runnable = new Receiverunnable(bufferedReader);
                                Thread thread = new Thread(runnable);
                                thread.start();
                            } catch (UnknownHostException e1) {
                                Log.v(TAG," UnknownHostException=="+e1);
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                Log.v(TAG," IOException=="+e1);
                                e1.printStackTrace();
                            }
                        }
                    });
                    thread1.start();
                    break;
                case R.id.btnOn:
                    Thread tt = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            printWriter.println(btnOn.getText());
                            printWriter.flush();
                            Log.v(TAG,"printWriter_Click =="+btnOn.getText());
                            tvLED.setText("LED ON");
                        }
                    });
                    tt.start();
                    break;
                case R.id.btnOff:
                    Thread ttt = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            printWriter.println(btnOff.getText());
                            printWriter.flush();
                            Log.v(TAG,"printWriter_Click =="+btnOn.getText());
                            tvLED.setText("LED OFF");
                        }
                    });
                    ttt.start();
                    break;
            }
        }
    };
    class Receiverunnable implements Runnable {
        BufferedReader bufferedReader;

        Receiverunnable(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
        }
        @Override
        public void run() {
            Log.v(TAG, "SerialRunnable_num()");
            String msg = "";
            try {
                while (true) {
                    msg = bufferedReader.readLine();
                    Log.v(TAG,"SerialRunnable_num()_msg============"+msg);
                    if (msg == "0") {
                        tvLED.setText("LED OFF");
                        continue;
                    }
                    if(msg == "1") {
                        tvLED.setText("LED ON");
                        continue;
                    }
                }
            } catch (IOException e) {
                Log.v(TAG,"run()_IOException=="+e);
            }
        }
    }
}
