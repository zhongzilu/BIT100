package com.zhongzilu.bit100.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.view.fragment.Bit100ArticleDetailFragment;

import java.io.File;

/**
 * 显示文章详情的Activity，通过Intent传值，可以把将要显示的文章唯一标示传给
 * Bit100ArticleDetailFragment，由ArticleDetailFragment去获取显示的内容
 * <p/>
 * Intent传值参数名为
 * {@param loadUrl
 * @value String} 或者{@param Bit100ArticleDetailActivity.EXTRA_LOADURL}
 * <p/>
 * Created by zhongzilu on 2016-07-22.
 */
public class Bit100ArticleDetailActivity extends AppCompatActivity {

    private static final String TAG = "Bit100ArticleDetailActivity==>";

    //UI
    private Bit100ArticleDetailFragment mFragment;
    private FragmentTransaction transaction;
    private ImageView mHeaderBgImage;

    private ArticleDetailBean mBean; //文章bean
    public static final String EXTRA_ARTICLE_BEAN = "article_bean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article_detail);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        setupActionBar();

        mHeaderBgImage = (ImageView) findViewById(R.id.img_article_detail);

        //获取其他界面传过来的值
        if (getIntent() != null) {
            mBean = getIntent().getParcelableExtra(EXTRA_ARTICLE_BEAN);
            if (mBean != null) {
                toolbar.setTitle(mBean.title);
            } else {
                LogUtil.d(TAG, "onCreate: article intent extra is null");
            }
        }

        String path = SharePreferenceUtil.getImagePath();
        if (path != null){
            mHeaderBgImage.setImageURI(Uri.fromFile(new File(path)));
        }

        if (mFragment == null){
            mFragment = Bit100ArticleDetailFragment.newInstance(mBean);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.layout_article_detail_parent, mFragment);
            transaction.commit();
        } else {
            transaction.show(mFragment);
        }

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFragment != null)
            mFragment.onHiddenChanged(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_share:
                if (mFragment != null)
                mFragment.onOptionsItemSelected(item);
                break;
        }
        return true;
    }
}
