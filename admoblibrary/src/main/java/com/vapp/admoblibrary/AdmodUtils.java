package com.vapp.admoblibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.vapp.admoblibrary.nativetemplate.NativeTemplateStyle;
import com.vapp.admoblibrary.nativetemplate.TemplateView;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdmodUtils {
    static ProgressDialog dialog;
    public static boolean isShow = true;
    public static boolean isInitializationComplete = false;
    public static long limitTime = 30000;
    public static long lastTimeShowInterstitial = 0;
    public static boolean isAdShowing = false;
    public static String ads_admob_id = "";
    public static boolean isTesting = false;
    public static boolean isFirstAppOpen = true;
    public static String ads_admob_open_id = "";
    public static String ads_admob_inter_id = "ca-app-pub-3940256099942544/1033173712";
    public static String ads_admob_banner_id = "ca-app-pub-3940256099942544/6300978111";
    public static String ads_admob_native_id = "ca-app-pub-3940256099942544/2247696110";
    public static String ads_admob_inter_test_id = "ca-app-pub-3940256099942544/1033173712";
    public static String ads_admob_banner_test_id = "ca-app-pub-3940256099942544/6300978111";
    public static String ads_admob_native_test_id = "ca-app-pub-3940256099942544/2247696110";
    public static String ads_admob_open_test_id = "ca-app-pub-3940256099942544/3419835294";
    public static String ads_admob_rewarded_test_id = "ca-app-pub-3940256099942544/5224354917";
    private static AppOpenAd appOpenAd = null;
    private static AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private static int numberOfAdsShowed = 0;
    // get AdRequest
    public static AdRequest getAdRequest(){
        AdRequest adRequest =  new AdRequest.Builder()
                .setHttpTimeoutMillis(5000)
                .addTestDevice("3C94990AA9A387A256D3B2BBBFEA51EA")
                .addTestDevice("6F599887BC401CFB1C7087F15D7C0834")
                .addTestDevice("B543DCF2C7591C7FB8B52A3A1E7138F6")
                .addTestDevice("8619926A823916A224795141B93B7E0B")
                .addTestDevice("6399D5AEE5C75205B6C0F6755365CF21")
                .addTestDevice("2E379568A9F147A64B0E0C9571DE812D")
                .addTestDevice("A0518C6FA4396B91F82B9656DE83AFC7")
                .addTestDevice("C8EEFFC32272E3F1018FC72ECBD46F0C")
                .addTestDevice("284A7F7624F1131E7341ECDCBBCDF9A8")
                .addTestDevice("FEECD9793CCCE1E0FF8D392B0DB65559")
                .addTestDevice("D34AE6EC4CBA619D6243B03D4E31EED6")
                .addTestDevice("25F9EEACB11D46869D2854923615D839")
                .addTestDevice("A5CB09DBBE486E3421502DFF53070339")
                .addTestDevice("5798E06F645D797640A9C4B90B6CBEA7")
                .addTestDevice("E91FD94E971864C3880FB434D1C39A03")
                .addTestDevice("50ACF2DAA0884FF8B08F7C823E046DEA")
                .addTestDevice("97F07D4A6D0145F9DB7114B63D3D8E9B")
                .addTestDevice("4C96668EC6F204034D0CDCE1B94A4E65")
                .addTestDevice("00A52C89E14694316247D3CA3DF19F6B")
                .addTestDevice("C38A7BF0A80E31BD6B76AF6D0C1EE4A1")
                .addTestDevice("CE604BDCEFEE2B9125CCFFC53E96022E")
                .addTestDevice("39D7026016640CEA1502836C6EF3776D")
                .addTestDevice("A99C99C378EE9BDE5D3DE404D3A4A812")
                .addTestDevice("EB28F4CCC32F14DC98068A063B97E6CE")
                .setHttpTimeoutMillis(5000)
                .build();
        return adRequest;
    }
    //check open network
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    //check network available
    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public static  void fetchAppOpenAds(Context context,Class nextActivity,String appOpenId) {
        // Have unused ad, no need to fetch another.
        MobileAds.initialize(context, initializationStatus -> {
        });

        if(isFirstAppOpen){
            isFirstAppOpen = false;
        }
        else{
            ads_admob_open_id = AppOpenManager.adUnitId;
        }
        if(isTesting){
            appOpenId = ads_admob_open_test_id;
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
                        appOpenAd = ad;
                        showAdIfAvailable(context,nextActivity);
                    }

                    /**
                     * Called when an app open ad has failed to load.
                     *
                     * @param loadAdError the error.
                     */
                    @Override
                    public void onAppOpenAdFailedToLoad(LoadAdError loadAdError) {
                        Intent i =  new Intent(context,nextActivity);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }

                };
        AdRequest request = AdmodUtils.getAdRequest();
        AppOpenAd.load(
                context, appOpenId, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }


    /** Utility method that checks if ad exists and can be shown. */
    public static boolean isAdAvailable() {
        return appOpenAd != null;
    }
    public static void fetchAd(Context context) {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            return;
        }
        if(isFirstAppOpen){
            isFirstAppOpen = false;
        }
        else{
            ads_admob_open_id = AppOpenManager.adUnitId;
        }
        if(isTesting){
            ads_admob_open_id = ads_admob_open_test_id;
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
                        appOpenAd = ad;
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
                context, AdmodUtils.ads_admob_open_id, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }
    /** Shows the ad if one isn't already showing. */
    public static void showAdIfAvailable(Activity currentActivity) {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isAdShowing && isAdAvailable()) {

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            appOpenAd = null;
                            isAdShowing = false;
                            fetchAd(currentActivity);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {}

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isAdShowing = true;
                        }
                    };

            appOpenAd.show(currentActivity, fullScreenContentCallback);

        } else {
            fetchAd(currentActivity);
        }
    }
    public static  void showAdIfAvailable(Context context,Class nextActivity) {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            appOpenAd = null;
                            isAdShowing = false;
                            fetchAd(context);
                            Intent i =  new Intent(context,nextActivity);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Intent i =  new Intent(context,nextActivity);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isAdShowing = true;
                        }
                    };

            appOpenAd.show( (Activity) context, fullScreenContentCallback);
    }



    //load ads Banner
    public static void loadAdBanner(Context context, AdView adView, String s){
        MobileAds.initialize(context, initializationStatus -> isInitializationComplete = true);
//        if (isTesting){
//            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//        }else {
//            adView.setAdUnitId(s);
//        }
//        adView.setAdUnitId(s);

        if(isTesting){
            s = ads_admob_banner_test_id;
        }
        adView.setAdUnitId(s);
        adView.loadAd(getAdRequest());
        Log.e(" Admod", "loadAdBanner");
    }
    public static void loadAdBanner(Context context, ViewGroup viewGroup, String bannerId){
        MobileAds.initialize(context, initializationStatus -> isInitializationComplete = true);
        AdView mAdView = new AdView(context);
        if(isTesting){
            bannerId = ads_admob_banner_test_id;
        }
        mAdView.setAdUnitId(bannerId);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        viewGroup.removeAllViews();
        viewGroup.addView(mAdView);
        mAdView.loadAd(AdmodUtils.getAdRequest());
        Log.e(" Admod", "loadAdBanner");
    }

    //show ads Banner
    public static void showAdBanner(final AdView adView){
        adView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
               adView.setVisibility(View.GONE);
            }
        });
        Log.e(" Admod", "showAdBanner");
    }

    // ads native
   @SuppressLint("StaticFieldLeak")
   public static AdLoader adLoader;
    public static void loadAdNativeAds(final Context context, String s, final TemplateView templateView){
        templateView.setVisibility(View.VISIBLE);

        if(isTesting){
            s = ads_admob_native_test_id;
        }
        adLoader = new AdLoader.Builder(context, s)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        templateView.setStyles(styles);
                        templateView.setNativeAd(unifiedNativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        templateView.setVisibility(View.GONE);
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adLoader.loadAd(getAdRequest());
        Log.e(" Admod", "loadAdNativeAds");
        isAdShowing = true;
    }

    public static void showAdNativeAds(final Context context,  String s){
         adLoader = new AdLoader.Builder(context, s)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        // Handle the failure by logging, altering the UI, and so on.
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
    }

    //reward
    static boolean isReward = false;
    static RewardedAd rewardedAd;

    public static void loadAndShowAdReward(Activity activity, String id){
        if(isTesting){
            id = ads_admob_rewarded_test_id;
        }
        rewardedAd = new RewardedAd(activity, id);
        isReward = false;
        dialog = new ProgressDialog(activity,R.style.AppCompatAlertDialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Loading ads. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                dialog.dismiss();

                RewardedAdCallback adCallback = new RewardedAdCallback() {
                    @Override
                    public void onRewardedAdOpened() {
                        // Ad opened.
                    }

                    @Override
                    public void onRewardedAdClosed() {

                    }

                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem reward) {
                        isReward = true;

                    }

                    @Override
                    public void onRewardedAdFailedToShow(AdError adError) {
                        Toast.makeText(activity,"FailedToShow",Toast.LENGTH_SHORT).show();

                    }
                };
                rewardedAd.show(activity, adCallback);
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                Toast.makeText(activity,"FailedToLoad",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    public static void loadAndShowAdRewardAndAddActivity(Activity activity, Class destActivity, String id){
        rewardedAd = new RewardedAd(activity, id);
         isReward = false;
        dialog = new ProgressDialog(activity,R.style.AppCompatAlertDialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Loading ads. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                dialog.dismiss();

                RewardedAdCallback adCallback = new RewardedAdCallback() {
                    @Override
                    public void onRewardedAdOpened() {
                        Toast.makeText(activity,"Opened",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onRewardedAdClosed() {
                        if (isReward){
                            addNewActivity(activity,destActivity);
                        }
                    }

                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem reward) {
                        Toast.makeText(activity,"EarnedReward",Toast.LENGTH_SHORT).show();
                        isReward = true;

                    }

                    @Override
                    public void onRewardedAdFailedToShow(AdError adError) {
                        Toast.makeText(activity,"FailedToShow",Toast.LENGTH_SHORT).show();

                    }
                };
                rewardedAd.show(activity, adCallback);
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                Toast.makeText(activity,"FailedToLoad",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    public static void loadAndShowAdRewardWithCallbackNotCheckTime(Activity activity, String admobId, AdCallback adCallback2, boolean enableLoadingDialog){

        if(isTesting){
            admobId = ads_admob_rewarded_test_id;
        }

        rewardedAd = new RewardedAd(activity, admobId);
        isReward = false;

        if(enableLoadingDialog){
            dialog = new ProgressDialog(activity, R.style.AppCompatAlertDialogStyle);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading ads. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                dialog.dismiss();

                RewardedAdCallback adCallback = new RewardedAdCallback() {
                    @Override
                    public void onRewardedAdOpened() {
                    }

                    @Override
                    public void onRewardedAdClosed() {
                        if (isReward){
                            adCallback2.onAdClosed();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem reward) {
                        isReward = true;

                    }

                    @Override
                    public void onRewardedAdFailedToShow(AdError adError) {
                        adCallback2.onAdFail();
                        dialog.dismiss();

                    }
                };
                rewardedAd.show(activity, adCallback);
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                adCallback2.onAdFail();
                dialog.dismiss();

            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }


    //inter ads
    public static InterstitialAd mInterstitialAd;
    public static void loadAdInterstitial(Context context, String s){
        long currentTime = getCurrentTime();
        if(currentTime-lastTimeShowInterstitial >= limitTime) {
            dialog = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading ads. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
        mInterstitialAd  = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(s);
        mInterstitialAd.loadAd(getAdRequest());
        Log.e(" Admod", "loadAdInterstitial");
    }
    public static void loadAndShowInterstitialAddNewActivity(Context context, String admobId, Class destActivity) {
        if(!isInitializationComplete){
            MobileAds.initialize(context, initializationStatus -> isInitializationComplete = true);
        }
        if(isTesting){
            admobId = ads_admob_inter_test_id;
        }
        long currentTime = getCurrentTime();
        if (currentTime - lastTimeShowInterstitial >= limitTime) {
            dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading ads. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(admobId);
        mInterstitialAd.loadAd(getAdRequest());
        Log.e(" Admod", "loadAdInterstitial");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                isAdShowing = true;
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "showAdInterstitial");
                Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                if (dialog != null) {
                    dialog.dismiss();
                }
                addNewActivity(context, destActivity);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed
            }

            @Override
            public void onAdClicked() {
                if (dialog != null) {
                    dialog.dismiss();
                }
                isAdShowing = false;
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                addNewActivity(context, destActivity);
                if (dialog != null) {
                    dialog.dismiss();
                }
                isAdShowing = false;
            }
        });
        Log.e(" Admod", "showAdInterstitial");
    }
        else{
            addNewActivity(context, destActivity);
        }
    }

    public static void loadAndShowInterstitialStartNewActivity(Context context, String admobId, Class destActivity) {
        if(!isInitializationComplete){
            MobileAds.initialize(context, initializationStatus -> isInitializationComplete = true);
        }
        if(isTesting){
            admobId = ads_admob_inter_test_id;
        }
        long currentTime = getCurrentTime();
        if (currentTime - lastTimeShowInterstitial >= limitTime) {
            dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading ads. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(admobId);
            mInterstitialAd.loadAd(getAdRequest());
            Log.e(" Admod", "loadAdInterstitial");

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                    isAdShowing = true;
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e(" Admod", "showAdInterstitial");
                    Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                    Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    startNewActivity(context, destActivity);
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed
                }

                @Override
                public void onAdClicked() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    startNewActivity(context, destActivity);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }
            });
            Log.e(" Admod", "showAdInterstitial");
        }
        else{
            startNewActivity(context, destActivity);
        }
    }

    public static void loadAndShowInterstitialAddNewActivityNotCheckTime(Context context, String admobId, Class destActivity) {
        if(!isInitializationComplete){
            MobileAds.initialize(context, initializationStatus -> isInitializationComplete = true);
        }
        if(isTesting){
            admobId = ads_admob_inter_test_id;
        }
            dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading ads. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(admobId);
            mInterstitialAd.loadAd(getAdRequest());
            Log.e(" Admod", "loadAdInterstitial");

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = true;
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e(" Admod", "showAdInterstitial");
                    Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                    Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    addNewActivity(context, destActivity);
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed
                }

                @Override
                public void onAdClicked() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    addNewActivity(context, destActivity);
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }
            });
            Log.e(" Admod", "showAdInterstitial");

    }
    public static void loadAndShowAdInterstitialWithCallbackNotCheckTime(Context context, String admobId,boolean isShow, AdCallback adCallback, boolean enableLoadingDialog){

        if(isTesting){
            admobId = ads_admob_inter_test_id;
        }
            if(enableLoadingDialog){
                dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Loading");
                dialog.setMessage("Loading ads. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(admobId);
            mInterstitialAd.loadAd(getAdRequest());
            Log.e(" Admod", "loadAdInterstitial");

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = true;
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e(" Admod", "showAdInterstitial");
                    Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                    Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    adCallback.onAdClosed();
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed
                }

                @Override
                public void onAdClicked() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    adCallback.onAdClosed();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }
            });
            Log.e(" Admod", "showAdInterstitial");
    }

    public static void loadAndShowAdInterstitialWithCallbackAlternate(Context context, String admobId,boolean isShow, AdCallback adCallback, boolean enableLoadingDialog){
        numberOfAdsShowed += 1;
        if(numberOfAdsShowed % 2 ==0){
            adCallback.onAdClosed();
        }
        else{
            if(isTesting){
                admobId = ads_admob_inter_test_id;
            }
            if(enableLoadingDialog){
                dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Loading");
                dialog.setMessage("Loading ads. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(admobId);
            mInterstitialAd.loadAd(getAdRequest());
            Log.e(" Admod", "loadAdInterstitial");

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    mInterstitialAd.show();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = true;
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e(" Admod", "showAdInterstitial");
                    Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                    Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    adCallback.onAdClosed();
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed
                }

                @Override
                public void onAdClicked() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    adCallback.onAdClosed();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }
            });
            Log.e(" Admod", "showAdInterstitial");
        }
    }


    public static void showAdInterstitial(){
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                long currentTime = getCurrentTime();
                if (currentTime - lastTimeShowInterstitial >= limitTime) {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "showAdInterstitial");
                Log.e(" Admod","errorCodeAds:" +adError.getMessage());
                Log.e(" Admod","errorCodeAds:" +adError.getCause());
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed
            }
            @Override
            public void onAdClicked() {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        Log.e(" Admod", "showAdInterstitial");
    }
    public static void showAdInterstitialAndStartNewActivity(final Context context, final Class activity){

     mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                long currentTime = getCurrentTime();
                if (currentTime - lastTimeShowInterstitial >= limitTime) {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                } else {
                    startNewActivity(context, activity);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "showAdInterstitial");
                Log.e(" Admod","errorCodeAds:" +adError.getMessage());
                Log.e(" Admod","errorCodeAds:" +adError.getCause());
                if(dialog!=null){
                    dialog.dismiss();
                }
                startNewActivity(context,activity);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed
            }
            @Override
            public void onAdClicked() {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {
                startNewActivity(context, activity);
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        Log.e(" Admod", "showAdInterstitial");
    }
    public static void showAdInterstitialAndAddNewActivity(final Context context, final Class activity){
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                long currentTime = getCurrentTime();
                if (currentTime - lastTimeShowInterstitial >= limitTime) {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                } else {
                    addNewActivity(context, activity);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "showAdInterstitial");
                Log.e(" Admod","errorCodeAds:" +adError.getMessage());
                Log.e(" Admod","errorCodeAds:" +adError.getCause());
                if(dialog!=null){
                    dialog.dismiss();
                }
                addNewActivity(context,activity);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                //Toast.makeText(ActivitySplash.this, "666666", Toast.LENGTH_SHORT).show();
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {

                addNewActivity(context, activity);
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        Log.e(" Admod", "showAdInterstitial");
    }
    public static void loadAndShowAdInterstitialWithCallback(Context context, String admobId,boolean isShow, AdCallback adCallback, boolean enableLoadingDialog){

        if(isTesting){
            admobId = ads_admob_inter_test_id;
        }
        long currentTime = getCurrentTime();
        if (currentTime - lastTimeShowInterstitial >= limitTime && isShow && isNetworkConnected(context)) {
            if(enableLoadingDialog){
                dialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setTitle("Loading");
                dialog.setMessage("Loading ads. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(admobId);
            mInterstitialAd.loadAd(getAdRequest());
            Log.e(" Admod", "loadAdInterstitial");

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = true;
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e(" Admod", "showAdInterstitial");
                    Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                    Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    adCallback.onAdClosed();
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed
                }

                @Override
                public void onAdClicked() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    adCallback.onAdClosed();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    isAdShowing = false;
                }
            });
            Log.e(" Admod", "showAdInterstitial");
        }
        else{
            adCallback.onAdClosed();
        }
    }



    public static void showAdInterstitialWithCallback(AdCallback adCallback){
        long currentTime = getCurrentTime();
        if (currentTime - lastTimeShowInterstitial >= limitTime) {
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    long currentTime = getCurrentTime();
                    if (currentTime - lastTimeShowInterstitial >= limitTime) {
                        lastTimeShowInterstitial = currentTime;
                        mInterstitialAd.show();
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e(" Admod", "showAdInterstitial");
                    Log.e(" Admod","errorCodeAds:" +adError.getMessage());
                    Log.e(" Admod","errorCodeAds:" +adError.getCause());
                    if(dialog!=null){
                        dialog.dismiss();
                    }
                    adCallback.onAdClosed();
                }
                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed
                }
                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                    //Toast.makeText(ActivitySplash.this, "666666", Toast.LENGTH_SHORT).show();
                    if(dialog!=null){
                        dialog.dismiss();
                    }
                }
                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }
                @Override
                public void onAdClosed() {

                    if(dialog!=null){
                        dialog.dismiss();
                    }
                    adCallback.onAdClosed();
                }
            });
        }
        else{
            adCallback.onAdClosed();
        }
    }
    public static void showAdInterstitialAndReplaceNewFragment(FragmentManager fm, Fragment fragment,int contentFrame, boolean addToBackStack){
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                long currentTime = getCurrentTime();
                if (currentTime - lastTimeShowInterstitial >= limitTime) {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                } else {
                    replaceFragment(fm, fragment,contentFrame,addToBackStack);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "showAdInterstitial");
                Log.e(" Admod","errorCodeAds:" +adError.getMessage());
                Log.e(" Admod","errorCodeAds:" +adError.getCause());
                if(dialog!=null){
                    dialog.dismiss();
                }
                replaceFragment(fm, fragment,contentFrame,addToBackStack);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                //Toast.makeText(ActivitySplash.this, "666666", Toast.LENGTH_SHORT).show();
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {

                replaceFragment(fm, fragment,contentFrame,addToBackStack);
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        Log.e(" Admod", "showAdInterstitial");
    }
    public static void showAdInterstitialAndAddNewFragment(AppCompatActivity context, Fragment fragment,int contentFrame, boolean addToBackStack){
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                long currentTime = getCurrentTime();
                if (currentTime - lastTimeShowInterstitial >= limitTime) {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                } else {
                    addFragment(context, fragment,contentFrame,addToBackStack);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "showAdInterstitial");
                Log.e(" Admod","errorCodeAds:" +adError.getMessage());
                Log.e(" Admod","errorCodeAds:" +adError.getCause());
                if(dialog!=null){
                    dialog.dismiss();
                }
                addFragment(context, fragment,contentFrame,addToBackStack);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                //Toast.makeText(ActivitySplash.this, "666666", Toast.LENGTH_SHORT).show();
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {

                addFragment(context, fragment,contentFrame,addToBackStack);
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        Log.e(" Admod", "showAdInterstitial");
    }

    private static void addNewActivity(Context context, Class activity){
        Intent i =  new Intent(context,activity);
        context.startActivity(i);
    }
    private static void startNewActivity(Context context, Class activity){
        Intent i =  new Intent(context,activity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    public static void addFragment(AppCompatActivity context, Fragment fragment, int contentFrame, boolean addToBackStack) {
        FragmentTransaction transaction = context.getSupportFragmentManager()
                .beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        } else {
            transaction.addToBackStack(fragment.toString());
        }
        if (fragment.getTag() == null) {
            transaction.replace(contentFrame, fragment, fragment.toString());
        } else {
            transaction.replace(contentFrame, fragment, fragment.getTag());
        }
        transaction.commit();
    }
    public static void replaceFragment(FragmentManager fm, Fragment fragment, int contentFrame, boolean addToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        } else {
            transaction.addToBackStack(fragment.toString());
        }
        if (fragment.getTag() == null) {
            transaction.replace(contentFrame, fragment, fragment.toString());
        } else {
            transaction.replace(contentFrame, fragment, fragment.getTag());
        }
        transaction.commit();
    }

    private static long getCurrentTime() {
        return System.currentTimeMillis();
    }
    public static void getDeviceID(Context context){
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();
        Log.i("deviceid =",deviceId);
    }
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void showAdInterstitialAndAddNewActivityWithIntent(final Context context, Intent intent) {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                long currentTime = getCurrentTime();
                if (currentTime - lastTimeShowInterstitial >= limitTime) {
                    lastTimeShowInterstitial = currentTime;
                    mInterstitialAd.show();
                } else {
                    addNewActivityWithIntent(context,  intent);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(" Admod", "showAdInterstitial");
                Log.e(" Admod", "errorCodeAds:" + adError.getMessage());
                Log.e(" Admod", "errorCodeAds:" + adError.getCause());
                if (dialog != null) {
                    dialog.dismiss();
                }
                addNewActivityWithIntent(context,  intent);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                //Toast.makeText(ActivitySplash.this, "666666", Toast.LENGTH_SHORT).show();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                addNewActivityWithIntent(context,  intent);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        Log.e(" Admod", "showAdInterstitial");
    }

    private static void addNewActivityWithIntent(Context context, Intent intent) {
        context.startActivity(intent);
    }

}
