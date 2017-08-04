package com.yhrjkf.woyundong.bean;

/**
 * Created by Administrator on 2017/3/10.
 */

public class VerifyKeyBean {

    /**
     * flag : 503
     * message : 0非11位联通手机号码
     */

    private String flag;
    private String message;

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
}
