package com.example.androidlectureexample;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class SubKakaoBookSearchSearvice extends Service {
    public SubKakaoBookSearchSearvice() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        //Activity로 부터 전달된 intent를 통해 keyword를 얻어옴
        String keyword = intent.getStringExtra("keyword");

        //Network연결을 통해 Open API를 호출하는 시간이 걸리는 작업을 수항하는 Thread를 이용해서 처리

        KakaoBookSearchRunnable runnable = new KakaoBookSearchRunnable(keyword);
        Thread thread = new Thread(runnable);
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    class KakaoBookSearchRunnable implements Runnable{
        private String keyword;

        public KakaoBookSearchRunnable() {
        }

        public KakaoBookSearchRunnable(String keyword) {
            this.keyword = keyword;
        }

        @Override
        public void run() {
            //Network 접속을 통해 Open API를 호출
            //KAKAO Developer 사이트에서 OPEN API 의 호출방식 알아야한다
            //Host 와 GET을 사용
            String url = "https://dapi.kakao.com/v3/search/book?target=title";
            url +="&query="+keyword;

            //url 주소로 Network 연결
            //예외처리 해줘야함
            try {
                //1.HTTp 접속을 하기 위해 URL객체 생성
                URL dbjUrl = new URL(url);
                //2. URL Connection
                HttpURLConnection con = (HttpURLConnection)dbjUrl.openConnection();
                //3. 연결 설정이 들어간다 ( 호출 방식 (GET, POST), 인증 처리 )
                //3.1 호출 방식 (API 문서를 보고 설정)
                con.setRequestMethod("GET");
                //3.2 인증 처리 (HEADER 부분에 처리해야함)
                con.setRequestProperty("Authorization", "KakaoAK a85301089026f3d76b61ac72f59b1d91");
                //접속 성공시
                //접속이 성공하면 결과 데이터를 JSON으로 보내주게 되고 해당 데이터를 읽어옴
                //데이터 연결통로 (Stream)을 읽어서 데이터를 읽어온다
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line ;
                StringBuffer stringBuffer = new StringBuffer();

                //반복적으로 서버가 보내주는 데이터 읽어옴 (통로를 통해 받아오는게 null이 아닐때까지 일거온다)
                while ((line = bufferedReader.readLine())!=null){
                    //서버에서 받아오는 한줄 한줄을 stringBuffer에 저장
                    stringBuffer.append(line);
                }
                //통로를 닫아주는 부분
                bufferedReader.close();
                Log.v("KakaoBookSearchRunnable","stringBuffer=="+stringBuffer.toString());
                //documents:"[{책 정보1},{책 청보2},{책 정보3},...]"  형태로 된 JSON을 처리해서 documents라고 되어있는key값으에 대해 Value값을 객체화 해서 가져와야함
                //Jackson library를 이용해서 처리
                ObjectMapper mapper = new ObjectMapper();
                //stringBuffer.toString()를 읽어서 객체화 할지
                //Json을 읽어서 documents 를 key로 설정하고 최상위 객체인 Object type 으로 책 정보들을 객체화 할거다.
                Map<String,Object> map = mapper.readValue(stringBuffer.toString(), new TypeReference<Map<String,Object>>() {});
                Object jsonObject = map.get("documents");
                //jsonObject => "[{책 정보1},{책 청보2},{책 정보3},...]"
                //위에서 얻은 객체를 다시 JSON 문자열로 변환
                String jsonString = mapper.writeValueAsString(jsonObject);
                //jsonString => "[{책 정보1},{책 청보2},{책 정보3},...]" 형태로 변환
                Log.v("KakaoBookSearchRunnable","jsonString=="+jsonString);

                //Json 배열을 ArrayList 로 변환 (ArrayList 안에 책 객체가 들어있는 형태로)
                ArrayList<KakaoBookVO> searchBook = mapper.readValue(jsonString, new TypeReference<ArrayList<KakaoBookVO>>() {});

                ArrayList<String> resultData = new ArrayList<String>();
                for(KakaoBookVO vo : searchBook){
                    resultData.add(vo.getTitle());
                    Log.v("KakaoBookSearchRunnable", "KakaoBookVO=="+vo.getTitle());
                }

                //책 제목만 들어있는 ArrayList를 만들어 Activity 에 전달
                Intent intent1 = new Intent(getApplicationContext(), KakaoBookSearchActivity.class);
                intent1.putExtra("BOOKRESULT",resultData);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent1);

            }catch (Exception e){
                Log.v("KakaoBookSearchRunnable","Exception=="+e.toString());
            }
        }
    }
}

//VO class
class KakaoBookVO{
    private ArrayList<String> authors;
    private String contents;
    private String datetime;
    private String isbn;
    private String price;
    private String publisher;
    private String sale_price;
    private String status;
    private String thumbnail;
    private String title;
    private ArrayList<String> translators;
    private String url;

    public KakaoBookVO() {
    }

    public KakaoBookVO(ArrayList<String> authors, String contents, String datetime, String isbn, String price, String publisher, String sale_price, String status, String thumbnail, String title, ArrayList<String> translators, String url) {
        this.authors = authors;
        this.contents = contents;
        this.datetime = datetime;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.sale_price = sale_price;
        this.status = status;
        this.thumbnail = thumbnail;
        this.title = title;
        this.translators = translators;
        this.url = url;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getTranslators() {
        return translators;
    }

    public void setTranslators(ArrayList<String> translators) {
        this.translators = translators;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}