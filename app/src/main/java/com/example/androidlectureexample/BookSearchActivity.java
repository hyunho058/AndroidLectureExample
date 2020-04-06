package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BookSearchActivity extends AppCompatActivity {
    String TAG = "BookSearchActivity";
    EditText etSearchBook;
    Button btnSearch;
    ListView lvBookList;
    Handler handler;
    String[] BookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        etSearchBook = findViewById(R.id.etSearchBook);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(mClick);
        lvBookList=findViewById(R.id.lvBookList);

        handler = new Handler(){
            //handleMessage를 overriding하면서 instance를 생성.
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                BookList = bundle.getStringArray("BookList");
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,BookList);
                Log.v("BookSearchActivity","BookList"+BookList);
                lvBookList.setAdapter(adapter);
            }
        };
        lvBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                Intent intent = new Intent(BookSearchActivity.this, BookInfor.class);
                Log.v(TAG,"onItemClick item=="+item);
                Log.v(TAG,"onItemClick item position=="+position);
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });
    }

    ListView.OnClickListener mClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSearch:
                    Log.v(TAG,"onClick_etSearchBook =="+etSearchBook.getText().toString());
                    Thread thread = new Thread(new BookSearchRunnable(handler,etSearchBook.getText().toString()));
                    thread.start();
                    break;
            }
        }
    };
}

class BookSearchRunnable implements Runnable {
    Handler handler;
    String title;

    public BookSearchRunnable(Handler handler, String title) {
        this.handler = handler;
        this.title = title;
    }
    //web Application 호출
    //결과를 받아와서 그 결과를 만든후 handler를 통해 activity에 전달
    @Override
    public void run() {
        //1.
        String url="http://70.12.60.100:8080/bookSearch/BookInfo?keyword="+title;
        Log.v("BookSearchRunnable","url=="+url);
        //2.
        try {
            //3.
            URL objUrl = new URL(url);
            //4.
            HttpURLConnection conn = (HttpURLConnection)objUrl.openConnection();
            //5.
            conn.setRequestMethod("GET");
            //6.
            int responseCode=conn.getResponseCode();
            Log.v("BookSearchRunnable","responseCode=="+responseCode);
            //7.
            // AndroidManifest.xml 설정
            //8.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String readLine = "";
            StringBuffer responseText = new StringBuffer();
            while ((readLine=bufferedReader.readLine())!=null){
                responseText.append(readLine);
            }
            bufferedReader.close();
            Log.v("BookSearchRunnable","responseText=="+responseText.toString());
            //jackson
            ObjectMapper mapper = new ObjectMapper();
            String[] resultArr = mapper.readValue(responseText.toString(),String[].class);
            //activity에 data전달

            Bundle bundle = new Bundle();
            bundle.putStringArray("BookList",resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

        }catch (Exception e){
//            e.printStackTrace();
            Log.v("BookSearchRunnable","BookSearchException=="+e.toString());
        }
    }
}
