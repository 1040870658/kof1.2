package com.example.ye.kofv12.com.example.com.example.SubFragment2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ye.kofv12.MyActivity;
import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.adapter.GroupAdapter;
import com.example.ye.kofv12.com.example.com.example.adapter.LiveAdapter;
import com.example.ye.kofv12.com.example.com.example.presenter.MatchPresenter;
import com.example.ye.kofv12.com.example.com.example.util.HoverListDecorator;
import com.example.ye.kofv12.com.example.com.example.util.MyDecoration;
import com.example.ye.kofv12.com.example.com.example.util.RetrieveMatch;
import com.example.ye.kofv12.com.example.com.example.view.CommentTextView;
import com.example.ye.kofv12.com.example.com.example.view.RefreshRecyclerViewManager;
import com.example.ye.kofv12.com.example.model.DecoratorModel;
import com.example.ye.kofv12.com.example.model.MatchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yechen on 2017/6/10.
 */

public class SubFragment_2_1 extends Fragment {
    private MatchPresenter matchPresenter;
    private MatchHandler matchHandler;
    private List[] matchModels;
    private List<List> matchLists;
    private View contentView;
    //private Thread thread;
    private GroupAdapter adapter;
    private DecoratorModel decoratorModel;
    private RefreshRecyclerViewManager refreshRecyclerViewManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(contentView == null){
            contentView = inflater.inflate(R.layout.layout_subfragment_2_1,null);
            matchModels  = new ArrayList[10];
            matchLists = new ArrayList<>();
            matchHandler = new MatchHandler();
            matchPresenter = new MatchPresenter(matchModels,matchHandler);
            decoratorModel = new DecoratorModel(getResources(),matchLists,new ArrayList<String>());
            HoverListDecorator hoverListDecorator = new HoverListDecorator(decoratorModel);
            adapter = new LiveAdapter(matchLists,getActivity());
            refreshRecyclerViewManager = new RefreshRecyclerViewManager(getActivity(),contentView,matchPresenter,adapter);
            refreshRecyclerViewManager.addItemDecoration(hoverListDecorator);
            refreshRecyclerViewManager.addItemDecoration(new MyDecoration(getContext(),MyDecoration.VERTICAL_LIST));
           // thread = new Thread(matchPresenter);
            //thread.start();
            MyActivity.executorService.submit(matchPresenter);
        }
        return contentView;
    }
    private class MatchHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            ArrayList<String> titles = (ArrayList<String>) msg.getData().get("titles");
            for (int i = 0; i != titles.size(); i++) {
                if (i < matchLists.size()) {
                    matchLists.set(i, matchModels[i]);
                } else {
                    matchLists.add(matchModels[i]);
                }
            }
            int loc = titles.size();
            if (matchLists.size() > titles.size()) {
                while (loc < matchLists.size()) {
                    matchLists.remove(loc);
                }
            }
            decoratorModel.notifyDataSetChanged(titles);
            adapter.notifyDataSetChanged();
            refreshRecyclerViewManager.setRefreshing(false);
        }
            //ArrayList<MatchModel> matchModels = (ArrayList<MatchModel>) msg.getData().get("matchInfo");
            //String string = (String) msg.getData().get("html");
            //textView.setText(string);
    }
}
