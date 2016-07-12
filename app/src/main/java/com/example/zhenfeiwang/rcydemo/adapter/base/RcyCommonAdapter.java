package com.example.zhenfeiwang.rcydemo.adapter.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhenfeiwang.rcydemo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhenfei.wang on 2016/7/12.
 * recycleview 通用的Adapter
 * 只支持单一布局
 */
public abstract class RcyCommonAdapter <T> extends RecyclerView.Adapter<RcyViewHolder>{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected boolean loadMore;
    protected Map<Integer, Integer> map = new HashMap<>();


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
        return RcyViewHolder.get(mContext, parent, viewType);
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

    @Override
    public int getItemCount()
    {
        return  loadMore ? mDatas.size() + 1 : mDatas.size();
    }
}
