package gnnsnowszerro.com.viewpager;

/**
 * Created by Roma on 15.06.2017.
 */

public interface IViewPager {
    void onInstallClicked();
    void onPrivacyClicked();
    void onCoinsCountChanged();
    void onBannerLoaded(String name,String rating,String buttonText,String imgUrl);
}
