package com.example.ye.kofv12;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ye.kofv12.com.example.com.example.adapter.CommentAdapter;
import com.example.ye.kofv12.com.example.com.example.presenter.CommentPresenter;
import com.example.ye.kofv12.com.example.com.example.util.HoverListDecorator;
import com.example.ye.kofv12.com.example.com.example.util.MyDecoration;
import com.example.ye.kofv12.com.example.com.example.view.RefreshRecyclerViewManager;
import com.example.ye.kofv12.com.example.model.CommentModel;
import com.example.ye.kofv12.com.example.model.DecoratorModel;
import com.example.ye.kofv12.com.example.model.NewsModel;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yechen on 2017/6/20.
 */

public class CommentActivity extends Activity {
    private RefreshRecyclerViewManager refreshRecyclerViewManager;
    private List<List> commentData = new ArrayList();
    private List titles;
    private CommentAdapter commentAdapter;
    private NewsModel newsModel;
    private CommentHandler commentHandler;
    private CommentPresenter commentPresenter;
    private Thread thread;
    private ProgressBar progressBar;
    private DecoratorModel decoratorModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment_activity);
        customizeActionBar();
        titles = new ArrayList();
        titles.add(getResources().getString(R.string.hover_hot_conmments));
        titles.add(getResources().getString(R.string.hover_all_comments));
        commentData.add(new ArrayList());
        commentData.add(new ArrayList());
        newsModel = getIntent().getParcelableExtra("news");
        commentAdapter = new CommentAdapter(commentData,this);
        progressBar = (ProgressBar) findViewById(R.id.pb_comment);
        commentHandler = new CommentHandler();
        decoratorModel = new DecoratorModel(getResources(),commentData,titles);
        commentPresenter = new CommentPresenter(commentHandler,newsModel.getId());
        refreshRecyclerViewManager = new RefreshRecyclerViewManager(this,commentPresenter,commentAdapter);
        refreshRecyclerViewManager.addItemDecoration(new MyDecoration(this,MyDecoration.VERTICAL_LIST));
        refreshRecyclerViewManager.addItemDecoration(new HoverListDecorator(decoratorModel));
        thread = new Thread(commentPresenter);
        thread.start();
    }
    private class CommentHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CommentPresenter.ALL_COMMENT:
                    decoratorModel.getDatas().set(1,(ArrayList<CommentModel>) msg.getData().get("allComment"));
                    decoratorModel.notifyDataSetChanged();
                    refreshRecyclerViewManager.notifyDataSetChanged();
                    break;
                case CommentPresenter.TOP_COMMENT:
                    decoratorModel.getDatas().set(0,(ArrayList<CommentModel>) msg.getData().get("topComment"));
                    decoratorModel.notifyDataSetChanged();
                    refreshRecyclerViewManager.notifyDataSetChanged();
                    break;
                case SubFragment_1_1.REFRESH_COMPLETED:
                    refreshRecyclerViewManager.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    }
    void customizeActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.detail_actionbar_style, null);
        WeakReference<TextView> textViewWeakReference =
                new WeakReference<TextView>((TextView)actionbarLayout.findViewById(R.id.tv_comment));
        textViewWeakReference.get().setVisibility(View.GONE);
        getActionBar().setCustomView(actionbarLayout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
