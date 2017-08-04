package com.yhrjkf.woyundong.bean;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/16.
 */

public class CSportPointBean{

    private String mType; //1运动中 2暂停中
    private ArrayList<com.amap.api.maps.model.LatLng> mPoints;

    public CSportPointBean(String type, ArrayList<com.amap.api.maps.model.LatLng> vector){
        mType = type;
        mPoints = vector;
    }

    public String getType() {
        return mType;
    }

    public ArrayList<com.amap.api.maps.model.LatLng> getPoints() {
        return mPoints;
    }
}
