package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SqliteBasicActivity extends AppCompatActivity {
    String TAG = "SqliteBasicActivity";
    SQLiteDatabase sqLiteDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_basic);

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
                    //DB 생성
                    // /data/data/App/   경로에 DB 생성됨
                    String dbName = etDBName.getText().toString();
                    //(Name, 읽기/쓰기(MODE_PRIVATE 는 상수로 0이다),
                    sqLiteDatabase = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                    Log.v(TAG, "onClick()_dbTest");
                    break;
                case R.id.btnTableCreate:
                    //Table 생성
                    String tableName = etTableName.getText().toString();
                    if(sqLiteDatabase == null){
                        Log.v(TAG,"DB생성 해주세요");
                        return;
                    }else {
                        //DB에 대한 reference가 존재
                        //SQL을 이용해서 Database안에 Table 생성
                        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER, mobile TEXT)";
                        //SQL을 어떤 database에서 실행할지 결정
                        sqLiteDatabase.execSQL(sql);
                        Log.v(TAG,"Table생성");
                    }
                    break;
                case R.id.btnInsertInfo:
                    String name = etEmpName.getText().toString();
                    int age = Integer.parseInt(etEmpAge.getText().toString());
                    String phone = etEmpPhone.getText().toString();
                    if(sqLiteDatabase == null){
                        Log.v(TAG,"DB 생성 해주세요");
                        return;
                    }else {
                        String sql ="INSERT INTO emp(name,age,mobile) VALUES" + "('"+name+"',"+age+",'"+phone+"')";
                        sqLiteDatabase.execSQL(sql);
                        Log.v(TAG,"Data Insert");
                    }
                    etEmpName.setText("");
                    etEmpAge.setText("");
                    etEmpPhone.setText("");
                    break;
                case R.id.btnEmpCall:
                    //Select 처리
                    String sql = "SELECT _id, name, age, mobile FROM emp";
                    if(sqLiteDatabase==null){
                        Log.v(TAG,"DB 생성 해주세요");
                        return;
                    }else {
                        //execSQL() = select 계열이 아닌 SQL문장을 실행할떄 사용.
                        //cursor 는 최초 컬럼을 가리키고 있다. 컬럼 에 포함된 데이터를 출력하기 위해서는 cursor 를 내려야하고(다음 데이터가 있으면 true 없으면 false 값을 반환)
                        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
                        Log.v(TAG,"empTable ="+cursor.toString());

                        while (cursor.moveToNext()){
                            int id = cursor.getInt(0); // cursor 위치 의 컬럼
                            String name1 = cursor.getString(1); //두번째 컬럼
                            int age1 = cursor.getInt(2);
                            String mobile = cursor.getString(3);

                            String result = "레코드 : " + id + "," + name1 + "." + age1 + "," + mobile;

                            tvEmpInfo.append(result+"\n");
                        }

                    }
                    break;
            }
        }
    };
}
