package com.example.admin.phone_call;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Admin on 2/12/2018.
 */

public class Phone_Manager extends BroadcastReceiver {

    TelephonyManager telephonyManager;

    public Phone_Manager() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        final boolean[] xState = {false};
        telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        //checking the call state
        String Str = bundle.get("state") + "";
        switch (Str) {
            case "IDLE":
                Log.e("tag", "inside idle");
                if (!xState[0]) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int duration = getdetails(context, bundle.get("incoming_number") + "");
                    Log.e("tag", duration + "");
                    if (duration == 0) {
//                        Intent i = new Intent(context, Pop.class);
//                        Bundle extras = new Bundle();
//                        extras.putString("Extra_name", getName(context, bundle.get("incoming_number") + ""));
//                        extras.putString("Extra_number",bundle.get("incoming_number") + "");
//                        i.putExtras(extras);
//                        context.startActivity(i);
                          sendIntend(context,bundle.get("incoming_number")+"");
                    }
                }
                xState[0] = true;
                break;
            case "OFFHOOK":
                if (!xState[0]) {
                    Log.e("tagg", "inside offhook");
                }
                xState[0] = true;
                break;

            case "RINGING":
                xState[0] = true;
                break;
        }


    }
    //sending intend to next activity
    private void sendIntend(Context context, String incoming_number) {
        String Name = null;
        String Thumbnail = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        int number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int tn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);
        while (cursor.moveToNext()){
            if (cursor.getString(number).contains(incoming_number)){
                Name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                Thumbnail = cursor.getString(tn);
                break;
            }
        }
        Intent i = new Intent(context, Pop.class);
        Bundle extras = new Bundle();
        extras.putString("Extra_name", Name);
        extras.putString("Extra_number",incoming_number);
        extras.putString("Extra_img_uri",Thumbnail);
        i.putExtras(extras);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    //returning duration to check for setting a remind
    private int getdetails(Context context, String s) {
        int duration = 1;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return duration;
        }
        Cursor logCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls.NUMBER , CallLog.Calls.DURATION}, CallLog.Calls.NUMBER+"=?", new String[]{s}, CallLog.Calls.DATE + " DESC");
        int number = logCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int dur = logCursor.getColumnIndex(CallLog.Calls.DURATION);
        while (logCursor.moveToNext()){
            Log.e("tag",s+"          "+logCursor.getString(number)+"   "+logCursor.getString(dur));
            logCursor.moveToFirst();
            duration = Integer.parseInt(logCursor.getString(dur));
            break;
        }
        logCursor.close();
        return duration;
    }


}
