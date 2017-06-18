package gnnsnowszerro.com.earncoins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import gnnsnowszerro.com.R;

/**
 * Created by Roma on 15.06.2017.
 */

public class EarnCoinsListAdapter extends BaseAdapter {

    private final String[] names = {"Quick Offers","Pro Tasks","Daily Offers","Watch Video","Rate Us"};
    private final int[] resources = {R.drawable.ic_quick_offers,R.drawable.ic_pro_tasks,R.drawable.ic_daily_offers,R.drawable.ic_watch_video,R.drawable.ic_rate_us};

    private Context context;

    @BindView(R.id.earncoint_list_item_button_imageview)
    ImageView mImageView;
    @BindView(R.id.earncoint_list_item_button_textview)
    TextView mTextView;

    public EarnCoinsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
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
        View view = LayoutInflater.from(context).inflate(R.layout.earncoins_list_item,null);
        ButterKnife.bind(this,view);

        mTextView.setText(names[position]);
        mImageView.setBackgroundResource(resources[position]);

        return view;
    }


}
