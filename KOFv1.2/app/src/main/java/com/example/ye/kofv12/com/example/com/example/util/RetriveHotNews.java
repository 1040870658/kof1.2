package com.example.ye.kofv12.com.example.com.example.util;

import com.example.ye.kofv12.com.example.com.example.presenter.NewsPresenter;

import java.io.IOException;

/**
 * Created by yechen on 2017/1/24.
 */

public class RetriveHotNews implements Runnable {
    private NewsPresenter newsPresenter;
    public RetriveHotNews(NewsPresenter newsPresenter){
        this.newsPresenter = newsPresenter;
    }
    @Override
    public void run() {
        newsPresenter.receiveHotNews();
    }
}
