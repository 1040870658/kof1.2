package com.example.ye.kofv12;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ye.kofv12.com.example.StartReceiver;
import com.example.ye.kofv12.com.example.com.example.util.SplashScreen;
import com.example.ye.kofv12.com.example.fragments.FragmentMain_1;
import com.example.ye.kofv12.com.example.fragments.FragmentMain_2;
import com.example.ye.kofv12.com.example.fragments.FragmentMain_3;
import com.example.ye.kofv12.com.example.fragments.*;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_2;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_3;


public class MyActivity extends FragmentActivity {

    private Thread netWorkThread;
    private DrawerLayout drawerLayout;
    private LinearLayout drawerSetting;
    private FragmentTabHost mainContent;
    private int[] tablayouts = new int[]{
            R.layout.tablayout_m1,
            R.layout.tablayout_m2,
            R.layout.tablayout_m3,
            R.layout.tablayout_m4
    };
    private StartReceiver receiver;
    public static final int CONNETCTING = 0x10000001;
    public static final int DISCONNECTING = 0x10000000;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DISCONNECTING:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
                    builder.setTitle("确认手机网络连接后重新打开程序");
                    builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int which) {
                            System.exit(0);
                        }
                    });
                    builder.show().setCancelable(false);
                    break;
                case CONNETCTING:
                    break;
                default:break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        SplashScreen splashScreen = new SplashScreen(this,R.style.mydialog,R.layout.layout_splash);
        splashScreen.show();
        netWorkThread = new Thread(new NetworkAvailableMannager(this,handler));
        netWorkThread.start();
        customizeActionBar();
        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
    }

    private void initView(){
        drawerLayout = (DrawerLayout)findViewById(R.id.main_container);
        drawerSetting = (LinearLayout)findViewById(R.id.main_drawer);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mainContent = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mainContent.setup(this,getSupportFragmentManager(),R.id.realcontent);
        mainContent.addTab(mainContent.newTabSpec("m0")
                .setIndicator(getLayoutInflater().inflate(tablayouts[0],null)),FragmentMain_1.class,null);
        mainContent.addTab(mainContent.newTabSpec("m1")
                .setIndicator(getLayoutInflater().inflate(tablayouts[1],null)),FragmentMain_2.class,null);
        mainContent.addTab(mainContent.newTabSpec("m2")
                .setIndicator(getLayoutInflater().inflate(tablayouts[2],null)),FragmentMain_3.class,null);
        mainContent.addTab(mainContent.newTabSpec("m3")
                .setIndicator(getLayoutInflater().inflate(tablayouts[3],null)),FragmentMain_4.class,null);
    }
    public void Switch(View view){

        if(drawerLayout.isDrawerOpen(drawerSetting)){
            drawerLayout.closeDrawer(drawerSetting);
        }
        else{
            drawerLayout.openDrawer(drawerSetting);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class NetworkAvailableMannager implements Runnable {
        private Context context;
        private Handler handler;
        public NetworkAvailableMannager(Context context,Handler handler){
            this.handler = handler;
            this.context = context;
        }
        @Override
        public void run() {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 当前网络是连接的
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        // 当前所连接的网络可用
                        handler.sendEmptyMessage(MyActivity.CONNETCTING);
                        return;
                    }
                }
            }
            handler.sendEmptyMessage(MyActivity.DISCONNECTING);
        }

    }

    void customizeActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.actionbar_style, null);
        getActionBar().setCustomView(actionbarLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
