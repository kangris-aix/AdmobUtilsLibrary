package com.vapp.admobexample.main;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.vapp.admobexample.R;
import com.vapp.admobexample.SplashActivity;
import com.vapp.admoblibrary.AdmodUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdmodUtils.loadAndShowInterstitialAddNewActivityNotCheckTime(MainActivity.this, "ca-app-pub-3940256099942544/5224354917",Second2Fragment.class);
            }
        });
    }

}