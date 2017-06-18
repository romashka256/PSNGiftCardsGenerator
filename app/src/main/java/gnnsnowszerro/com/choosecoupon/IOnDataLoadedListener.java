package gnnsnowszerro.com.choosecoupon;

import java.util.List;

/**
 * Created by Roma on 16.06.2017.
 */

public interface IOnDataLoadedListener {
    void onDataLoaded(List<ModelCoupons> list);
    void onDataLoadedById(ModelCoupons coupon);
}
