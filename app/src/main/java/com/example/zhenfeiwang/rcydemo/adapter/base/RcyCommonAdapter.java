package com.example.zhenfeiwang.rcydemo.adapter.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhenfeiwang.rcydemo.R;
import com.example.zhenfeiwang.rcydemo.adapter.IPisotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenfei.wang on 2016/7/12.
 * recycleview 通用的Adapter
 * 只支持单一布局
 */
public  abstract class RcyCommonAdapter <T> extends RecyclerView.Adapter<RcyViewHolder> implements IPisotion{
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected boolean loadMore;


    /**
     *
     * @param context
     * @param datas
     * @param loadMore 是否需要底部加载更多
     * @param rv
     */
    public RcyCommonAdapter(Context context, List<T> datas , boolean loadMore,RecyclerView rv)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
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
                    // 若是最后一个 且需要加载更多，则强制让最后一个条目占满横屏
                    if (type == R.layout.item_list_footer) {
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
        return RcyViewHolder.get(mContext, parent, viewType, this);
    }

    @Override
    public int getItemViewType(int position) {
        int lId = 0;
        if(loadMore && position + 1 == getItemCount()){
            lId = R.layout.item_list_footer;
        } else {
            lId = getmLayoutId(position);
        }
        return lId;
    }

    @Override
    public void onBindViewHolder(RcyViewHolder holder, int position)
    {
        if(getItemViewType(position)  == R.layout.item_list_footer){
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
    public abstract int getmLayoutId(int position);
    public abstract void onItemClickListener(int position);

    @Override
    public int getItemCount()
    {
        return  loadMore ? mDatas.size() + 1 : mDatas.size();
    }

    @Override
    public void clickPosition(int position) {
        if(getItemViewType(position)  != R.layout.item_list_footer){
            onItemClickListener(position);
        }
    }
}
