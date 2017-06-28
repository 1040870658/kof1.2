package com.example.ye.kofv12.com.example.com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.listener.VideoPageListener;
import com.example.ye.kofv12.com.example.model.NewsModel;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yechen on 2017/6/13.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DisplayMetrics metrics;
    private List<NewsModel> news;
    private Context context;
    public VideoAdapter(List<NewsModel> news, DisplayMetrics metrics, Context context) {
        super();
        this.news = news;
        this.metrics = metrics;
        this.context = context;
    }

    private DisplayImageOptions displayImageOptions =
            new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageOnFail(R.drawable.ic_launcher)
                    .showImageOnLoading(R.drawable.abc_list_pressed_holo_dark)
                    .showImageForEmptyUri(R.drawable.ic_launcher)
                    .build();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, null);

        int position = i;
        SubFragment_1_1.TextViewHolder holder = new SubFragment_1_1.TextViewHolder(view, metrics);
        holder.setListener(news.get(position),context,new VideoPageListener(news.get(position),context));
        holder.title.setText(news.get(position).getTitle());
        holder.summary.setText(news.get(position).getSummary());
        holder.player.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(news.get(position).getImage(), holder.image, displayImageOptions);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if (news.size() == 0)
            return;
        int position = i;
        SubFragment_1_1.TextViewHolder holder = (SubFragment_1_1.TextViewHolder) viewHolder;
        holder.title.setText(news.get(position).getTitle());
        holder.summary.setText(news.get(position).getSummary());
        holder.comment_total.setText(news.get(position).getComment_total()+"评论");
        holder.player.setVisibility(View.VISIBLE);
        holder.setListener(news.get(position),context,new VideoPageListener(news.get(position),context));
        ImageLoader.getInstance().displayImage(news.get(position).getImage(), holder.image, displayImageOptions);

    }

    @Override
    public int getItemCount() {
        return news.size();
    }


}