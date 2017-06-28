package com.example.ye.kofv12.com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.SubFragment2.SubFragment_2_1;
import com.example.ye.kofv12.com.example.com.example.SubFragment4.SubFragment_4_1;
import com.example.ye.kofv12.com.example.com.example.presenter.DatasetPresenter;

import java.util.ArrayList;

/**
 * Created by ye on 2016/9/2.
 */
public class FragmentMain_2 extends FragmentMain_1{
    @Override
    protected ArrayList getTabhostResources() {
        ArrayList<String > titles = new ArrayList<>();
        titles.add(getString(R.string.sub2_tabs_1));
        titles.add(getString(R.string.sub4_tabs_1));
        titles.add(getString(R.string.sub4_tabs_2));
        titles.add(getString(R.string.sub4_tabs_3));
        titles.add(getString(R.string.sub4_tabs_4));
        titles.add(getString(R.string.sub4_tabs_5));
        return titles;
    }

    @Override
    protected void initSubFragments() {
        subfragments = new ArrayList<>();
        subfragments.add(new SubFragment_2_1());
    }

}
