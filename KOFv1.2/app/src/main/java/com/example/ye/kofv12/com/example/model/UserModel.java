package com.example.ye.kofv12.com.example.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yechen on 2017/6/13.
 */

public class UserModel implements Parcelable {
    private String head;
    private String name;
    @Override
    public int describeContents() {
        return 0;
    }

    public UserModel(){}
    public UserModel(String head, String name){
        this.head = head;
        this.name = name;
    }
    public UserModel(Parcel in){
        this.head = in.readString();
        this.name = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(head);
        dest.writeString(name);
    }
    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>()
    {
        public UserModel createFromParcel(Parcel in)
        {
            return new UserModel(in);
        }

        public UserModel[] newArray(int size)
        {
            return new UserModel[size];
        }
    };
}
