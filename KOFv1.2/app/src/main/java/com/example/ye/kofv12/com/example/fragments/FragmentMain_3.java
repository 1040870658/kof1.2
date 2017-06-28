package com.example.ye.kofv12.com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_2;

import java.util.ArrayList;

/**
 * Created by ye on 2016/9/2.
 */
public class FragmentMain_3 extends FragmentMain_1{
    @Override
    protected ArrayList getTabhostResources() {
        ArrayList<String > titles = new ArrayList<>();
        titles.add(getString(R.string.sub3_tabs_1));
        titles.add(getString(R.string.sub3_tabs_2));
        return titles;
    }

    @Override
    protected void initSubFragments() {
        subfragments = new ArrayList<>(2);
        subfragments.add(SetUpSubFragment(SubFragment_1_1.VIDEO));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.COLLECTION));
    }
}
