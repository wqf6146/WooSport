package com.yhrjkf.woyundong.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class WeatherBean {


    /**
     * flag : scuss
     * message : {"temperature":"3℃~15℃","city":"保定","weather_pic":"http://app1.showapi.com/weather/icon/day/01.png","PM2.5":"52","quality":"良"}
     */

    private String flag;
    private MessageBean message;

    public static WeatherBean objectFromData(String str) {

        return new Gson().fromJson(str, WeatherBean.class);
    }

    public static WeatherBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), WeatherBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<WeatherBean> arrayWeatherBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<WeatherBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<WeatherBean> arrayWeatherBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<WeatherBean>>() {
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

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * temperature : 3℃~15℃
         * city : 保定
         * weather_pic : http://app1.showapi.com/weather/icon/day/01.png
         * PM2.5 : 52
         * quality : 良
         */

        private String temperature;
        private String city;
        private String weather_pic;
        @SerializedName("PM2.5")
        private String _$PM2576; // FIXME check this code
        private String quality;

        public static MessageBean objectFromData(String str) {

            return new Gson().fromJson(str, MessageBean.class);
        }

        public static MessageBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), MessageBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<MessageBean> arrayMessageBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<MessageBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<MessageBean> arrayMessageBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<MessageBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getWeather_pic() {
            return weather_pic;
        }

        public void setWeather_pic(String weather_pic) {
            this.weather_pic = weather_pic;
        }

        public String get_$PM2576() {
            return _$PM2576;
        }

        public void set_$PM2576(String _$PM2576) {
            this._$PM2576 = _$PM2576;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }
    }
}
