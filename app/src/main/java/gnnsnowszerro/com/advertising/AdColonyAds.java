package gnnsnowszerro.com.advertising;

import android.app.Activity;
import android.widget.Toast;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;

import gnnsnowszerro.com.StoreCoins;

/**
 * Created by Roma on 17.06.2017.
 */

public class AdColonyAds{

    private final int reward = 10;
    private  final String ZONE_ID = "vzac61b40e83e8436c9e";
    private  final String APP_ID_ADCOLONY = "appa567471ee29646b5b5";

    private Activity activity;
    private AdColonyInterstitial _ad = null;
    private AdColonyInterstitialListener listener;
    private StoreCoins storeCoins;

    public AdColonyAds(Activity activity) {
        this.activity = activity;
        storeCoins = new StoreCoins(activity.getApplicationContext());
        configureAdcolony();
        loadAdcolony();
    }

    public void showAd() {
        configureAdcolony();
        if (_ad != null) {
            _ad.show();
        } else Toast.makeText(activity, "No ads", Toast.LENGTH_SHORT).show();
    }

    private void configureAdcolony() {
        AdColony.configure(activity,APP_ID_ADCOLONY, ZONE_ID);
        listener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial ad) {
                _ad = ad;
            }

            @Override
            public void onClosed(AdColonyInterstitial ad) {
                super.onClosed(ad);
                storeCoins.updateCoinsCount(reward);
            }
        };
        AdColony.requestInterstitial(APP_ID_ADCOLONY, listener);
    }

    private void loadAdcolony() {
        AdColony.requestInterstitial(ZONE_ID, listener);
    }
}
