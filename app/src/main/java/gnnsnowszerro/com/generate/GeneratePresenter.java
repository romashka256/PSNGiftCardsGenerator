package gnnsnowszerro.com.generate;

import android.app.Activity;

import gnnsnowszerro.com.advertising.AppNext;
import gnnsnowszerro.com.viewpager.OnBannerLoadListener;

/**
 * Created by Roma on 16.06.2017.
 */

public class GeneratePresenter implements IGeneratePresenter, OnBannerLoadListener{

    private ValidetedData validetedData;
    private IGenerateFragemntView view;
    private AppNext appNext;

    public GeneratePresenter(IGenerateFragemntView view,Activity activity) {
        validetedData = new ValidetedData();
        this.view = view;
        appNext = new AppNext(activity,this);
    }

    @Override
    public void onGenerateClicked(int coins, int expectedcoins, String email) {
        if (validetedData.isEnough(coins, expectedcoins)) {
            if (validetedData.isMailValid(email)) {
                view.onGenerateClicked(2);
            } else {
                view.onGenerateClicked(1);
            }
        } else {
            view.onGenerateClicked(0);
        }

    }

    @Override
    public void onRedeemClicked(String email) {
        if(validetedData.isMailBlank(email)){
            view.onRedeemClicked(0);
        }else if(validetedData.isMailValid(email)){
            view.onRedeemClicked(2);
        }else{
            view.onRedeemClicked(1);
        }
    }


    public void initAppNext() {
        appNext.initAppnext();
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
