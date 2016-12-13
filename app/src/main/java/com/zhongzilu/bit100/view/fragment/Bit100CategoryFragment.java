package com.zhongzilu.bit100.view.fragment;

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
import com.zhongzilu.bit100.application.App;
import com.zhongzilu.bit100.application.helper.CacheHelper;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.application.util.NetworkUtil;
import com.zhongzilu.bit100.application.util.RequestUtil;
import com.zhongzilu.bit100.model.bean.CategoriesBean;
import com.zhongzilu.bit100.model.bean.ItemDecoratorBean;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.model.bean.TagsBean;
import com.zhongzilu.bit100.model.response.AllCategoriesResponse;
import com.zhongzilu.bit100.view.activity.Bit100ArticleListActivity;
import com.zhongzilu.bit100.view.adapter.CategoryRecyclerViewAdapter;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

import java.util.ArrayList;

/**
 * 分类版块Fragment
 * Created by zhongzilu on 2016-09-17.
 */
public class Bit100CategoryFragment extends Fragment
        implements MyItemClickListener, MyItemLongClickListener, RequestUtil.RequestCallback {
    private static final String TAG = "Bit100CategoryFragment==>";

    private View contentView;
    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private CategoryRecyclerViewAdapter mAdapter;
    private ArrayList<PushModel> mPushList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadMore = false;
    private boolean isFirst = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if (contentView == null)
            contentView = inflater.inflate(R.layout.include_common_recycler_view, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if (mRecyclerView == null)
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_common_recyclerView);
        if (mRefresh == null)
            mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_common_refresh);

        mRefresh.setOnRefreshListener(() -> {
            if (isNetwork()) {
                mPushList.clear();
                addTagsTestData();
                RequestUtil.getAllCategories(Bit100CategoryFragment.this);
            } else {
                endLoading();
            }
        });

        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        //设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //实例化对象
        mAdapter = new CategoryRecyclerViewAdapter(getActivity(), mPushList);

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

//                    if (mPushList.size() > 21) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        Snackbar.make(recyclerView, R.string.toast_no_more, Snackbar.LENGTH_SHORT).show();
                        isLoadMore = false;
//                        return;
//                    }
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

    // zhongzilu: 2016-10-27 添加标签的测试数据
    private void addTagsTestData(){
        String[] tags = getResources().getStringArray(R.array.tags);
        mPushList.add(new PushModel(CategoryRecyclerViewAdapter.TYPE_ITEM_DECORATOR,
                new ItemDecoratorBean("标签", String.valueOf(tags.length)) ));
        mPushList.add(new PushModel(CategoryRecyclerViewAdapter.TYPE_TAGS, new TagsBean(tags)));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst){
            if (isNetwork()) {
                RequestUtil.getAllCategories(this);
            } else {
                loadLocalCategoriesCache();
            }
            isFirst = false;
        }
    }

    private boolean isNetwork(){
        if (NetworkUtil.getNetworkState()){
            return true;
        } else {
            Toast.makeText(App.getAppContext(), R.string.toast_check_network,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void loadLocalCategoriesCache(){
        String json = CacheHelper.getAllCategories();
        if (!TextUtils.isEmpty(json)){
            handleCategoryResponse(json);
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        int type = mPushList.get(position).getPushType();
        switch (type){
            //点击目录分类
            case CategoryRecyclerViewAdapter.TYPE_CATEGORY:
                CategoriesBean categoriesBean = (CategoriesBean) mPushList.get(position).getPushObject();
                Intent intent = new Intent(getActivity(), Bit100ArticleListActivity.class);
                intent.putExtra(Bit100ArticleListActivity.EXTRA_CATEGORY_BEAN, categoriesBean);
                startActivity(intent);
                break;
            //点击标签分类
            case CategoryRecyclerViewAdapter.TYPE_TAGS:

                break;
        }
    }

    @Override
    public void onItemLongClick(RecyclerView.ViewHolder holder, View view, int position) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_item3, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_gift:
                Toast.makeText(getActivity(), "点击隐藏任务", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public OnResponseListener<String> callback() {
        return new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                LogUtil.d(TAG, "onSucceed: " + response);
                switch (what){
                    case RequestUtil.TAG_GET_ALL_CATEGORIES:
                        handleCategoryResponse(response.get());
                        CacheHelper.saveAllCategories(response.get());
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
                endLoading();
            }

            @Override
            public void onFinish(int what) {

            }
        };
    }

    private void handleCategoryResponse(final String json){
        new Thread(() -> {

            AllCategoriesResponse result = new Gson().fromJson(json,
                    new TypeToken<AllCategoriesResponse>(){}.getType());
            if ("ok".equals(result.status)){
                mPushList.clear();
                addTagsTestData();

                mPushList.add(new PushModel(CategoryRecyclerViewAdapter.TYPE_ITEM_DECORATOR,
                        new ItemDecoratorBean("目录", String.valueOf(result.categories.length)) ));

                for (CategoriesBean cb : result.categories){
                    mPushList.add(new PushModel(CategoryRecyclerViewAdapter.TYPE_CATEGORY, cb));
                }

            } else {
                Toast.makeText(getActivity(), result.error, Toast.LENGTH_SHORT).show();
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                    endLoading();
                }
            });

        }).start();

    }

    private void endLoading(){
        if (mRefresh.isRefreshing())
            mRefresh.setRefreshing(false);
    }
}
