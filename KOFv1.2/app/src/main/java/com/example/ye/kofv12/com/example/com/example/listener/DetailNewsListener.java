package com.example.ye.kofv12.com.example.com.example.listener;

import android.view.View;

import com.example.ye.kofv12.com.example.model.NewsModel;

/**
 * Created by yechen on 2017/6/12.
 */

public abstract class   DetailNewsListener implements View.OnClickListener{
    public abstract void setNewsModel(NewsModel news);
}
