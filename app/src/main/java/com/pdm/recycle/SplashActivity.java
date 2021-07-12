package com.pdm.recycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pdm.recycle.api.AppUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        iniciarAplicativo();
    }

    private void iniciarAplicativo() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent
                        = new Intent(
                                SplashActivity.this,MainActivity.class
                );
                startActivity(intent);
                finish();
                return;
            }
        }, AppUtil.TIME_SPLASH);
    }
}