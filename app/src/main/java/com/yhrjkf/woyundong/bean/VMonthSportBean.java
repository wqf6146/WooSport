package com.yhrjkf.woyundong.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class VMonthSportBean {


    /**
     * Recode : 200
     * Msg : 成功
     * Data : [0.98,1.3,0,0,0,0,0,0,0,0.64,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
     * Sum : {"time":0.28,"calorie":"48","distance":0.64,"count":17}
     */

    private String Recode;
    private String Msg;
    private SumBean Sum;
    private List<Double> Data;

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

    public SumBean getSum() {
        return Sum;
    }

    public void setSum(SumBean Sum) {
        this.Sum = Sum;
    }

    public List<Double> getData() {
        return Data;
    }

    public void setData(List<Double> Data) {
        this.Data = Data;
    }

    public static class SumBean {
        /**
         * time : 0.28
         * calorie : 48
         * distance : 0.64
         * count : 17
         */

        private double time;
        private String calorie;
        private double distance;
        private int count;

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
