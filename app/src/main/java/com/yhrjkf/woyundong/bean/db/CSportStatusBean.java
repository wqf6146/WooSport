package com.yhrjkf.woyundong.bean.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/22.
 */

@Entity
public class CSportStatusBean implements Parcelable {


    @Id Long id;

    boolean bVoiceSports; //语音播报功能

    boolean bVoiceDistance; //语音距离
    boolean bVoiceTime;     //语音时长
    boolean bVoiceSpeed;    //语音配速
    String mStrReportStatus;
    String mDistance;
    String mTime;
    boolean bAutoPause;
    public CSportStatusBean( boolean bVoiceSports, boolean bVoiceDistance,
                             boolean bVoiceTime, boolean bVoiceSpeed,boolean bAutoPause, String mStrReportStatus,
                             String mDistance, String mTime){
        this.bVoiceSports = bVoiceSports;
        this.bVoiceDistance = bVoiceDistance;
        this.bVoiceTime = bVoiceTime;
        this.bVoiceSpeed = bVoiceSpeed;
        this.bAutoPause = bAutoPause;
        this.mStrReportStatus = mStrReportStatus;
        this.mDistance = mDistance;
        this.mTime = mTime;
    }

    public String getMStrReportStatus() {
        return this.mStrReportStatus;
    }
    public void setMStrReportStatus(String mStrReportStatus) {
        this.mStrReportStatus = mStrReportStatus;
    }
    public boolean getBVoiceSpeed() {
        return this.bVoiceSpeed;
    }
    public void setBVoiceSpeed(boolean bVoiceSpeed) {
        this.bVoiceSpeed = bVoiceSpeed;
    }
    public boolean getBVoiceTime() {
        return this.bVoiceTime;
    }
    public void setBVoiceTime(boolean bVoiceTime) {
        this.bVoiceTime = bVoiceTime;
    }
    public boolean getBVoiceDistance() {
        return this.bVoiceDistance;
    }
    public void setBVoiceDistance(boolean bVoiceDistance) {
        this.bVoiceDistance = bVoiceDistance;
    }
    public boolean getBVoiceSports() {
        return this.bVoiceSports;
    }
    public void setBVoiceSports(boolean bVoiceSports) {
        this.bVoiceSports = bVoiceSports;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMTime() {
        return this.mTime;
    }
    public void setMTime(String mTime) {
        this.mTime = mTime;
    }
    public String getMDistance() {
        return this.mDistance;
    }
    public void setMDistance(String mDistance) {
        this.mDistance = mDistance;
    }
    @Generated(hash = 2016455068)
    public CSportStatusBean(Long id, boolean bVoiceSports, boolean bVoiceDistance, boolean bVoiceTime, boolean bVoiceSpeed,
            String mStrReportStatus, String mDistance, String mTime, boolean bAutoPause) {
        this.id = id;
        this.bVoiceSports = bVoiceSports;
        this.bVoiceDistance = bVoiceDistance;
        this.bVoiceTime = bVoiceTime;
        this.bVoiceSpeed = bVoiceSpeed;
        this.mStrReportStatus = mStrReportStatus;
        this.mDistance = mDistance;
        this.mTime = mTime;
        this.bAutoPause = bAutoPause;
    }

    @Generated(hash = 1149663705)
    public CSportStatusBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeByte(this.bVoiceSports ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bVoiceDistance ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bVoiceTime ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bVoiceSpeed ? (byte) 1 : (byte) 0);
        dest.writeString(this.mStrReportStatus);
        dest.writeString(this.mDistance);
        dest.writeString(this.mTime);
        dest.writeByte(this.bAutoPause ? (byte) 1: (byte) 0);
    }

    public boolean getBAutoPause() {
        return this.bAutoPause;
    }

    public void setBAutoPause(boolean bAutoPause) {
        this.bAutoPause = bAutoPause;
    }

    protected CSportStatusBean(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.bVoiceSports = in.readByte() != 0;
        this.bVoiceDistance = in.readByte() != 0;
        this.bVoiceTime = in.readByte() != 0;
        this.bVoiceSpeed = in.readByte() != 0;
        this.mStrReportStatus = in.readString();
        this.mDistance = in.readString();
        this.mTime = in.readString();
        this.bAutoPause = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CSportStatusBean> CREATOR = new Parcelable.Creator<CSportStatusBean>() {
        @Override
        public CSportStatusBean createFromParcel(Parcel source) {
            return new CSportStatusBean(source);
        }

        @Override
        public CSportStatusBean[] newArray(int size) {
            return new CSportStatusBean[size];
        }
    };
}
