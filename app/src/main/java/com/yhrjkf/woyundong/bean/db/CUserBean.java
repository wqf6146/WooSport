package com.yhrjkf.woyundong.bean.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/24.
 */
@Entity
public class CUserBean {
    @Id Long id;
    String mPhone;
    String mPassword;
    String mSpassword;

    public CUserBean(String phone,String password,String spassword){
        mPhone = phone;
        mPassword = password;
        mSpassword = spassword;
    }

    public String getMPassword() {
        return this.mPassword;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getMPhone() {
        return this.mPhone;
    }

    public void setMPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMSpassword() {
        return this.mSpassword;
    }

    public void setMSpassword(String mSpassword) {
        this.mSpassword = mSpassword;
    }

    @Generated(hash = 1997340865)
    public CUserBean(Long id, String mPhone, String mPassword, String mSpassword) {
        this.id = id;
        this.mPhone = mPhone;
        this.mPassword = mPassword;
        this.mSpassword = mSpassword;
    }

    @Generated(hash = 558317003)
    public CUserBean() {
    }

  
}
