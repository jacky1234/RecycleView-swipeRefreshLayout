package com.example.zhenfeiwang.rcydemo;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zhenfeiwang.rcydemo.adapter.TestAdapter;

import junit.runner.Version;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView rv;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;
    private List<String> data;
    private TestAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Utils.refresh(data);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    break;
                case 1:
                    adapter.loadMore(Utils.loadMore());
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        init();
    }

    private void init() {
        data = new ArrayList<String>();
        layoutManager = new LinearLayoutManager(this);
//        layoutManager = new GridLayoutManager(this, 2);
        refreshLayout.setOnRefreshListener(this);
        rv.setLayoutManager(layoutManager);
        adapter = new TestAdapter(this, data, true, rv);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, refreshLayout, layoutManager) {
            @Override
            public void loadMore() {
                if (!adapter.isLoadFinish()) {
                    handler.sendEmptyMessageDelayed(1, 2000);
                }
            }
        });
        rv.setAdapter(adapter);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }
}
