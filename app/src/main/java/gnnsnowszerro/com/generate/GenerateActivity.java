package gnnsnowszerro.com.generate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import gnnsnowszerro.com.R;
import gnnsnowszerro.com.StoreCoins;

/**
 * Created by Roma on 15.06.2017.
 */

public class GenerateActivity extends AppCompatActivity {

    private int coins;
    private int discount;
    private boolean type;
    private int coinsBalance;
    private StoreCoins storeCoins;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.generate_activity);

        storeCoins = new StoreCoins(this);
        coinsBalance = storeCoins.getCoinsCount();

        getDataFromIntent();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.add(R.id.generate_container,GenerateFragment.newInstance(coins, discount, type));
        fragmentTransaction.commit();


        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("REDEEM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setIcon(R.drawable.back_row);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));

    }

    private void getDataFromIntent(){
        Intent intent = getIntent();

        coins = intent.getIntExtra("COINS_KEY",0);
        discount = intent.getIntExtra("DISCOUNT_KEY",0);
        type = intent.getBooleanExtra("TYPE_KEY",false);
    }

    public static Intent getIntent(Context context,int coins, int discount, boolean type){
        Intent intent = new Intent(context,GenerateActivity.class);
        intent.putExtra("COINS_KEY",coins);
        intent.putExtra("DISCOUNT_KEY",discount);
        intent.putExtra("TYPE_KEY",type);

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coins, menu);
        MenuItem countCoins = menu.findItem(R.id.countCoins);
        String coinsBalanceStr = coinsBalance + "";
        countCoins.setTitle(coinsBalanceStr);
        return  true;

    }

    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
