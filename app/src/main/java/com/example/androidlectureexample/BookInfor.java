package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class BookInfor extends AppCompatActivity {
    String TAG = "BookInfor";
    TextView tvTitle;
    Handler handler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_infor);
        tvTitle=findViewById(R.id.tvTitle);
        Intent intent = getIntent();
        tvTitle.setText(intent.getStringExtra("item"));

        Thread thread = new Thread(new BookPrintRunnable(handler, intent.getStringExtra("isbn")));
        thread.start();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

            }
        };

    }
}
class BookPrintRunnable implements Runnable{
    private  Handler handler;
    private  String isbn;

    public BookPrintRunnable(Handler handler, String isbn) {
        this.handler = handler;
        this.isbn = isbn;
    }

    @Override
    public void run() {

    }
}