package com.pdm.recycle.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pdm.recycle.R;

public class DescarteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_descarte);
    }

    public void redirectMapsDescarte(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}