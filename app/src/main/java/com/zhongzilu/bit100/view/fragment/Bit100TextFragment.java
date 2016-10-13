package com.zhongzilu.bit100.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongzilu.bit100.R;

/**
 * Created by zhongzilu on 16-9-16.
 */
public class Bit100TextFragment extends Fragment {

    private View contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null)
            contentView = inflater.inflate(R.layout.fragment_text_layout, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
