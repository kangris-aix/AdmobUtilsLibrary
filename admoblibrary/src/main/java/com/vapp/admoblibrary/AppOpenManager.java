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
  /** Creates and returns ad request. */
  private AdRequest getAdRequest() {
    return new AdRequest.Builder().build();
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
      AdmodUtils.showAdIfAvailable(currentActivity);
      Log.d(LOG_TAG, "onStart");
    }
  }
}