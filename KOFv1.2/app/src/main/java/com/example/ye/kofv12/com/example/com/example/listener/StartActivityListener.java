package com.example.ye.kofv12.com.example.com.example.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.ye.kofv12.com.example.DetailActivity;
import com.example.ye.kofv12.com.example.model.NewsModel;

/**
 * Created by yechen on 2017/5/25.
 */

public class StartActivityListener extends DetailNewsListener{
    private NewsModel newsModel;
    private Context context;

    public StartActivityListener(NewsModel news, Context context){
        this.newsModel = news;
        this.context = context;
    }
    public void setNewsModel(NewsModel news){
        newsModel = news;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("news",newsModel);
        context.startActivity(intent);
    }
}
