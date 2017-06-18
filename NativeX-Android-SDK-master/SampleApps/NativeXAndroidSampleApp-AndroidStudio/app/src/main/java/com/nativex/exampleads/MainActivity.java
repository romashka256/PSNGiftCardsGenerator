// example MainActivity that utilize NativeX Ads
// author: Karol Ryt
// date: 24.08.2014
package com.nativex.exampleads;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.nativex.monetization.MonetizationManager;
import com.nativex.monetization.business.reward.Reward;
import com.nativex.monetization.communication.RedeemRewardData;
import com.nativex.monetization.enums.AdEvent;
import com.nativex.monetization.enums.NativeXAdPlacement;
import com.nativex.monetization.listeners.OnAdEventV2;
import com.nativex.monetization.listeners.RewardListener;
import com.nativex.monetization.listeners.SessionListener;
import com.nativex.monetization.mraid.AdInfo;

// simple Activity that utilize NativeX SDK 
public class MainActivity extends Activity implements OnAdEventV2, SessionListener, RewardListener {

	// variable to control if app is ready to show ads
	private Boolean _canShowAds = false;
	//reference for the apps buttons
	private ImageButton launchButton;
	private ImageButton menuButton;
	private ImageButton levelButton;
	private ImageButton storeButton;
	private ImageButton playerButton;
	
	private MediaPlayer musicPlayer;
	
	

