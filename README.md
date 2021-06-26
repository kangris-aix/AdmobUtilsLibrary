# AdmobUtilsLibrary
- init
```bash
AdmodUtils.initAdmob(Context context, boolean isDebug, boolean isAddDeviceTest);

// isDebug:bool = use admob id test
// isAddDeviceTest:bool = get and add device test this device
```
- Interstitial
  + loadAndShowAdInterstitialWithCallback

```bash 
AdmodUtils.loadAndShowAdInterstitialWithCallback(context, admobId, limitTime, 
      new AdCallback() {
                    @Override
                    public void onAdClosed() {
                      //code here
                    }

                    @Override
                    public void onAdFail() {
                      //code here
                    }
                }, isEnableDialog);

// admobId:String
// limitTime:Int (milisecond)
// isEnableDialog:Bool 
```
  + loadAdInterstitial
  + showAdInterstitialWithCallback
- AdBanner
- AdReward
- AdNative
- AppOpenAds
# PurchaseUtils
- init
```bash
PurchaseUtils.initBilling(Context context,String play_console_license, String idSubscribe);
```
- subscribe
```bash
PurchaseUtils.subscribe(Activity context, String idSubscribe);
```
- isPurchased
```bash
PurchaseUtils.isPurchased(String idSubscribe);
```
