package gnnsnowszerro.com.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import gnnsnowszerro.com.R;
import gnnsnowszerro.com.StoreCoins;

/**
 * Created by Roma on 15.06.2017.
 */

public class ViewPagerActivity extends AppCompatActivity {

    private StoreCoins storeCoins;
    public MenuItem coinsBalanceMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.view_pager_acivity);

        getSupportActionBar().setElevation(0);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_background));

        storeCoins = new StoreCoins(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coins, menu);
        coinsBalanceMenuItem = menu.findItem(R.id.countCoins);
        String coinsBalance = String.valueOf(storeCoins.getCoinsCount());
        coinsBalanceMenuItem.setTitle(coinsBalance);
        return true;
    }
}
