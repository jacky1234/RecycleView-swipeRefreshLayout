package com.example.zhenfeiwang.rcydemo.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import com.example.zhenfeiwang.rcydemo.R;
import com.example.zhenfeiwang.rcydemo.adapter.base.RcyCommonAdapter;
import com.example.zhenfeiwang.rcydemo.adapter.base.RcyViewHolder;

import java.util.List;

/**
 * Created by zhenfei.wang on 2016/7/12.
 */
public class TestAdapter extends RcyCommonAdapter<String> {
    public TestAdapter(Context context, int layoutId, List datas , boolean loadMore, RecyclerView rv) {
        super(context, layoutId, datas, loadMore, rv);
    }

    @Override
    public void convert(RcyViewHolder holder, String s) {
        TextView tv = holder.getView(R.id.tv);
        tv.setText(s);
    }
}
