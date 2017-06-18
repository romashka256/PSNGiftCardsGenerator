package gnnsnowszerro.com.earncoins;

import android.app.Activity;
import android.provider.Settings;

import java.util.Locale;

import gnnsnowszerro.com.advertising.AdColonyAds;
import gnnsnowszerro.com.advertising.Adxmi;
import gnnsnowszerro.com.advertising.NativeX;
import gnnsnowszerro.com.advertising.OfferToro;

/**
 * Created by Roma on 17.06.2017.
 */

public class EarnCoinsPresenter implements IEarnCoinsFragment {

    private Activity activity;
    private OfferToro offerToro;
    private NativeX nativeX;
    private Adxmi adxmi;
    private AdColonyAds adcolony;

    public EarnCoinsPresenter(Activity activity) {
        this.activity = activity;
        offerToro = new OfferToro(activity);
        nativeX = new NativeX(activity);
        adxmi = new Adxmi(activity);
        adcolony = new AdColonyAds(activity);

    }

    public void initOfferToro() {
        offerToro.initOfferToro();
    }

    public void showOfferToro() {
        offerToro.show();
    }

    public void initNativeX() {
        nativeX.initNativeX();
    }

    public void showNativeX() {
        nativeX.showNativeX();
    }
    public void  loadNativeX() {

    }

    public void initAdxmi() {
    }

    public void showAdxmi() {
        adxmi.showAdxmi();
    }

    public void initAdColony() {

    }

    public void showAdColony() {
        adcolony.showAd();
    }

    @Override
    public void onQuickOffersClicked() {

    }

    @Override
    public void onProTasksClicked() {

    }

    @Override
    public void onDailyOffersClicked() {

    }

    @Override
    public void onRateUsClicked() {

    }

    @Override
    public void onWatchVideoClicked() {

    }

    private String getAdColonyDeviceId() {
        String android_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = (android_id).toUpperCase(Locale.ENGLISH);
        return deviceId;
    }
}
