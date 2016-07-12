package com.example.zhenfeiwang.rcydemo.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhenfei.wang on 2016/7/12.
 * recycleview的通用ViewHolder
 */
public class RcyViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public RcyViewHolder(Context context, View itemView, ViewGroup parent)
    {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static RcyViewHolder get(Context context, ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        RcyViewHolder holder = new RcyViewHolder(context, itemView, parent);
        return holder;
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public RcyViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
