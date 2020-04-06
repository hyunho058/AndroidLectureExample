package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class DataFromActivity extends AppCompatActivity {
    Spinner spinner_01;
    Button btnSendData;
    ArrayList<String> arrayList;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_from);

        spinner_01 = findViewById(R.id.spinner_01);
        btnSendData = findViewById(R.id.btnSendData);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("resultValue", result);
                setResult(7000,intent);
                finish();
            }
        });

        arrayList = new ArrayList<String>();
        arrayList.add("포도");
        arrayList.add("딸기");
        arrayList.add("사과");
        arrayList.add("거봉");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,arrayList);
        spinner_01.setAdapter(arrayAdapter);

        spinner_01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                result = arrayList.get(position);
                Log.v("DataFromActivity","arrayList.get(position)=========="+arrayList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
