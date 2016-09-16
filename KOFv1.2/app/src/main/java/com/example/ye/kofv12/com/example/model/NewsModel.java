package com.example.ye.kofv12.com.example.model;

import java.util.List;

/**
 * Created by ye on 2016/9/8.
 */
public class NewsModel {
    private List<String> titles;
    private List<String> summaries;
    private List<String> images;
    private int num;

    public NewsModel(){}

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<String> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<String> summaries) {
        this.summaries = summaries;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
