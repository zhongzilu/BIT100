package com.zhongzilu.bit100.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.BitmapUtil;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.StatusBarUtils;
import com.zhongzilu.bit100.widget.TouchImageView;

/**
 * 图片画廊，用于查看网页上的图片
 * Created by zhongzilu on 2016-09-16.
 */
public class GalleryActivity extends BaseActivity {
    private static final String TAG = "GalleryActivity==>";

    //Extra Tag
    public static final String EXTRA_IMAGES_LIST = "images_list";
    public static final String EXTRA_CURRENT_IMAGE_POSITION = "image_position";

    //Extra Value
    private String[] mList;
    private int mChosePosition;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Handler mHideHandler;
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
//            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mViewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setToolbar();

        mViewPager = (ViewPager) findViewById(R.id.container);

        Intent intent = getIntent();
        if (intent != null){
            mList = intent.getStringArrayExtra(EXTRA_IMAGES_LIST);
            mChosePosition = intent.getIntExtra(EXTRA_CURRENT_IMAGE_POSITION, 0);
        }

        // 如果mList为空，则不再进行其他操作
        if (mList == null) {
            LogUtil.d(TAG, "onCreate: mList is null");
            return;
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mList);
        mViewPager.setOffscreenPageLimit(2);
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mChosePosition, true);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.gallery_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        StatusBarUtils.from(this)
                //沉浸状态栏
                .setTransparentStatusbar(true)
                //白底黑字状态栏
                .setLightStatusBar(false)
                //设置toolbar,actionbar等view
                .setActionbarView(toolbar)
                .process();
        setupActionBar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.fade_anim_in, R.anim.fade_anim_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                finish();
                overridePendingTransition(R.anim.fade_anim_in, R.anim.fade_anim_out);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    /**==========================================================================================
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String TAG = "PlaceholderFragment==>";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_IMAGE_URL = "image_url";
        private View contentView;
        private TouchImageView imageView;

        public PlaceholderFragment() {}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(String url) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_IMAGE_URL, url);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (contentView == null)
                contentView = inflater.inflate(R.layout.fragment_gallery, container, false);
            return contentView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            setHasOptionsMenu(true);

            imageView = (TouchImageView) view.findViewById(R.id.img_touch_image);
            String url = getArguments().getString(ARG_IMAGE_URL);
            LogUtil.d(TAG, "onViewCreated: url==>" + url);
            Glide.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .into(imageView);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_gallery, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_save_image:
                    imageView.setDrawingCacheEnabled(true);
                    Bitmap bitmap = imageView.getDrawingCache();
                    if (bitmap == null){
                        LogUtil.d(TAG, "onOptionsItemSelected: get bitmap is null");
                        break;
                    }
                    String path = BitmapUtil.saveBitmap(bitmap);
                    imageView.setDrawingCacheEnabled(false);
                    //通知图库刷新保存的图片
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(path)));
                    Toast.makeText(getActivity(), getString(R.string.toast_image_saved) + path, Toast.LENGTH_LONG).show();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**======================================================================================
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String[] imgSrc;

        public SectionsPagerAdapter(FragmentManager fm, String[] imgS) {
            super(fm);
            this.imgSrc = imgS;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(imgSrc[position]);
        }

        @Override
        public int getCount() {
            // Show imgSrc length total pages.
            return imgSrc.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}
