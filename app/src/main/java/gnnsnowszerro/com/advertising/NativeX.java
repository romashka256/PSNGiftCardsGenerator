package gnnsnowszerro.com.advertising;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.business.reward.Reward;
import com.nativex.monetization.communication.RedeemRewardData;
import com.nativex.monetization.enums.AdEvent;
import com.nativex.monetization.enums.NativeXAdPlacement;
import com.nativex.monetization.listeners.OnAdEventV2;
import com.nativex.monetization.listeners.RewardListener;
import com.nativex.monetization.listeners.SessionListener;
import com.nativex.monetization.mraid.AdInfo;
import com.nativex.msdk.out.MVOfferWallHandler;

import gnnsnowszerro.com.StoreCoins;

/**
 * Created by Roma on 17.06.2017.
 */

public class NativeX{

    private StoreCoins storeCoins;
    private Activity activity;
    private MVOfferWallHandler offerWallHandler;
    private String TAG = "NetiveX";

    public NativeX(Activity activity) {
        this.activity = activity;
        storeCoins = new StoreCoins(activity);
    }

    public static final String APP_ID_NATIVEX = "120243";

    public void initNativeX(){
        SessionListener sessionListener = new SessionListener() {
            @Override
            public void createSessionCompleted(boolean success, boolean isOfferWallEnabled, String sessionId) {
                if (success) {
                    Log.i(TAG, "Nativex success");
                } else {
                    Log.i(TAG, "Nativex error");
                }
            }
        };
        MonetizationManager.createSession(activity, APP_ID_NATIVEX, sessionListener);
    }

    public void showNativeX() {
        RewardListener rewardListener = new RewardListener() {
            @Override
            public void onRedeem(RedeemRewardData data) {
                for (Reward reward : data.getRewards()) {
                    Log.i(TAG, "Reward: rewardName:" + reward.getRewardName()
                            + " rewardId:" + reward.getRewardId()
                            + " amount:" + Double.toString(reward.getAmount()));
                    storeCoins.updateCoinsCount((int) reward.getAmount());
                }
            }
        };

        MonetizationManager.setRewardListener(rewardListener); // SET Reward Listener

        OnAdEventV2 onAdEventListener = new OnAdEventV2() {
            @Override
            public void onEvent(AdEvent adEvent, AdInfo adInfo, String s) {
                switch (adEvent) {
                    case ALREADY_FETCHED:
                        Log.i(TAG, "ALREADY_FETCHED");
                        break;
                    case ALREADY_SHOWN:
                        Log.i(TAG, "ALREADY_SHOWN");
                        break;
                    case BEFORE_DISPLAY:
                        Log.i(TAG, "BEFORE_DISPLAY");
                        break;
                    case DISMISSED:
                        Log.i(TAG, "DISMISSED");
                        break;
                    case DISPLAYED:
                        Log.i(TAG, "DISPLAYED");
                        break;
                    case DOWNLOADING:
                        Log.i(TAG, "DOWNLOADING");
                        break;
                    case ERROR:
                        Log.i(TAG, "ERROR " + s);
                        break;
                    case EXPIRED:
                        Log.i(TAG, "EXPIRED");
                        break;
                    case FETCHED:
                        Log.i(TAG, "FETCHED");
                        break;
                    case NO_AD:
                        Log.i(TAG, "NO_AD " + s);
                        Toast.makeText(activity, "No Ad - " + s, Toast.LENGTH_SHORT).show();
                        break;
                    case USER_NAVIGATES_OUT_OF_APP:
                        Log.i(TAG, "USER_NAVIGATES_OUT_OF_APP");
                        break;
                    case IMPRESSION_CONFIRMED:
                        Log.i(TAG, "IMPRESSION_CONFIRMED");
                        break;
                    case AD_CONVERTED:
                        Log.i(TAG, "AD_CONVERTED");
                        break;
                    default:
                        break;
                }
            }
        };

        MonetizationManager.fetchAd(activity, NativeXAdPlacement.Main_Menu_Screen , onAdEventListener); // Fetch Ad

        if (MonetizationManager.isAdReady(NativeXAdPlacement.Main_Menu_Screen)) {
            MonetizationManager.showReadyAd(activity,  NativeXAdPlacement.Main_Menu_Screen, onAdEventListener);
        }
    }
}
