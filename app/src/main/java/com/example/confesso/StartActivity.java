package com.example.confesso;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private static int SPlASH_TIME_OUT=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent SignIn = new Intent(StartActivity.this, SignInActivity.class);
                startActivity(SignIn);
                finish();
            }
        },SPlASH_TIME_OUT);


    }
}
