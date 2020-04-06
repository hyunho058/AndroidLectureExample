package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SubImplicitIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_implicit_intent);

        Intent intent = getIntent();
        Log.v("SubImplicitIntent","sendData"+intent.getStringExtra("sendData"));
        Toast.makeText(getApplicationContext(),intent.getStringExtra("sendData"),Toast.LENGTH_LONG).show();
    }
}
