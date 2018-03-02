package com.example.admin.phone_call;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Admin on 3/2/2018.
 */

public class NotificationManager {

  private   static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    Context mContext;
    private static Intent intent;

    public NotificationManager(Context context,String[] details){
        mContext = context;
        alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
        intent = new Intent(mContext, MyBroadcastReceiver.class);
        Bundle extras = new Bundle();
        extras.putString("Extra_name", details[0]);
        extras.putString("Extra_number", details[1]);
        extras.putString("Extra_img_uri",details[2]);
        intent.putExtras(extras);
        int requestCode = Integer.parseInt(details[1].substring(4,7));
        pendingIntent = PendingIntent.getBroadcast(
                mContext,requestCode, intent,PendingIntent.FLAG_UPDATE_CURRENT);

    }

    //seting reminder
    public void remind(Calendar time,String[] details) {

        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
        Toast.makeText(mContext, "Alarm set", Toast.LENGTH_SHORT).show();

    }
    //cancel reminder
    public void cancel(String num){
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
    }
}
