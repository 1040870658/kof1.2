package com.example.ye.kofv12;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ye.kofv12.aidl.INewsPoll;
import com.example.ye.kofv12.com.example.com.example.presenter.NewsPresenter;
import com.example.ye.kofv12.com.example.com.example.util.RetrieveNews;
import com.example.ye.kofv12.com.example.model.NewsModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

/**
 * Created by yechen on 2017/1/24.
 */

public class MainService extends Service{

    private NewsPresenter newsPresenter;
    private RetrieveNews retrieveNews;
    int size;
    public static final int POLLING_GAP = 1000*60*5 ;
    public static final int LONG_POLLING = POLLING_GAP * 2;
    private Thread scheduler ;
    private List<NewsModel> newsModels = new CopyOnWriteArrayList<>();
    private List<NewsModel> hotNewsModels = new CopyOnWriteArrayList<>();
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Binder mBinder ;
    private Boolean state;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NewsPresenter.HOTNEWSFINISH:
                    break;
                case NewsPresenter.NEWSFINSH:
                    if(size != newsModels.size() && newsModels.size() != 0 ){
                        if(state == true) {
                            size = newsModels.size();
                            Log.e("news_service",size+"");
                            NewsModel tmp = newsModels.get(0);
                            Intent intent = new Intent(MainService.this,MyActivity.class);
                            intent.putExtra("news",newsModels.get(0));
                            PendingIntent pendingIntent = PendingIntent.getActivity(MainService.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            builder.setContentTitle(tmp.getTitle());
                            builder.setContentText(tmp.getSummary());
                            builder.setWhen(System.currentTimeMillis());
                            Notification notification = builder.build();
                            notification.contentIntent = pendingIntent;
                            notificationManager.notify(1, notification);
                        }
                    }
                    break;
                default:
                    ;
            }
        }
    };

    public NewsPresenter getNewsPresenter(){
        return newsPresenter;
    }
    public void modifyState(boolean state){
        this.state = state;
    }
    public boolean getState(){
        return state;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        state = false;
        newsPresenter = new NewsPresenter(hotNewsModels,newsModels,handler);
        newsPresenter.setNewsurl(1);
        mBinder = new NewsServiceBinder(this);
        retrieveNews = new RetrieveNews(newsPresenter,1,false);
        scheduler = new ScheduleThread(this);
       // scheduler.scheduleAtFixedRate(retrieveNews,POLLING_GAP, POLLING_GAP*10,TimeUnit.MILLISECONDS);
        scheduler.start();
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon_launcher);
        builder.setAutoCancel(true);
    //    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.my_ic_launcher));
    }
    public void setNewsList(List<NewsModel> newsModels){
        this.newsModels = newsModels;
    }
    public RetrieveNews getRetrieveNews(){
        return retrieveNews;
    }
    private class ScheduleThread extends Thread{
        private WeakReference<MainService> service;
        public ScheduleThread(MainService service){
            this.service = new WeakReference<MainService>(service);
        }

        @Override
        public void run() {
            super.run();
            while (true){
                if(service.get() != null && service.get().getState()){
                    service.get().getRetrieveNews().run();
                    try {
                        Thread.sleep(POLLING_GAP);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                else{
                    if(service.get() == null)
                        return;
                    service.get().getRetrieveNews().run();
                    try {
                        Thread.sleep(LONG_POLLING);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }
    private class NewsServiceBinder extends INewsPoll.Stub{

        private WeakReference<MainService> reference ;
        public NewsServiceBinder(MainService service){

            reference = new WeakReference<MainService>(service);
        }
        @Override
        public List<NewsModel> getNewsList() throws RemoteException {
            return newsModels;
        }

        @Override
        public NewsModel getNews(int id) throws RemoteException{
            for(NewsModel newsModel : newsModels){
                if(newsModel.getId() == id){
                    return newsModel;
                }
            }
            return null;
        }
        @Override
        public void registerListener() throws RemoteException {

        }

        @Override
        public void modifyState(boolean state) throws RemoteException {
            if(reference != null)
                reference.get().modifyState(state);
        }

        @Override
        public void setNewsList(List<NewsModel> newsModels){
            if(reference != null)
                reference.get().setNewsList(newsModels);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if(mBinder == null){
            Log.e("mainservice","bind failed");
        }
        return mBinder;
    }
}
