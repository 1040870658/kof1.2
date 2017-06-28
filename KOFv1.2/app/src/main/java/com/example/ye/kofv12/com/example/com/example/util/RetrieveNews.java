package com.example.ye.kofv12.com.example.com.example.util;

import com.example.ye.kofv12.com.example.com.example.presenter.NewsPresenter;

/**
 * Created by yechen on 2017/1/24.
 */

public class RetrieveNews implements Runnable {
    private int page;
    private boolean require_hot_news;
    private NewsPresenter newsPresenter;
    public RetrieveNews(NewsPresenter newsPresenter,int page,boolean require_hot_news){
        this.newsPresenter = newsPresenter;
        this.page = page;
        this.require_hot_news = require_hot_news;
    }
    @Override
    public void run() {
        newsPresenter.receiveNews(page);
        if(require_hot_news)
             newsPresenter.receiveHotNews();
    }
}
