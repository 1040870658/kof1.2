package com.example.ye.kofv12.com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.model.ScreenModel;
import com.example.ye.kofv12.com.example.subfragments.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ye on 2016/9/2.
 */
public class FragmentMain_1 extends Fragment{
    private View contentView;
    private FragmentTabHost mTabhost;
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> subfragments;
    private HorizontalScrollView hsv_container;
    private ScreenModel screenModel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(contentView == null) {
            contentView = getActivity().getLayoutInflater().inflate(R.layout.layout_fragmentmain_1, null);
            screenModel = new ScreenModel(this.getActivity());
            initSubFragments();
            mTabhost = (FragmentTabHost) contentView.findViewById(android.R.id.tabhost);
            mTabhost.setup(getActivity(), getChildFragmentManager(), R.id.fragment_content);
            initTabhost(getActivity().getLayoutInflater());
            viewPager = (ViewPager) contentView.findViewById(R.id.vp_fm1);
            initViewPager();
            hsv_container = (HorizontalScrollView) contentView.findViewById(R.id.tabwidget_hsv);
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initScroller(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    private void initScroller(Bundle savedInstanceState){
        int scroll_x = hsv_container.getScrollX();
        int scroll_y = hsv_container.getScrollY();
        int tab = mTabhost.getCurrentTab();
        TabWidget tabWidget = mTabhost.getTabWidget();
        View currentWidget = tabWidget.getChildTabViewAt(tab);
        hsv_container.smoothScrollTo((int)currentWidget.getX(),(int)currentWidget.getY());
        if(null != savedInstanceState && null != savedInstanceState.get("scroll_x")){
            scroll_x = (Integer)savedInstanceState.get("scroll_x");
            scroll_y = (Integer)savedInstanceState.get("scroll_y");
            hsv_container.smoothScrollTo(scroll_x,scroll_y);
        }
    }
    private View getIndicatorView(LayoutInflater inflater,String text){
        View contentview = inflater.inflate(R.layout.tablayout_sub,null);
        TextView textView = (TextView)contentview.findViewById(R.id.sub_text);
        textView.setText(text);
        return contentview;
    }
    private void initViewPager(){
        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return subfragments.get(i);
            }

            @Override
            public int getCount() {
                return subfragments.size();
            }
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                mTabhost.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("scroll_x",hsv_container.getScrollX());
        outState.putInt("scroll_y",hsv_container.getScrollY());
    }

    private void initTabhost(LayoutInflater inflater){
        final TabWidget tabWidget = mTabhost.getTabWidget();
        mTabhost.addTab(mTabhost.newTabSpec("sub_tab1_1")
                .setIndicator(getIndicatorView(inflater,getString(R.string.sub1_tabs_1))), Fragment.class, null);
        mTabhost.addTab(mTabhost.newTabSpec("sub_tab1_2")
                .setIndicator(getIndicatorView(inflater,getString(R.string.sub1_tabs_2))), Fragment.class, null);
        mTabhost.addTab(mTabhost.newTabSpec("sub_tab1_3")
                .setIndicator(getIndicatorView(inflater,getString(R.string.sub1_tabs_3))), Fragment.class, null);
        mTabhost.addTab(mTabhost.newTabSpec("sub_tab1_4")
                .setIndicator(getIndicatorView(inflater,getString(R.string.sub1_tabs_4))), Fragment.class, null);
        mTabhost.addTab(mTabhost.newTabSpec("sub_tab1_5")
                .setIndicator(getIndicatorView(inflater,getString(R.string.sub1_tabs_5))), Fragment.class, null);
        mTabhost.addTab(mTabhost.newTabSpec("sub_tab1_6")
                .setIndicator(getIndicatorView(inflater,getString(R.string.sub1_tabs_6))), Fragment.class, null);
        mTabhost.addTab(mTabhost.newTabSpec("sub_tab1_7")
                .setIndicator(getIndicatorView(inflater,getString(R.string.sub1_tabs_7))), Fragment.class, null);

        int tab_count = tabWidget.getTabCount();
        for(int i = 0;i != tab_count;i ++){
            tabWidget.getChildTabViewAt(i)
                    .setMinimumWidth(screenModel.getScreenWidth()/screenModel.getTAB_DISPLAY_NUM());
        }
        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int tab = mTabhost.getCurrentTab();
                int scroll_x = (int)(tabWidget.getChildTabViewAt(tab).getX()-tabWidget.getChildTabViewAt(tab).getWidth());
                viewPager.setCurrentItem(tab,true);
                hsv_container.smoothScrollTo(scroll_x,(int)tabWidget.getY());
            }
        });
    }
    private void initSubFragments(){
        subfragments = new ArrayList<Fragment>();
        subfragments.add(new SubFragment_1_1());
        subfragments.add(new SubFragment_1_2());
        subfragments.add(new SubFragment_1_3());
        subfragments.add(new SubFragment_1_4());
        subfragments.add(new SubFragment_1_5());
        subfragments.add(new SubFragment_1_6());
        subfragments.add(new SubFragment_1_7());
    }
    /*private void setSubTabs(){
        tabLayout.getTabAt(0).setText(getString(R.string.sub1_tabs_1));
        tabLayout.getTabAt(1).setText(getString(R.string.sub1_tabs_2));
        tabLayout.getTabAt(2).setText(getString(R.string.sub1_tabs_3));
        tabLayout.getTabAt(3).setText(getString(R.string.sub1_tabs_4));
        tabLayout.getTabAt(4).setText(getString(R.string.sub1_tabs_5));
        tabLayout.getTabAt(5).setText(getString(R.string.sub1_tabs_6));
        tabLayout.getTabAt(6).setText(getString(R.string.sub1_tabs_7));
    }*/

}
