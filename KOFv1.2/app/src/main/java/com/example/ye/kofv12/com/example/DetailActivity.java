package com.example.ye.kofv12.com.example;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ye.kofv12.CommentActivity;
import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.presenter.DetailPresenter;
import com.example.ye.kofv12.com.example.model.NewsModel;

import java.lang.ref.WeakReference;

/**
 * Created by yechen on 2017/5/7.
 */

public class DetailActivity extends Activity{
    private WebView webView;
    private NewsModel news;
    private Handler delayHandler;
    private TextView textView;
    private ProgressBar progressBar;
    private DetailPresenter detailPresenter;
    private String url = "https://www.dongqiudi.com/article/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView = (TextView) findViewById(R.id.tv_html);
        delayHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){

                    case DetailPresenter.REQUEST_FINISHED:
                        webView.loadData(msg.getData().getString("html"),"text/html","UTF-8");
                        webView.setVisibility(View.VISIBLE);
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
        news = getIntent().getExtras().getParcelable("news");
        customizeActionBar();
        textView.setMovementMethod(new ScrollingMovementMethod());
        progressBar = (ProgressBar) findViewById(R.id.pb_detail);
        webView = (WebView) findViewById(R.id.wv_detail);
        webView.loadUrl(url+news.getId());
       // webView.addJavascriptInterface(new HtmlJsHandler(),"showHtml");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String filter = "javascript:function hide(){"+
                        "var nav = document.getElementsByTagName('nav')[0];"+
                        "document.getElementsByTagName('body')[0].removeChild(nav);"+
                        "var links = document.getElementsByTagName('a');"+
                        "links[0].remove();" +
                        "links[links.length -1 ].remove();"+
                        "nav.remove();"+
                        "var footer = document.getElementById('footer');"+
                        "var header = document.getElementById('header');"+
                        "var btnOpen = document.getElementById('btn_open');"+
                        "var r_btnOpen = btnOpen.parentNode;"+
                        //"var vote = document.getElementById('voteTemplate');"+
                        //"vote.remove();"+
                        "r_btnOpen.remove();"+
                        "header.remove();"+
                        "footer.remove();"+
                        //"var btns = document.getElementsByClassName('btn');"+
                        //"for(var r_btn in btns){"+
                        //"r_btn.parentNode.remove();}"+
                        "window.scrollBy(0,-119)"+
                        "}";
              //  view.loadUrl("javascript:window.showHtml.showHtml('<head>'+" +
                //        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                //创建方法
               view.loadUrl(filter);
                view.loadUrl("javascript:hide();");
                view.scrollBy(0,120);
                delayHandler.sendEmptyMessageDelayed(1,10);
                progressBar.setVisibility(View.GONE);
                //加载方法
            //    view.loadUrl("javascript:hideOther();");
            }
        });
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
    void customizeActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.detail_actionbar_style, null);
        WeakReference<TextView> textViewWeakReference =
                new WeakReference<TextView>((TextView)actionbarLayout.findViewById(R.id.tv_comment));
        textViewWeakReference.get().setText(news.getComment_total()+"评论");
        getActionBar().setCustomView(actionbarLayout);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
            return;
        }
        webView.destroy();
        finish();
    }

    public void Enter(View view){
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("news",news);
        startActivity(intent);
    }
}
