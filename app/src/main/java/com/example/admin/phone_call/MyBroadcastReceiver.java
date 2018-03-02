package com.example.admin.phone_call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Admin on 2/22/2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    //Starting Media player
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.reminder);
        mediaPlayer.start();
        String name = intent.getStringExtra("Extra_name");
        String number = intent.getStringExtra("Extra_number");
        String Thumbnail = intent.getStringExtra("Extra_img_uri");
        Intent i = new Intent(context, Pop.class);
        Bundle extra = new Bundle();
        extra.putString("Extra_name", name);
        extra.putString("Extra_number",number);
        extra.putString("Extra_img_uri",Thumbnail);
        i.putExtras(extra);
        context.startActivity(i);
    }
}