    // ---------------------------------------------------------------
    //
    // Activity Messages + SDK Initialization
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//SAMPLE APP SPECIFIC CODE:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launchButton = (ImageButton) findViewById(R.id.ButtonGameLaunch);
        menuButton = (ImageButton) findViewById(R.id.ButtonMainMenu);
        levelButton = (ImageButton) findViewById(R.id.ButtonLevelFailed);
        storeButton = (ImageButton) findViewById(R.id.ButtonStoreOpen);
        playerButton = (ImageButton) findViewById(R.id.ButtonPlayerGenerated);
        //disabling button while ad is not fetched
        launchButton.setEnabled(false);
        menuButton.setEnabled(false);
        levelButton.setEnabled(false);
        storeButton.setEnabled(false);
        playerButton.setEnabled(false);
        //END
    }
     
    @Override
    protected void onResume() {
        super.onResume();

        // create Session
        MonetizationManager.createSession(getApplicationContext(), "22520", this);
        
        // setting the currency listener
        // It is recommended that you implement this even if you do not plan to use
        // rewarded ads. You can then simply enable rewarded ads from Self Service without
        // any code changes or a new release.
        MonetizationManager.setRewardListener(this);

		// resume/start music
		playAudio();
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	pauseAudio();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	destroyAudio();
    }
    
    // audio control functions
    private void prepareAudio() {
    	try {
    		if (musicPlayer == null) {
    			// La Plume by Whiteyes, CC 3.0 BY-NC-ND
    			// https://www.jamendo.com/en/track/37340/la-plume
    	        musicPlayer = MediaPlayer.create(getApplicationContext(), R.raw.whiteeyes_la_plume);
    			musicPlayer.setLooping(true);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private void pauseAudio() {
    	try {
        	if ((musicPlayer != null) && (musicPlayer.isPlaying())) {
        		musicPlayer.pause();
        	}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    private void playAudio() {
    	try {
        	if (musicPlayer == null) {
        		prepareAudio();
        	}
    		musicPlayer.start();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    private void destroyAudio() {
    	if (musicPlayer != null) {
    		musicPlayer.release();
    		musicPlayer = null;
    	}
    }

	//Listener callback for createSession
	public void createSessionCompleted(boolean success, boolean isOfferWallEnabled, String message) {
		if (success) {	
            // A session with our servers was established successfully.
            // The app is ready to show ads.
			System.out.println("Wahoo! We are now ready to show an ad!");
    		_canShowAds = true;
    		
    		//We are fetching all the ads here as this is a one scene app. 
    		//However, in a typical integration you will want to spread these calls out in your game. 
    		//It is recommended that you add these calls in an area that would allow around 5 seconds before attempting to show
    		MonetizationManager.fetchAd(this, NativeXAdPlacement.Game_Launch, this);
    		MonetizationManager.fetchAd(this, NativeXAdPlacement.Main_Menu_Screen, this);
    		MonetizationManager.fetchAd(this, NativeXAdPlacement.Level_Failed, this);
    		MonetizationManager.fetchAd(this, NativeXAdPlacement.Store_Open, this);
    		MonetizationManager.fetchAd(this, NativeXAdPlacement.Player_Generated_Event, this);
        } else {
            // Establishing a session with our servers failed.
            // The app will be unable to show ads until a session is established.
        	System.out.println("Oh no! Something isn't set up correctly - re-read the documentation or ask customer support for help https://selfservice.nativex.com/Help");
        	_canShowAds = false;
        }
		
	}
    
    // ---------------------------------------------------------------
    //
    // GUI Logic
    
    //
    public void OnButtonInterstitialClick(View view) {
        android.util.Log.d("Click button", "OnButtonInterstitialClick");
        
        if(_canShowAds == true)
        	//shows an ad that is already fetched and ready to show instantly
        	//NOTE: if the ad has not been fetched yet this method will not do anything
        	MonetizationManager.showReadyAd(this, NativeXAdPlacement.Game_Launch, this);
        else
        	android.util.Log.d("ShowAd","Can't show ads but still button clicked");
    }
    
	//
    public void OnButtonMultiofferClick(View view) {
        android.util.Log.d("Click button", "OnButtonMultiofferClick");
        
        if(_canShowAds == true)
        	//shows an ad that is already fetched and ready to show instantly
        	//NOTE: if the ad has not been fetched yet this method will not do anything
        	MonetizationManager.showReadyAd(this, NativeXAdPlacement.Main_Menu_Screen, this);
        else
        	android.util.Log.d("ShowAd","Can't show ads but still button clicked");
    }
    
    //
    public void OnButtonMultiofferVideoClick(View view) {
        android.util.Log.d("Click button", "OnButtonMultiofferVideoClick");
        
        if(_canShowAds == true)
        	//shows an ad that is already fetched and ready to show instantly
        	//NOTE: if the ad has not been fetched yet this method will not do anything
        	MonetizationManager.showReadyAd(this, NativeXAdPlacement.Store_Open, this);
        else
        	android.util.Log.d("ShowAd","Can't show ads but still button clicked");
    }
    
    //
    public void OnButtonVideoClick(View view) {
        android.util.Log.d("Click button", "OnButtonVideoClick");
        
        if(_canShowAds == true)
        	//shows an ad that is already fetched and ready to show instantly
        	//NOTE: if the ad has not been fetched yet this method will not do anything
        	MonetizationManager.showReadyAd(this, NativeXAdPlacement.Level_Failed, this);
        else
        	android.util.Log.d("ShowAd","Can't show ads but still button clicked");
    }
    
    //
    public void OnButtonPlayerGeneratedClick(View view) {
        android.util.Log.d("Click button", "OnButtonPlayerGeneratedClick");
        
        if(_canShowAds == true)
        	//shows an ad that is already fetched and ready to show instantly
        	//NOTE: if the ad has not been fetched yet this method will not do anything
        	MonetizationManager.showReadyAd(this, NativeXAdPlacement.Player_Generated_Event, this);
        else
        	android.util.Log.d("ShowAd","Can't show ads but still button clicked");
    }

	@Override
	public void onEvent(AdEvent event, AdInfo adInfo, String message) {
		switch (event) {
		case FETCHED:
			// the ad has been loaded and is now ready to display
			
			//enable the buttons within the sample app
			adIsAvailable(adInfo.getPlacement());
			break;
		case NO_AD:
			//no ad is available to show at this time
			// if app has a free coins button, make sure that button is hidden when no ads 
			// are available for rewards
			//freeCoinsButton.setVisibility(View.GONE);
			break;
		case BEFORE_DISPLAY:
			if (adInfo.willPlayAudio()) {
				// Ad will play music, so make sure game music is paused
				pauseAudio();
			}
			break;
		case DISMISSED:
			//user has closed the ad
			
			//disable the button within the sample app
			adIsNotAvailable(adInfo.getPlacement());

			//fetch a new ad
			MonetizationManager.fetchAd(this, adInfo.getPlacement(), this);

			// if music is paused, resume
			if (musicPlayer.isPlaying() == false) {
				playAudio();
			}

			break;
		case VIDEO_COMPLETED:
			// Video has completed; rewards will be rewarded if applicable
			break;
		case ERROR:
			//there was an error when attempting to fetch or display the ad
			System.out.println("Error: "+message);
			break;
		default:
			break;
		}
		
	}

    // RewardListener implementation
	@Override
	public void onRedeem(RedeemRewardData rewardData) {
		//Take possession of the balances returned here.
        int totalRewardAmount = 0;
        for (Reward reward : rewardData.getRewards()) {
            Log.d("SampleApp", "Reward: rewardName:" + reward.getRewardName()
                    + " rewardId:" + reward.getRewardId()
                    + " amount:" + Double.toString(reward.getAmount()));
            // add the reward amount to the total
            totalRewardAmount += reward.getAmount();
        }
    	rewardData.showAlert(MainActivity.this);
	}

	// Sample App Specific code:
	// This code will disable the button in the sample app corresponding to the placement that is passed in
	public void adIsNotAvailable(String placement){
		Log.d("SampleApp", "Ad is not available for placement: " + placement);
		if(placement.equals(NativeXAdPlacement.Game_Launch.toString())){
			launchButton.setEnabled(false);
		}else if (placement.equals(NativeXAdPlacement.Main_Menu_Screen.toString())){
			menuButton.setEnabled(false);
		}else if (placement.equals(NativeXAdPlacement.Level_Failed.toString())){
			levelButton.setEnabled(false);
		}else if (placement.equals(NativeXAdPlacement.Store_Open.toString())){
			storeButton.setEnabled(false);
		}else if (placement.equals(NativeXAdPlacement.Player_Generated_Event.toString())){
			playerButton.setEnabled(false);
		}
	}
	// Sample App Specific code:
	// This code will enable the button in the sample app corresponding to the placement that is passed in
	public void adIsAvailable(String placement){
		Log.d("SampleApp", "Ad for placement ready: " + placement);
		if(placement.equals(NativeXAdPlacement.Game_Launch.toString())){
			launchButton.setEnabled(true);
		}else if (placement.equals(NativeXAdPlacement.Main_Menu_Screen.toString())){
			menuButton.setEnabled(true);
		}else if (placement.equals(NativeXAdPlacement.Level_Failed.toString())){
			levelButton.setEnabled(true);
		}else if (placement.equals(NativeXAdPlacement.Store_Open.toString())){
			storeButton.setEnabled(true);
		}else if (placement.equals(NativeXAdPlacement.Player_Generated_Event.toString())){
			playerButton.setEnabled(true);
		}
	}
}
