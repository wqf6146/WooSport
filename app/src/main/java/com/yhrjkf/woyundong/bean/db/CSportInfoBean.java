package com.yhrjkf.woyundong.bean.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.vise.log.ViseLog;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/3/7.
 */
@Entity
public class CSportInfoBean implements Parcelable,Cloneable {

    public CSportInfoBean(){
        setDistance(0d);
        setCalorie(0d);
        setSpeed(0d);
    }
    @Id
    private Long id;
    private String types;
    private Double distance;
    private Double calorie;
    private int calorie_unit;
    private Double speed;
    private String start_time;
    private String finish_time;
    private String time;
    private String path;

    public void setCalorie_unit(int calorie_unit) {
        this.calorie_unit = calorie_unit;
    }

    public int getCalorie_unit() {
        return calorie_unit;
    }

    public double getCalorie() {
        return calorie;
    }

    public double getDistance() {
        return distance;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getPath() {
        return path;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getTime() {
        return time;
    }

    public String getTypes() {
        return types;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.types);
        dest.writeValue(this.distance);
        dest.writeValue(this.calorie);
        dest.writeInt(this.calorie_unit);
        dest.writeString(this.start_time);
        dest.writeString(this.finish_time);
        dest.writeValue(this.speed);
        dest.writeString(this.time);
        dest.writeString(this.path);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSpeed() {
        return this.speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    protected CSportInfoBean(Parcel in) {
        this.types = in.readString();
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
        this.calorie = (Double) in.readValue(Double.class.getClassLoader());
        this.calorie_unit = in.readInt();
        this.start_time = in.readString();
        this.finish_time = in.readString();
        this.time = in.readString();
        this.path = in.readString();
        this.speed = in.readDouble();
    }

    @Generated(hash = 1745075505)
    public CSportInfoBean(Long id, String types, Double distance, Double calorie, int calorie_unit, Double speed,
            String start_time, String finish_time, String time, String path) {
        this.id = id;
        this.types = types;
        this.distance = distance;
        this.calorie = calorie;
        this.calorie_unit = calorie_unit;
        this.speed = speed;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.time = time;
        this.path = path;
    }
    public static final Parcelable.Creator<CSportInfoBean> CREATOR = new Parcelable.Creator<CSportInfoBean>() {
        @Override
        public CSportInfoBean createFromParcel(Parcel source) {
            return new CSportInfoBean(source);
        }

        @Override
        public CSportInfoBean[] newArray(int size) {
            return new CSportInfoBean[size];
        }
    };

    public Object clone(){
        CSportInfoBean o = null;
        try{
            o = (CSportInfoBean)super.clone();
        }catch (CloneNotSupportedException e){
            ViseLog.e(e);
        }
        return o;
    }
}
