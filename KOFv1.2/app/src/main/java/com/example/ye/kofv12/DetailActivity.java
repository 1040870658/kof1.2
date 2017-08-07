package com.example.ye.kofv12;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.ye.kofv12.aidl.INewsPoll;
import com.example.ye.kofv12.com.example.com.example.presenter.DetailPresenter;
import com.example.ye.kofv12.com.example.model.NewsModel;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yechen on 2017/5/7.
 */

public class DetailActivity extends Activity{
    private  final int COMMENT_READY = 0x00007001;
    private WebView webView;
    private NewsModel news;
    private Handler delayHandler;
    private TextView textView;
    private ProgressBar progressBar;
    private DetailPresenter detailPresenter;
    private ViewGroup rootLayout;
    private NewsConnector newsConnector;
    private INewsPoll mbinder;
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    private String URL = "https://www.dongqiudi.com/article/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rootLayout = (ViewGroup) findViewById(R.id.rl_detail_root);
        textView = (TextView) findViewById(R.id.tv_html);

        delayHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case COMMENT_READY:
                        if(news != null)
                            customizeActionBar(news.getComment_total());
                        break;
                    case DetailPresenter.REQUEST_FINISHED:
                        webView.loadDataWithBaseURL(null,msg.getData().getString("html"),"text/html;",
                                "UTF-8",detailPresenter.getHistory());
                        //detailPresenter.setHistoryUrl(detailPresenter.getUrl());
                        break;
                    case 0:
                        textView.setText(msg.getData().getString("html"));
                        webView.setVisibility(View.INVISIBLE);
                        break;
                        default:
                            webView.setVisibility(View.VISIBLE);
                }
            }
        };
        webView = (WebView) findViewById(R.id.wv_detail);
        news = getIntent().getExtras().getParcelable("news");

        customizeActionBar(news.getComment_total());
        textView.setMovementMethod(new ScrollingMovementMethod());
        progressBar = (ProgressBar) findViewById(R.id.pb_detail);
       // webView.loadUrl(url+news.getId());
       // webView.addJavascriptInterface(new HtmlJsHandler(),"showHtml");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
            webView.getSettings().setLoadsImagesAutomatically(true);
        }
        webView.setWebViewClient(new CusWebViewClient());
        webView.setWebChromeClient(new CusWebChromeClient());

        Intent intent = new Intent(this,MainService.class);
        newsConnector = new NewsConnector();
        bindService(intent,newsConnector,BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        detailPresenter = new DetailPresenter(delayHandler,URL+news.getId()+".html");
        fixedThreadPool.submit(detailPresenter);
        super.onResume();
    }

    private class NewsConnector implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mbinder = INewsPoll.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    private class CusWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                // 网页加载完成
                progressBar.setVisibility(View.GONE);
            } else {
                // 加载中
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        }

    }
    private class CusWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            int newsTarget = url.indexOf("news");
            if(newsTarget < 0)
                return true;
            String sep = url.substring(newsTarget);
            sep = sep.substring(sep.indexOf("/")+1);
            for(int i = 0;i < sep.length();i ++)
                if(sep.charAt(i) > '9' || sep.charAt(i) < '0')
                    return true;
            detailPresenter.setUrl(URL+sep+".html");
            fixedThreadPool.submit(new RewriteComment(Integer.parseInt(sep)));
            fixedThreadPool.submit(detailPresenter);
            return true;
        }

    }

    private class RewriteComment implements Runnable{

        private int id;
        public RewriteComment(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            if(mbinder != null){
                try {
                    news = mbinder.getNews(id);
                    delayHandler.sendEmptyMessage(COMMENT_READY);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class HtmlJsHandler{
        @JavascriptInterface
        public void showHtml(String html){
            Log.e("html",html);
           /* Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("html",html);
            message.what = 0;
            message.setData(bundle);
            delayHandler.sendMessage(message);*/
            new Thread(new DetailPresenter(delayHandler,html)).start();
            //textView.setText(html);
           // webView.setVisibility(View.INVISIBLE);
        }
    }
    void customizeActionBar(int numOfComment) {
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.detail_actionbar_style, null);
        WeakReference<TextView> textViewWeakReference =
                new WeakReference<TextView>((TextView)actionbarLayout.findViewById(R.id.tv_comment));
        textViewWeakReference.get().setText(numOfComment+"评论");
        getActionBar().setCustomView(actionbarLayout);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
            return;
        }
        rootLayout.removeView(webView);
        webView.destroy();
        unbindService(newsConnector);
        finish();
    }

    public void Enter(View view){
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("news",news);
        startActivity(intent);
    }
}
