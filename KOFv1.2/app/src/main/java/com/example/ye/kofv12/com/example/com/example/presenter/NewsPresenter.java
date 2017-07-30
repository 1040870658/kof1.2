package com.example.ye.kofv12.com.example.com.example.presenter;

import android.os.Handler;
import android.util.Log;

import com.example.ye.kofv12.com.example.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by ye on 2016/9/8.
 */
public class NewsPresenter {
    public static final int HOTNEWSFINISH = 0x00000011;
    public static final int NEWSFINSH = 0x00000012;
    public static final String MAINURL = "https://www.dongqiudi.com";
    public static final String NEWSURL = "https://www.dongqiudi.com/archives/";
    private String newsUrl = NEWSURL;
    private List<NewsModel> hotNews;
    private List<NewsModel> news;
    private WeakReference<Handler> handler;
    private OkHttpClient client;
    public NewsPresenter(List<NewsModel> hotNews,List<NewsModel> news,Handler handler){
        this.hotNews = hotNews;
        this.handler = new WeakReference<Handler>(handler);
        this.news = news;
    }
    public void setNewsurl(int archive){
        this.newsUrl = NEWSURL + archive;
    }
    public void receiveHotNews(){
        try {
           /* netWorkConnection = NetWorkConnection.getInstance(MAINURL,"GET");
            netWorkConnection.Connect();
            setHotNews(netWorkConnection.getResponseFromConn());*/
            client = NetWorkConnection.getInstance();
            setHotNews(NetWorkConnection.get(client,MAINURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void receiveNews(int page){
        try {
           /* netWorkConnection = NetWorkConnection.getInstance(newsUrl,"GET");
            netWorkConnection.setPostPoperty("page",page+"");
            netWorkConnection.Connect();
            setNews(netWorkConnection.getResponseFromConn());*/
            client = NetWorkConnection.getInstance();
            if(page == 1) {
                setNews(NetWorkConnection.get(client, newsUrl));
            }
            else{
                addNews(NetWorkConnection.get(client,newsUrl));
            }
            Log.e("newsurl",newsUrl);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void addNews(String response){

        int lastID;
        List<NewsModel> differList = new ArrayList<>();
        if(news != null && news.size() > 0){
            lastID = news.get(news.size() - 1).getId();
        }
        else{
            lastID = -1;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray data=jsonObject.getJSONArray("data");
            int id;
            int comment_total;
            for(int i = 0;i != data.length();i ++){
                JSONObject tmp = (JSONObject) data.get(i);
                NewsModel newsModel = new NewsModel();
                String address = "dongqiudi.com/article/"+tmp.get("id");
                String title = (String) tmp.get("title");
                String img = (String)tmp.get("thumb");
                String summary = "";
                if(tmp.isNull("description") != true)
                    summary = (String) tmp.get("description");
                id = (int) tmp.get("id");
                if(id == lastID){
                    continue;
                }
                comment_total = (int) tmp.get("comments_total");
                String display_time = (String) tmp.get("display_time");
                newsModel.setImage(img);
                newsModel.setTitle(title);
                newsModel.setAddress(address);
                newsModel.setSummary(summary);
                newsModel.setId(id);
                newsModel.setComment_total(comment_total);
                newsModel.setDisplay_time(display_time);
                differList.add(newsModel);
            }
            news.addAll(differList);
            if(handler.get() != null)
                handler.get().sendEmptyMessage(NEWSFINSH);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void setHotNews(String response){
        final String h3 = "<h3>";
        hotNews.clear();
        String address;
        String img;
        String title;
        String id;
        NewsModel tmp;
        String show = response.substring(response.indexOf("show"),response.indexOf("div id=\"cur\""));
        while(show.contains("<li>")){
            tmp = new NewsModel();
            show = show.substring(show.indexOf("<li>")+1);
            address = show.substring(show.indexOf("https"),show.indexOf("\" target"));
            img = show.substring(show.indexOf("http:"),show.indexOf("\" alt"));
            title = show.substring(show.indexOf(h3)+h3.length(),show.indexOf("</h3>"));
            show = show.substring(show.indexOf("/news/"));
            id = show.substring(show.indexOf("/news/")+"/news/".length(),show.indexOf("\">"));
            tmp.setAddress(address);
            tmp.setId(Integer.valueOf(id));
            tmp.setTitle(title);
            tmp.setImage(img);
            hotNews.add(tmp);
        }
        if(hotNews != null && hotNews.size() != 0 && handler.get() != null)
             handler.get().sendEmptyMessage(HOTNEWSFINISH);
    }

    private void setNews(String response) {
        int firstID;
        List<NewsModel> differList = new ArrayList<>();
        if(news != null && news.size() > 0){
            firstID = news.get(0).getId();
        }
        else{
            firstID = -1;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray data=jsonObject.getJSONArray("data");
            int id;
            int comment_total;
            for(int i = 0;i != data.length();i ++){
                JSONObject tmp = (JSONObject) data.get(i);
                NewsModel newsModel = new NewsModel();
                String address = "dongqiudi.com/article/"+tmp.get("id");
                String title = (String) tmp.get("title");
                String img = (String)tmp.get("thumb");
                String summary = "";
                if(tmp.isNull("description") != true)
                    summary = (String) tmp.get("description");
                id = (int) tmp.get("id");
                if(id == firstID){
                    break;
                }
                comment_total = (int) tmp.get("comments_total");
                String display_time = (String) tmp.get("display_time");
                newsModel.setImage(img);
                newsModel.setTitle(title);
                newsModel.setAddress(address);
                newsModel.setSummary(summary);
                newsModel.setId(id);
                newsModel.setComment_total(comment_total);
                newsModel.setDisplay_time(display_time);
                differList.add(newsModel);
            }
            news.addAll(0,differList);
            if(handler.get() != null)
                handler.get().sendEmptyMessage(NEWSFINSH);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
