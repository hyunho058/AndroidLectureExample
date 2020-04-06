package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ServiceDataTransferActivity extends AppCompatActivity {
    TextView tvDataFromService;
    EditText etDataToService;
    Button btnDataSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_data_transfer);

        tvDataFromService=findViewById(R.id.tvDataFromService);
        etDataToService=findViewById(R.id.etDataToService);

        btnDataSend=findViewById(R.id.btnDataSend);
        btnDataSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubServiceDataTransferActivity.class);
                intent.putExtra("ActivityData",etDataToService.getText().toString());
                startService(intent);
            }
        });
    }
    //Service로부터 intent가 도착하면 onNewIntent() method가 호출
    @Override
    protected void onNewIntent(Intent intent) {
        //여기서 Service가 보내주는 결과 데이터를 받아서 화면처리
        tvDataFromService.setText(intent.getStringExtra("resultData"));
        super.onNewIntent(intent);
    }
}
