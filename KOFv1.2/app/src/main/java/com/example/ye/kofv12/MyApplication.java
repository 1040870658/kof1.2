package com.example.ye.kofv12;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * Created by yechen on 2017/1/21.
 */


public class MyApplication extends Application{
    //private RequestQueue mRequest;
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    //    mRequest = Volley.newRequestQueue(this);
    }
  /*  public RequestQueue getmRequestQueue(){
        if(mRequest == null)
            mRequest = Volley.newRequestQueue(this);
        return mRequest;
    }*/
}
