package com.example.ye.kofv12.com.example.com.example.adapter;

/**
 * Created by yechen on 2017/2/8.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.model.NewsModel;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;


public class OtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DisplayMetrics metrics;
    private List<NewsModel> news;
    private Context context;
    public OtherAdapter(List<NewsModel> news, DisplayMetrics metrics, Context context) {
        super();
        this.news = news;
        this.metrics = metrics;
        this.context = context;
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
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, null);

        SubFragment_1_1.TextViewHolder holder = new SubFragment_1_1.TextViewHolder(view, metrics);
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
        holder.setListener(news.get(position),context);
        //ImageLoader.getInstance().displayImage(news.get(position).getImage(), holder.image, displayImageOptions);
        Glide.with(context).load(news.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return news.size();
    }


}