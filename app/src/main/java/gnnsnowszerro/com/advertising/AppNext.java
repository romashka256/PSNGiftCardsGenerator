package gnnsnowszerro.com.advertising;

import android.app.Activity;
import android.os.AsyncTask;

import com.appnext.appnextsdk.API.AppnextAPI;
import com.appnext.appnextsdk.API.AppnextAd;
import com.appnext.appnextsdk.API.AppnextAdRequest;

import java.util.ArrayList;

import gnnsnowszerro.com.viewpager.OnBannerLoadListener;

/**
 * Created by Roma on 17.06.2017.
 */

public class AppNext {

    private AppnextAPI appnextAPI;
    private AppnextAd ad;
    private Activity activity;
    private OnBannerLoadListener listener;

    public static final String APP_ID_APPNEXT = "322054";
    public static final String PLACEMENT_ID_APPNEXT = "71e1876b-4594-4065-8d8a-9739c2e519de";

    public AppNext(Activity activity,OnBannerLoadListener listener) {
        this.activity = activity;
        this.listener = listener;
    }



    public void loadBanner(){
        new AppNextLoadTask().execute();
    }

    public void onInstallClicked(){
        appnextAPI.adClicked(ad);
    }

    public void onImagePrivacyClicked(){
        appnextAPI.privacyClicked(ad);
    }

    public void initAppnext(){
        appnextAPI = new AppnextAPI(activity, PLACEMENT_ID_APPNEXT);

        appnextAPI.setAdListener(new AppnextAPI.AppnextAdListener() {
            @Override
            public void onAdsLoaded(ArrayList<AppnextAd> arrayList) {
                ad = arrayList.get(0);

                listener.onLoaded(ad.getAdTitle(), ad.getStoreRating(), ad.getButtonText(), ad.getImageURL());

                appnextAPI.adImpression(ad);
            }

            @Override
            public void onError(String s) {

            }

    });
    }
    class AppNextLoadTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            initAppnext();
            appnextAPI.loadAds(new AppnextAdRequest().setCount(1));
            return null;
        }
    }
}


