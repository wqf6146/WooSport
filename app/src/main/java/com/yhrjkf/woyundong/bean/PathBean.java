package com.yhrjkf.woyundong.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */

public class PathBean {


    /**
     * Recode : 200
     * Msg : 成功
     * Data : [{"type":"1","path":"38.872027,115.568896.38.871997,115.568896.38.871967,115.568896.38.871937,115.568896.38.871907,115.568896.38.871877,115.568896.38.871847,115.568896.38.871817,115.568896.38.871787,115.568896"},{"type":"2","path":"38.871757,115.568896.38.871727,115.568896.38.871697,115.568896.38.871667,115.568896.38.871637,115.568896.38.871607,115.568896.38.871577,115.568896.38.871547,115.568896.38.871517,115.568896.38.871487,115.568896.38.871457,115.568896.38.871427,115.568896.38.871397,115.568896.38.871367,115.568896.38.871337,115.568896"},{"type":"1","path":"38.871307,115.568896.38.871277,115.568896.38.871247,115.568896.38.871217,115.568896.38.871187,115.568896.38.871157,115.568896.38.871127,115.568896.38.871097,115.568896.38.871067,115.568896.38.871037,115.568896"},{"type":"2","path":"38.871007,115.568896"}]
     */

    private String Recode;
    private String Msg;
    private List<DataBean> Data;

    public static PathBean objectFromData(String str) {

        return new Gson().fromJson(str, PathBean.class);
    }

    public static PathBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), PathBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<PathBean> arrayPathBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<PathBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<PathBean> arrayPathBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<PathBean>>() {
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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * type : 1
         * path : 38.872027,115.568896.38.871997,115.568896.38.871967,115.568896.38.871937,115.568896.38.871907,115.568896.38.871877,115.568896.38.871847,115.568896.38.871817,115.568896.38.871787,115.568896
         */

        private String type;
        private String path;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DataBean> arrayDataBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
