package com.example.ye.kofv12.com.example.com.example.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.example.ye.kofv12.R;

/**
 * Created by ye on 2016/9/5.
 */
public class SplashScreen extends Dialog{
    private final int DELAY = 4000;
    private int view_id;
    public SplashScreen(Context context,int theme,int layoutid){
        super(context,theme);
        view_id = layoutid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view_id);
        setCancelable(false);
        getWindow().setWindowAnimations(R.anim.splash_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },DELAY);
    }
}
