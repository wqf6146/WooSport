package com.yhrjkf.woyundong.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */

public class MyRankBean {

    /**
     * uid : 158
     * name : 123
     * headImg : http://101.200.40.54/image/201703241447501442.jpg
     * km : 1.2
     * nub : 22
     */

    private String uid;
    private String name;
    private String headImg;
    private double km;
    private int nub;

    public static MyRankBean objectFromData(String str) {

        return new Gson().fromJson(str, MyRankBean.class);
    }

    public static MyRankBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), MyRankBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<MyRankBean> arrayMyRankBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<MyRankBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<MyRankBean> arrayMyRankBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<MyRankBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public int getNub() {
        return nub;
    }

    public void setNub(int nub) {
        this.nub = nub;
    }
}
