package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class BroadcastSMSActivity extends AppCompatActivity {
    String TAG="BroadcastSMSActivity";
    TextView tvSender;
    TextView tvMessage;
    TextView tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_sms);

        tvSender=findViewById(R.id.tvSender);
        tvMessage=findViewById(R.id.tvMessage);
        tvDate=findViewById(R.id.tvDate);

        //1. 보안처리 (AndroidManifest.xml파일에 기본 보안에 대한 설정)
        //1.1 Android version 이 마쉬멜로우 버전 이전, 이후인지 check
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //1.1.1 Version M 이상
            Log.v(TAG,"Version Check=="+Build.VERSION.SDK_INT);
            int permissionResult = ActivityCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.RECEIVE_SMS);

            if(permissionResult == PackageManager.PERMISSION_DENIED){
                //권한 없을 경우
                //1. 앱을 처음 실행해서 물어본적이 없는경우
                //2. 권한 허용에 대해서 사용자에게 물어보았지만 사용자가 거절을 선택 했을경우
                if(shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)){
                    //true => 권한을 거부한적 있는 경우(일반적으로 dialog를 이용해서 다시 물어봄)
                    AlertDialog.Builder dialog = new AlertDialog.Builder(BroadcastSMSActivity.this);
                    dialog.setTitle("권한 필요");
                    dialog.setMessage("SMS 수신기능이 필요합니다, 수락할거니");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.v(TAG,"dialog_onClick==YES");
                            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},100);
                        }
                    });
                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.v(TAG,"dialog_onClick==NO");
                        }
                    });
                    dialog.create().show();
                }else {
                    //false => 한번도 물어본적 없는경우
                    requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},100);
                    //여러개의 권한을 물어볼수 있기 때문에 String[] 배열에 넣어줌 한뻐너에 다 처리할 수 있음
                }
            }else {
                //권한 있을 경우
                Log.v(TAG,"Check=="+permissionResult+" /보안설정 통과");
            }
        }else {
            //1.1.2 Version M 미만
            Log.v(TAG,"Version Check=="+Build.VERSION.SDK_INT+" /보안설정 통과");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v(TAG,"onRequestPermissionsResult() _ requestCode=="+requestCode);
        //사용자가 권한을 설정하게 되면 이 Method가 마지막으로 호출 됨.
        if(requestCode == 100){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //사용자가 권한 허용을 눌렀을 경우
                Log.v(TAG,"onRequestPermissionsResult()_보안 통과"+grantResults[0]);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tvSender.setText(intent.getStringExtra("sender"));
        tvMessage.setText(intent.getStringExtra("msg"));
        tvDate.setText(intent.getStringExtra("reDate"));
    }
}
