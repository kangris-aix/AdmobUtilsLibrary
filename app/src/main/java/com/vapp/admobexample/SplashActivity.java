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
        AdmodUtils.loadAdInterstitial(SplashActivity.this, AdmodUtils.ads_admob_inter_id);
        AdmodUtils.showAdInterstitialAndStartNewActivity(SplashActivity.this, MainActivity.class);
        AdmodUtils.limitTime = 500000;
    }
}
