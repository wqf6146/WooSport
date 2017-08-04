package com.yhrjkf.woyundong.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class VMonthBean {

    /**
     * Recode : 200
     * Msg : 成功
     * Data : ["2016-7","2016-8","2016-9","2016-10","2016-11","2016-12","2017-1","2017-2","2017-3"]
     */

    private String Recode;
    private String Msg;
    private List<String> Data;

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

    public List<String> getData() {
        return Data;
    }

    public void setData(List<String> Data) {
        this.Data = Data;
    }
}
