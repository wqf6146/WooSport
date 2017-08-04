package com.yhrjkf.woyundong.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class CNewsBean {

    /**
     * Recode : 200
     * Msg : 成功
     * Data : [{
     * "id":11,"name":"要闻","type":1,"description":"重要新闻","created_at":"2016-07-26 11:01:04","updated_at":"2016-07-26 11:01:04"},
     * {"id":12,"name":"生活","type":1,"description":"生活新闻","created_at":"2016-07-26 11:01:22","updated_at":"2016-07-26 11:01:22"},{"id":13,"name":"其他","type":1,"description":"其他新闻","created_at":"2016-07-26 11:01:46","updated_at":"2016-07-26 11:01:46"}]
     */

    private String Recode;
    private String Msg;
    private List<DataBean> Data;

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
         * id : 11
         * name : 要闻
         * type : 1
         * description : 重要新闻
         * created_at : 2016-07-26 11:01:04
         * updated_at : 2016-07-26 11:01:04
         */

        private int id;
        private String name;
        private int type;
        private String description;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
