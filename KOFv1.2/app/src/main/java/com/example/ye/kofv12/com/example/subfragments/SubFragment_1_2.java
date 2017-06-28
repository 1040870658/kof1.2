package com.example.ye.kofv12.com.example.subfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ye.kofv12.R;

/**
 * Created by ye on 2016/9/2.
 */
public class SubFragment_1_2 extends Fragment{
    private String name ="sub2";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(name,"CreateView");
        return inflater.inflate(R.layout.layout_subfragment1_2,null);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e(name,"Started");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(name,"Resumed");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(name,"Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(name,"Stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(name,"Destroyed");
    }
}
