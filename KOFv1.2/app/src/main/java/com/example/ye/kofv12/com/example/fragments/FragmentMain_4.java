package com.example.ye.kofv12.com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.SubFragment4.SubFragment_4_1;
import com.example.ye.kofv12.com.example.com.example.presenter.DatasetPresenter;

import java.util.ArrayList;

/**
 * Created by ye on 2016/9/5.
 */
public class FragmentMain_4 extends FragmentMain_1 {
    @Override
    protected ArrayList getTabhostResources() {
        ArrayList<String > titles = new ArrayList<>();
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
        SubFragment_4_1 subFragment_4_1 = new SubFragment_4_1();
        subFragment_4_1.setArguments(IdFactory(DatasetPresenter.CSL));
        subfragments.add(subFragment_4_1);

        subFragment_4_1 = new SubFragment_4_1();
        subFragment_4_1.setArguments(IdFactory(DatasetPresenter.EPL));
        subfragments.add(subFragment_4_1);

        subFragment_4_1 = new SubFragment_4_1();
        subFragment_4_1.setArguments(IdFactory(DatasetPresenter.GPL));
        subfragments.add(subFragment_4_1);

        subFragment_4_1 = new SubFragment_4_1();
        subFragment_4_1.setArguments(IdFactory(DatasetPresenter.SPL));
        subfragments.add(subFragment_4_1);

        subFragment_4_1 = new SubFragment_4_1();
        subFragment_4_1.setArguments(IdFactory(DatasetPresenter.ISA));
        subfragments.add(subFragment_4_1);
    }
    private Bundle IdFactory(int id){
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        return bundle;
    }
}
