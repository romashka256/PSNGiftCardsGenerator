package gnnsnowszerro.com.generate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import gnnsnowszerro.com.R;
import gnnsnowszerro.com.StoreCoins;

public class GenerateFragment extends Fragment implements IGenerateFragemntView,View.OnClickListener{

    private GeneratePresenter generatePresenter;
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
    @BindView(R.id.generate_banner)
    LinearLayout mBanner;
    @BindView(R.id.generate_fragment_discount_textview)
    TextView mDisocountCount;
    @BindView(R.id.generate_fragment_coins_textview)
    TextView mCoinsCount;
    @BindView(R.id.generate_fragment_generate_button)
    Button mGenerateButton;
    @BindView(R.id.generate_fragment_redeem_button)
    Button mRedeemButton;
    @BindView(R.id.generate_fragment_edittext)
    EditText mEmailEditText;

    private int  coins;
    private int discount;
    private boolean isProc;
    private StoreCoins storeCoins;

    public static GenerateFragment newInstance(int coins,int discount,boolean isProcent) {
        GenerateFragment fragment = new GenerateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("COINS_KEY",coins);
        bundle.putInt("DISCOUNT_KEY",discount);
        bundle.putBoolean("TYPE_KEY",isProcent);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        coins = bundle.getInt("COINS_KEY");
        discount = bundle.getInt("DISCOUNT_KEY");
        isProc = bundle.getBoolean("TYPE_KEY");

        mCoinsCount.setText(coins + "");
        if(isProc) {
            mDisocountCount.setText(discount + "%");
        }else{
            mDisocountCount.setText(discount + "$");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.generate_fragment,container,false);

        ButterKnife.bind(this,view);

        mBanner.setVerticalGravity(LinearLayout.INVISIBLE);
        mGenerateButton.setOnClickListener(this);
        mRedeemButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        generatePresenter = new GeneratePresenter(this,getActivity());
        generatePresenter.initAppNext();
        generatePresenter.loadAppNextBanner();
        storeCoins = new StoreCoins(getActivity());

    }

    @Override
    public void onGenerateClicked(int type) {
        switch (type){
            case 0 :
                buildAlertDialog("Sorry","You don’t have enough");
                break;
            case 1 :
                buildAlertDialog("No email address", "Email address can’t be blank");
                break;
            case 2 :
                buildAlertDialog("Excellent","You will get your code");
                break;
        }
    }

    @Override
    public void onRedeemClicked(int type) {
        switch (type){
            case 0 :
                buildAlertDialog("No email adress","Email address can’t be blank");
                break;
            case 1 :
                buildAlertDialog("Invalid Email","Your Email address in invalid. Please provide a valid address");
                break;
            case 2 :
                buildAlertDialog("Error","Generate code at first, please");
             break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.generate_fragment_generate_button :
                generatePresenter.onGenerateClicked(storeCoins.getCoinsCount(),Integer.parseInt(mCoinsCount.getText().toString()), String.valueOf(mEmailEditText.getText()));
                break;
            case R.id.generate_fragment_redeem_button :
                generatePresenter.onRedeemClicked(String.valueOf(mEmailEditText.getText()));
                break;
            case R.id.install :
                onInstallClicked();
                break;
            case R.id.privacy :
                onPrivacyClicked();
                break;
        }
    }

    @Override
    public void onInstallClicked() {
        generatePresenter.onInstallClicked();
    }

    @Override
    public void onPrivacyClicked() {
        generatePresenter.onPrivacyClicked();
    }

    @Override
    public void onBannerLoaded(String name, String rating, String buttonText, String imgUrl) {
        mBanner.setVerticalGravity(LinearLayout.VISIBLE);
        mAppNameAppBanner.setText(name);
        mRatindAppBanner.setText(rating);
        mInstallButtonBanner.setText(buttonText);
        Picasso.with(getContext()).load(imgUrl).into(mImageViewBanner);
    }

    private void buildAlertDialog(String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(description)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
