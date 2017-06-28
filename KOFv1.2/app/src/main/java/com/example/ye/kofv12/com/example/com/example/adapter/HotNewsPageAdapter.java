package com.example.ye.kofv12.com.example.com.example.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;

import java.util.List;

/**
 * Created by yechen on 2017/2/8.
 */

public class HotNewsPageAdapter extends PagerAdapter {

    private SubFragment_1_1.HotNewsHolder hotNewsHolder;
    private List<ImageView> imageViews;
    public HotNewsPageAdapter(SubFragment_1_1.HotNewsHolder hotNewsHolder){
        this.hotNewsHolder = hotNewsHolder;
        this.imageViews = hotNewsHolder.getImageViews();
    }

    @Override
    public void notifyDataSetChanged() {
        hotNewsHolder.setImageViews();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(getCount() != 0) {
            container.addView( imageViews.get(position));
            return imageViews.get(position);
        }
        else{
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( imageViews.get(position));
    }
}