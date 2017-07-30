package com.example.ye.kofv12.com.example.com.example.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.ye.kofv12.com.example.model.CommentModel;
import com.example.ye.kofv12.com.example.subfragments.SubFragment_1_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by yechen on 2017/6/13.
 */

public class VideoPresenter implements Runnable {
    public static final int STAKE_HOLDER = 0x00000040;
    public static final int VIDEO_SOURCE = 0x00000041;
    public static final int FINISHED_INIT = 0x00000042;
    public static final int VIDEO_BACK = 0x00000043;
    public static final int TOP_COMMENT = 0x00000044;
    public static final int ALL_COMMENT = 0x00000045;
    public static final String VideoURL = "https://www.dongqiudi.com/article/";
    private OkHttpClient client;
    private boolean videoLoaded = false;
    private Handler handler;
    private List<CommentModel> commentModels;
    private int arg;

    public VideoPresenter(List<CommentModel> commentModels, Handler handler, int arg) {
        this.commentModels = commentModels;
        this.handler = handler;
        this.arg = arg;
    }

    public void reloadVideo() {
        videoLoaded = false;
    }

    public void requestData(int arg) {
        try {
            client = NetWorkConnection.getInstance();
            String requestURL = VideoURL + arg;
            String html = NetWorkConnection.get(client, requestURL);
            if (videoLoaded == false)
                proceedVideo(html);
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

            nameEndIndex = commentHtml.indexOf(endName);
            String name = commentHtml.substring(commentHtml.indexOf(beginName) + beginName.length(), nameEndIndex);
            commentHtml = commentHtml.substring(nameEndIndex + endName.length());

            timeEndIndex = commentHtml.indexOf(endTime);
            String time = commentHtml.substring(commentHtml.indexOf(beginTime) + beginTime.length(), timeEndIndex);
            commentHtml = commentHtml.substring(timeEndIndex);

            contentIndex = commentHtml.indexOf(endComment);
            String content = commentHtml.substring(commentHtml.indexOf(beginComment) + beginComment.length(), contentIndex);
            commentHtml = commentHtml.substring(contentIndex);
            content = content.replace("<br />", "\n");
            content = content.replace("    ", "");

            agreeIndex = commentHtml.indexOf(endZan);
            String agreeNum = commentHtml.substring(commentHtml.indexOf(beginZan) + beginZan.length(), agreeIndex);
            commentHtml = commentHtml.substring(agreeIndex);

            comments.add(new CommentModel(name, content, time, img_src, Integer.parseInt(agreeNum)));
            commentStart = commentHtml.indexOf(beginHead);

        }
    }

    private void proceedVideo(String html) {
        String begin = "div class=\"video\"";
        String srcBegin = "src=\"";
        String srcEnd = "\" title=";
        String thumbBegin = "thumb=\"";
        String thumbEnd = "\"  hash=\"";
        html = html.substring(html.indexOf(begin));
        String url = html.substring(html.indexOf(srcBegin) + srcBegin.length(), html.indexOf(srcEnd));
        String thumb = html.substring(html.indexOf(thumbBegin) + thumbBegin.length(), html.indexOf(thumbEnd));
        Bundle bundle = new Bundle();
        bundle.putString("thumb", thumb);
        bundle.putString("url", url);
        Message message = new Message();
        message.setData(bundle);
        message.what = VIDEO_SOURCE;
        handler.sendMessage(message);
        videoLoaded = true;
    }

    @Override
    public void run() {
        requestData(arg);
    }
}
