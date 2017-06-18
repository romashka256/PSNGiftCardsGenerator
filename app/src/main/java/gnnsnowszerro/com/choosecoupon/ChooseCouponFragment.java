package gnnsnowszerro.com.choosecoupon;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gnnsnowszerro.com.R;
import gnnsnowszerro.com.generate.GenerateActivity;

public class ChooseCouponFragment extends Fragment implements IChooseCouponView{


    @BindView(R.id.list_view_choose_coupon_fragment)
    ListView mListOfCoupons;

    ChooseCouponPresenter presenter;

 public static ChooseCouponFragment newInstance() {
        ChooseCouponFragment fragment = new ChooseCouponFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ChooseCouponPresenter(this,getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choose_coupon_fragment, container, false);

        ButterKnife.bind(this,v);

        mListOfCoupons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCouponClicked(position);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.loadCoupones();
    }

    @Override
    public void onCouponClicked(int pos) {
       presenter.onItemClick(pos);
    }

    @Override
    public void onCouponsLoaded(List<ModelCoupons> list) {
        mListOfCoupons.setAdapter(new CouponsListAdapter(getActivity(),list));
    }

    @Override
    public void onCouponLoadedById(ModelCoupons coupon) {
        Intent intent = GenerateActivity.getIntent(getActivity(),coupon.getCoins(),coupon.getDiscount(),coupon.isType());
        startActivity(intent);
    }
}
