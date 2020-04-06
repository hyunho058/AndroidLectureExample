package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class KakaoBookSearchActivity extends AppCompatActivity {
    String TAG = "KakaoBookSearchActivity";
    EditText etBookSearch;
    Button btnBookSearch1;
    ListView lvBookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_book_search);

        etBookSearch=findViewById(R.id.etBookSearch);
        lvBookList=findViewById(R.id.lvBookList);

        btnBookSearch1=findViewById(R.id.btnBookSearch1);
        btnBookSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SubKakaoBookSearchSearvice.class);
                intent.putExtra("keyword",etBookSearch.getText().toString());
                startService(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<String> booksTitle = (ArrayList<String>)intent.getExtras().get("BOOKRESULT");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, booksTitle);
        lvBookList.setAdapter(adapter);

    }
}
