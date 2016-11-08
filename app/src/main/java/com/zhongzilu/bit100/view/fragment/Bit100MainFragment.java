package com.zhongzilu.bit100.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;
import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.helper.CacheHelper;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.RequestMood;
import com.zhongzilu.bit100.application.util.RequestMoodHandler;
import com.zhongzilu.bit100.application.util.RequestUtil;
import com.zhongzilu.bit100.model.bean.ArticleDetailBean;
import com.zhongzilu.bit100.model.bean.CardMoodModel;
import com.zhongzilu.bit100.model.bean.CategoriesBean;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.model.bean.TagBean;
import com.zhongzilu.bit100.model.response.AllPostsByCategoryResponse;
import com.zhongzilu.bit100.model.response.AllPostsResponse;
import com.zhongzilu.bit100.view.activity.Bit100ArticleDetailActivity;
import com.zhongzilu.bit100.view.activity.Bit100MainActivity;
import com.zhongzilu.bit100.view.activity.MoodCardActivity;
import com.zhongzilu.bit100.view.activity.SettingsActivity;
import com.zhongzilu.bit100.view.adapter.MainRecyclerViewAdapter;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 【主页】版块的子Fragment
 * Created by zhongzilu on 2016-09-16.
 */
public class Bit100MainFragment extends Fragment
        implements MyItemClickListener, MyItemLongClickListener, RequestUtil.RequestCallback {

    private static final String TAG = "Bit100MainFragment==>";

    //UI
    private View contentView;
    private RecyclerView mRecyclerView;
    private MainRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;

    //Value
    private static CategoriesBean mCategories;
    private static TagBean mTagBean;

    //other
    private ArrayList<PushModel> mPushList = new ArrayList<>();
    private boolean isLoadMore = false;
    private boolean isFirst = true;
    private LinearLayoutManager mLayoutManager;

    public static Bit100MainFragment newInstance(CategoriesBean bean) {

        Bundle args = new Bundle();
        args.putParcelable("categories", bean);
        mCategories = bean;
        Bit100MainFragment fragment = new Bit100MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Bit100MainFragment newInstance(TagBean bean) {

        Bundle args = new Bundle();
        args.putParcelable("tags", bean);
        mTagBean = bean;
        Bit100MainFragment fragment = new Bit100MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null)
            contentView = inflater.inflate(R.layout.fragment_main_layout, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //设置Fragment可以重载Activity的菜单栏
        setHasOptionsMenu(true);

        if (mRecyclerView == null)
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_common_recyclerView);
        if (mRefresh == null)
            mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_common_refresh);

        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPushList.clear();
                mPushList.add(new PushModel(MainRecyclerViewAdapter.TYPE_NULL, new Object()));
                RequestUtil.getAllPosts(Bit100MainFragment.this);
                requestMoodPosts();
            }
        });

        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(){
        //设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //实例化对象
        mAdapter = new MainRecyclerViewAdapter(getActivity(), mPushList);

        //设置监听器
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);

        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && isLoadMore) {

//                    if (mPushList.size() > 11) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        Snackbar.make(recyclerView, R.string.toast_no_more, Snackbar.LENGTH_SHORT).show();
//                        isLoadMore = false;
//                        return;
//                    }
//
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

        //添加一个空布局，用来方便之后动态添加数据到position=1的位置
        mPushList.add(new PushModel(MainRecyclerViewAdapter.TYPE_NULL, new Object()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            loadLocalAllPostsCache();
            RequestUtil.getAllPosts(this);
            requestMoodPosts();
            isFirst = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.d(TAG, "onHiddenChanged: " + hidden);
        if (hidden && isFirst){
            switchGetPostsRequest();
            isFirst = false;
        }
    }

    private void switchGetPostsRequest() {
        if (mCategories == null && mTagBean == null){
            LogUtil.e(TAG, "onHiddenChanged: mCategoriesBean & mTagBean is both null");
            throw new NullPointerException("mCategoriesBean & mTagBean is both null");
        }

        if (mCategories != null){
            //根据目录请求文章列表
            RequestUtil.getAllPostsByCategoryId(mCategories, this);
        } else {
            //根据标签请求文章列表
        }
    }

    private void loadLocalAllPostsCache(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = CacheHelper.getPostsAll();
                if (!TextUtils.isEmpty(json)) {
                    handleAllPostsResponse(json);
                }
            }
        }).start();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        int type = mAdapter.getItemViewType(position);
        ArticleDetailBean bean;
        switch (type) {
            case MainRecyclerViewAdapter.TYPE_MAIN_ARTICLE_ITEM:
                switch (view.getId()){
                    //点击分享按钮
                    case R.id.img_article_share:
                        bean = (ArticleDetailBean) mPushList.get(position).getPushObject();
                        String shareText = bean.title +
                                "【来自"+getString(R.string.app_name)+"App】\n" + bean.url;
                        shareAction(shareText);
                        break;
                    //点击点赞按钮
                    case R.id.img_article_up:
                        Snackbar.make(view, "点赞", Snackbar.LENGTH_SHORT).show();
                        break;
                    //默认跳转文章详情页
                    default:
                        bean = (ArticleDetailBean) mPushList.get(position).getPushObject();
                        Intent intent = new Intent(getActivity(), Bit100ArticleDetailActivity.class);
                        intent.putExtra(Bit100ArticleDetailActivity.EXTRA_ARTICLE_BEAN, bean);
                        startActivity(intent);
                        break;
                }
                break;
            case MainRecyclerViewAdapter.TYPE_MAIN_MOOD_ITEM:
                CardMoodModel mood = (CardMoodModel) mPushList.get(position).getPushObject();
                Intent intent = new Intent(getActivity(), MoodCardActivity.class);
                intent.putExtra(MoodCardActivity.EXTRA_MOOD_OBJECT, mood);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemLongClick(RecyclerView.ViewHolder holder, View view, int position) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//        mSearchView = (SearchView) searchItem.getActionView();
//        mSearchView.setIconifiedByDefault(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_change_skin:
                LogUtil.d(TAG, "onOptionsItemSelected: click change skin");
                Activity activity = getActivity();
                if (activity instanceof Bit100MainActivity) {
                    ((Bit100MainActivity) activity).changeSkin();
                }
                break;
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;

            case R.id.action_share_app:
                shareAction(getString(R.string.share_application));
                break;
        }

        return true;
    }

    private void shareAction(String text){

        Snackbar.make(getView(), R.string.toast_invoking_share, Snackbar.LENGTH_SHORT).show();
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("text/plain");
        localIntent.putExtra("android.intent.extra.TEXT", text);
        localIntent.putExtra("android.intent.extra.SUBJECT", "这是分享内容");
        startActivity(Intent.createChooser(localIntent, getString(R.string.title_chooser_share)));

    }

    @Override
    public OnResponseListener<String> callback() {
        return new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, final Response<String> response) {
                LogUtil.d(TAG, "onSucceed: response==>" + response.get());
                switch (what){
                    case RequestUtil.TAG_GET_POSTS_BY_CATEGORIES:
                        handleAllPostsByCategoryResponse(response.get());
                        break;
                    case RequestUtil.TAG_GET_ALL_POSTS:
                        CacheHelper.savePostsAll(response.get());
                        handleAllPostsResponse(response.get());
                        break;
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                Toast.makeText(getActivity(),
                        TextUtils.isEmpty(response.get())
                                ? getString(R.string.error_network_failed)
                                : response.get()
                        , Toast.LENGTH_SHORT).show();
                mRecyclerView.setVisibility(View.VISIBLE);
                if (mRefresh.isRefreshing())
                    mRefresh.setRefreshing(false);
            }

            @Override
            public void onFinish(int what) {

            }
        };
    }

    /**
     * 处理获取全部文章的网络请求返回数据
     * @param json 请求响应返回的JSON数据
     */
    private void handleAllPostsResponse(String json){
        AllPostsResponse allPosts = new Gson().fromJson(json,
                new TypeToken<AllPostsResponse>(){}.getType());
        try {
            if ("ok".equals(allPosts.status)){

                for (ArticleDetailBean bean : allPosts.posts){
                    mPushList.add(new PushModel(MainRecyclerViewAdapter.TYPE_MAIN_ARTICLE_ITEM, bean));
                }

                if (allPosts.posts.length < 5)
                    mAdapter.setMoreVisible(false);

            } else {
                if (!TextUtils.isEmpty(allPosts.error))
                    Toast.makeText(getActivity(), allPosts.error, Toast.LENGTH_SHORT).show();
            }

            mAdapter.notifyDataSetChanged();
            mRecyclerView.setVisibility(View.VISIBLE);
            if (mRefresh.isRefreshing())
                mRefresh.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理根据目录获取文章列表的网络请求返回数据
     * @param json 请求响应返回的JSON数据
     */
    private void handleAllPostsByCategoryResponse(String json){
        AllPostsByCategoryResponse allPosts = new Gson().fromJson(json,
                new TypeToken<AllPostsByCategoryResponse>(){}.getType());

        if ("ok".equals(allPosts.status)){

            for (ArticleDetailBean bean : allPosts.posts){
                mPushList.add(new PushModel(MainRecyclerViewAdapter.TYPE_MAIN_ARTICLE_ITEM, bean));
            }

            if (allPosts.posts.length < 5)
                mAdapter.setMoreVisible(false);

        } else {
            if (!TextUtils.isEmpty(allPosts.error))
                Toast.makeText(getActivity(), allPosts.error, Toast.LENGTH_SHORT).show();
        }

        mAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.VISIBLE);
        if (mRefresh.isRefreshing())
            mRefresh.setRefreshing(false);
    }

    private void handleAllPostsByTagResponse(String json) {

    }

    private void requestMoodPosts() {
        new RequestMood()
        .addRequestMoodHandler(new RequestMoodHandler() {
            @Override
            public void onResponse(List<CardMoodModel> paramList) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mPushList.clear();
                for (CardMoodModel mood : paramList){
                    mAdapter.addItem(new PushModel(MainRecyclerViewAdapter.TYPE_MAIN_MOOD_ITEM, mood));
                }
            }
        }).start();
    }

}
