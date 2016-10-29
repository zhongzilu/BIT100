package com.zhongzilu.bit100.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhongzilu.bit100.R;
import com.zhongzilu.bit100.model.bean.IconItemDecoratorBean;
import com.zhongzilu.bit100.model.bean.ItemDecoratorBean;
import com.zhongzilu.bit100.model.bean.PushModel;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;
import com.zhongzilu.bit100.view.viewholder.HeaderViewHolder;
import com.zhongzilu.bit100.view.viewholder.IconDecoratorViewHolder;
import com.zhongzilu.bit100.view.viewholder.ItemDecoratorViewHolder;
import com.zhongzilu.bit100.view.viewholder.LoadingMoreViewHolder;
import com.zhongzilu.bit100.view.viewholder.ToastItemViewHolder;

import java.util.ArrayList;

/**
 * RecyclerViewAdapter的抽象基类，后续所有自定的RecyclerViewAdapter都需要继承该类
 * Created by zhongzilu on 2016-10-24.
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Type Tag
    public static final int TYPE_NULL = -2;
    public static final int TYPE_HEADER = -1;
    public static final int TYPE_ITEM_DECORATOR = 0x001;
    public static final int TYPE_ICON_ITEM_DECORATOR = 0x002;
    public static final int TYPE_TOAST = 0x003;
    public static final int TYPE_LOADING_MORE = 0x004;

    //other
    View mHeaderView;
    boolean isMoreVisible = true;
    Context context;
    ArrayList<PushModel> mPushList;

    //listener
    MyItemClickListener mItemClickListener;
    MyItemLongClickListener mItemLongClickListener;

    public BaseRecyclerViewAdapter(Context context, ArrayList<PushModel> mPushList) {
        this.context = context;
        this.mPushList = mPushList;
    }

    @Override
    public int getItemViewType(int position) {
        if (isMoreVisible && position + 1 == getItemCount()) {
            return TYPE_LOADING_MORE;
        }
        return mPushList.get(position).getPushType();
    }

    @Override
    public int getItemCount() {
        if (isMoreVisible)
            return mPushList.size() == 0 ? 0 : mPushList.size() + 1;
        return mPushList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                //头部横幅
                return new HeaderViewHolder(mHeaderView, mItemClickListener, mItemLongClickListener);
            case TYPE_NULL:
                //空布局
                view = LayoutInflater.from(context).inflate(R.layout.layout_height_zero, parent, false);
                return new HeaderViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_ITEM_DECORATOR:
                //item的分割布局
                view = LayoutInflater.from(context).inflate(R.layout.include_item_decorator_layout, parent, false);
                return new ItemDecoratorViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_ICON_ITEM_DECORATOR:
                //带图标的item分割布局
                view = LayoutInflater.from(context).inflate(R.layout.include_icon_item_decorator_layout, parent, false);
                return new IconDecoratorViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_LOADING_MORE:
                //底部加载更多
                view = LayoutInflater.from(context).inflate(R.layout.include_loading_more, parent, false);
                return new LoadingMoreViewHolder(view);
            case TYPE_TOAST:
                //提示信息布局
                view = LayoutInflater.from(context).inflate(R.layout.include_toast_layout, parent, false);
                return new ToastItemViewHolder(view, mItemClickListener, mItemLongClickListener);
            default:
                return getViewHolder(parent, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TYPE_TOAST:break;
            case TYPE_ITEM_DECORATOR:
                ItemDecoratorBean itemDecoratorBean =
                        (ItemDecoratorBean) mPushList.get(position).getPushObject();
                ItemDecoratorViewHolder itemDecoratorViewHolder = (ItemDecoratorViewHolder) holder;

                itemDecoratorViewHolder.mTitle.setText(itemDecoratorBean.getmTitle());
                itemDecoratorViewHolder.mSubTitle.setText(itemDecoratorBean.getmSubTitle());
                break;

            case TYPE_ICON_ITEM_DECORATOR:
                IconItemDecoratorBean iconItemDecoratorBean =
                        (IconItemDecoratorBean) mPushList.get(position).getPushObject();
                IconDecoratorViewHolder iconDecoratorViewHolder = (IconDecoratorViewHolder) holder;

                iconDecoratorViewHolder.mIcon.setImageResource(iconItemDecoratorBean.getmIconRes());
                iconDecoratorViewHolder.mName.setText(iconItemDecoratorBean.getmTitle());
                break;
        }
    }

    /**
     * 在指定位置添加一个新的Item
     * 添加之后只刷新指定位置的数据
     * @param model
     * @param positionToAdd
     */
    public void addItem(PushModel model, int positionToAdd) {
        if (model != null && positionToAdd > 0) {
            mPushList.add(positionToAdd, model);
            notifyItemInserted(positionToAdd);
        }
    }

    /**
     * 按对象添加数据条目
     * 添加之后会刷新全部数据
     * @param model
     */
    public void addItem(PushModel model){
        if (model != null) {
            mPushList.add(model);
            notifyDataSetChanged();
        }
    }

    /**
     * 按集合添加数据
     * 添加之后会刷新全部数据
     * @param list
     */
    public void addItem(ArrayList<PushModel> list){
        if (list != null && list.size() >= 0) {
            for (PushModel pushModel : list) {
                mPushList.add(pushModel);
            }
            notifyDataSetChanged();
        }
    }

    /**清除所有数据，通常在刷新需要替换全部数据的时候使用*/
    public void clearAll(){
        mPushList.clear();
    }

    public void remove(int position){
        mPushList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 添加头部视图
     * @param view
     */
    public void addHeaderView(View view){
        mHeaderView = view;
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    /**
     * 设置Item长按监听
     * @param listener
     */
    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    /**
     * 设置是否显示【加载更多】的布局，为了解决当数据量少的时候，
     * 界面上会一直出现【加载更多】布局的问题
     */
    public void setMoreVisible(boolean isVisible) {
        this.isMoreVisible = isVisible;
    }

    /**抽象方法，用于在子类中返回匹配类型的viewHolder*/
    abstract public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);
}
