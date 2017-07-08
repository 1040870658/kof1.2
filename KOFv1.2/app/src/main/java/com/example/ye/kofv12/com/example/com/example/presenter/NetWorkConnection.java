package com.example.ye.kofv12.com.example.com.example.presenter;

import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yechen on 2017/1/23.
 */

public class NetWorkConnection {
    private static OkHttpClient client;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private NetWorkConnection(){
    };
    public static OkHttpClient getInstance() throws IOException{
        Object lock = new Object();
        synchronized (lock) {
            if (client == null) {
                synchronized (lock) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(2, TimeUnit.MINUTES)
                            .readTimeout(2, TimeUnit.MINUTES)
                            .writeTimeout(2, TimeUnit.MINUTES);
                    client = builder.build();
                }
            }
        }
        return client;
    }

    public static String get(OkHttpClient client,String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.e("get method", url);
        Response response = client.newCall(request).execute();
        return response.body().string();

    }
    public static String post(OkHttpClient client,String url,String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /*
    private HttpURLConnection conn;
    private URL url;
    private LinkedHashMap conn_cache;
    public static NetWorkConnection getInstance(String urlString,String method) throws IOException{

        if(netWorkConnection == null){
            netWorkConnection = new NetWorkConnection();
        }
        netWorkConnection.setUrl(urlString,method);
        return netWorkConnection;
    }
    public void setUrl(String urlString) throws IOException{
       // this.conn = hitCache(urlString);
     //   if(conn == null) {
            this.url = new URL(urlString);
            this.conn = (HttpURLConnection) url.openConnection();
            this.conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
            conn_cache.put(urlString,conn);
     //   }
    }
    public void setUrl(String urlString,String method) throws IOException{
     //   this.conn = hitCache(urlString);
      //  if(conn == null) {
            this.url = new URL(urlString);
            this.conn = (HttpURLConnection) url.openConnection();
            this.conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
            conn_cache.put(urlString,conn);
        //}
        conn.setRequestMethod(method);
    }


    public void setMethod(String method)throws IOException{
        conn.setRequestMethod(method);
    }
    public void Connect() throws IOException {
        conn.connect();
    }
    public void disConnect(){
        conn.disconnect();
    }
    public void setPostPoperty(String key,String value){
        conn.setRequestProperty(key,value);
    }
    public String getResponseFromConn()throws IOException{
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine())!=null){
            sb.append(line);
        }
        is.close();
        isr.close();
        br.close();
        netWorkConnection.disConnect();
        return new String(sb);
    }
    @Nullable
    private HttpURLConnection hitCache(String url)throws IOException{
        Iterator iterator = conn_cache.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            if (entry.getKey().equals(url)) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) entry.getValue();
                httpURLConnection.disconnect();
                return httpURLConnection;
            }
        }
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        Log.e("network","destroyed");
        conn.disconnect();
        super.finalize();
    }*/

}
