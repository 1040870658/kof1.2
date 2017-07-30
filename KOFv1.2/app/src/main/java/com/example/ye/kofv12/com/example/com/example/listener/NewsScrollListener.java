package com.example.ye.kofv12.com.example.com.example.listener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

import com.bumptech.glide.Glide;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by yechen on 2017/5/22.
 */

public class NewsScrollListener extends OnScrollListener {
    //private ImageLoader imageLoader;
    private Context context;
    private Handler handler;
    public static int page = 1;

    private final boolean pauseOnScroll;
    public NewsScrollListener( Context context, Handler handler, boolean pauseOnScroll) {
        super();
        this.pauseOnScroll = pauseOnScroll;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState){
            case RecyclerView.SCROLL_STATE_IDLE:
                //imageLoader.resume();
                Glide.with(context).resumeRequests();
                if(isVisBottom(recyclerView)){
                    Message msg = new Message();
                    msg.arg1 = ++page;
                    msg.what = SubFragment_1_1.REQUIRE_APPEND;
                    handler.sendMessage(msg);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                if(pauseOnScroll) {
                    Glide.with(context).pauseRequests();
                    //imageLoader.pause();
                }
                break;
        }
    }
    public static boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }
}
