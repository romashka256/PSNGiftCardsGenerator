package gnnsnowszerro.com;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class StoreCoins {

    private static final String TAG = "###" + "CoinsManager";
    private SharedPreferences.Editor editor;
    public SharedPreferences pref;

    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "CoinsCount";
    public static final String COINS_COUNT = "TotalCoinsCount";
    private static final String OFFER_TORE_COINS_COUNT = "OfferToroCoinsCount";
    private static final String ADXMI_COINS_COUNT = "ADXMICoinsCount";

    public StoreCoins(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void updateCoinsCount(int earnedCoins) {
        int totalCoins = getCoinsCount() + earnedCoins;
        Log.i(TAG, "Updated coins count +" + earnedCoins);
        editor.putInt(COINS_COUNT, totalCoins);
        editor.commit();
    }

    public void updateOfferToroCoinsCount(int offerToroCount){
        int OfferToroCoinsCount = getOfferToroCount();
        if (offerToroCount > OfferToroCoinsCount){
            int earnCoins = offerToroCount - OfferToroCoinsCount;
            editor.putInt( OFFER_TORE_COINS_COUNT, earnCoins);
            updateCoinsCount(earnCoins);
            editor.commit();
        }
    }

    public void updateAdxmiCoinsCount(int AdxmiCount){
        int AdxmiCoinsCount = getAdxmiCount();
        if (AdxmiCount > AdxmiCoinsCount){
            int earnCoins = AdxmiCount - AdxmiCoinsCount;
            editor.putInt(ADXMI_COINS_COUNT, earnCoins);
            updateCoinsCount(earnCoins);
            editor.commit();
        }
    }

    public int getCoinsCount() {
        return pref.getInt(COINS_COUNT, 0);
    }

    private int getOfferToroCount(){
        return pref.getInt( OFFER_TORE_COINS_COUNT, 0);
    }

    private int getAdxmiCount(){
        return pref.getInt(ADXMI_COINS_COUNT, 0);
    }
}
