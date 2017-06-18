package gnnsnowszerro.com.generate;

/**
 * Created by Roma on 16.06.2017.
 */

public interface IGeneratePresenter {
    void onGenerateClicked(int coins,int expectedcoins,String email);
    void onRedeemClicked(String email);
}
