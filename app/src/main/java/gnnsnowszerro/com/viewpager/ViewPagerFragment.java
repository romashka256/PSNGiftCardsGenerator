package gnnsnowszerro.com.viewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import gnnsnowszerro.com.R;

public class ViewPagerFragment extends Fragment implements IViewPager, View.OnClickListener {
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.title)
    TextView mAppNameAppBanner;
    @BindView(R.id.rating)
    TextView mRatindAppBanner;
    @BindView(R.id.install)
    Button mInstallButtonBanner;
    @BindView(R.id.icon)
    ImageView mImageViewBanner;
    @BindView(R.id.privacy)
    ImageView mImageViewPrivacyBanner;
    @BindView(R.id.view_pager_banner)
    LinearLayout mBanner;

    private ViewPagerPresenter presenter;

    public static ViewPagerFragment newInstance() {
        ViewPagerFragment fragment = new ViewPagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ViewPagerPresenter(this, getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadAppNextBanner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_pager_fragment, container, false);
        ButterKnife.bind(this, v);

        mBanner.setVisibility(LinearLayout.INVISIBLE);

        ViewPagerAdapter customAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        mViewPager.setAdapter(customAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mInstallButtonBanner.setOnClickListener(this);
        mImageViewPrivacyBanner.setOnClickListener(this);


        return v;
    }


    @Override
    public void onInstallClicked() {
        presenter.onInstallClicked();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.install :
                onInstallClicked();
                break;
            case R.id.privacy :
                onPrivacyClicked();
                break;
        }
    }

    @Override
    public void onBannerLoaded(String name, String rating, String buttonText, String imgUrl) {
        mBanner.setVisibility(LinearLayout.VISIBLE);
        mAppNameAppBanner.setText(name);
        mRatindAppBanner.setText(rating);
        mInstallButtonBanner.setText(buttonText);
        Picasso.with(getContext()).load(imgUrl).into(mImageViewBanner);
    }

    @Override
    public void onPrivacyClicked() {
        presenter.onPrivacyClicked();
    }
}
