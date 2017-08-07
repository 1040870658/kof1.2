package com.example.ye.kofv12.com.example.com.example.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.ye.kofv12.VideoActivity;
import com.example.ye.kofv12.com.example.model.NewsModel;

/**
 * Created by yechen on 2017/6/13.
 */

public class VideoPageListener extends DetailNewsListener {
    private NewsModel newsModel;
    private Context context;
    public VideoPageListener(NewsModel newsModel, Context context){
        this.context = context;
        this.newsModel = newsModel;
    }
    @Override
    public void setNewsModel(NewsModel news) {
        this.newsModel = news;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("news",newsModel);
        context.startActivity(intent);
    }
}
