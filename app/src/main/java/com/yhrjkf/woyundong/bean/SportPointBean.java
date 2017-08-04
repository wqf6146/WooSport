package com.yhrjkf.woyundong.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Administrator on 2017/3/16.
 */

public class SportPointBean implements Parcelable {

    private String mType; //1运动中 2暂停中
    private ArrayList<com.amap.api.maps.model.LatLng> mPoints;

    public SportPointBean(String type,ArrayList<com.amap.api.maps.model.LatLng> vector){
        mType = type;
        mPoints = vector;
    }

    public String getType() {
        return mType;
    }

    public ArrayList<com.amap.api.maps.model.LatLng> getPoints() {
        return mPoints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mType);
        dest.writeTypedList(this.mPoints);
    }

    protected SportPointBean(Parcel in) {
        this.mType = in.readString();
        this.mPoints = in.createTypedArrayList(com.amap.api.maps.model.LatLng.CREATOR);
    }

    public static final Creator<SportPointBean> CREATOR = new Creator<SportPointBean>() {
        @Override
        public SportPointBean createFromParcel(Parcel source) {
            return new SportPointBean(source);
        }

        @Override
        public SportPointBean[] newArray(int size) {
            return new SportPointBean[size];
        }
    };
}
