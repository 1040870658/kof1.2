package com.example.ye.kofv12.com.example.model;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by ye on 2016/9/6.
 */
public class ScreenModel {
    private int screenWidth;
    private int screenHeight;
    private final int TAB_DISPLAY_NUM = 5;

    public ScreenModel(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setScreenHeight(displayMetrics.heightPixels);
        setScreenWidth(displayMetrics.widthPixels);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getTAB_DISPLAY_NUM() {
        return TAB_DISPLAY_NUM;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
