package com.example.androidlectureexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

public class SUbBroadcastSMS extends BroadcastReceiver {
    //보안 설정이 되었으면 특정 Signal(Broadcast기 전파되면)이 발생하면 해당 Broadcast를 받을수 있다.
    String TAG="SUbBroadcastSMS";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Broadcast를 받으면 호출
        //SMS가 도착하면 호출
        Log.v(TAG,"SMS 도착");
        //Intent안에 포함되어 있는 정보 추출
        Bundle bundle = intent.getExtras();
        //Bundle안에 key,value형태로 데이터가 여러게 저장되어있는데 sms 정보는 "pdus"라는 키값으로 저장되어있다
        //동시에 여러개의 SMS가 도착할수 있다 (객체 1개가 SMS메시지 1개를 의미함)
        Object[] objects = (Object[])bundle.get("pdus");

        SmsMessage[] smsMessages = new SmsMessage[objects.length];
        //예제에서는 1개의 SMS만 전달된다고 가정하고 진행한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Object 객체 형테를SmsMessage 객체 형테로 converting
            String format = bundle.getString("format");
            smsMessages[0] = SmsMessage.createFromPdu((byte[])objects[0],format);
            // SmsMessage의 static메서드인 createFromPdu로 objects의
            // 데이터를 message에 담는다
            // 이 때 pdusObj는 byte배열로 형변환을 해줘야 함
        }else {
            smsMessages[0] = SmsMessage.createFromPdu((byte[])objects[0]);
        }
        String sender = smsMessages[0].getOriginatingAddress();
        String msg = smsMessages[0].getMessageBody();
        String reDate = new Date(smsMessages[0].getTimestampMillis()).toString();

        Log.v(TAG,"sender="+sender);
        Log.v(TAG,"msg="+msg);
        Log.v(TAG,"reDate="+reDate);

        Intent intent1 = new Intent(context, BroadcastSMSActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("sender",sender);
        intent1.putExtra("msg",msg);
        intent1.putExtra("reDate",reDate);
        context.startActivity(intent1);
    }
}
