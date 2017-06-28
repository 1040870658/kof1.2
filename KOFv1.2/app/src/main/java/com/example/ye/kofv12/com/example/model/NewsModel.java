package com.example.ye.kofv12.com.example.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by ye on 2016/9/8.
 */
public class NewsModel implements Parcelable{
    private int id;
    private String display_time;
    private int comment_total;
    private String title;
    private String summary;
    private String image;
    private String address;

    public NewsModel(){


    }

    public NewsModel(String title,String summary,String image){
        this.title  = title;
        this.summary = summary;
        this.image = image;
    }
    public NewsModel(String title,String summary,String image,String address){
        this.title  = title;
        this.summary = summary;
        this.image = image;
        this.address = address;
    }

    public NewsModel(int id, String display_time, String address, String title, int comment_total, String summary, String image) {
        this.id = id;
        this.display_time = display_time;
        this.address = address;
        this.title = title;
        this.comment_total = comment_total;
        this.summary = summary;
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getDisplay_time() {
        return display_time;
    }

    public int getComment_total() {
        return comment_total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDisplay_time(String display_time) {
        this.display_time = display_time;
    }

    public void setComment_total(int comment_total) {
        this.comment_total = comment_total;
    }

    public NewsModel(Parcel in){
        id = in.readInt();
        comment_total = in.readInt();
        display_time = in.readString();
        title = in.readString();
        summary = in.readString();
        image = in.readString();
        address = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(comment_total);
        dest.writeString(display_time);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(image);
        dest.writeString(address);
    }
    public static final Parcelable.Creator<NewsModel> CREATOR = new Parcelable.Creator<NewsModel>()
    {
        public NewsModel createFromParcel(Parcel in)
        {
            return new NewsModel(in);
        }

        public NewsModel[] newArray(int size)
        {
            return new NewsModel[size];
        }
    };
}
