package com.example.androidlectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {
    String TAG ="ContactActivity";
    Button btnGetContact;
    TextView tvResultContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        tvResultContact=findViewById(R.id.tvResultContact);
        btnGetContact=findViewById(R.id.btnGetContact);
        btnGetContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"onClick()=="+btnGetContact.getText().toString());
                processContact();
            }
        });

        //보안 설정
        //1. 보안처리 (AndroidManifest.xml파일에 기본 보안에 대한 설정)
        //1.1 Android version 이 마쉬멜로우 버전 이전, 이후인지 check
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //1.1.1 Version M 이상
            Log.v(TAG,"Version Check=="+Build.VERSION.SDK_INT);
            int permissionResult = ActivityCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.READ_CONTACTS);

            if(permissionResult == PackageManager.PERMISSION_DENIED){
                //권한 없을 경우
                //1. 앱을 처음 실행해서 물어본적이 없는경우
                //2. 권한 허용에 대해서 사용자에게 물어보았지만 사용자가 거절을 선택 했을경우
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.READ_CONTACTS)){
                    //true => 권한을 거부한적 있는 경우(일반적으로 dialog를 이용해서 다시 물어봄)
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ContactActivity.this);
                    dialog.setTitle("권한 필요");
                    dialog.setMessage("주소록 권한이 필요합니다, 수락할거니");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.v(TAG,"dialog_onClick==YES");
                            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS},100);
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
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},100);
                    //여러개의 권한을 물어볼수 있기 때문에 String[] 배열에 넣어줌 한뻐너에 다 처리할 수 있음
                }
            }else {
                //권한 있을 경우
                Log.v(TAG,"Check=="+permissionResult+" /보안설정 통과");
                processContact();
            }
        }else {
            //1.1.2 Version M 미만
            Log.v(TAG,"Version Check=="+Build.VERSION.SDK_INT+" /보안설정 통과");
        }
    }

    public void processContact() {
        //주소록 가져오는 Code
        //1.Contact Resolver 를 이용해서 데이터 가져온다.
        // select 계열을 사용 = > query() method를 활용
        // 1 => URI (Content Provider)
        // 2 =>  null 은 모든 column 을 가져온다
        // 3 => null 은 조건없ㅇ 다 들고옴
        // 4 => null 조건절에 사용하는 값
        // 5  => 정렬방향
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null,null);
        while (cursor.moveToNext()){
            //주소의 저장된 사람의 이름과ID를 가져온다
            //전화번호는 다른 Table에서 관리된다(전화번호는 여러개가 될 수 있기 때문이다)
            //ID를 이용해서 각 사람의 전화번호를 다시 받아와야함.
            //ID Column 을 가져와 index를 가져와줌 (우리가 column의 순서 를 알지 못하기 떄무네 아래처럼 ㅊ러ㅣ)
            String id = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.v(TAG,"processContact()_id="+id+" / name="+name);

            Cursor mobilecursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id, //조건 => id가 일치하면 가져옴
                    null,null);
            String msg="";
            while (mobilecursor.moveToNext()){
                String mobile = mobilecursor.getString(mobilecursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                msg = "이름:"+name+","+"전화번호: "+mobile;
            }
            tvResultContact.append(msg+"\n");
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
                Log.v(TAG,"onRequestPermissionsResult()_보안 통과_grantResults[0]=="+grantResults[0]);
                processContact();
            }
        }
    }

}
