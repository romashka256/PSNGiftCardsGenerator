package com.mopub.mobileads;

import android.app.Activity;
import android.content.Context;

import com.mopub.common.logging.MoPubLog;
import com.nativex.common.SharedPreferenceManager;
import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.enums.AdEvent;
import com.nativex.monetization.listeners.OnAdEventV2;
import com.nativex.monetization.mraid.AdInfo;

import java.util.Map;

/**
 * NativeXCustomEventInterstitial
 * <p/>
 * Copyright (c) 2015 NativeX, LLC.  All rights reserved.
 * <p/>
 * Created by joel.braun on 2015.04.22
 */

@SuppressWarnings("UnusedDeclaration")
public class NativeXCustomEventInterstitial extends CustomEventInterstitial implements OnAdEventV2 {

    private CustomEventInterstitialListener moPubListener = null;
    private Activity moPubActivity = null;

    private static final boolean enableLogging = true;

    private String _placementName;

    @Override
    protected void loadInterstitial(Context context, CustomEventInterstitialListener eventListener, Map<String, Object> localExtras, Map<String, String> serverExtras) {

        this.moPubListener = eventListener;

        if (enableLogging) { MoPubLog.d("LoadInterstitial hit"); }

        SharedPreferenceManager.setBuildType("MoPub");

        if ((context instanceof Activity) == false) {
            MoPubLog.e("NativeX Adaptor: context is not type Activity!");
            if (eventListener != null) {
                eventListener.onInterstitialFailed(MoPubErrorCode.INTERNAL_ERROR);
            }
            return;
        }
        // at this point we're guaranteed context is of type Activity
        this.moPubActivity = (Activity)context;

        // reset default
        String appId = "";
        this._placementName = "";

        if (enableLogging) { MoPubLog.d("serverExtras entry:"); }
        for (Map.Entry<String, String> entry : serverExtras.entrySet()) {
            if (enableLogging) { MoPubLog.d("    " + entry.getKey() + ":" + entry.getValue()); }
            if (entry.getKey().compareToIgnoreCase("appid") == 0) {
                appId = entry.getValue();
            }
            else if (entry.getKey().compareToIgnoreCase("placementid") == 0) {
                this._placementName = entry.getValue();
            }
        }

        if (appId.isEmpty()) {
            MoPubLog.e("NativeX Adaptor: Cannot request interstitial; required App ID is missing or empty");
            if (eventListener != null) {
                eventListener.onInterstitialFailed(MoPubErrorCode.UNSPECIFIED);
            }
            return;
        } else if (_placementName.isEmpty()) {
            MoPubLog.e("NativeX Adaptor: Cannot request interstitial; required placement name is missing or empty");
            if (eventListener != null) {
                eventListener.onInterstitialFailed(MoPubErrorCode.UNSPECIFIED);
            }
            return;
        }

        // for debugging
        if (enableLogging) { MonetizationManager.enableLogging(true); }

        MonetizationManager.fetchAdStateless(this.moPubActivity, appId, _placementName, this);
    }

    @Override
    protected void showInterstitial() {
        MonetizationManager.showReadyAdStateless(this.moPubActivity, _placementName, this);
    }

    // cleanup code only
    @Override
    protected void onInvalidate() {
    }

    @Override
    public void onEvent(AdEvent event, AdInfo adInfo, String message) {
        if (enableLogging) { MoPubLog.d("Placement: '" + adInfo.getPlacement() + "' event: '" + event.toString() + "'"); }

        if (this.moPubListener == null) {
            MoPubLog.e("NativeX Adaptor; MoPub Event Listener is null!");
        }

        switch (event) {
            case FETCHED:
            case ALREADY_FETCHED:
                if (this.moPubListener != null) { this.moPubListener.onInterstitialLoaded(); }
                break;

            case ERROR:  // An error has occurred and the ad is going to be closed.
                MoPubLog.e("NativeX Adaptor: error occurred - " + message);
                if (this.moPubListener != null) { this.moPubListener.onInterstitialFailed(MoPubErrorCode.INTERNAL_ERROR); }
                break;

            case NO_AD:
                if (enableLogging) { MoPubLog.i("NativeX Adaptor: No ads to be shown"); }
                if (this.moPubListener != null) { this.moPubListener.onInterstitialFailed(MoPubErrorCode.NO_FILL); }
                break;

            case IMPRESSION_CONFIRMED:
                if (this.moPubListener != null) { this.moPubListener.onInterstitialShown(); }
                break;
            case DISMISSED:
                if (this.moPubListener != null) { this.moPubListener.onInterstitialDismissed(); }
                break;
            case USER_NAVIGATES_OUT_OF_APP:
                if (this.moPubListener != null) {
                    // NOTE: Mopub's onLeaveApplcation() calls onInterstitialClicked() directly.. where iOS's version of willLeaveApplication does nothing (noop)
                    // only want to call one of these; keeping this in line with given event..
                    this.moPubListener.onLeaveApplication();
                    //this.moPubListener.onInterstitialClicked();
                }
                break;
            default:
                break;
        }
    }
}
