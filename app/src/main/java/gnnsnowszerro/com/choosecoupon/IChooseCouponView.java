package gnnsnowszerro.com.choosecoupon;

import java.util.List;

/**
 * Created by Roma on 16.06.2017.
 */

public interface IChooseCouponView {
    void onCouponClicked(int pos);
    void onCouponsLoaded(List<ModelCoupons> list);
    void onCouponLoadedById(ModelCoupons coupon);
}
