package gnnsnowszerro.com.choosecoupon;

import android.content.Context;

import java.util.List;

/**
 * Created by Roma on 16.06.2017.
 */

public class ChooseCouponPresenter implements IChooseCouponPresenter,IOnDataLoadedListener{

    private IChooseCouponView view;
    private RepoCoupons repo;
    private Context context;


    public ChooseCouponPresenter(IChooseCouponView view,Context context) {
        this.view = view;
        repo = new RepoCoupons(this);
        this.context = context;
    }



    @Override
    public void onItemClick(int pos) {
        repo.getCouponById(pos);
    }

    @Override
    public void loadCoupones() {
        repo.getCoupones();
    }

    @Override
    public void onDataLoaded(List<ModelCoupons> list) {
        view.onCouponsLoaded(list);
    }

    @Override
    public void onDataLoadedById(ModelCoupons coupon) {
        view.onCouponLoadedById(coupon);
    }
}
