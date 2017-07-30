package com.example.ye.kofv12.com.example.com.example;

import android.app.Activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.ye.kofv12.MyActivity;
import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.adapter.CommentAdapter;
import com.example.ye.kofv12.com.example.com.example.presenter.VideoPresenter;
import com.example.ye.kofv12.com.example.com.example.util.HoverListDecorator;
import com.example.ye.kofv12.com.example.com.example.util.ImageDecoder;
import com.example.ye.kofv12.com.example.com.example.util.MyDecoration;
import com.example.ye.kofv12.com.example.com.example.view.RefreshRecyclerViewManager;
import com.example.ye.kofv12.com.example.model.CommentModel;
import com.example.ye.kofv12.com.example.model.DecoratorModel;
import com.example.ye.kofv12.com.example.model.NewsModel;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yechen on 2017/6/13.
 */

public class VideoActivity extends Activity {
    public static int VIDEO_HEIGHT_SAMPLE = 3;
    private VideoView videoView;
    private NewsModel newsModel;
    private VideoPresenter videoPresenter;
    private List<CommentModel> commentModels;
    private VideoHandler videoHandler;
    private ImageView cover;
    private BitmapDrawable background;
    private DisplayMetrics metrics;
    private Future future;
    private LinearLayout.LayoutParams full_param;
    private LinearLayout.LayoutParams part_param;
    private RelativeLayout videoContainer;
    //private ExecutorService excecutors;
    private MyMediaController mediaController;
    private RelativeLayout rlCover;
    private String url = "https://www.dongqiudi.com/article/";
    private RefreshRecyclerViewManager refreshRecyclerViewManager;
    private CommentAdapter commentAdapter;
    private List<List> commentData = new ArrayList();
    private ArrayList<CommentModel> topComments ;
    private ArrayList<CommentModel> allComments ;
    private DecoratorModel decoratorModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //excecutors =  Executors.newFixedThreadPool(2);
        newsModel = getIntent().getParcelableExtra("news");
        commentModels = new ArrayList<>();
        videoHandler = new VideoHandler();
        videoPresenter = new VideoPresenter(commentModels,videoHandler, newsModel.getId());
        initVideo();
        initComments();
        MyActivity.executorService.submit(videoPresenter);
    }
    private void initComments(){
        topComments = new ArrayList<CommentModel>();
        allComments = new ArrayList<CommentModel>();
        List<String> titles = new ArrayList<String>();
        titles.add(getResources().getString(R.string.hover_hot_conmments));
        titles.add(getResources().getString(R.string.hover_all_comments));
        commentData.add(topComments);
        commentData.add(allComments);
        commentAdapter = new CommentAdapter(commentData,this);
        decoratorModel = new DecoratorModel(getResources(),commentData,titles);
        refreshRecyclerViewManager = new RefreshRecyclerViewManager(this,videoPresenter,commentAdapter);
        refreshRecyclerViewManager.addItemDecoration(new MyDecoration(this,MyDecoration.VERTICAL_LIST));
        refreshRecyclerViewManager.addItemDecoration(new HoverListDecorator(decoratorModel));
    }
    private void initVideo(){
        videoContainer = (RelativeLayout) findViewById(R.id.rl_video);
        full_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        part_param = (LinearLayout.LayoutParams) videoContainer.getLayoutParams();
        part_param.height = metrics.heightPixels / 3;
        cover = (ImageView) findViewById(R.id.iv_cover);
       // commentsView = (RecyclerView) findViewById(R.id.rcv_player_comment);
        videoView = (VideoView) findViewById(R.id.vv_player);
        rlCover = (RelativeLayout) findViewById(R.id.rl_cover);
        mediaController = new MyMediaController(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        mediaController.addView(getLayoutInflater().inflate(R.layout.mediacontroller_full,null),params);
        videoView.setMediaController(mediaController);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoView.isPlaying()) {
                    videoView.pause();
                    cover.setVisibility(View.VISIBLE);
                    videoView.setBackgroundDrawable(background);
                }
                else if(videoView.isPlaying() == false){
                    videoView.start();
                    videoView.resume();
                    cover.setVisibility(View.INVISIBLE);
                    videoView.setBackgroundDrawable(null);
                }
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.seekTo(0);
                cover.setVisibility(View.VISIBLE);
            }
        });
    }
    private class VideoHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VideoPresenter.VIDEO_SOURCE:
                    Bundle bundle = msg.getData();
                    String url = (String) bundle.get("url");
                    Uri uri = Uri.parse(url);
                    videoView.setVideoPath(uri.toString());
                    rlCover.setVisibility(View.GONE);
                    cover.setVisibility(View.VISIBLE);
                    if(background == null) {
                        future = MyActivity.executorService.submit(new ImageDecoder(metrics, (String) bundle.get("thumb"),videoHandler));
                    }
                    else{
                        videoView.setBackgroundDrawable(background);
                    }
                    break;
                case VideoPresenter.VIDEO_BACK:
                    try {
                            background = new BitmapDrawable(getResources(), (Bitmap) future.get());
                            videoView.setBackgroundDrawable(background);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    break;
                case VideoPresenter.TOP_COMMENT:
                    decoratorModel.getDatas().set(0,(ArrayList<CommentModel>) msg.getData().get("topComment"));
                    decoratorModel.notifyDataSetChanged();
                    refreshRecyclerViewManager.notifyDataSetChanged();
                    break;
                case VideoPresenter.ALL_COMMENT:
                    decoratorModel.getDatas().set(1,(ArrayList<CommentModel>) msg.getData().get("allComment"));
                    decoratorModel.notifyDataSetChanged();
                    refreshRecyclerViewManager.notifyDataSetChanged();
                    break;
                case SubFragment_1_1.REFRESH_COMPLETED:
                    refreshRecyclerViewManager.setRefreshing(false);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.suspend();
      //  excecutors.shutdown();
    }
    private class  MyMediaController extends MediaController{
        public MyMediaController(Context context) {
            super(context);
        }

        @Override
        public void setAnchorView(View view) {
            super.setAnchorView(view);
            ImageView fullScreenController = (ImageView) LayoutInflater.from(VideoActivity.this).
                    inflate(R.layout.mediacontroller_full,null);
            fullScreenController.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                }
            });
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
          //  params.height = 10;
            params.gravity = Gravity.RIGHT|Gravity.TOP;
            addView(fullScreenController,params);

        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            videoContainer.setLayoutParams(full_param);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
            videoContainer.setLayoutParams(part_param);
        }
        else{}
    }
}
