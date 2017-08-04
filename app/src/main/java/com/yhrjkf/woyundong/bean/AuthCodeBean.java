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

public class AuthCodeBean {

    /**
     * flag : sucss
     * message : 123456
     * data : 29
     */

    private String flag;
    private String message;
    private String data;

    public static AuthCodeBean objectFromData(String str) {

        return new Gson().fromJson(str, AuthCodeBean.class);
    }

    public static AuthCodeBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), AuthCodeBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<AuthCodeBean> arrayAuthCodeBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AuthCodeBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<AuthCodeBean> arrayAuthCodeBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<AuthCodeBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
