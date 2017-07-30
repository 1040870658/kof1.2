package com.example.ye.kofv12.com.example.com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.view.CommentTextView;
import com.example.ye.kofv12.com.example.model.MatchModel;

import java.util.List;

/**
 * Created by yechen on 2017/6/25.
 */

public class LiveAdapter extends GroupAdapter {


    public LiveAdapter(List<List> groups, Context context) {
        super(groups, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_live,null);
        return new LiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LiveViewHolder liveViewHolder = (LiveViewHolder)holder;
        MatchModel matchModel = (MatchModel) data.get(position);
        liveViewHolder.homeTextView.setText(matchModel.getHomeName());
        //ImageLoader.getInstance().displayImage(matchModel.getHomeSrc(),liveViewHolder.homeImageView);
        //ImageLoader.getInstance().displayImage(matchModel.getAwaySrc(),liveViewHolder.awayImageView);
        Glide.with(context).load(matchModel.getHomeSrc()).into(liveViewHolder.homeImageView);
        Glide.with(context).load(matchModel.getAwaySrc()).into(liveViewHolder.awayImageView);
        liveViewHolder.awayTextView.setText(matchModel.getAwayName());
        liveViewHolder.roundTextView.setText(matchModel.getRound());
        liveViewHolder.timeTextView.setText(matchModel.getTime());
        liveViewHolder.panel.setText(matchModel.getStat());
    }
    private static class LiveViewHolder extends RecyclerView.ViewHolder{
        public TextView homeTextView;
        public TextView awayTextView;
        public TextView roundTextView;
        public TextView timeTextView;
        public TextView panel;
        public ImageView homeImageView;
        public ImageView awayImageView;
        public LiveViewHolder(View itemView) {
            super(itemView);
            homeImageView = (ImageView) itemView.findViewById(R.id.iv_home);
            awayImageView = (ImageView) itemView.findViewById(R.id.iv_away);
            homeTextView = (TextView) itemView.findViewById(R.id.tv_home);
            awayTextView = (TextView) itemView.findViewById(R.id.tv_away);
            roundTextView = (TextView) itemView.findViewById(R.id.tv_round);
            timeTextView = (TextView) itemView.findViewById(R.id.tv_time);
            panel = (TextView) itemView.findViewById(R.id.ctv_panel);
        }
    }

}
