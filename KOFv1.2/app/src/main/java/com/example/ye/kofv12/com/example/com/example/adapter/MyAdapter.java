package com.example.ye.kofv12.com.example.com.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.model.NewsModel;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yechen on 2017/2/8.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HEAD = 0x00000000;
    private final int TEXT = 0x00000001;
    private List<NewsModel> hotNews;
    private List<NewsModel> news;
    private HotNewsPageAdapter hotNews_adapter;
    private DisplayMetrics metrics;
    public MyAdapter(List<NewsModel>hotNews, List<NewsModel>news, HotNewsPageAdapter hotNews_adapter, DisplayMetrics metrics) {
        super();
        this.news = news;
        this.hotNews = hotNews;
        this.hotNews_adapter = hotNews_adapter;
        this.metrics = metrics;

    }

    public HotNewsPageAdapter getHotNews_adapter(){
        return hotNews_adapter;
    }
    private DisplayImageOptions displayImageOptions =
            new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageOnFail(R.drawable.my_ic_launcher)
                    .showImageOnLoading(R.drawable.abc_list_pressed_holo_dark)
                    .showImageForEmptyUri(R.drawable.my_ic_launcher)
                    .build();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (getItemViewType(i) == HEAD) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hotnews, null);
            SubFragment_1_1.HotNewsHolder hotNewsHolder = new SubFragment_1_1.HotNewsHolder(view,viewGroup.getContext(), hotNews, metrics);
            hotNewsHolder.setAdapter(hotNews_adapter);
            return hotNewsHolder;
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, null);

            int position = i;
            SubFragment_1_1.TextViewHolder holder = new SubFragment_1_1.TextViewHolder(view, metrics);
            holder.title.setText(news.get(position).getTitle());
            holder.summary.setText(news.get(position).getSummary());
            ImageLoader.getInstance().displayImage(news.get(position).getImage(), holder.image, displayImageOptions);
            return holder;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) != HEAD ) {
            if(news.size() == 0)
                return;
            int position = i-1;
            SubFragment_1_1.TextViewHolder holder = (SubFragment_1_1.TextViewHolder) viewHolder;
            holder.title.setText(news.get(position).getTitle());
            holder.summary.setText(news.get(position).getSummary());


            ImageLoader.getInstance().displayImage(news.get(position).getImage(),holder.image,displayImageOptions);
        }
        else{
            SubFragment_1_1.HotNewsHolder hotNewsHolder = (SubFragment_1_1.HotNewsHolder)viewHolder;
            if(hotNews.size() != 0)
                hotNewsHolder.hotTextView.setText(hotNews.get(0).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (hotNews.size() != 0)
            return news.size() + 1;
        else
            return news.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD;
        else
            return TEXT;
    }

}