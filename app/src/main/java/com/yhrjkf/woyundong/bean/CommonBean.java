package com.yhrjkf.woyundong.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class CommonBean {

    /**
     * Recode : 200
     * Msg : 成功
     * Data : success
     */

    private String Recode;
    private String Msg;
    private String Data;

    public static CommonBean objectFromData(String str) {

        return new Gson().fromJson(str, CommonBean.class);
    }

    public static CommonBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CommonBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CommonBean> arrayCommonBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CommonBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CommonBean> arrayCommonBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CommonBean>>() {
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
