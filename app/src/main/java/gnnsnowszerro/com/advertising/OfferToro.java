package gnnsnowszerro.com.advertising;

import android.app.Activity;
import android.util.Log;

import com.offertoro.sdk.OTOfferWallSettings;
import com.offertoro.sdk.interfaces.OfferWallListener;
import com.offertoro.sdk.sdk.OffersInit;

import gnnsnowszerro.com.StoreCoins;

/**
 * Created by Roma on 17.06.2017.
 */

public class OfferToro {


    public static final String SECRET_KEY = "e04ac7cb5cba11fbed15a53cab99e952";
    public static final String APP_ID_OFFER_TORO = "2632";
    public static final String USER_ID = "4746";
    private static final String TAG = "OfferToroProvider";
    private StoreCoins storeCoins;

    private Activity activity;

    public OfferToro(Activity activity) {
        this.activity = activity;
        storeCoins = new StoreCoins(activity);
    }

    public void initOfferToro() {
        OTOfferWallSettings.getInstance().configInit(APP_ID_OFFER_TORO, SECRET_KEY, USER_ID);

        OfferWallListener offerWallListener = new OfferWallListener() {
            @Override
            public void onOTOfferWallInitSuccess() {
                Log.i(TAG, "onOTOfferWallInitSuccess");
            }

            @Override
            public void onOTOfferWallInitFail(String s) {
                Log.i(TAG, "onOTOfferWallInitFail " + s);
            }

            @Override
            public void onOTOfferWallOpened() {
                Log.i(TAG, "onOTOfferWallOpened");
            }

            @Override
            public void onOTOfferWallCredited(double credits, double totalCredits) {
                Log.i(TAG, "credits = " + credits +
                        "\ntotalCredits = " + totalCredits);
                storeCoins.updateOfferToroCoinsCount((int) totalCredits);
            }

            @Override
            public void onOTOfferWallClosed() {
            }
        };
        OffersInit.getInstance().setOfferWallListener(offerWallListener);
    }

    public void show(){
        OffersInit.getInstance().showOfferWall(activity);
    }


}
