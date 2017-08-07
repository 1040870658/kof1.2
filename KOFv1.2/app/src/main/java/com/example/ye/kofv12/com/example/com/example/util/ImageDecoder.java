package com.example.ye.kofv12.com.example.com.example.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.ye.kofv12.VideoActivity;
import com.example.ye.kofv12.com.example.com.example.presenter.VideoPresenter;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

/**
 * Created by yechen on 2017/6/13.
 */

public class ImageDecoder implements Callable{
    private URL url;
    private DisplayMetrics metrics;
    private URLConnection urlConnection;
    private Handler handler;
    public ImageDecoder(DisplayMetrics metrics, String imgUrl, Handler handler) {
        try {
            this.url = new URL(imgUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.metrics = metrics;
        this.handler = handler;
    }

    @Override
    public Object call() throws Exception {
        Log.e("imageDecoder",url.toString());
        urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        is.close();
        handler.sendEmptyMessage(VideoPresenter.VIDEO_BACK);
        return bitmap;
    }
    private int CalculateSample(Bitmap bitmap){
        int height = metrics.heightPixels /VideoActivity.VIDEO_HEIGHT_SAMPLE;
        int width = metrics.widthPixels;
        int sample = 1;
        if(bitmap.getHeight() > height && bitmap.getWidth() > width) {
            sample = min( bitmap.getHeight() / height, bitmap.getWidth() / width);
        }
        return sample;
    }
    private int min(int a, int b){
        if (a < b)
            return  a;
        return b;
    }
}
