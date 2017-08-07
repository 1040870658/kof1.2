package com.example.ye.kofv12.com.example;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yechen on 2017/7/11.
 */

public class StartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_TIME_TICK)){
            isAlive(context,"com.example.MyActivity");
        }
    }
    public static boolean isAlive(Context context,String className){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningTaskInfo> processes = (ArrayList) activityManager.getRunningTasks(Integer.MAX_VALUE);
        for(ActivityManager.RunningTaskInfo activity : processes){
            Log.e("running",activity.baseActivity.toString());
        }
        Log.e("received",context.getClass().toString());
        return  true;
    }
}
