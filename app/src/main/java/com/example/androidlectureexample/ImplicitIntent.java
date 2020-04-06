package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ImplicitIntent extends AppCompatActivity {
    String TAG = "ImplicitIntent";
    Button btnImplicitIntent;
    Button btnCall;
    Button btnGoogle;
    Button btnCall01;
    EditText etCall;
    EditText etCall01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_intent);

        etCall=findViewById(R.id.etCall);
        etCall01=findViewById(R.id.etCall01);

        btnImplicitIntent=findViewById(R.id.btnImplicitIntent);
        btnImplicitIntent.setOnClickListener(mClick);
        btnCall=findViewById(R.id.btnCall);
        btnCall.setOnClickListener(mClick);
        btnGoogle=findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(mClick);
        btnCall01=findViewById(R.id.btnCall01);
        btnCall01.setOnClickListener(mClick);

    }

    View.OnClickListener mClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnImplicitIntent:
                    Log.v(TAG,"onClick_btnImplicitIntent=="+btnImplicitIntent);
                    Intent intent = new Intent();
                    intent.setAction("MyAction");
                    intent.addCategory("IntentTest");
                    intent.putExtra("sendData","야이야이자슥아");
                    startActivity(intent);
                    break;
                case R.id.btnCall:
                    Intent intent1 = new Intent();
                    intent1.setAction(Intent.ACTION_DIAL);
                    intent1.setData(Uri.parse("tel:"+etCall.getText()));
                    startActivity(intent1);
                    break;
                case R.id.btnGoogle:
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.co.kr/")));
                    break;
                case R.id.btnCall01:
                    //사용자가 사용하는 안드로이드 버전이 6(M) 버전 이상인지 확인
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        //사용자의 Android version이 6 이상
                        //사용자 권한 중 전화걸기 권한이 설정되어 있는지 확인
                        int permissionResult = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
                        //권한을 이미 허용했는지 그렇지 않은지를 비교해서 다르게 처리
                        //PERMISSION_DENIED는 허용 안함 => -1 으로 정수 처리됨
                        //PERMISSION_GRANTED는 => 0
                        if(permissionResult == PackageManager.PERMISSION_DENIED){
                            //권한이 없는 경우

                            //권한 설정을 거부한 적이 있는지 그렇지 않은지 확인
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                //d임의로 app의 권한을 끄거나 권한 요청화면에서 거절을 클릭했을 경우 해당 영역이 실행조건이 됨
                                //아래 코드를 안해주면 거부시 해당 기능을 쓸수 없기때문에 설정을 해줘야한다
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ImplicitIntent.this);
                                dialog.setTitle("권한요청에 대한 Dialog");
                                dialog.setMessage("전화걸기 기능 필요");
                                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1000);
                                    }
                                });
                                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //할 일이 없다
                                    }
                                });
                                dialog.create().show();
                            }else {
                                //권한을 거부한 적이 없음.
                                //권한 설정창 띄움
                                //군한 설정창이 전화 카메라 등등 여러가지들을 배열로 넣어 설정해준다
                                //requestCode는 나중에 받아볼 코드
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1000);
                            }
                        }else {
                            //권한이 허용된 경우
                            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+etCall01.getText())));
                        }
                    }else{
                        //사용자의 Android version이 6 이하
                        startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+etCall01.getText())));
                    }
                    break;
            }
        }
    };
    //권한 허용을 누르면 해당 메소드로 넘어온다
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //위에서 수행한 권한 요청에 대한 응답인지 확인
        if(requestCode == 1000){
            //int[] grantResults 여러개의 권한 결과를 받아 오기때문에 배열이다
            //권한 요청의 응답개수가 1개 이상이고 지금 상황에서 전화걸기 기능 1개만 물어봤으니까 첫번째 배열에 그 결과가 담겨 호출
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //권한 허용
//                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+etCall01.getText())));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+etCall01.getText()));
                startActivity(intent);
            }
        }
    }
}
