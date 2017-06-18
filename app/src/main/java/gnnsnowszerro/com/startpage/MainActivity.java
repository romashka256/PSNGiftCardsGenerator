package gnnsnowszerro.com.startpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import net.adxmi.android.AdManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import gnnsnowszerro.com.CheckRecentRun;
import gnnsnowszerro.com.R;
import gnnsnowszerro.com.viewpager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity implements IStartPage {

    public final static String PREFS = "PrefsFile";
    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;
    private final static String TAG = "MainActivity";
    private final String AdxmiAPP_ID = "01bc87913a968e80";
    private final String AdxmiAPP_SECRET = "19edf67159c7e389";


    @BindView(R.id.get_started_button)
    Button mGetStartedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        AdManager.getInstance(this).init(AdxmiAPP_ID,AdxmiAPP_SECRET);
        AdManager.getInstance(this).setEnableDebugLog(false);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked();
            }
        };

        mGetStartedButton.setOnClickListener(onClickListener);

        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

        if (!settings.contains("lastRun"))
            enableNotification(null);
        else
            recordRunTime();

        Log.v(TAG, "Starting CheckRecentRun service...");
        startService(new Intent(this,  CheckRecentRun.class));
    }

    @Override
    public void onButtonClicked() {

        Intent intent = new Intent(this, ViewPagerActivity.class);
        startActivity(intent);

    }

    public void recordRunTime() {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.commit();
    }

    public void enableNotification(View v) {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.putBoolean("enabled", true);
        editor.commit();
    }

}
