package com.example.androidlectureexample;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class SubContentProvider extends ContentProvider {
    String TAG = "SubContentProvider";
    SQLiteDatabase sqLiteDatabase;
    //URI 형싱 (Content Provider를 찾기 위한 특수한 문자열
    // content://Authority/BASE_PATH(테이블 이름)/#숫자(레코드 번호)
    // 예) content://com.exam.person.provider/person/1
    public SubContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG,"insert()_Start");
        //database Table 에 insert 하는 방법중 하나, SQL문을 이용하지 않음
        // values에 key, value형태로 임력할 데이터 명시. (key, null, values)
        sqLiteDatabase.insert("person",null,values);
        return uri;
    }

    @Override
    public boolean onCreate() {
        //App이 실행되면 호출됨
        // TODO: Implement this to initialize your content provider on startup.
        Log.v(TAG,"onCreate()_Start");
        //Database 생성및 Table 생성에 관한 Code가 나ㅏㅗ면 된다
        PersonDatabaseHelper helper = new PersonDatabaseHelper(getContext());
        //Database reference 를 획득하는 코드
        sqLiteDatabase=helper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // 1 번쨰 인자 = table 명 ,
        // 2 번째 인자 = projection(select에서 가져올 컬렴명을 문자열로 표현)
        // 3 번째 인자 = selection(where절 조건 명시)
        // 4 번째 인자 = selection 의 In Parameter의 값
        // 5 번째 인자 = groun by
        // 6 번째 인자 = having절
        // 7 번째 인자 = wjdfuf qkdqjq
        Cursor cursor = sqLiteDatabase.query(
                "person", projection, selection, selectionArgs,null,null, sortOrder);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
