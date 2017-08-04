package com.yhrjkf.woyundong.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/27.
 */

public class LoginBean implements Parcelable,Serializable {

    /**
     * Recode : 200
     * Msg : 成功
     * Data : {"id":10,"phone":"15933227792","email":"456123@qq.com","password":"63ee451939ed580ef3c4b6f0109d1fd0","salt":"msYzqS98uikcknQwlXguBy","avatar":"http://0109.yuanhuiit.cn/image/201610311734518270.jpg","point":2023,"sex":2,"height":160,"weight":47,"birthday":"2016-8-9","bmi":18.36,"realname":"1","motto":"自由","company":"","two_level":"","three_level":"","four_level":"","com_level":5,"level":5,"xp":0.84706666666667,"employee_num":"0","remember_token":null,"created_at":"2016-07-27 14:53:42","updated_at":"2017-02-27 08:55:59","star":0}
     */

    private String Recode;
    private String Msg;
    private UserBean Data;

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

    public UserBean getData() {
        return Data;
    }

    public void setData(UserBean Data) {
        this.Data = Data;
    }


    public static class UserBean implements Parcelable,Serializable {
        /**
         * id : 10
         * phone : 15933227792
         * email : 456123@qq.com
         * password : 63ee451939ed580ef3c4b6f0109d1fd0
         * salt : msYzqS98uikcknQwlXguBy
         * avatar : http://0109.yuanhuiit.cn/image/201610311734518270.jpg
         * point : 2023
         * sex : 2
         * height : 160
         * weight : 47
         * birthday : 2016-8-9
         * bmi : 18.36
         * realname : 1
         * motto : 自由
         * company :
         * two_level :
         * three_level :
         * four_level :
         * com_level : 5
         * level : 5
         * xp : 0.84706666666667
         * employee_num : 0
         * remember_token : null
         * created_at : 2016-07-27 14:53:42
         * updated_at : 2017-02-27 08:55:59
         * star : 0
         * "walk_speed": "4.17",
            "run_speed": "8.33"
         */

        private int id;
        private String phone;
        private String email;
        private String password;
        private String salt;
        private String avatar;
        private int point;
        private int sex;
        private int height;
        private int weight;
        private String birthday;
        private double bmi;
        private String realname;
        private String motto;
        private String company;
        private String two_level;
        private String three_level;
        private String four_level;
        private int com_level;
        private int level;
        private double xp;
        private String employee_num;
        private Object remember_token;
        private String created_at;
        private String updated_at;
        private int star;
        private String walk_speed;
        private String run_speed;

        public String getRun_speed() {
            return run_speed;
        }

        public void setRun_speed(String run_speed) {
            this.run_speed = run_speed;
        }

        public String getWalk_speed() {
            return walk_speed;
        }

        public void setWalk_speed(String walk_speed) {
            this.walk_speed = walk_speed;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public double getBmi() {
            return bmi;
        }

        public void setBmi(double bmi) {
            this.bmi = bmi;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMotto() {
            return motto;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getTwo_level() {
            return two_level;
        }

        public void setTwo_level(String two_level) {
            this.two_level = two_level;
        }

        public String getThree_level() {
            return three_level;
        }

        public void setThree_level(String three_level) {
            this.three_level = three_level;
        }

        public String getFour_level() {
            return four_level;
        }

        public void setFour_level(String four_level) {
            this.four_level = four_level;
        }

        public int getCom_level() {
            return com_level;
        }

        public void setCom_level(int com_level) {
            this.com_level = com_level;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public double getXp() {
            return xp;
        }

        public void setXp(double xp) {
            this.xp = xp;
        }

        public String getEmployee_num() {
            return employee_num;
        }

        public void setEmployee_num(String employee_num) {
            this.employee_num = employee_num;
        }

        public Object getRemember_token() {
            return remember_token;
        }

        public void setRemember_token(Object remember_token) {
            this.remember_token = remember_token;
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

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.phone);
            dest.writeString(this.email);
            dest.writeString(this.password);
            dest.writeString(this.salt);
            dest.writeString(this.avatar);
            dest.writeInt(this.point);
            dest.writeInt(this.sex);
            dest.writeInt(this.height);
            dest.writeInt(this.weight);
            dest.writeString(this.birthday);
            dest.writeDouble(this.bmi);
            dest.writeString(this.realname);
            dest.writeString(this.motto);
            dest.writeString(this.company);
            dest.writeString(this.two_level);
            dest.writeString(this.three_level);
            dest.writeString(this.four_level);
            dest.writeInt(this.com_level);
            dest.writeInt(this.level);
            dest.writeDouble(this.xp);
            dest.writeString(this.employee_num);
            //dest.writeParcelable(this.remember_token, flags);
            dest.writeString(this.created_at);
            dest.writeString(this.updated_at);
            dest.writeInt(this.star);
            dest.writeString(this.walk_speed);
            dest.writeString(this.run_speed);
        }

        public UserBean() {
        }

        protected UserBean(Parcel in) {
            this.id = in.readInt();
            this.phone = in.readString();
            this.email = in.readString();
            this.password = in.readString();
            this.salt = in.readString();
            this.avatar = in.readString();
            this.point = in.readInt();
            this.sex = in.readInt();
            this.height = in.readInt();
            this.weight = in.readInt();
            this.birthday = in.readString();
            this.bmi = in.readDouble();
            this.realname = in.readString();
            this.motto = in.readString();
            this.company = in.readString();
            this.two_level = in.readString();
            this.three_level = in.readString();
            this.four_level = in.readString();
            this.com_level = in.readInt();
            this.level = in.readInt();
            this.xp = in.readDouble();
            this.employee_num = in.readString();
           // this.remember_token = in.readParcelable(Object.class.getClassLoader());
            this.created_at = in.readString();
            this.updated_at = in.readString();
            this.star = in.readInt();
            this.walk_speed = in.readString();
            this.run_speed = in.readString();
        }

        public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
            @Override
            public UserBean createFromParcel(Parcel source) {
                return new UserBean(source);
            }

            @Override
            public UserBean[] newArray(int size) {
                return new UserBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Recode);
        dest.writeString(this.Msg);
        dest.writeParcelable(this.Data, flags);
    }

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.Recode = in.readString();
        this.Msg = in.readString();
        this.Data = in.readParcelable(UserBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginBean> CREATOR = new Parcelable.Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
