package gnnsnowszerro.com.earncoins;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gnnsnowszerro.com.R;


/**
 * Created by Roma on 16.06.2017.
 */

public class EarnCoinsFragment extends Fragment implements IEarnCoinsFragment, AdapterView.OnItemClickListener {


    private EarnCoinsPresenter presenter;

    @BindView(R.id.earncoins_fragment_list)
    ListView mListView;

    public static EarnCoinsFragment newInstance() {
        EarnCoinsFragment fragment = new EarnCoinsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new EarnCoinsPresenter(getActivity());
        presenter.initOfferToro();
        presenter.initNativeX();
        presenter.initAdxmi();
        presenter.initAdColony();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.earncoins_fragment, container, false);

        ButterKnife.bind(this, view);

        mListView.setAdapter(new EarnCoinsListAdapter(getContext()));
        mListView.setOnItemClickListener(this);

        /** Set reward listener for your app to be alerted of reward events */

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadNativeX();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                onQuickOffersClicked();
                break;
            case 1:
                onProTasksClicked();
                break;
            case 2:
                onDailyOffersClicked();
                break;
            case 3:
                onWatchVideoClicked();
                break;
            case 4:
                onRateUsClicked();
                break;
        }
    }

    @Override
    public void onQuickOffersClicked() {
        presenter.showOfferToro();
    }

    @Override
    public void onProTasksClicked() {
        presenter.showNativeX();
    }

    @Override
    public void onDailyOffersClicked() {
        presenter.showAdxmi();
    }

    @Override
    public void onRateUsClicked() {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.gnnsnowszerro"));
        startActivity(browserIntent);
    }

    @Override
    public void onWatchVideoClicked() {
        presenter.showAdColony();
    }
}

