package com.example.ye.kofv12.com.example.com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yechen on 2017/6/17.
 */

public abstract class GroupAdapter extends RecyclerView.Adapter {
    protected List<List> groups;
    protected List data;
    protected Context context;

    public GroupAdapter(List<List> groups, Context context){
        this.groups = groups;
        data = new ArrayList();
        for(List group:groups){
            data.addAll(group);
        }
        this.context = context;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        data = new ArrayList();
        for(List group:groups){
            if(group == null)
                return count;
            count ++;
            data.addAll(group);
        }
        return data.size();
    }

}
