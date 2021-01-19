package com.vapp.admobexample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vapp.admobexample.main.MainActivity;
import com.vapp.admoblibrary.AdmodUtils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdmodUtils.fetchAppOpenAds(this,MainActivity.class,"ca-app-pub-3940256099942544/3419835294");
        AdmodUtils.limitTime = 300000;
    }
}
