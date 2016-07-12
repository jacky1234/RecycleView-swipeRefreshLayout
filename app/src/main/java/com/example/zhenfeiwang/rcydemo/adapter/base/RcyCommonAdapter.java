package com.example.zhenfeiwang.rcydemo.adapter.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhenfeiwang.rcydemo.R;

import java.util.List;

/**
 * Created by zhenfei.wang on 2016/7/12.
 * recycleview 通用的Adapter
 * 只支持单一布局
 */
public abstract class RcyCommonAdapter <T> extends RecyclerView.Adapter<RcyViewHolder>{
    private static final int CONTENT_FOOTER = 0x222;                     // 尾部
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected boolean loadMore;


    public RcyCommonAdapter(Context context, int layoutId, List<T> datas , boolean loadMore,RecyclerView rv)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
        this.loadMore = loadMore;
        setSpanCount(rv);
    }

    /**
     * 设置每个条目占用的列数
     * @param recyclerView recycleView
     */
    private void setSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (type == CONTENT_FOOTER) {
                        return gridLayoutManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }


    @Override
    public RcyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        RcyViewHolder viewHolder = null;
        if(viewType == CONTENT_FOOTER){
             viewHolder = RcyViewHolder.get(mContext, parent, R.layout.item_list_footer);
        }else {
             viewHolder = RcyViewHolder.get(mContext, parent, mLayoutId);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(loadMore){
            if(position + 1 == getItemCount()){
                return CONTENT_FOOTER;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RcyViewHolder holder, int position)
    {
            if(position + 1 == getItemCount() && loadMore){
                checkLoadStatus(holder);
            }else {
                 convert(holder, mDatas.get(position));
             }
    }

    private void checkLoadStatus(RcyViewHolder holder) {
        if(getItemCount() != 1){
             holder.getView(R.id.tv_item_footer_load_more).setVisibility(View.GONE);
             holder.getView(R.id.pb_item_footer_loading).setVisibility(View.VISIBLE);
        }else {
            holder.getView(R.id.tv_item_footer_load_more).setVisibility(View.GONE);
            holder.getView(R.id.pb_item_footer_loading).setVisibility(View.GONE);
        }
    }


    public abstract void convert(RcyViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return loadMore ? mDatas.size() + 1 : mDatas.size();
    }
}
