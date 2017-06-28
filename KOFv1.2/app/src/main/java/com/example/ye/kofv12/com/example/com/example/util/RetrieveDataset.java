package com.example.ye.kofv12.com.example.com.example.util;

import com.example.ye.kofv12.com.example.com.example.presenter.DatasetPresenter;
import com.example.ye.kofv12.com.example.com.example.presenter.NewsPresenter;

/**
 * Created by yechen on 2017/6/7.
 */

public class RetrieveDataset implements Runnable {
    private int arg;
    private DatasetPresenter datasetPresenter;
    public RetrieveDataset(DatasetPresenter datasetPresenter, int arg){
        this.datasetPresenter = datasetPresenter;
        this.arg = arg;
    }
    @Override
    public void run() {
        datasetPresenter.requestData(arg);
    }
}
