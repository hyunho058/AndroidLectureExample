package com.example.androidlectureexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContentProviderActivity extends AppCompatActivity {
    String TAG="ContentProviderActivity";
    TextView tvEmpInfo;
    EditText etEmpName;
    EditText etEmpAge;
    EditText etEmpPhone;
    Button btnInsertInfo;
    Button btnEmpCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        tvEmpInfo=findViewById(R.id.tvEmpInfo);

        etEmpName=findViewById(R.id.etEmpName);
        etEmpAge=findViewById(R.id.etEmpAge);
        etEmpPhone=findViewById(R.id.etEmpPhone);

        btnInsertInfo=findViewById(R.id.btnInsertInfo);
        btnInsertInfo.setOnClickListener(mClick);
        btnEmpCall=findViewById(R.id.btnEmpCall);
        btnEmpCall.setOnClickListener(mClick);
    }

    View.OnClickListener mClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnInsertInfo:
                    //Data 입력
                    //content provider를 찾아야함 => cp를 통해 insert작업
                    String uriString = "content://com.exam.person.provider/person";
                    Uri uri = new Uri.Builder().build().parse(uriString);
                    //HashMap형테로 데이터 베이스에 입력할 데이터를 저장
                    ContentValues values = new ContentValues();
                    //put(column, value)
                    values.put("name","홍길동");
                    values.put("age",20);
                    values.put("mobile","010-5555-6666");
                    getContentResolver().insert(uri,values);
                    Log.v(TAG,"데이터 입력");
                    break;
            }
        }
    };
}
//1.SQLite를 이용하기 때문에 SQLiteOpenHelper class를 이용해서 DB 이용
class PersonDatabaseHelper extends SQLiteOpenHelper{
    String TAG="PersonDatabaseHelper";

    public PersonDatabaseHelper(@Nullable Context context) {
        super(context, "person.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG,"onCreate()_Start");
        //Database가 초기에 만들어지는 시점에 Table 을 같이 만든다.
        //DB Create Code
        //SQL을 이용해서 Database안에 Table 생성
        String sql = "CREATE TABLE IF NOT EXISTS " +"person(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, mobile TEXT)";
        //SQL을 어떤 database에서 실행할지 결정
        db.execSQL(sql);
        Log.v(TAG,"Table생성");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG,"onUpgrade()_Start");
    }
}