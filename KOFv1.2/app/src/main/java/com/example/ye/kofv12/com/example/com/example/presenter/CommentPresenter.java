package com.example.ye.kofv12.com.example.com.example.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.ye.kofv12.com.example.model.CommentModel;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

import static com.example.ye.kofv12.com.example.com.example.presenter.VideoPresenter.TOP_COMMENT;

/**
 * Created by yechen on 2017/6/20.
 */

public class CommentPresenter implements Runnable {
    private OkHttpClient client;
    private Handler handler;
    private int arg;
    public static final int ALL_COMMENT = 0x00000055;
    public static final int TOP_COMMENT = 0x00000056;
    public static String CommentURL = "https://www.dongqiudi.com/article/";

    public CommentPresenter(Handler handler, int arg) {
        this.handler = handler;
        this.arg = arg;
    }

    public void requestData(int arg) {
        try {
            client = NetWorkConnection.getInstance();
            String requestURL = CommentURL + arg;
            String html = NetWorkConnection.get(client, requestURL);
            proceedComments(html);
            handler.sendEmptyMessage(SubFragment_1_1.REFRESH_COMPLETED);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void proceedComments(String html) {
        ArrayList<CommentModel> topComments = new ArrayList();
        ArrayList<CommentModel> allComments = new ArrayList();
        Message messageTop = new Message();
        Message messageAll = new Message();
        Bundle bundleTop = new Bundle();
        Bundle bundleAll = new Bundle();

        proceedCommentProcess(html, topComments, true);
        bundleTop.putParcelableArrayList("topComment", topComments);
        messageTop.setData(bundleTop);
        messageTop.what = TOP_COMMENT;
        handler.sendMessage(messageTop);

        proceedCommentProcess(html, allComments, false);
        bundleAll.putParcelableArrayList("allComment", allComments);
        messageAll.what = ALL_COMMENT;
        messageAll.setData(bundleAll);
        handler.sendMessage(messageAll);
    }

    private void proceedCommentProcess(String html, List comments, boolean isTop) {
        String beginTop = "top_comment";
        String beginAll = "all_comment";
        String beginHead = "img src=\"";
        String endHead = "\" class=\"head\"";
        String beginName = "<span class=\"name\">";
        String endName = "</span>";
        String beginTime = "<span class=\"time\">";
        String endTime = "</span>";
        String beginComment = "<p class=\"comCon\">";
        String endComment = "<div class=\"image-view text-center\">";
        String beginZan = "赞（";
        String endZan = "）</a>";
        String endAll = "<div class=\"loading\"><span class=\"load\"></span> 正在加载...</div>";
        int top = html.indexOf(beginTop);
        int all = html.indexOf(beginAll);
        Log.e("top", top + "");
        Log.e("all", all + "");
        String commentHtml;

        if (isTop) {
            if (top == -1)
                return;
            commentHtml = html.substring(top, all);
        } else {
            if (all == -1)
                return;
            commentHtml = html.substring(all, html.indexOf(endAll));
        }
        int commentStart;
        int headEndIndex;
        int nameEndIndex;
        int timeEndIndex;
        int contentIndex;
        int agreeIndex;
        commentStart = commentHtml.indexOf(beginHead);
        while (commentStart != -1) {
            headEndIndex = commentHtml.indexOf(endHead);
            String img_src = commentHtml.substring(commentStart + beginHead.length(), headEndIndex);
            commentHtml = commentHtml.substring(headEndIndex);
            //      Log.e("img",img_src);

            nameEndIndex = commentHtml.indexOf(endName);
            String name = commentHtml.substring(commentHtml.indexOf(beginName) + beginName.length(), nameEndIndex);
            commentHtml = commentHtml.substring(nameEndIndex + endName.length());
            //     Log.e("name",name);

            timeEndIndex = commentHtml.indexOf(endTime);
            String time = commentHtml.substring(commentHtml.indexOf(beginTime) + beginTime.length(), timeEndIndex);
            commentHtml = commentHtml.substring(timeEndIndex);
            //     Log.e("time",time);

            contentIndex = commentHtml.indexOf(endComment);
            String content = commentHtml.substring(commentHtml.indexOf(beginComment) + beginComment.length(), contentIndex);
            commentHtml = commentHtml.substring(contentIndex);
            content = content.replace("<br />", "\n");
            content = content.replace("    ", "");
            //       Log.e("content",content);

            agreeIndex = commentHtml.indexOf(endZan);
            String agreeNum = commentHtml.substring(commentHtml.indexOf(beginZan) + beginZan.length(), agreeIndex);
            commentHtml = commentHtml.substring(agreeIndex);
            //       Log.e("agreeNum",agreeNum);

            comments.add(new CommentModel(name, content, time, img_src, Integer.parseInt(agreeNum)));
            commentStart = commentHtml.indexOf(beginHead);
        }
    }

    @Override
    public void run() {
        requestData(arg);

    }
}
