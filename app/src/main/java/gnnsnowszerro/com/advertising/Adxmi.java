package gnnsnowszerro.com.advertising;

import android.app.Activity;

import net.adxmi.android.AdManager;
import net.adxmi.android.os.OffersManager;
import net.adxmi.android.os.PointsManager;

import gnnsnowszerro.com.StoreCoins;

/**
 * Created by Roma on 17.06.2017.
 */

public class Adxmi{
    private Activity activity;

    private StoreCoins storeCoins;
    private String APP_ID = "01bc87913a968e80";
    private String SECRET_KEY = "19edf67159c7e389";
    public Adxmi(Activity activity) {
        this.activity = activity;
        storeCoins = new StoreCoins(activity);
        initAdxmi();
    }

    public void initAdxmi(){
        AdManager.getInstance(activity).init(APP_ID,SECRET_KEY);
        OffersManager.getInstance(activity).onAppLaunch();
        AdManager.getInstance(activity).setEnableDebugLog(false);
    }

    public void showAdxmi(){

        OffersManager.getInstance(activity).showOffersWall();

        int myAdxmiCoinsBalance = PointsManager.getInstance(activity).queryPoints();

        storeCoins.updateAdxmiCoinsCount(myAdxmiCoinsBalance);
    }

}
