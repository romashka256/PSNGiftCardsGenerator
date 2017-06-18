package gnnsnowszerro.com.choosecoupon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roma on 16.06.2017.
 */

public class RepoCoupons {

    private IOnDataLoadedListener listener;
    private int[] pricelist = {28000,11500,6000,75000,38000,19500,9900,4000};
    private int[] proc = {50,20,10,200,100,50,25,10};

    private List<ModelCoupons> modelCouponses = new ArrayList<>();

    public RepoCoupons(IOnDataLoadedListener listener) {
        this.listener = listener;
    }

    public void getCoupones(){
        modelCouponses.clear();
        for(int i = 0; i< pricelist.length;i++){
            ModelCoupons modelCoupon = new ModelCoupons();

            modelCoupon.setCoins(pricelist[i]);
            modelCoupon.setDiscount(proc[i]);
            if(i<3){
                modelCoupon.setType(true);
            }else{
                modelCoupon.setType(false);
            }

            modelCouponses.add(modelCoupon);
        }
         listener.onDataLoaded(modelCouponses);
    }
    public void getCouponById(int pos){
        listener.onDataLoadedById(modelCouponses.get(pos));
    }
}

