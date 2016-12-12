package com.zhongzilu.bit100.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.NetworkUtil;
import com.zhongzilu.bit100.application.util.RequestUtil;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.model.bean.CategoriesBean;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.model.bean.TagBean;
import com.zhongzilu.bit100.model.response.AllPostsByCategoryResponse;
import com.zhongzilu.bit100.view.adapter.MainRecyclerViewAdapter;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;

import java.util.ArrayList;

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
public class Bit100ArticleListActivity extends BaseActivity
        implements RequestUtil.RequestCallback, MyItemClickListener{

    private static final String TAG = "Bit100ArticleDetailActivity==>";

    //UI
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;
    private MainRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    //Extra Tag
    public static final String EXTRA_CATEGORY_BEAN = "category_bean";
    public static final String EXTRA_TAG_BEAN = "tag_bean";

    //Extra Value
    private CategoriesBean mCategoryBean; //目录bean
    private TagBean mTagBean; //标签Bean

    //Other
    private boolean isLoadMore = false;
    private ArrayList<PushModel> mPushList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list_activity);
        setupCenterTitleToolbar();
        setupActionBar();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_common_recyclerView);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh_common_refresh);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCategoryBean != null){
                    if (isHaveNetwork()) {
                        mPushList.clear();
                        RequestUtil.getAllPostsByCategoryId(mCategoryBean, Bit100ArticleListActivity.this);
                    }
                } else if (mTagBean != null){

                }
            }
        });

        initRecyclerView();

        initExtraData();

    }

    private void initExtraData() {
        //获取其他界面传过来的值
        if (getIntent().hasExtra(EXTRA_CATEGORY_BEAN)) {
            //获取传值中的目录分类Bean对象
            mCategoryBean = getIntent().getParcelableExtra(EXTRA_CATEGORY_BEAN);
            if (mCategoryBean != null) {
                setCenterTitleText(mCategoryBean.title);
                getDataByCategory();
            } else {
                LogUtil.e(TAG, "onCreate: get intent extra EXTRA_CATEGORY_BEAN is null");
            }
        }

        else if (getIntent().hasExtra(EXTRA_TAG_BEAN)){

            //获取传值中的标签分类Bean对象
            mTagBean = getIntent().getParcelableExtra(EXTRA_TAG_BEAN);
            if (mTagBean != null){
                setCenterTitleText(mTagBean.title);
                getDataByTag();
            } else {
                LogUtil.e(TAG, "onCreate: get intent extra EXTRA_TAG_BEAN is null");
            }

        }

        //经过以上的判断，现在的mCategoryBean和mTagBean肯定都为空，所以抛出异常
        else if (mCategoryBean == null && mTagBean == null){
            throw new NullPointerException(TAG +
                    " onCreate: get intent EXTRA_CATEGORY_BEAN & EXTRA_TAG_BEAN is null");
        }
    }

    private void initRecyclerView() {
        //设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //实例化对象
        mAdapter = new MainRecyclerViewAdapter(this, mPushList);

        //设置监听器
        mAdapter.setOnItemClickListener(this);

        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && isLoadMore) {

                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    Snackbar.make(recyclerView, R.string.toast_no_more, Snackbar.LENGTH_SHORT).show();
                    isLoadMore = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItem = mAdapter.getItemCount();
                if (lastItem == totalItem - 1 && dy > 0) {
                    isLoadMore = true;
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private boolean isHaveNetwork() {

        boolean haveNetwork = NetworkUtil.getNetworkState();
        if (!haveNetwork){
            Toast.makeText(this, getString(R.string.error_network_failed), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void getDataByTag() {
        if (isHaveNetwork()){

        }

    }

    private void getDataByCategory() {
        if (isHaveNetwork()) {
            RequestUtil.getAllPostsByCategoryId(mCategoryBean, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.cancelAllRequest();
    }

    @Override
    public OnResponseListener callback() {
        return new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                handleAllPostsByCategoryResponse(response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(Bit100ArticleListActivity.this,
                        TextUtils.isEmpty(response.get())
                                ? getString(R.string.error_network_failed)
                                : response.get()
                        , Toast.LENGTH_SHORT).show();
                endLoading();
            }

            @Override
            public void onFinish(int what) {

            }
        };
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        int type = mAdapter.getItemViewType(position);
        final ArticleDetailBean bean;
        switch (type) {
            case MainRecyclerViewAdapter.TYPE_MAIN_ARTICLE_ITEM:
                switch (view.getId()) {
                    //点击分享按钮
                    case R.id.img_article_share:
                        bean = (ArticleDetailBean) mPushList.get(position).getPushObject();
                        String shareText = bean.title +
                                "【来自" + getString(R.string.app_name) + "App】\n" + bean.url;
                        shareAction(shareText);
                        break;
                    //点击点赞按钮
//                    case R.id.img_article_up:
//                        Snackbar.make(view, "点赞", Snackbar.LENGTH_SHORT).show();
//                        break;
                    //默认跳转文章详情页
                    default:
                        bean = (ArticleDetailBean) mPushList.get(position).getPushObject();
                        Intent intent = new Intent(this, Bit100ArticleDetailActivity.class);
                        intent.putExtra(Bit100ArticleDetailActivity.EXTRA_ARTICLE_BEAN, bean);
                        startActivity(intent);
                        break;
                }
                break;
        }
    }

    /**
     * 处理根据目录获取文章列表的网络请求返回数据
     * @param json 请求响应返回的JSON数据
     */
    private void handleAllPostsByCategoryResponse(final String json){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AllPostsByCategoryResponse allPosts = new Gson().fromJson(json,
                            new TypeToken<AllPostsByCategoryResponse>(){}.getType());

                    if ("ok".equals(allPosts.status)){

                        for (ArticleDetailBean bean : allPosts.posts){
                            mPushList.add(new PushModel(MainRecyclerViewAdapter.TYPE_MAIN_ARTICLE_ITEM, bean));
                        }

//                        if (allPosts.posts.length < 4)
                            mAdapter.setMoreVisible(false);

                    } else {
                        if (!TextUtils.isEmpty(allPosts.error))
                            Toast.makeText(Bit100ArticleListActivity.this, allPosts.error, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            endLoading();
                        }
                    });
                }
            }
        }).start();
    }

    private void handleAllPostsByTagResponse(String json) {

    }

    private void shareAction(String text){
        Toast.makeText(this, R.string.toast_invoking_share, Toast.LENGTH_SHORT).show();
        Intent localIntent = new Intent(Intent.ACTION_SEND);
        localIntent.setType("text/plain");
        localIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(localIntent, getString(R.string.title_chooser_share)));
    }

    private void endLoading(){
        if (mRefresh.isRefreshing())
            mRefresh.setRefreshing(false);
    }
}
