package com.example.ye.kofv12.com.example.com.example.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.ye.kofv12.com.example.model.MatchModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by yechen on 2017/6/10.
 */


/**
 * Created by yechen on 2017/6/6.
 */

public class MatchPresenter implements Runnable{
    public static final int STAKE_HOLDER = 0x00000031;
    public static final int REQUEST_FINISHED = 0x00000032;
    public static final int CSL = 51;
    public static final int EPL = 8;
    public static final int ISA = 13;
    public static final int SPL = 7;
    public static final int GPL = 9;
    private final int VALUES = 8;
    private OkHttpClient client;
    public static final String DataURL = "https://www.dongqiudi.com/match/fetch_new?tab=null&date=";
    private WeakReference<Handler> handler;
    private List[] matchModels;

    public MatchPresenter(List[] matchModels, Handler handler) {
        this.matchModels = matchModels;
        this.handler = new WeakReference<Handler>(handler);
    }

    public void requestData() {
        try {
            client = NetWorkConnection.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            String yyyymm = dateFormat.format(Calendar.getInstance().getTime());
            yyyymm += "-";
            dateFormat.applyPattern("dd");
            String dd = dateFormat.format(Calendar.getInstance().getTime());
            int day = Integer.parseInt(dd);
            String yesterday = yyyymm + (day - 1);
            String today = yyyymm + day;
            String tomorrow = yyyymm + (day + 1);
            String requestYesterday = DataURL + yesterday + "&scroll_times=0&tz=-8";
            String requestToday = DataURL + today + "&scroll_times=0&tz=-8";
            String requestTomorrow = DataURL + tomorrow + "&scroll_times=0&tz=-8";
            //  proceedData(NetWorkConnection.get(client,requestYesterday));
            //proceedData(NetWorkConnection.get(client, requestToday));
            //  proceedData(NetWorkConnection.get(client,requestTomorrow));
            modelProceeder(NetWorkConnection.get(client,requestToday));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modelProceeder(String html){
        String allBegin = "tr";
        String childTag = "td";
        String atrRel = "rel";
        String atrId = "id";
        String classTimes = "times";
        String classRound = "round";
        String classAway = "home";
        String classHome = "away";
        String classStat = "stat";
        String tagTh = "th";
        String atrImg = "img";
        String atrSrc = "src";
        MatchModel matchModel;

        int groupNum = -1;
        ArrayList<String > titles = new ArrayList<String>();
        try{
            JSONObject jsonObject = new JSONObject(html);
            html = jsonObject.getString("html");
            Document document = Jsoup.parse(html);
            Elements body = document.getElementsByTag(allBegin);
            Elements child;
            Element away;
            Element home;
            Elements stat;
            Elements group = document.getElementsByTag(tagTh);
            for(Element item : group){
                if(titles.contains(item.text()))
                    continue;
                titles.add(item.text());
            }
            for(Element element : body){
                child = element.getElementsByTag(childTag);
                if(child == null || child.size() <= 0){
                    groupNum++;
                    matchModels[groupNum] = new ArrayList();
                    continue;
                }
                matchModel = new MatchModel();
                matchModel.setRel(element.attr(atrRel));
                matchModel.setId(element.attr(atrId));
                matchModel.setRound(element.getElementsByClass(classRound).get(0).text());
                away = element.getElementsByClass(classAway).get(0);
                home = element.getElementsByClass(classHome).get(0);
                stat = element.getElementsByClass(classStat);
                matchModel.setAwayName(away.text());
                matchModel.setAwaySrc(away.select(atrImg).attr(atrSrc));
                matchModel.setHomeName(home.text());
                matchModel.setHomeSrc(home.select(atrImg).attr(atrSrc));
                matchModel.setTime(element.getElementsByClass(classTimes).get(0).text());
                if(stat != null && stat.size() > 0) {
                    matchModel.setStat(stat.get(0).text());
                }
                matchModels[groupNum].add(matchModel);
            }
            Bundle bundle = new Bundle();
            //bundle.putString("html",html);
            // bundle.putParcelableArrayList("matchInfo", matchModels);
            bundle.putStringArrayList("titles",titles);
            Message message = new Message();
            message.setData(bundle);
            if(handler.get() != null)
                handler.get().sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
   /* private void proceedData(String html) {
        try {
            JSONObject jsonObject = new JSONObject(html);
            html = jsonObject.getString("html");

            String groupBegin = "th colspan=\"6\">";
            String groupEnd = "</th>";

            String liveBegin = "<i>";
            String liveEnd = "</i>";
            String matchBegin = "tr rel=\"";
            String idBegin = "\" id=\"";
            String idEnd = "\" class=\"matchItem\"  >";
            String timeBegin = "td class=\"times\"";
            String cut_timeEnd = "</td>";
            String roundBegin = "td class=\"round\">";
            String roundEnd = "</td>";
            String cut_roundEnd = "<td class=\"away\">";
            String awaySrcBegin = "img src=\"";
            String cut_awaySrcEnd = "\" onerror";
            String awayNameBegin = "\">";
            String cut_awayNameEnd = "</a>";
            String statBegin = "<td class=";
            String statStart = "\">";
            String statEnd = "</td>";
            String cut_statEnd = "</td class=\"home\">";
            String homeNameBegin = ".html\">";
            String homeNameEnd = "<img src=\"";
            String homeSrcEnd = "\" onerror";
            String linkBegin = "<a href=\"";
            String cut_linkEnd = "\" target";
            String linkNameBegin = "\">";
            String linkNameEnd = "</a>";
            String link = "<td class=\"link\">";
            MatchModel matchModel;

            List<String> groupHtml = new ArrayList();
            int groupStart = html.indexOf(groupBegin);
            int groupNum = 0;
            ArrayList<String > titles = new ArrayList<String>();
            Log.e("groupStart", groupStart + "");
            while (groupStart != -1) {
                int nextGroup = html.indexOf(groupBegin, groupStart + groupBegin.length());
                if (nextGroup != -1) {
                    groupHtml.add(html.substring(groupStart, nextGroup));
                } else {
                    groupHtml.add(html.substring(groupStart));
                }
                groupStart = nextGroup;
            }

            for (String subHtml : groupHtml) {
                matchModel = new MatchModel();
                String groupName = subHtml.substring(subHtml.indexOf(groupBegin) + groupBegin.length(),
                        subHtml.indexOf(groupEnd));
                titles.add(groupName);
                int subStart = subHtml.indexOf(matchBegin);
                Log.e("subStart", subStart + "");
                while (subStart != -1) {
                    matchModels[groupNum] = new ArrayList();
                    subHtml = subHtml.substring(subStart);

                    matchModel.setRel(subHtml.substring(subHtml.indexOf(matchBegin) +
                                    matchBegin.length(),
                            subHtml.indexOf(idBegin)));
                    matchModel.setId(subHtml.substring(subHtml.indexOf(idBegin) +
                                    idBegin.length(),
                            subHtml.indexOf(idEnd)));
                    int timeIndex = subHtml.indexOf(timeBegin);
                    int liveIndex = subHtml.indexOf(liveBegin);
                    if(liveIndex > 0 && liveIndex - timeIndex > 0 && liveIndex - timeIndex < 100) {
                        matchModel.setTime(subHtml.substring(liveIndex + linkBegin.length(),
                                subHtml.indexOf(liveEnd)));
                    }
                    else{
                        matchModel.setTime(subHtml.substring(timeIndex +
                                        timeBegin.length(),
                                subHtml.indexOf(cut_timeEnd)));
                    }

                    subHtml = subHtml.substring(subHtml.indexOf(cut_timeEnd) + cut_timeEnd.length());

                    matchModel.setRound(subHtml.substring(subHtml.indexOf(roundBegin) +
                                    roundBegin.length(),
                            subHtml.indexOf(roundEnd)));

                    subHtml = subHtml.substring(subHtml.indexOf(cut_roundEnd) + cut_roundEnd.length());

                    matchModel.setAwaySrc(subHtml.substring(subHtml.indexOf(awaySrcBegin) +
                                    awaySrcBegin.length(),
                            subHtml.indexOf(cut_awaySrcEnd)));

                    subHtml = subHtml.substring(subHtml.indexOf(cut_awaySrcEnd) + cut_awaySrcEnd.length());

                    matchModel.setAwayName(subHtml.substring(subHtml.indexOf(awayNameBegin) +
                                    awayNameBegin.length(),
                            subHtml.indexOf(cut_awayNameEnd)));

                    subHtml = subHtml.substring(subHtml.indexOf(statBegin) + statBegin.length());

                    int statIndex = subHtml.indexOf(statStart);
                    matchModel.setStat(subHtml.substring(statIndex +
                            statStart.length(),
                            subHtml.indexOf(statEnd)));
                    subHtml = subHtml.substring(subHtml.indexOf(cut_statEnd) + cut_statEnd.length());

                    matchModel.setHomeName(subHtml.substring(subHtml.indexOf(homeNameBegin) +
                                    homeNameBegin.length(),
                            subHtml.indexOf(homeNameEnd)));

                    matchModel.setHomeSrc(subHtml.substring(subHtml.indexOf(homeNameEnd) +
                                    homeNameEnd.length(),
                            subHtml.indexOf(homeSrcEnd)));

                    int linkIndex = subHtml.indexOf(link);
                    int linkExist = subHtml.indexOf(linkBegin);
                    while(linkExist > 0 && linkIndex > linkExist){
                        linkExist = subHtml.indexOf(linkBegin,linkExist + 1);
                    }
                    if (linkExist > 0 && linkExist - linkIndex < 50) {
                        matchModel.setLink(subHtml.substring(subHtml.indexOf(linkBegin) +
                                        linkBegin.length(),
                                subHtml.indexOf(cut_linkEnd)));

                        subHtml = subHtml.substring(subHtml.indexOf(cut_linkEnd) + cut_linkEnd.length());

                        matchModel.setLinkName(subHtml.substring(subHtml.indexOf(linkNameBegin) +
                                        linkNameBegin.length(),
                                subHtml.indexOf(linkNameEnd)));
                        subHtml = subHtml.substring(subHtml.indexOf(linkNameEnd) + linkNameEnd.length());
                    } else {
                        matchModel.setLink("");
                        matchModel.setLinkName("");
                    }

                    subStart = subHtml.indexOf(matchBegin);
                    matchModels[groupNum].add( matchModel );
                }
                groupNum++;
            }
            Bundle bundle = new Bundle();
            //bundle.putString("html",html);
           // bundle.putParcelableArrayList("matchInfo", matchModels);
            bundle.putStringArrayList("titles",titles);
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/

    @Override
    public void run() {

        requestData();
    }
}
