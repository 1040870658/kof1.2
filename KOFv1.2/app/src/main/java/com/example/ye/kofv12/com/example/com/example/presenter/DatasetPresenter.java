package com.example.ye.kofv12.com.example.com.example.presenter;

import android.os.Handler;

import com.example.ye.kofv12.com.example.model.DatasetModel;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by yechen on 2017/6/6.
 */

public class DatasetPresenter {
    public static final int STAKE_HOLDER = 0x00000021;
    public static final int REQUEST_FINISHED = 0x00000022;
    public static final int CSL = 51;
    public static final int EPL = 8;
    public static final int ISA = 13;
    public static final int SPL = 7;
    public static final int GPL = 9;
    private final int VALUES = 8;
    private OkHttpClient client;
    public static final String DataURL = "http://www.dongqiudi.com/data";
    private WeakReference<Handler> handler;
    private List<DatasetModel> datasetModels;
    public DatasetPresenter(List<DatasetModel> datasetModels,Handler handler){
        this.datasetModels = datasetModels;
        this.handler = new WeakReference<Handler>(handler);
    }
    public void requestData(int arg){
        try {
            client = NetWorkConnection.getInstance();
            String requestURL = DataURL + "?competition="+ arg;
            proceedData(NetWorkConnection.get(client,requestURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void proceedData(String html){
        String begin = "td class=\"team\"";
        String end = "div class=\"loading\"";
        String img_b = "src=\"";
        String img_b_delta = "http://img";
        String img_e = "\" alt=";
        String name_b = "\" alt=\"\">";
        String name_e = "</a>";
        String value_b = "<td>";
        String value_e = "</td>";
        html = html.substring(html.indexOf(begin),html.indexOf(end));
        while(html.indexOf(begin) != -1){
            html = html.substring(html.indexOf(begin) + 1);
            DatasetModel datasetModel = new DatasetModel();
            String img = html.substring(html.indexOf(img_b+img_b_delta)+img_b.length(),html.indexOf(img_e));
            String name = html.substring(html.indexOf(name_b) + name_b.length(), html.indexOf(name_e));
            html = html.substring(html.indexOf(value_e)+value_e.length());
            int values[] = new int[VALUES];
            for(int i = 0;i != VALUES;i ++){
                int b = html.indexOf(value_b);
                int e = html.indexOf(value_e);
                values[i] = Integer.parseInt(html.substring(b+value_b.length(),e));
                html = html.substring(e+value_e.length());
            }
            datasetModel.setName(name);
            datasetModel.setLogo(img);
            datasetModel.setMatch_num(values[0]);
            datasetModel.setWin_num(values[1]);
            datasetModel.setDraw_num(values[2]);
            datasetModel.setLoose_num(values[3]);
            datasetModel.setCon_score(values[5]);
            datasetModel.setPro_score(values[4]);
            datasetModel.setPure_score(values[6]);
            datasetModel.setMark(values[7]);
            datasetModels.add(datasetModel);
        }
        if(handler.get() != null)
            handler.get().sendEmptyMessage(DatasetPresenter.REQUEST_FINISHED);
    }
}
