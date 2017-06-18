package gnnsnowszerro.com.choosecoupon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gnnsnowszerro.com.R;

/**
 * Created by Roma on 15.06.2017.
 */

public class CouponsListAdapter extends BaseAdapter {

    private Context context;
    private List<ModelCoupons> couponses;

    @BindView(R.id.discount_item_textview)
    TextView mDiscountTextView;
    @BindView(R.id.discount_item_imageview)
    ImageView mDiscountImageView;
    @BindView(R.id.discount_item_textview_coins)
    TextView mDiscountCoinsTextView;

    public CouponsListAdapter(Context context, List<ModelCoupons> coupones) {
        this.context = context;
        this.couponses = coupones;
    }

    @Override
    public int getCount() {
        return couponses.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_coupon_item,null);
        ButterKnife.bind(this,view);

        mDiscountCoinsTextView.setText(couponses.get(position).getCoins() + " Coins");
        if(couponses.get(position).isType()){
            mDiscountTextView.setText(couponses.get(position).getDiscount()+"%");
            mDiscountTextView.setTextSize(18);

        }else{
            mDiscountTextView.setText(couponses.get(position).getDiscount()+"$\nPS Gift Card");
            mDiscountTextView.setTextSize(15);
        }

        if(couponses.get(position).isType()){
            mDiscountImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.discount));
        }else{
            mDiscountImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.gift_card));
        }

        return view;
    }


}
