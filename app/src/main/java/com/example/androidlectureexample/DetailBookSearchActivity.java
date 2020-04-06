package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class DetailBookSearchActivity extends AppCompatActivity {
    String TAG ="DetailBookSearchActivity";
    EditText etSearchBook;
    Button btnSearch;
    ListView lvBookList;
    Handler handler;
    BookInfoVO[] bookList;
    String[] title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book_search);

        etSearchBook = findViewById(R.id.etSearchBook);
        lvBookList = findViewById(R.id.lvBookList);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(mClic);

        //Thread가 handler를 통해 Activity에게 데이터를 전달(=sendMessage), Activity가 데이터를 받으면 Handler안에 handlerMessage가 호출된다
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                Bundle bundle = msg.getData();
                bookList = (BookInfoVO[]) bundle.getSerializable("BookList");
                title = new String[bookList.length];
                int i =0;
                //ArrayList안에 VO들을 반복해서 제목만 뽑아서 String[]안에 저장
                for(BookInfoVO vo : bookList){
                    title[i++]=vo.getBtitle();
                }
                Log.v(TAG,"title=="+bookList);
                ArrayAdapter arrayAdapter =  new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,title);
                lvBookList.setAdapter(arrayAdapter);
            }
        };

        lvBookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                Intent intent = new Intent(DetailBookSearchActivity.this, BookInfor.class);
                Log.v(TAG,"onItemClick item=="+item);
                Log.v(TAG,"onItemClick item position=="+position);
                Log.v(TAG,"onItemClick getBisbn=="+bookList[position].getBisbn());
                intent.putExtra("isbn",bookList[position].getBisbn());
                intent.putExtra("item",item);
                startActivity(intent);
            }
        });
    }


    View.OnClickListener mClic = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSearch:
                    Thread thread = new Thread(new BookInfoRunnable(handler, etSearchBook.getText().toString()));
                    thread.start();
            }
        }
    };
}

class BookInfoRunnable implements Runnable{

    private  Handler handler;
    private  String keyword;

    public BookInfoRunnable(Handler handler, String keyword) {
        this.handler = handler;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        //Thread가 시작되면 수행하는 작업
        String url = "http://70.12.60.100:8080/bookSearch/BookInfo?keyword="+keyword;

        try {
            URL objUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)objUrl.openConnection();
            conn.setRequestMethod("GET");

            int responseCode=conn.getResponseCode();
            Log.v("BookSearchRunnable","responseCode=="+responseCode);

            //연결 성공후 데이터를 받아오기 위한 통로 생성.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //서버가 보내주는 데이터를 문자열로 만든다.
            String readLine = "";
            StringBuffer responseText = new StringBuffer();
            while ((readLine=bufferedReader.readLine())!=null){
                responseText.append(readLine);
            }
            bufferedReader.close();
            Log.v("BookSearchRunnable","responseText=="+responseText.toString());
            //JACKSON library
            ObjectMapper mapper = new ObjectMapper();
            BookInfoVO[] resultArr = mapper.readValue(responseText.toString(),BookInfoVO[].class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("BookList",resultArr);
            Message msg = new Message();
            msg.setData(bundle);
            handler.sendMessage(msg);

        }catch (Exception e){
            Log.v("BookSearchRunnable","BookSearchException=="+e.toString());
        }
    }
}