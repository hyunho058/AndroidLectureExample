package com.example.androidlectureexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SqliteHelperActivity extends AppCompatActivity {
    String TAG = "SqliteHelperActivity";

    TextView tvEmpInfo;
    EditText etDBName;
    EditText etTableName;
    EditText etEmpName;
    EditText etEmpAge;
    EditText etEmpPhone;
    Button btnCreate;
    Button btnTableCreate;
    Button btnInsertInfo;
    Button btnEmpCall;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_helper);

        etDBName=findViewById(R.id.etDBName);
        etTableName=findViewById(R.id.etTableName);
        etEmpName=findViewById(R.id.etEmpName);
        etEmpAge=findViewById(R.id.etEmpAge);
        etEmpPhone=findViewById(R.id.etEmpPhone);

        tvEmpInfo=findViewById(R.id.tvEmpInfo);

        btnCreate=findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(mClick);
        btnTableCreate=findViewById(R.id.btnTableCreate);
        btnTableCreate.setOnClickListener(mClick);
        btnInsertInfo=findViewById(R.id.btnInsertInfo);
        btnInsertInfo.setOnClickListener(mClick);
        btnEmpCall=findViewById(R.id.btnEmpCall);
        btnEmpCall.setOnClickListener(mClick);
    }

    View.OnClickListener mClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnCreate:
                    //상요자가 입력한 DB Name 을 가져온다.
                    String dbName = etDBName.getText().toString();
                    //DB Create (Database가 존자한다면 Open만 실행
                    //Heper class를 이용해서 database를 생성및 Open
                    MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(
                            getApplicationContext(), dbName, null, 2);
                    sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
                    //1. 새로운 DB Create
                    //helper가 생성되면서 Database가 만들어지고 onCreate() -> onOpen()

                    break;
            }
        }
    };
}
//SQLiteOpenHelper Class
class MyDatabaseHelper extends SQLiteOpenHelper{
    String TAG="MyDatabaseHelper";

    public MyDatabaseHelper(@Nullable Context context, @Nullable String dbName, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, null, version);
        //dbName으로 만든 데이터베이스가 없으면 해당 데이터베이스를 생성할떄 version 정보를 같이명시
        //onCreate() 호출 => onOpen()호출
        //db가 기존에 존재하면 onOpen() 만 호출
        //version값 이 기본 만들었을때의 version값 과 다르면 onUpgrade()호출
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //Database가 Open할때
        Log.v(TAG,"onOpen() Start");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Database가 존재하지 않아서 생성할떄 호출
        Log.v(TAG,"onCreate() Start");
        //DB Create Code
        //SQL을 이용해서 Database안에 Table 생성
        String sql = "CREATE TABLE IF NOT EXISTS " +"person(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, mobile TEXT)";
        //SQL을 어떤 database에서 실행할지 결정
        db.execSQL(sql);
        Log.v(TAG,"Table생성");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //기존 DB의 스키마를 변경하는 작업
        //초창기 앱을 만들어서 배포할떄 DB스키마 생성 -> 앱을 업그래이드해서 다시 배포할떄 DB스키마를 다시 생성하기위해
        //이전 DB를 drop시키고 새로운 DB를 만드는 작업
        Log.v(TAG,"onUpgrade() Start___기존에 있는 db와 version이 다를때 호출");
    }
}