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
import com.zhongzilu.bit100.model.bean.TagsBean;
import com.zhongzilu.bit100.view.adapter.listener.MyItemClickListener;
import com.zhongzilu.bit100.view.adapter.listener.MyItemLongClickListener;
import com.zhongzilu.bit100.view.viewholder.CategoryViewHolder;
import com.zhongzilu.bit100.view.viewholder.HeaderViewHolder;
import com.zhongzilu.bit100.view.viewholder.IconDecoratorViewHolder;
import com.zhongzilu.bit100.view.viewholder.ItemDecoratorViewHolder;
import com.zhongzilu.bit100.view.viewholder.LoadingMoreViewHolder;
import com.zhongzilu.bit100.view.viewholder.MainItemViewHolder;
import com.zhongzilu.bit100.view.viewholder.TagsLayoutViewHolder;
import com.zhongzilu.bit100.view.viewholder.ToastItemViewHolder;

import java.util.ArrayList;

/**
 * RecyclerView的适配器，用于添加数据和显示不同的布局
 * Created by zhongzilu on 2016-09-16.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    private ArrayList<PushModel> mPushList;
    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;

    public static final int TYPE_NULL = -1;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LOADING_MORE = 1;
    public static final int TYPE_ITEM_DECORATOR = 2;
    public static final int TYPE_ICON_ITEM_DECORATOR = 3;

    //主页item布局类型
    public static final int TYPE_MAIN_ITEM = 100;
    public static final int TYPE_TOAST = 101;

    //分类item布局类型
    public static final int TYPE_TAGS = 110;
    public static final int TYPE_CATEGORY = 111;

    private static View mHeaderView;

    private static final String TAG = "RecycleViewAdapter==>";

    public RecycleViewAdapter(Context context, ArrayList<PushModel> list) {
        this.mPushList = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return TYPE_LOADING_MORE;
        }

        return mPushList.get(position).getPushType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_HEADER:
                //头部横幅
                return new HeaderViewHolder(mHeaderView, mItemClickListener, mItemLongClickListener);
            case TYPE_LOADING_MORE:
                //底部加载更多
                view = LayoutInflater.from(context).inflate(R.layout.include_loading_more, parent, false);
                return new LoadingMoreViewHolder(view);
            case TYPE_MAIN_ITEM:
                //普通条目
                view = LayoutInflater.from(context).inflate(R.layout.item_artical_list, parent, false);
                return new MainItemViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_TOAST:
                //提示信息布局
                view = LayoutInflater.from(context).inflate(R.layout.include_toast_layout, parent, false);
                return new ToastItemViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_ITEM_DECORATOR:
                //item的分割布局
                view = LayoutInflater.from(context).inflate(R.layout.include_item_decorator_layout, parent, false);
                return new ItemDecoratorViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_ICON_ITEM_DECORATOR:
                //带图标的item分割布局
                view = LayoutInflater.from(context).inflate(R.layout.include_icon_item_decorator_layout, parent, false);
                return new IconDecoratorViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_CATEGORY:
                //目录item布局
                view = LayoutInflater.from(context).inflate(R.layout.item_category_layout, parent, false);
                return new CategoryViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_TAGS:
                //标签布局
                view = LayoutInflater.from(context).inflate(R.layout.item_tags_layout, parent, false);
                return new TagsLayoutViewHolder(view, mItemClickListener, mItemLongClickListener);
            case TYPE_NULL:
                view = LayoutInflater.from(context).inflate(R.layout.layout_height_zero, parent, false);
                return new HeaderViewHolder(view, mItemClickListener, mItemLongClickListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)){

            case TYPE_MAIN_ITEM:

                break;

            case TYPE_TOAST:

                break;

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

            case TYPE_CATEGORY:

                break;

            case TYPE_TAGS:
                TagsBean tagsBean = (TagsBean) mPushList.get(position).getPushObject();
                TagsLayoutViewHolder tagsLayoutViewHolder = (TagsLayoutViewHolder) holder;

                tagsLayoutViewHolder.mTagGroup.setTags(tagsBean.getmList());

                break;

            default:break;
        }
    }

    @Override
    public int getItemCount() {
        return mPushList.size() == 0 ? 0 : mPushList.size() + 1;
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
}
