package com.zhongzilu.bit100.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.SharePreferenceUtil;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.view.base.BaseToolbarActivity;
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
public class Bit100ArticleDetailActivity extends BaseToolbarActivity {

    private static final String TAG = "Bit100ArticleDetailActivity==>";

    //UI
    private Bit100ArticleDetailFragment mFragment;
    private FragmentTransaction transaction;
    private ImageView mHeaderBgImage;

    private ArticleDetailBean mBean; //文章bean
    public static final String EXTRA_ARTICLE_BEAN = "article_bean";
    private Toolbar toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_article_detail_layout;
    }

    @Override
    protected void initStatusBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_article_detail);
        toolbar.setTitle("");
        initToolbar(toolbar);
    }

    @Override
    public void onCreateAfter(Bundle savedInstanceState) {
        mHeaderBgImage = (ImageView) findViewById(R.id.img_article_detail);

        //获取其他界面传过来的值
        if (getIntent().hasExtra(EXTRA_ARTICLE_BEAN)) {
            mBean = getIntent().getParcelableExtra(EXTRA_ARTICLE_BEAN);
            if (mBean != null) {
                toolbar.setTitle(mBean.title);
            } else {
                LogUtil.e(TAG, "onCreate: article intent extra is null");
            }
        }

        if (mFragment == null){
            mFragment = Bit100ArticleDetailFragment.newInstance(mBean);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.layout_article_detail_parent, mFragment);
            transaction.commit();
        } else {
            transaction.show(mFragment);
        }

        String path = SharePreferenceUtil.getImagePath();
        if (path != null){
            mHeaderBgImage.setImageURI(Uri.fromFile(new File(path)));
        }

    }

    @Override
    public void initData() {

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
            case R.id.action_share:
                if (mFragment != null)
                    mFragment.onOptionsItemSelected(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
