package com.vapp.admoblibrary;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

public class AppOpenManager implements Application.ActivityLifecycleCallbacks,LifecycleObserver {
  private static final String LOG_TAG = "AppOpenManager";
  private AppOpenAd appOpenAd = null;
  private Activity currentActivity;
  private AppOpenAd.AppOpenAdLoadCallback loadCallback;
  private static Application myApplication = null;
  private boolean isShowingAd;
  private String adUnitId = "";
  private Boolean showAppOpenAd = true;

  public Boolean getShowAppOpenAd() {
    return showAppOpenAd;
  }

  public void setShowAppOpenAd(Boolean showAppOpenAd) {
    this.showAppOpenAd = showAppOpenAd;
  }

  public String getAdUnitId() {
    return adUnitId;
  }

  public void setAdUnitId(String adUnitId) {
    this.adUnitId = adUnitId;
  }


  /** Constructor */
  public AppOpenManager(Application application, String adUnitId) {
    this.myApplication = application;
    this.myApplication.registerActivityLifecycleCallbacks(this);
    ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    this.adUnitId = adUnitId;
  }

  public void fetchAd() {
    // Have unused ad, no need to fetch another.
    if (isAdAvailable()) {
      return;
    }

    loadCallback =
            new AppOpenAd.AppOpenAdLoadCallback() {
              /**
               * Called when an app open ad has loaded.
               *
               * @param ad the loaded app open ad.
               */
              @Override
              public void onAppOpenAdLoaded(AppOpenAd ad) {
                AppOpenManager.this.appOpenAd = ad;
              }

              /**
               * Called when an app open ad has failed to load.
               *
               * @param loadAdError the error.
               */
              @Override
              public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
                // Handle the error.
              }

            };
    AdRequest request = getAdRequest();
    AppOpenAd.load(
            myApplication, AdmodUtils.ads_admob_open_id, request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
  }
  /** Shows the ad if one isn't already showing. */
  public void showAdIfAvailable() {
    // Only show ad if there is not already an app open ad currently showing
    // and an ad is available.
    if (!isShowingAd && isAdAvailable()) {
      Log.d(LOG_TAG, "Will show ad.");

      FullScreenContentCallback fullScreenContentCallback =
              new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                  // Set the reference to null so isAdAvailable() returns false.
                  AppOpenManager.this.appOpenAd = null;
                  isShowingAd = false;
                  fetchAd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {}

                @Override
                public void onAdShowedFullScreenContent() {
                  isShowingAd = true;
                }
              };

      appOpenAd.show(currentActivity, fullScreenContentCallback);

    } else {
      Log.d(LOG_TAG, "Can not show ad.");
      fetchAd();
    }
  }
  /** Creates and returns ad request. */
  private AdRequest getAdRequest() {
    return new AdRequest.Builder().build();
  }

  /** Utility method that checks if ad exists and can be shown. */
  public boolean isAdAvailable() {
    return appOpenAd != null;
  }

  @Override
  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

  }

  @Override
  public void onActivityStarted(@NonNull Activity activity) {
    currentActivity = activity;
  }

  @Override
  public void onActivityResumed(@NonNull Activity activity) {
    currentActivity = activity;
  }

  @Override
  public void onActivityPaused(@NonNull Activity activity) {

  }

  @Override
  public void onActivityStopped(@NonNull Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(@NonNull Activity activity) {
    currentActivity = null;
  }

  /** LifecycleObserver methods */
  @OnLifecycleEvent(ON_START)
  public void onRestart() {
    if(!AdmodUtils.isAdShowing){
      showAdIfAvailable();
      Log.d(LOG_TAG, "onStart");
    }
  }
}