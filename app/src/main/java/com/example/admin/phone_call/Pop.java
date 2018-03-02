package com.example.admin.phone_call;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Admin on 2/22/2018.
 */

public class Pop extends Activity {

    String[] contactDetails;
    TextView Name, Number, close;
    String name, number,Thumbnail;
    TimePickerDialog timePickerDialog;
    WindowManager wm;
    ImageView imageView;
    RelativeLayout relativeLayout;
    View mView;
    Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        //window manager to show popup
       wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = mInflater.inflate(R.layout.dialog,relativeLayout,false);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSPARENT
        );
        Name = mView.findViewById(R.id.Contact_name);
        Number = mView.findViewById(R.id.Contact_number);
        imageView = mView.findViewById(R.id.imageview);
        close = mView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(mView);
            }
        });
        Bundle extras = getIntent().getExtras();
        Log.e(extras.getString("Extra_name"), extras.getString("Extra_number"));
        name = extras.getString("Extra_name");
        number = extras.getString("Extra_number");
        Thumbnail = extras.getString("Extra_img_uri");
        contactDetails = new String[] {name,number,Thumbnail};
        try{
            Log.e("tag",Thumbnail);
            imageView.setImageURI(Uri.parse(Thumbnail));
        }catch (NullPointerException x){
            Thumbnail = "@drawable/unknown";
        }
        Number.setText(number);
        try{
            Name.setText(name);
        }catch (NullPointerException x){

        }

        wm.addView(mView, params);
    }

    //onclick for 15 mins
    public void fmins(View view){
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        int minute = calSet.get(Calendar.MINUTE) + 15;
        int hr = calSet.get(Calendar.HOUR);
        int med = calSet.get(Calendar.AM_PM);
        Log.e("mins:",calSet.get(Calendar.MINUTE)+"          "+calSet.get(Calendar.HOUR));
        if (minute>=60){
            minute = minute - 60;
            hr = hr + 1;
        }

        if (med == Calendar.PM){
            hr = hr + 12;
            if (hr == 24){
                hr = 0;
            }
        }
        calSet.set(Calendar.HOUR_OF_DAY,hr);
        calSet.set(Calendar.MINUTE,minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);


        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }

        Log.e("mins::",calSet.get(Calendar.MINUTE)+"          "+calSet.get(Calendar.HOUR_OF_DAY));

        NotificationManager nManager = new NotificationManager(getApplicationContext(),contactDetails);
        nManager.remind(calSet,contactDetails);
        updateDb();
    }
    //onclick for 20mins
    public void tmins(View view){
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        int minute = calSet.get(Calendar.MINUTE) + 30;
        int hr = calSet.get(Calendar.HOUR);
        int med = calSet.get(Calendar.AM_PM);
        Log.e("mins:",calSet.get(Calendar.MINUTE)+"          "+calSet.get(Calendar.HOUR));
        if (minute>=60){
            minute = minute - 60;
            hr = hr + 1;
        }

        if (med == Calendar.PM){
            hr = hr + 12;
            if (hr == 24){
                hr = 0;
            }
        }
        calSet.set(Calendar.HOUR_OF_DAY,hr);
        calSet.set(Calendar.MINUTE,minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);


        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }

        Log.e("mins::",calSet.get(Calendar.MINUTE)+"          "+calSet.get(Calendar.HOUR_OF_DAY));

        NotificationManager nManager = new NotificationManager(getApplicationContext(),contactDetails);
        nManager.remind(calSet,contactDetails);
        updateDb();
    }
    // a custom time
    public void custom(View view){

        openTimePickerDialog(false);
    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        timePickerDialog.show();

    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);


            if (calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
            }
            Log.e("mins:",calSet.get(Calendar.MINUTE)+"          "+calSet.get(Calendar.HOUR_OF_DAY));
            NotificationManager nManager = new NotificationManager(getApplicationContext(),contactDetails);
            nManager.remind(calSet,contactDetails);
            updateDb();
        }
    };

    //call the number back
    public void callback(View View){
        String dial = "tel:"+number;
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        wm.removeView(mView);
    }
    //adding to db
    public void updateDb(){
       DatabaseHelper db = new DatabaseHelper(this);
        db.addRow(new Contact(name,number,"Pending",Thumbnail));
        wm.removeView(mView);
    }
}

