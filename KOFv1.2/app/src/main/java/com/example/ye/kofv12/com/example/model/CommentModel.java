package com.example.ye.kofv12.com.example.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yechen on 2017/6/13.
 */

public class CommentModel implements Parcelable {
    private String userName;
    private String comment;
    private String imgSrc;
    private String date;
    private int agreeNum;

    public CommentModel(){}
    public CommentModel(String userName, String comment,String date,String imgSrc,int agreeNum){
        this.userName = userName;
        this.comment = comment;
        this.date = date;
        this.imgSrc = imgSrc;
        this.agreeNum = agreeNum;
    }
    public CommentModel(String userName,String comment,String date,int agreeNum){
        this.userName = userName;
        this.comment = comment;
        this.date = date;
        this.agreeNum = agreeNum;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(comment);
        dest.writeString(date);
        dest.writeString(imgSrc);
        dest.writeInt(agreeNum);
    }
    public CommentModel(Parcel in){
        this.userName = in.readString();
        this.comment = in.readString();
        this.date = in.readString();
        this.imgSrc = in.readString();
        this.agreeNum = in.readInt();
    }
    public static final Parcelable.Creator<CommentModel> CREATOR = new Parcelable.Creator<CommentModel>(){
        @Override
        public CommentModel createFromParcel(Parcel source) {
            return new CommentModel(source);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

}
