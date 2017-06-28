package com.example.ye.kofv12.com.example.com.example.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ye.kofv12.R;

/**
 * Created by yechen on 2017/6/17.
 */

public class RefreshRecyclerViewManager implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Activity activity;
    private Runnable presenter;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Thread thread;

    public RefreshRecyclerViewManager(Activity activity, Runnable presenter, RecyclerView.Adapter adapter){
        init(activity,presenter,adapter);
    }
    private void init(Activity activity, Runnable presenter, RecyclerView.Adapter adapter){
        this.activity = activity;
        this.presenter = presenter;
        this.adapter = adapter;
        recyclerView = (RecyclerView) activity.findViewById(R.id.rcv_general);
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.srl_general);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN,
                Color.BLUE,
                Color.CYAN);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this.activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);
    }
    public RefreshRecyclerViewManager(Activity activity, View itemView, Runnable presenter, RecyclerView.Adapter adapter){
        this.activity = activity;
        this.presenter = presenter;
        this.adapter = adapter;
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rcv_general);
        swipeRefreshLayout = (SwipeRefreshLayout) itemView.findViewById(R.id.srl_general);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN,
                Color.BLUE,
                Color.CYAN);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this.activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);
    }
    public RefreshRecyclerViewManager(Activity activity, Runnable presenter, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager){
        this.activity = activity;
        this.presenter = presenter;
        recyclerView = (RecyclerView) activity.findViewById(R.id.rcv_general);
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.srl_general);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN,
                Color.BLUE,
                Color.CYAN);
        swipeRefreshLayout.setOnRefreshListener(this);
        this.layoutManager = layoutManager;
        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);
    }
    public void setRefreshSchemeColors(int... colors){
        swipeRefreshLayout.setColorSchemeColors(colors);
    }
    @Override
    public void onRefresh() {
        thread = new Thread(presenter);
        thread.start();
    }
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        this.recyclerView.addItemDecoration(itemDecoration);
    }
    public void notifyDataSetChanged(){
        this.adapter.notifyDataSetChanged();
    }
    public void setRefreshing(boolean refreshing){
        swipeRefreshLayout.setRefreshing(refreshing);
    }
}
