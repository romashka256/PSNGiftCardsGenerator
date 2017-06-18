package gnnsnowszerro.com.instructions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gnnsnowszerro.com.R;

/**
 * Created by Roma on 17.06.2017.
 */

public class InstructionsFragment extends Fragment{

    public static InstructionsFragment newInstance() {
        InstructionsFragment fragment = new InstructionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.instructions_fragment, container, false);
    }
}
