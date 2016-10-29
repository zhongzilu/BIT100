package com.zhongzilu.bit100.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.model.bean.CategoriesBean;
import com.zhongzilu.bit100.model.bean.TagBean;
import com.zhongzilu.bit100.view.fragment.Bit100MainFragment;

/**
 * 显示文章列表的Activity，通过Intent传值，可以把查询条件的实体类传给
 * Bit100MainFragment，由Bit100MainFragment去获取显示的内容
 * <p/>
 * Intent传值参数名为
 * {@param category_bean
 * @value CategoriesBean.class} 或者{@param Bit100ArticleListActivity.EXTRA_CATEGORY_BEAN}
 *
 * {@param tag_bean
 * @value TagBean.class} 或者{@param Bit100ArticleListActivity.EXTRA_TAG_BEAN}
 * <p/>
 * Created by zhongzilu on 2016-10-27.
 */
public class Bit100ArticleListActivity extends BaseActivity {

    private static final String TAG = "Bit100ArticleDetailActivity==>";

    //UI
    private Bit100MainFragment mFragment;
    private FragmentTransaction transaction;

    private CategoriesBean mCategoryBean; //目录bean
    private TagBean mTagBean; //标签Bean
    public static final String EXTRA_CATEGORY_BEAN = "category_bean";
    public static final String EXTRA_TAG_BEAN = "tag_bean";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list_activity);
        setupCenterTitleToolbar();
        setupActionBar();

        //获取其他界面传过来的值
        if (getIntent() != null) {
            //获取传值中的目录分类Bean对象
            mCategoryBean = getIntent().getParcelableExtra(EXTRA_CATEGORY_BEAN);
            if (mCategoryBean != null) {
                setCenterTitleText(mCategoryBean.title);
                newFragmentByCategory();
                return;
            } else {
                LogUtil.e(TAG, "onCreate: get intent extra EXTRA_CATEGORY_BEAN is null");
            }

            //获取传值中的标签分类Bean对象
            mTagBean = getIntent().getParcelableExtra(EXTRA_TAG_BEAN);
            if (mTagBean != null){
                setCenterTitleText(mTagBean.title);
                newFragmentByTag();
                return;
            } else {
                LogUtil.e(TAG, "onCreate: get intent extra EXTRA_TAG_BEAN is null");
            }

            //经过以上的判断，现在的mCategoryBean和mTagBean肯定都为空，所以抛出异常
            if (mCategoryBean == null && mTagBean == null){
                throw new NullPointerException(TAG +
                        " onCreate: get intent EXTRA_CATEGORY_BEAN & EXTRA_TAG_BEAN is null");
            }

        }

    }

    /**
     * 创建一个新的，用于根据目录请求文章列表的Fragment
     * Create by zhongzilu 2016-10-28
     */
    private void newFragmentByCategory(){
        if (mFragment == null){
            mFragment = Bit100MainFragment.newInstance(mCategoryBean);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.layout_article_fragment_wrap, mFragment);
            transaction.commit();
        } else {
            transaction.show(mFragment);
        }
    }

    /**
     * 创建一个新的，用于根据标签请求文章列表的Fragment
     * Create by zhongzilu 2016-10-28
     */
    private void newFragmentByTag(){
        if (mFragment == null){
            mFragment = Bit100MainFragment.newInstance(mTagBean);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.layout_article_fragment_wrap, mFragment);
            transaction.commit();
        } else {
            transaction.show(mFragment);
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

            default:
                if (mFragment != null)
                mFragment.onOptionsItemSelected(item);
                break;
        }
        return true;
    }
}
