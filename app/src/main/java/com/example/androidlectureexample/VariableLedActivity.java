package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class VariableLedActivity extends AppCompatActivity {
    String TAG = "VariableLedActivity";
    TextView tvValue;
    SeekBar sbLED;
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    final SharedObj sharedObj = new SharedObj();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variable_led_activity);

        tvValue = findViewById(R.id.tvValue);
        sbLED = findViewById(R.id.sbLED);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("70.12.60.100", 1234);
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    printWriter = new PrintWriter(socket.getOutputStream());
                    Thread thread1 = new Thread(new ReceiveRunnable1(bufferedReader));
                    thread1.start();
                    Log.v(TAG, "Connection Situation==" + socket.isConnected());
                    while (true) {
                        String msg = sharedObj.pop();
                        printWriter.println(msg);
                        printWriter.flush();
                    }
                } catch (IOException e) {
                    Log.v(TAG, "IOException==" + e);
                }
            }
        });
        thread.start();
        sbLED.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.v(TAG, "onProgressChanged_progress==" + progress);
                Log.v(TAG, "onProgressChanged_fromUser==" + fromUser);
                sharedObj.put(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    class SharedObj {
        Object monitor = new Object();
        LinkedList<String> list = new LinkedList<>();

        public void put(String line) {
            synchronized (monitor) {
                list.addLast(line);
                Log.v(TAG, "SharedObject_put()==" + line);
                monitor.notify();
            }
        }

        public String pop() {
            String result = "";
            synchronized (monitor) {
                if (list.isEmpty()) {
                    try {
                        monitor.wait();
                        result = list.removeFirst();
                    } catch (InterruptedException e) {
                        Log.v(TAG, "InterruptedException==" + e);
                    }
                } else {
                    Log.v(TAG, "SharedObject_pop()==" + list.getFirst());
                    result = list.removeFirst();
                }
            }
            return result;
        }
    }

    public class ReceiveRunnable1 implements Runnable {
        BufferedReader bufferedReader;

        ReceiveRunnable1(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
        }

        @Override
        public void run() {
            String msg = "";
            try {
                while (true) {
                    msg = bufferedReader.readLine();
                    Log.v(TAG, "ReceiveRunnable_msg== " + msg);
                    tvValue.setText(msg);
                }
            } catch (IOException e) {
                Log.v(TAG, "ReceiveRunnable_IOException== " + e);
            }
        }
    }
}
