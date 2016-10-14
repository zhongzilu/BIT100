package com.zhongzilu.bit100.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhongzilu.bit100.view.fragment.Bit100ArticleDetailFragment;
import com.zhongzilu.bit100.view.fragment.Bit100MainFragment;
import com.zhongzilu.bit100.view.fragment.Bit100CategoryFragment;
import com.zhongzilu.bit100.view.fragment.Bit100TextFragment;

import java.util.HashMap;

/**
 * ViewPager的适配器，用于OrganizationFragment滑动切换不同的分类Fragment
 *
 * Created by zhongzilu on 2016-09-16.
 */
public class Bit100PagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = {"主页", "最新", "分类"};
    private HashMap<Integer, Fragment> mFragmentMap = new HashMap<>();

    public Bit100PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Bit100MainFragment();
            case 1:
                return new Bit100ArticleDetailFragment();
            case 2:
                return new Bit100CategoryFragment();
        }
        return new Bit100TextFragment();
    }

}
