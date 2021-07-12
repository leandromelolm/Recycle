package com.pdm.recycle.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pdm.recycle.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    public void redirectLogin(View v) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void redirectRegister(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}