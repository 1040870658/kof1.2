package com.example.ye.kofv12.com.example.com.example.util;

import com.example.ye.kofv12.com.example.com.example.presenter.MatchPresenter;

/**
 * Created by yechen on 2017/6/10.
 */

public class RetrieveMatch implements Runnable {
    private int arg;
    private MatchPresenter matchPresenter;
    public RetrieveMatch(MatchPresenter matchPresenter, int arg){
        this.matchPresenter = matchPresenter;
        this.arg = arg;
    }
    @Override
    public void run() {
        matchPresenter.requestData();
    }
}
