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

public class NoticeBean {

    /**
     * Recode : 200
     * Msg : 成功
     * Data : 预计今天（22日），江南、华南的降水仍然较强，江西、广西等5省区有大雨。明天，南方的雨势暂时减弱，西北、华北地区的大范围雨雪天气将上线。在冷空气和雨雪的双重打压下，北方地区的气温将出现明显转折，北京后天可能度过近1个月来最冷一天。
     */

    private String Recode;
    private String Msg;
    private String Data;

    public static NoticeBean objectFromData(String str) {

        return new Gson().fromJson(str, NoticeBean.class);
    }

    public static NoticeBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), NoticeBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NoticeBean> arrayNoticeBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<NoticeBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<NoticeBean> arrayNoticeBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<NoticeBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getRecode() {
        return Recode;
    }

    public void setRecode(String Recode) {
        this.Recode = Recode;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
