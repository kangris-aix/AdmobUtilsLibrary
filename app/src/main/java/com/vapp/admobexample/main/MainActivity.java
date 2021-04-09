package com.vapp.admobexample.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vapp.admobexample.R;
import com.vapp.admoblibrary.AdCallback;
import com.vapp.admoblibrary.AdmodUtils;

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
//                AdmodUtils.loadAndShowInterstitialAddNewActivityNotCheckTime(MainActivity.this, "ca-app-pub-3940256099942544/5224354917",Second2Fragment.class);
                AdmodUtils.isTesting = true;
                AdmodUtils.loadAndShowAdRewardWithCallbackNotCheckTime(MainActivity.this, "1212", new AdCallback() {
                    @Override
                    public void onAdClosed() {

                    }

                    @Override
                    public void onAdFail() {

                    }
                }, true);

            }


        });
    }


}