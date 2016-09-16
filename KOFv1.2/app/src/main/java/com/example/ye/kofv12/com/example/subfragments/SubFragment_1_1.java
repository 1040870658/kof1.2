package com.example.ye.kofv12.com.example.subfragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.presenter.NewsPresenter;
import com.example.ye.kofv12.com.example.model.NewsModel;

/**
 * Created by ye on 2016/9/2.
 */
public class SubFragment_1_1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View contentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private final int REFRESH_COMPLETED = 0x000001;
    private MyAdapter adapter;
    private Handler handler;
    private NewsModel news;
    private NewsPresenter newsPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        news = new NewsModel();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(contentView == null) {
              handler = new Handler() {
                 public void handlerMessage(Message msg) {
                     switch (msg.what) {
                         case REFRESH_COMPLETED:
                          default:
                            ;
                        }
                    }
               };
        }
        return inflater.inflate(R.layout.layout_subfragment1_1,null);
    }
    @Override
    public void onRefresh(){
        handler.sendEmptyMessage(REFRESH_COMPLETED);
    }
    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        }
        @Override
        public int getItemCount() {
            return news.getNum();
        }
    }

}
