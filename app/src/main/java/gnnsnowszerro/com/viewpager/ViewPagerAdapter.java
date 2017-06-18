package gnnsnowszerro.com.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gnnsnowszerro.com.choosecoupon.ChooseCouponFragment;
import gnnsnowszerro.com.earncoins.EarnCoinsFragment;
import gnnsnowszerro.com.instructions.InstructionsFragment;

/**
 * Created by Roma on 15.06.2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ChooseCouponFragment.newInstance();
                break;
            case 1:
                fragment = EarnCoinsFragment.newInstance();
                break;
            case 2:
                fragment = InstructionsFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = null;
        switch (position) {
            case 0:
                name = "CHOOOSE COUPON";
                break;
            case 1:
                name = "EARN COINS";
                break;
            case 2:
                name = "INSTRUCTIONS";
                break;
        }
        return name;
    }
}
