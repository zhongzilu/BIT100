package com.zhongzilu.bit100.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.application.util.LogUtil;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.view.activity.Bit100ArticleDetailActivity;
import com.zhongzilu.bit100.view.activity.Bit100MainActivity;
import com.zhongzilu.bit100.view.activity.SettingsActivity;
import com.zhongzilu.bit100.view.adapter.RecycleViewAdapter;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;

import java.util.ArrayList;

/**
 * 【主页】版块的子Fragment
 * Created by zhongzilu on 2016-09-16.
 */
public class Bit100MainFragment extends Fragment
        implements MyItemClickListener, MyItemLongClickListener {

    private static final String TAG = "Bit100MainFragment==>";

    private View contentView;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mAdapter;
    private SwipeRefreshLayout mRefresh;

    private ArrayList<PushModel> mPushList = new ArrayList<>();
    private boolean isLoadMore = false;
    private boolean isFirst = true;
    private LinearLayoutManager mLayoutManager;
    private Handler simulationNetWorkDataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //添加数据
//            if (mPushList.isEmpty()) {
                mPushList.clear();

                mPushList.add(new PushModel(RecycleViewAdapter.TYPE_NULL, new Object()));

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                            mAdapter.addItem(new PushModel(RecycleViewAdapter.TYPE_TOAST, new Object()), 1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            mAdapter.remove(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                addTestDate();
//            }

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (mRefresh.isRefreshing())
                mRefresh.setRefreshing(false);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null)
            contentView = inflater.inflate(R.layout.fragment_main_layout, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if (mRecyclerView == null)
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_organization_info_main_list);
        if (mRefresh == null)
            mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simulationNetWorkDataHandler.sendEmptyMessageDelayed(0, 2000);
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
        mAdapter = new RecycleViewAdapter(getActivity(), mPushList);

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

                    if (mPushList.size() > 11) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        Toast.makeText(getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
                        isLoadMore = false;
                        return;
                    }

                    addTestDate();
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    mAdapter.notifyDataSetChanged();
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

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            simulationNetWorkDataHandler.sendEmptyMessageDelayed(0, 2000);
            isFirst = false;
        }
    }

    /**
     * 添加测试数据
     */
    private void addTestDate() {

        for (int i = 0; i < 5; i++) {
            mPushList.add(new PushModel(RecycleViewAdapter.TYPE_MAIN_ITEM, new Object()));
        }

    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        int type = mAdapter.getItemViewType(position);
        switch (type) {
            case RecycleViewAdapter.TYPE_MAIN_ITEM:
                startActivity(new Intent(getActivity(), Bit100ArticleDetailActivity.class));
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
                shareApplication();
                break;
        }

        return true;
    }

    private void shareApplication(){
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("text/plain");
        localIntent.putExtra("android.intent.extra.TEXT", getString(R.string.share_application));
        localIntent.putExtra("android.intent.extra.SUBJECT", "这是分享内容");
        startActivity(Intent.createChooser(localIntent, "选择分享"));
    }

}
