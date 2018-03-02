package com.example.admin.phone_call;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //displaying splash screen for few seconds
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(2000,1000){
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this,Home.class);
                startActivity(intent);
            }
        }.start();

    }}
