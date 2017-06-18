package gnnsnowszerro.com.viewpager;

import android.app.Activity;

import gnnsnowszerro.com.advertising.AppNext;

/**
 * Created by Roma on 17.06.2017.
 */

public class ViewPagerPresenter implements OnBannerLoadListener{

    private AppNext appNext;
    private IViewPager view;

    public ViewPagerPresenter(IViewPager view,Activity activity) {
        this.view = view;
        appNext = new AppNext(activity,this);
    }

    public void loadAppNextBanner(){
        appNext.loadBanner();
    }

    @Override
    public void onLoaded(String name, String rating, String buttonText, String imgUrl) {
        view.onBannerLoaded(name,rating,buttonText,imgUrl);
    }

    public void onInstallClicked(){
        appNext.onInstallClicked();
    }

    public void onPrivacyClicked(){
        appNext.onImagePrivacyClicked();
    }
}
