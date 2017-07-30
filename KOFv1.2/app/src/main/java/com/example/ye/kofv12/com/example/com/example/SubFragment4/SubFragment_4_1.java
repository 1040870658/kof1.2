package com.example.ye.kofv12.com.example.com.example.SubFragment4;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ye.kofv12.MyActivity;
import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.presenter.DatasetPresenter;
import com.example.ye.kofv12.com.example.com.example.util.MyDecoration;
import com.example.ye.kofv12.com.example.com.example.util.RetrieveDataset;
import com.example.ye.kofv12.com.example.model.DatasetModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by yechen on 2017/6/6.
 */

public class SubFragment_4_1 extends Fragment {
    private DisplayMetrics metrics;
    private View contentView;
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private List<DatasetModel> datasetModels;
    private DatasetPresenter datasetPresenter;
    private DataSetHandler handler = new DataSetHandler();
    //private Thread thread;
    private int identity = DatasetPresenter.CSL;
    private int screen_width_6;
    private int screen_width_4;
    private int screen_width_1;
    private int screen_width_3;
    private int screen_width_2;
    private ProgressBar progressBar;
    private int screen_height;
    private final int WIDTH_DIVIDE = 12;
    private final int HEIGHT_DIVIDE = 12;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.identity = args.getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(contentView == null){
            metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            screen_width_1 = metrics.widthPixels/WIDTH_DIVIDE;
            screen_width_2 = 2*screen_width_1;
            screen_width_3 = 3*screen_width_1;
            screen_width_4 = 4*screen_width_1;
            screen_width_6 = 6*screen_width_1;
            screen_height = metrics.heightPixels/HEIGHT_DIVIDE;

            datasetModels = new ArrayList<>();
            dataAdapter = new DataAdapter();
            contentView = inflater.inflate(R.layout.layout_subfragment4_1,null);
            progressBar = (ProgressBar) contentView.findViewById(R.id.pb_data);
            recyclerView = (RecyclerView) contentView.findViewById(R.id.rcv_sub4);
            datasetPresenter = new DatasetPresenter(datasetModels,handler);
            recyclerView.setAdapter(dataAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new MyDecoration(getActivity(),MyDecoration.VERTICAL_LIST));
            //thread = new Thread(new RetrieveDataset(datasetPresenter,identity));
            //thread.start();
            MyActivity.executorService.submit(new RetrieveDataset(datasetPresenter,identity));
        }
        return contentView;
    }
    private class DataAdapter extends RecyclerView.Adapter{
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if(viewType == 0){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dataset_title,null);
                HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
                setWidth(headerViewHolder.team,screen_width_2+screen_width_3,0);
                setWidth(headerViewHolder.match,screen_width_1,0);
                setWidth(headerViewHolder.win,screen_width_1,0);
                setWidth(headerViewHolder.loose,screen_width_1,0);
                setWidth(headerViewHolder.draw,screen_width_1,0);
                setWidth(headerViewHolder.mark,screen_width_1,0);
                setWidth(headerViewHolder.score,screen_width_2,0);
                return headerViewHolder;
            }
            else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.standard_dataset,null);
                if (viewType < 4)
                    view.setBackgroundResource(R.color.green);
                ItemViewHolder itemViewHolder = new ItemViewHolder(view);
                setWidth(itemViewHolder.team,screen_width_3,screen_height);
                setWidth(itemViewHolder.rank,screen_width_1,screen_height);
                setWidth(itemViewHolder.match,screen_width_1,screen_height);
                setWidth(itemViewHolder.win,screen_width_1,screen_height);
                setWidth(itemViewHolder.loose,screen_width_1,screen_height);
                setWidth(itemViewHolder.draw,screen_width_1,screen_height);
                setWidth(itemViewHolder.mark,screen_width_1,screen_height);
                setWidth(itemViewHolder.score,screen_width_2,screen_height);
                setWidth(itemViewHolder.iv_team,screen_width_1,screen_height);
                return itemViewHolder;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(position != 0){
                int data_position = position - 1;
                DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().build();
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                DatasetModel datasetModel = datasetModels.get(data_position);
                itemViewHolder.rank.setText(""+position);
                itemViewHolder.team.setText(datasetModel.getName());
                itemViewHolder.mark.setText(String.valueOf(datasetModel.getMark()));
                itemViewHolder.score.setText(datasetModel.getPro_score()+"/"+
                        datasetModel.getCon_score());
                itemViewHolder.loose.setText(""+datasetModel.getLoose_num());
                itemViewHolder.win.setText(""+datasetModel.getWin_num());
                itemViewHolder.draw.setText(""+datasetModel.getDraw_num());
                itemViewHolder.match.setText(""+datasetModel.getMatch_num());
                //ImageLoader.getInstance().displayImage(datasetModel.getLogo(),itemViewHolder.iv_team);
                Glide.with(getActivity()).load(datasetModel.getLogo()).into(itemViewHolder.iv_team);
            }
        }

        @Override
        public int getItemCount() {
            return datasetModels.size() + 1;
        }
    }
    private class DataSetHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DatasetPresenter.REQUEST_FINISHED:
                    progressBar.setVisibility(View.GONE);
                    dataAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder{
        public TextView team;
        public TextView match;
        public TextView win;
        public TextView draw;
        public TextView loose;
        public TextView score;
        public TextView mark;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            team = (TextView) itemView.findViewById(R.id.tv_title_team);
            match = (TextView) itemView.findViewById(R.id.tv_title_match_num);
            win = (TextView) itemView.findViewById(R.id.tv_title_win_num);
            draw = (TextView) itemView.findViewById(R.id.tv_title_draw_num);
            loose = (TextView) itemView.findViewById(R.id.tv_title_loose_num);
            score = (TextView) itemView.findViewById(R.id.tv_title_score_num);
            mark = (TextView) itemView.findViewById(R.id.tv_title_mark_num);
        }
    }
    private static class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView rank;
        public TextView team;
        public TextView match;
        public TextView win;
        public TextView draw;
        public TextView loose;
        public TextView score;
        public TextView mark;
        public ImageView iv_team;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rank = (TextView) itemView.findViewById(R.id.tv_data_rank);
            team = (TextView)itemView.findViewById(R.id.tv_data_team);
            match = (TextView) itemView.findViewById(R.id.tv_data_match_num);
            win = (TextView) itemView.findViewById(R.id.tv_data_win_num);
            draw = (TextView) itemView.findViewById(R.id.tv_data_draw_num);
            loose = (TextView) itemView.findViewById(R.id.tv_data_loose_num);
            score = (TextView) itemView.findViewById(R.id.tv_data_score_num);
            mark = (TextView) itemView.findViewById(R.id.tv_data_mark_num);
            iv_team = (ImageView) itemView.findViewById(R.id.iv_data_team);

        }
    }
    private void setWidth(View view,int width,int height){
        view.getLayoutParams().width = width;
        if (height != 0)
            view.getLayoutParams().height = height;
    }
}
