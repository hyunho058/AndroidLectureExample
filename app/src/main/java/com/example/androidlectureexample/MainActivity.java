package com.example.androidlectureexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    Button btnLinear;
    Button btn_widget;
    Button btn_image;
    Button btnActivityEvent;
    Button btnSwipe;
    Button btnSend;
    Button btnPull;
    Button btnANR;
    Button btnCounter;
    Button btnCounterProgress;
    Button btnCounterPHandler;
    Button btnBookSearch;
    Button btnBookSearch_01;
    Button dbtnImplicitIntent;
    Button btnService;
    Button btnServiceDataSend;
    Button btnkakaoBookSearch;
    Button btnBroadcastReceiver;
    Button btnSMS;
    Button btnNotification;
    Button btnSQLite;
    Button btnSQLiteHelper;
    Button btnContentProvider;
    Button btnContact;
    Button btnSerialConn;
    Button btnVariableLed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLinear = findViewById(R.id.btnLinear);
        btnLinear.setOnClickListener(mClick);
        btn_widget = findViewById(R.id.btn_widget);
        btn_widget.setOnClickListener(mClick);
        btn_image = findViewById(R.id.btn_image);
        btn_image.setOnClickListener(mClick);
        btnSwipe = findViewById(R.id.btnSwipe);
        btnSwipe.setOnClickListener(mClick);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(mClick);
        btnPull = findViewById(R.id.btnPull);
        btnPull.setOnClickListener(mClick);
        btnANR = findViewById(R.id.btnANR);
        btnANR.setOnClickListener(mClick);
        btnCounter = findViewById(R.id.btnCounter);
        btnCounter.setOnClickListener(mClick);
        btnCounterPHandler=findViewById(R.id.btnCounterPHandler);
        btnCounterPHandler.setOnClickListener(mClick);
        btnCounterProgress=findViewById(R.id.btnCounterProgress);
        btnCounterProgress.setOnClickListener(mClick);
        btnBookSearch = findViewById(R.id.btnBookSearch);
        btnBookSearch.setOnClickListener(mClick);
        btnBookSearch_01 = findViewById(R.id.btnBookSearch_01);
        btnBookSearch_01.setOnClickListener(mClick);
        dbtnImplicitIntent = findViewById(R.id.dbtnImplicitIntent);
        dbtnImplicitIntent.setOnClickListener(mClick);
        btnService = findViewById(R.id.btnService);
        btnService.setOnClickListener(mClick);
        btnServiceDataSend=findViewById(R.id.btnServiceDataSend);
        btnServiceDataSend.setOnClickListener(mClick);
        btnkakaoBookSearch=findViewById(R.id.btnkakaoBookSearch);
        btnkakaoBookSearch.setOnClickListener(mClick);
        btnBroadcastReceiver=findViewById(R.id.btnBroadcastReceiver);
        btnBroadcastReceiver.setOnClickListener(mClick);
        btnSMS=findViewById(R.id.btnSMS);
        btnSMS.setOnClickListener(mClick);
        btnNotification=findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(mClick);
        btnSQLite=findViewById(R.id.btnSQLite);
        btnSQLite.setOnClickListener(mClick);
        btnSQLiteHelper=findViewById(R.id.btnSQLiteHelper);
        btnSQLiteHelper.setOnClickListener(mClick);
        btnContentProvider=findViewById(R.id.btnContentProvider);
        btnContentProvider.setOnClickListener(mClick);
        btnContact=findViewById(R.id.btnContact);
        btnContact.setOnClickListener(mClick);
        btnSerialConn=findViewById(R.id.btnSerialConn);
        btnSerialConn.setOnClickListener(mClick);
        btnVariableLed=findViewById(R.id.btnVariableLed);
        btnVariableLed.setOnClickListener(mClick);

        btnActivityEvent = findViewById(R.id.btnActivityEvent);
        btnActivityEvent.setOnClickListener(mClick);
    }

    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("MainActivity", "onClick");
            //Activity 호출방식은 2가지가 있다
            //explicit방식, implicit 방식
            switch (v.getId()){
                case R.id.btnLinear:
                    Log.v(TAG,"onClick_btnLinear"+btnLinear);
                    //explicit 방식
                    Intent intent = new Intent();
                    ComponentName cname = new ComponentName("com.example.androidlectureexample", "com.example.androidlectureexample.LayoutActivity");
                    intent.setComponent(cname);
                    startActivity(intent);
                    break;
//                case R.id.btn_widget:
//                    Log.v(TAG,"onClick_btn_widget"+btn_widget);
//                    startActivity(new Intent(MainActivity.this, WidgetActivity.class));
//                    break;
                case R.id.btn_image:
                    startActivity(new Intent(MainActivity.this, EvnetActivity.class));
                    break;
                case R.id.btnActivityEvent:
                    startActivity(new Intent(MainActivity.this, TouchEvent.class));
                    break;
                case R.id.btnSwipe:
                    startActivity(new Intent(MainActivity.this, SwipeActivity.class));
                    break;
                case R.id.btnSend:
                    final EditText editText = new EditText(MainActivity.this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("ActivityDataSend");
                    builder.setMessage("Activity에 전달할 내용");
                    builder.setView(editText);
                    builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, SendMassageActivity.class);
                            intent.putExtra("sendMsg",editText.getText().toString());
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    break;
                case R.id.btnPull:
                    Log.v(TAG,"onClick_btnPull"+btnPull);
                    startActivityForResult(new Intent(MainActivity.this, DataFromActivity.class),1);
                    break;
                case R.id.btnANR:
                    Log.v(TAG,"onClick_btnANR"+btnANR);
                    startActivity(new Intent(MainActivity.this, ANRActivity.class));
                    break;
                case R.id.btnCounter:
                    Log.v(TAG,"onClick_btnCounter"+btnCounter);
                    startActivity(new Intent(MainActivity.this, CounterLogActivity.class));
                    break;
                case R.id.btnCounterProgress:
                    Log.v(TAG,"onClick_btnCounterProgress"+btnCounterProgress);
                    startActivity(new Intent(MainActivity.this, CounterProgressActivity.class));
                    break;
                case R.id.btnCounterPHandler:
                    Log.v(TAG,"onClick_btnCounterProgress"+btnCounterProgress);
                    startActivity(new Intent(MainActivity.this, CounterHandlerActivity.class));
                    break;
                case R.id.btnBookSearch:
                    Log.v(TAG, "onClick_btnBookSearch"+btnBookSearch);
                    Intent intent10 = new Intent(MainActivity.this, BookSearchActivity.class);
                    startActivity(intent10);
                    break;
                case R.id.btnBookSearch_01:
                    Log.v(TAG, "onClick_btnBookSearch_01 "+btnBookSearch_01);
                    startActivity(new Intent(MainActivity.this,DetailBookSearchActivity.class));
                    break;
                case R.id.dbtnImplicitIntent:
                    //Explicit Intent(명시적 인텐트)
                    Log.v(TAG,"onClick_dbtnImplicitIntent"+dbtnImplicitIntent);
                    startActivity(new Intent(MainActivity.this,ImplicitIntent.class));
                    break;
                case R.id.btnService:
                    startActivity(new Intent(MainActivity.this,ServiceLifecycleActivity.class));
                    break;
                case R.id.btnServiceDataSend:
                    startActivity(new Intent(MainActivity.this,ServiceDataTransferActivity.class));
                    break;
                case R.id.btnkakaoBookSearch:
                    startActivity(new Intent(MainActivity.this,KakaoBookSearchActivity.class));
                    break;
                case R.id.btnBroadcastReceiver:
                    startActivity(new Intent(MainActivity.this, BroadcastReceiverActivity.class));
                    break;
                case R.id.btnSMS:
                    startActivity(new Intent(MainActivity.this, BroadcastSMSActivity.class));
                    break;
                case R.id.btnNotification:
                    startActivity(new Intent(MainActivity.this, BroadcastNotificationActivity.class));
                    break;
                case R.id.btnSQLite:
                    startActivity(new Intent(MainActivity.this, SqliteBasicActivity.class));
                    break;
                case R.id.btnSQLiteHelper:
                    startActivity(new Intent(MainActivity.this, SqliteHelperActivity.class));
                    break;
                case R.id.btnContentProvider:
                    startActivity(new Intent(MainActivity.this, ContentProviderActivity.class));
                    break;
                case R.id.btnContact:
                    startActivity(new Intent(MainActivity.this, ContactActivity.class));
                    break;
                case R.id.btnSerialConn:
                    startActivity(new Intent(MainActivity.this, SerialPortClientActivity.class));
                    break;
                case R.id.btnVariableLed:
                    startActivity(new Intent(MainActivity.this, VariableLedActivity.class));
                    break;
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode ==7000){
            Toast.makeText(this, data.getStringExtra("resultValue"),Toast.LENGTH_SHORT).show();
            Log.v("MainActivity", "onActivityResult======="+data.getStringExtra("resultValue"));
        }
    }
}
