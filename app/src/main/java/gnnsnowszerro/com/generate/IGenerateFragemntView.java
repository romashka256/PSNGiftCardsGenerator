package gnnsnowszerro.com.generate;

/**
 * Created by Roma on 16.06.2017.
 */

public interface IGenerateFragemntView {
    void onGenerateClicked(int valid);
    void onRedeemClicked(int valid);
    void onInstallClicked();
    void onPrivacyClicked();
    void onBannerLoaded(String name,String rating,String buttonText,String imgUrl);
}
