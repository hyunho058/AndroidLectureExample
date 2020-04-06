package com.example.androidlectureexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BroadcastNotificationActivity extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;
    Button btnResisterNotification;
    Button btnSendSignal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_notification);

        btnResisterNotification=findViewById(R.id.btnResisterNotification);
        btnResisterNotification.setOnClickListener(mClick);
        btnSendSignal=findViewById(R.id.btnSendSignal);
        btnSendSignal.setOnClickListener(mClick);
    }

    View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnResisterNotification:
                    //Broadcast Receiver 등록
                    //1. IntentFilter 만든다 (Action 설정)
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("MY_NOTI_SIGNAL");
                    //2. Broadcast 를 받는 Receiver를 생성
                    broadcastReceiver = new BroadcastReceiver(){
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            //Broadcast를 받으면 호출
                            //Notification 을 띄운다
                            // NOTIFICATION_SERVICE == 백그라운드 이벤트를 알려주는  notificationManager
                            NotificationManager notificationManager =
                                    (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

                            //Android Oreo(8Version) 이전, 이후에따라 코드 차이가 있다.
                            //8버전 이상은 Channel을 활용(알림의 종류가 많아짐, 중요한 알림일 경우 전동과 소리가 함께 나타나게 처리 가능)
                            String channelID = "MY_CHANNEL";
                            String channelName = "MY_CHANNEL_NAME";
                            int importance = NotificationManager.IMPORTANCE_HIGH;
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                //Oreo 이상일떄 반드시 Channel을 만들어서 사용해야 한다.
                                NotificationChannel notificationChannel =
                                        new NotificationChannel(channelID,channelName,importance); //(ID, NAME, 중요도)
                                //noti 체널을 만든 후에 설정을 붙임
                                notificationChannel.enableVibration(true);
                                notificationChannel.setVibrationPattern(new long[]{100,200,100,300}); //ms단위
                                notificationChannel.enableLights(true);
                                notificationChannel.setLockscreenVisibility(
                                        Notification.VISIBILITY_PRIVATE);//잠김 상태에서도 화면에 표시 할지 설정
                                notificationManager.createNotificationChannel(notificationChannel);
                            }

                            //Notification 만든다 (Builder를 이용)
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                    context.getApplicationContext(), channelID);
                            //Notification 을 띄우기 위해서 Intent필요
                            Intent intent1 = new Intent(
                                    getApplicationContext(),BroadcastNotificationActivity.class);
                            //Notification 이 Activity위에 띄워야 하기때문에 아래 설정이 필요
                            intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //중복되지 않는 상수값을 얻기위해 사용. (requestID 가 중복되지 않아야함)
                            int requestID = (int)System.currentTimeMillis();
                            //PendingIntent를 생상해서 사용해야함
                            PendingIntent pendingIntent = PendingIntent.getActivity(
                                    getApplicationContext(),requestID, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                            //builder를 이용해서 Notification 생성
                            builder.setContentTitle("NotiTitle");
                            builder.setContentText("NotiBody");
                            builder.setDefaults(Notification.DEFAULT_ALL);//알림, 사운드, 진동(default 로 하면 기본값으로 적용)
                            builder.setAutoCancel(true);//알림 터치시 반응 후 삭제
                            builder.setSound(RingtoneManager.getDefaultUri(
                                    RingtoneManager.TYPE_NOTIFICATION));//알림의 기본음으로 설정
                            builder.setSmallIcon(android.R.drawable.btn_star);//작은 아이콘 설정
                            builder.setContentIntent(pendingIntent);
                            //Notification 띄운다.
                            notificationManager.notify(0,builder.build());
                        }
                    };
                    //3. Receiver를 filter와 함께 등록
                    registerReceiver(broadcastReceiver,intentFilter);
                    break;
                case R.id.btnSendSignal:
                    Intent intent = new Intent("MY_NOTI_SIGNAL");
                    sendBroadcast(intent);
                    break;
            }
        }
    };
}