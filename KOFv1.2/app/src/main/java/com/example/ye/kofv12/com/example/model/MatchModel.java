package com.example.ye.kofv12.com.example.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by yechen on 2017/6/10.
 */

public class MatchModel implements Parcelable{
    private String rel;
    private String id;
    private String time;
    private String round;
    private String stat;
    private String homeSrc;
    private String homeName;
    private String awaySrc;
    private String awayName;
    private String link;
    private String linkName;

    public MatchModel() {
    }

    public MatchModel(Parcel in){
        rel = in.readString();
        id = in.readString();
        time = in.readString();
        round = in.readString();
        stat = in.readString();
        homeSrc = in.readString();
        homeName = in.readString();
        awaySrc = in.readString();
        awayName = in.readString();
        link = in.readString();
        linkName = in.readString();
    }
    public MatchModel(String rel, String id, String round, String link, String awaySrc, String time, String stat, String homeName, String homeSrc, String awayName, String linkName) {
        this.rel = rel;
        this.id = id;
        this.round = round;
        this.link = link;
        this.awaySrc = awaySrc;
        this.time = time;
        this.stat = stat;
        this.homeName = homeName;
        this.homeSrc = homeSrc;
        this.awayName = awayName;
        this.linkName = linkName;
    }

    public String getRel() {
        return rel;
    }

    public String getLink() {
        return link;
    }

    public String getAwaySrc() {
        return awaySrc;
    }

    public String getAwayName() {
        return awayName;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getHomeSrc() {
        return homeSrc;
    }

    public String getStat() {
        return stat;
    }

    public String getRound() {
        return round;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public String getLinkName(){
        return linkName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setRel(String rel) {
        Log.e("rel",rel);
        this.rel = rel;
    }

    public void setLinkName(String linkName) {
        linkName = linkName.replace(" ","");
        linkName = linkName.replace("\n","");
        Log.e("linkname",linkName);
        this.linkName = linkName;
    }

    public void setLink(String link) {
        Log.e("link",link);
        this.link = link;
    }

    public void setId(String id) {
        Log.e("id",id);
        this.id = id;
    }

    public void setTime(String time) {
        Log.e("time",time);
        this.time = time;
    }

    public void setRound(String round) {
        round = round.replace("\n","");
        round= round.replace(" ","");
        Log.e("round",round);
        this.round = round;
    }

    public void setStat(String stat) {
        stat = stat.replace(" ","");
        Log.e("stat",stat);
        this.stat = stat;
    }

    public void setHomeSrc(String homeSrc) {
        this.homeSrc = homeSrc;
    }

    public void setHomeName(String homeName) {
        homeName = homeName.trim();
        Log.e("homeName",homeName);
        this.homeName = homeName;
    }

    public void setAwayName(String awayName) {
        awayName = awayName.trim();
        Log.e("awayName",awayName);
        this.awayName = awayName;
    }

    public void setAwaySrc(String awaySrc) {
        Log.e("awaySrc",awaySrc);
        this.awaySrc = awaySrc;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rel);
        dest.writeString(id);
        dest.writeString(time);
        dest.writeString(round);
        dest.writeString(stat);
        dest.writeString(homeSrc);
        dest.writeString(homeName);
        dest.writeString(awaySrc);
        dest.writeString(awayName);
        dest.writeString(link);
        dest.writeString(linkName);
    }
    public static final Parcelable.Creator<MatchModel> CREATOR = new Parcelable.Creator<MatchModel>(){

        @Override
        public MatchModel createFromParcel(Parcel source) {
            return new MatchModel(source);
        }

        @Override
        public MatchModel[] newArray(int size) {
            return new MatchModel[size];
        }
    };
}
