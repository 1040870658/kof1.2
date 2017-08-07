package com.example.ye.kofv12.com.example.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ye on 2016/9/2.
 */
public class FragmentMain_1 extends Fragment{
    private View contentView;
    private FragmentTabHost mTabhost;
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;
    protected List<Fragment> subfragments;
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
    protected void initViewPager(){
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
        if (hsv_container == null)
            return;
        outState.putInt("scroll_x",hsv_container.getScrollX());
        outState.putInt("scroll_y",hsv_container.getScrollY());
    }

    protected ArrayList getTabhostResources(){
        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.sub1_tabs_1));
        titles.add(getString(R.string.sub1_tabs_2));
        titles.add(getString(R.string.sub1_tabs_3));
        titles.add(getString(R.string.sub1_tabs_4));
        titles.add(getString(R.string.sub1_tabs_5));
        titles.add(getString(R.string.sub1_tabs_6));
        titles.add(getString(R.string.sub1_tabs_7));
        titles.add(getString(R.string.sub1_tabs_9));
        return titles;
    }
    private void initTabhost(LayoutInflater inflater){
        final TabWidget tabWidget = mTabhost.getTabWidget();
        ArrayList<String> titles = getTabhostResources();
        int index = 1;
        for(String title : titles) {
            mTabhost.addTab(mTabhost.newTabSpec("sub_tab_"+ index)
                    .setIndicator(getIndicatorView(inflater, title)), Fragment.class, null);
            index ++;
        }

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
    protected void initSubFragments(){
      /*  subfragments = new ArrayList<Fragment>();
        Bundle bundle = new Bundle();
        bundle.putInt(SubFragment_1_1.ARG,1);
        SubFragment_1_1 tmp = new SubFragment_1_1();
        tmp.setArguments(bundle);
        subfragments.add(tmp);
        tmp = new SubFragment_1_1();
        tmp.setArguments(bundle);
        subfragments.add(tmp);
        subfragments.add(new SubFragment_1_2());
        subfragments.add(new SubFragment_1_3());
        subfragments.add(new SubFragment_1_4());
        subfragments.add(new SubFragment_1_5());
        subfragments.add(new SubFragment_1_6());
        subfragments.add(new SubFragment_1_7());*/
        subfragments = new ArrayList<Fragment>();
        subfragments.add(SetUpSubFragment(SubFragment_1_1.IMPORTANT));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.LAUGH));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.TALK));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.EPL));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.CSL));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.SPL));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.GPL));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.ISA));
        subfragments.add(SetUpSubFragment(SubFragment_1_1.SPECIAL));
    }
    protected SubFragment_1_1 SetUpSubFragment(int arg){
        Bundle bundle = new Bundle();
        bundle.putInt(SubFragment_1_1.ARG,arg);
        SubFragment_1_1 tmp = new SubFragment_1_1();
        tmp.setArguments(bundle);
        return tmp;
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
