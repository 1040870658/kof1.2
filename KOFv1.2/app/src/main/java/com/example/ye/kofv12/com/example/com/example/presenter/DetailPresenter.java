package com.example.ye.kofv12.com.example.com.example.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;

/**
 * Created by yechen on 2017/7/30.
 */

public class DetailPresenter implements Runnable{

    public static final int REQUEST_FINISHED = 0x00000080;
    public WeakReference<Handler> handlerWeakReference;
    private OkHttpClient client;
    private String url;
    private String html;
    private String historyUrl;
    public DetailPresenter(Handler handler,String url){
        this.handlerWeakReference = new WeakReference<Handler>(handler);
        this.url = url;
        this.historyUrl = null;
    }
    public DetailPresenter(Handler handler,String url,String historyUrl){
        this.handlerWeakReference = new WeakReference<Handler>(handler);
        this.url = url;
        this.historyUrl = historyUrl;
    }
    public DetailPresenter(Handler handler){
        this.handlerWeakReference = new WeakReference<Handler>(handler);
    }
    public void setUrl(String url){
        this.url = url;
    }
    private void requestData(){
        try {
            client = NetWorkConnection.getInstance();
            html = NetWorkConnection.get(client,url);
            modelProceeder(html);
            if(handlerWeakReference != null){
                Bundle bundle = new Bundle();
                bundle.putString("html",html);
                bundle.putString("history",historyUrl);
                Message message = new Message();
                message.setData(bundle);
                message.what=REQUEST_FINISHED;
                handlerWeakReference.get().sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  String getHistory(){
        return historyUrl;
    }
    public String getUrl(){
        return url;
    }
    public void setHtml(String html) {
        this.html = html;
    }

    public void setHistoryUrl(String historyUrl) {
        this.historyUrl = historyUrl;
    }

    private void modelProceeder(String html){
        String loadingClass = "loading";
        String commentID = "recommend";
        String aTag = "a";
        Document document = Jsoup.parse(html);
        Elements loading = document.getElementsByClass(loadingClass);
        Elements as = document.getElementsByTag(aTag);
        for(Element a : as){
            String data_link = a.attr("data-link");
            String href = a.attr("href");
            if(TextUtils.isEmpty(data_link) && TextUtils.isEmpty(href))
                a.attr("data-link","dongqiudi://null");
        }
        if (loading != null && !loading.isEmpty() && loading.get(0) != null){
            loading.get(0).remove();
        }
        this.html = document.html();
    }
    @Override
    public void run() {
        requestData();
    }
}
