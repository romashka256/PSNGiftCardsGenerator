package com.mopub.mobileads;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.mopub.common.LifecycleListener;
import com.mopub.common.MoPubReward;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.CustomEventRewardedVideo.CustomEventRewardedVideoListener;
import com.nativex.common.SharedPreferenceManager;
import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.business.reward.Reward;
import com.nativex.monetization.communication.RedeemRewardData;
import com.nativex.monetization.enums.AdEvent;
import com.nativex.monetization.listeners.OnAdEventV2;
import com.nativex.monetization.listeners.RewardListener;
import com.nativex.monetization.mraid.AdInfo;

import java.util.Map;

/**
 * NativeXCustomRewardedVideo
 * <p/>
 * Copyright (c) 2015 NativeX, LLC.  All rights reserved.
 * <p/>
 * Created by joel.braun on 2015.06.29
 */
class NativeXCustomRewardedVideo extends CustomEventRewardedVideo implements OnAdEventV2, RewardListener, CustomEventRewardedVideoListener {

    private String _placementName;
    private Activity _moPubActivity = null;
    private boolean _converted = false;

    private static final boolean bEnableLogging = true;

    //<editor-fold desc="CustomEventRewardedVideo Implementation">
    protected CustomEventRewardedVideoListener getVideoListenerForSdk() {
        return this;
    }

    protected LifecycleListener getLifecycleListener() {
        return null;
    }

    @NonNull
    protected String getAdNetworkId() {
        return this._placementName;
    }

    protected void onInvalidate() {
        // do nothing here..
    }

    protected boolean checkAndInitializeSdk(@NonNull Activity launcherActivity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) {
        MonetizationManager.setRewardListener(this);
        return true;

    }

    protected void loadWithSdkInitialized(@NonNull Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) {

        this._moPubActivity = activity;

        if (bEnableLogging) { MoPubLog.d("[nxVidAdapter]: Load Rewarded Video hit"); }

        SharedPreferenceManager.setBuildType("MoPub");

        // reset default
        String appId = "";
        this._placementName = "";

        if (bEnableLogging) { MoPubLog.d("[nxVidAdapter]: serverExtras entry:"); }

        for (Map.Entry<String, String> entry : serverExtras.entrySet()) {
            if (bEnableLogging) { MoPubLog.d("[nxVidAdapter]:     " + entry.getKey() + ":" + entry.getValue()); }
            if (entry.getKey().compareToIgnoreCase("appid") == 0) {
                appId = entry.getValue();
            }
            else if (entry.getKey().compareToIgnoreCase("placementid") == 0) {
                this._placementName = entry.getValue();
            }
        }

        if (appId.isEmpty()) {
            MoPubLog.e("NativeX Rewarded Video: Cannot request rewarded video; required App ID is missing or empty");
            return;
        } else if (_placementName.isEmpty()) {
            MoPubLog.e("NativeX Rewarded Video: Cannot request interstitial; required placement name is missing or empty");
            return;
        }

        // for debugging
        if (bEnableLogging) { MonetizationManager.enableLogging(true); }

        MonetizationManager.fetchAdStateless(activity, appId, _placementName, this);
    }

    protected boolean hasVideoAvailable() {
        return MonetizationManager.isAdReady(this._placementName);
    }

    protected void showVideo() {
        MonetizationManager.showReadyAdStateless(this._moPubActivity, this._placementName, this);
    }
    //</editor-fold>


    //<editor-fold desc="AdEvent + Reward listener">
    @Override
    public void onEvent(AdEvent event, AdInfo adInfo, String message) {

        if (bEnableLogging) { MoPubLog.d("[nxVidAdapter]: Placement: '" + adInfo.getPlacement() + "' event: '" + event.toString() + "'"); }

        switch (event) {
            case FETCHED:
            case ALREADY_FETCHED:
                MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(NativeXCustomRewardedVideo.class, _placementName);
                break;

            case ERROR:
                MoPubLog.e("NativeX Rewarded Video Adapter: error occurred - " + message);
                MoPubRewardedVideoManager.onRewardedVideoPlaybackError(NativeXCustomRewardedVideo.class, _placementName, MoPubErrorCode.UNSPECIFIED);

                break;

            case NO_AD:
                if (bEnableLogging) { MoPubLog.i("[nxVidAdapter]: No ads to be shown"); }
                MoPubRewardedVideoManager.onRewardedVideoLoadFailure(NativeXCustomRewardedVideo.class, _placementName, MoPubErrorCode.NO_FILL);
                break;

            case IMPRESSION_CONFIRMED:
                MoPubRewardedVideoManager.onRewardedVideoStarted(NativeXCustomRewardedVideo.class, _placementName);
                break;

            case DISMISSED:
                MoPubRewardedVideoManager.onRewardedVideoClosed(NativeXCustomRewardedVideo.class, _placementName);

                if (this._converted) {
                    MonetizationManager.redeemRewards();    // should use normal listener callback, which should be this..
                    // reset converted, in case this object is reused..
                    this._converted = false;
                }
                break;

            case USER_NAVIGATES_OUT_OF_APP:
                MoPubRewardedVideoManager.onRewardedVideoClicked(NativeXCustomRewardedVideo.class, _placementName);
                //MoPubRewardedVideoManager.onRewardedVideoClosed(NativeXCustomRewardedVideo.class, _placementName);
                break;

            case AD_CONVERTED:
                this._converted = true; // assuming this is rewarded video.. used only to denote making redeem rewards call
                break;

            default:
                break;
        }
    }

    @Override
    public void onRedeem(RedeemRewardData rewardData) {
        if (bEnableLogging) { MoPubLog.d("[nxVidAdapter]: onRedeem: " + _placementName); }

        if (rewardData.getRewards().length <= 0) {
            if (bEnableLogging) { MoPubLog.d("[nxVidAdapter]: No reward amount found; not calling Mopub reward redemption"); }
            return;
        }

        String rewardType = "";
        int rewardAmount = 0;
        for(Reward r : rewardData.getRewards()) {
            // hopefully only one reward type..
            if (rewardType.isEmpty() || (rewardType.compareToIgnoreCase(r.getRewardName()) == 0)) {
                if (rewardType.isEmpty()) {
                    rewardType = r.getRewardName();
                }
                rewardAmount += r.getAmount();
            }
        }

        MoPubReward mpReward = MoPubReward.success(rewardType, rewardAmount);
        MoPubRewardedVideoManager.onRewardedVideoCompleted(NativeXCustomRewardedVideo.class, _placementName, mpReward);
    }
    //</editor-fold>

}
