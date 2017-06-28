package com.example.ye.kofv12.com.example.com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.view.RoundImageView;
import com.example.ye.kofv12.com.example.model.CommentModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yechen on 2017/6/17.
 */

public class CommentAdapter extends GroupAdapter {


    public CommentAdapter(List<List> groups, Context context) {
        super(groups, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_layout,null);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder viewHolder = (CommentViewHolder)holder;
        CommentModel commentModel = (CommentModel)data.get(position);
        ImageLoader.getInstance().displayImage(commentModel.getImgSrc(),viewHolder.headImageView);
        viewHolder.contentTextView.setText(commentModel.getComment());
        viewHolder.nameTextView.setText(commentModel.getUserName());
        viewHolder.agreeTextView.setText(commentModel.getAgreeNum()+"");
        viewHolder.timeTextView.setText(commentModel.getDate());
    }
    private static class CommentViewHolder extends RecyclerView.ViewHolder{
        public ImageView headImageView;
        public TextView nameTextView;
        public TextView timeTextView;
        public TextView agreeTextView;
        public TextView contentTextView;

        public CommentViewHolder(View itemView) {
            super(itemView);
            headImageView = (ImageView) itemView.findViewById(R.id.iv_person);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_person);
            timeTextView = (TextView) itemView.findViewById(R.id.tv_time);
            agreeTextView = (TextView) itemView.findViewById(R.id.tv_agree);
            contentTextView = (TextView) itemView.findViewById(R.id.tv_comment_content);
        }
    }
}
