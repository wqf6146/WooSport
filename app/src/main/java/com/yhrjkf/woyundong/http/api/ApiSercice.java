package com.yhrjkf.woyundong.http.api;

import com.yhrjkf.woyundong.bean.CNewsBean;
import com.yhrjkf.woyundong.bean.InvitationBean;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.NewBean;
import com.yhrjkf.woyundong.bean.RecentSportMsgBean;
import com.yhrjkf.woyundong.bean.VMonthBean;
import com.yhrjkf.woyundong.bean.VMonthSportBean;

//import retrofit.http.Field;
//import retrofit.http.FormUrlEncoded;
//import retrofit.http.GET;
//import retrofit.http.POST;
//import retrofit.http.Path;
//import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/27.
 */

public interface ApiSercice {

    String URL =   "http://121.18.239.124:8181";
    String PORTS = "http://121.18.239.124:8181";// 端口

//    String URL = "http://101.200.40.54";
//    String PORTS = "http://101.200.40.54";

    String DISNEWS = "http://121.18.239.124:81/index.php/Home/news";

    String URL_LOGIN = URL + "/api/user/login";
//    @POST("/api/user/login")
//    @FormUrlEncoded
//    Observable<LoginBean> doLogin(@Field("phone") String phone, @Field("password") String password);

    String URL_CHECKKEY = URL + "/api/user/checkkey";
//    @POST("/api/user/checkkey")
//    @FormUrlEncoded
//    Observable<InvitationBean> doInvitation(@Field("uid") int uid,@Field("key") String key);

    String URL_CATLIST = URL + "/api/cat/list";
//    @POST("/api/cat/list")
//    @FormUrlEncoded
//    Observable<CNewsBean> doCNewsList(@Field("type") int type);

    //咨询列表
    String URL_NEWSLIST = URL + "/api/news/list";
//    @POST("/api/news/list")
//    @FormUrlEncoded
//    Observable<NewBean> doNewInfo(@Field("cat") int cat);

    //获取月份运动信息
    String URL_MONSTS = URL + "/api/movement/monsts";
//    @POST("/api/movement/monsts")
//    @FormUrlEncoded
//    Observable<VMonthBean> doGetVMonth(@Field("uid") int uid);

    //获取具体月份运动信息
    String URL_MONSTINFO = URL + "/api/movement/monstinfo";
//    @POST("/api/movement/monstinfo")
//    @FormUrlEncoded
//    Observable<VMonthSportBean> doGetVMonthSportInfo(@Field("uid") int uid, @Field("monst") String monst);

    //获取最近运动数据
    String URL_RECENTSPORT = URL + "/api/movement/been";
//    @GET("/api/movement/been")
//    Observable<RecentSportMsgBean> deGetRecentSportMsg(@Query("uid") int uid);

    String WEATHER = "http://v.juhe.cn/weather/geo";
    String KEY = "81711211cb1e08186e882ef02c44a600";



    String EXCEPTION = "http://121.18.239.124:8181/api/user/log";
     String RANKING = PORTS + "/api/movement/ranking";// 排名接口
     String EVENT = PORTS + "/api/event/list";// 活动接口
     String LOGIN = PORTS + "/api/user/login";// 登录接口
     String REGISTER = PORTS + "/api/user/register";// 注册接口
     String SIGN = PORTS + "/api/user/sign";// 签到接口
     String NEWS = PORTS + "/api/news/list";// 新闻接口
     String MEDAL = PORTS + "/api/user/medal";// 勋章接口
     String  RECORD = PORTS + "/api/movement/record";// 用户记录接口
     String PORTRAIT = PORTS + "/api/user/avatar";// 头像上传接口
     String USER = PORTS + "/api/user/update";// 修改用户信息接口
     String HISTORY = PORTS + "/api/movement/note";// 运动历史接口
     String ADDED = PORTS + "/api/movement/new";// 新增用户记录接口
     String ACTIVATE = PORTS + "/api/user/checkkey";// 邀请码接口
     String STARTSHINE = "http://c.eqxiu.com/s/bffprQTL";// 星光大道接口
     String INTEGRAL = "http://121.18.239.124:81/?key=b7d0bb6e7e9762cd846309efc21d3c01&uid=";// 积分商城接口
     String NOTE = "http://121.18.239.124:81/index.php/home/user/index";// 短信接口
    String AUTHCODE = "http://121.18.239.124:81/index.php/Home/User"; //获取验证码
    String RESETPWD = PORTS + "/api/user/pwdreset";  //重置密码
    String UPDATEPWD = PORTS +"/api/user/pwdupdate"; //修改密码
    String MYRANK = PORTS + "/api/movement/ownranking"; //自己排名
    String NOTICE = PORTS + "/api/news/notice"; //获取公告
     String CATLIST = PORTS + "/api/cat/list";// 新闻分类接口
     String RECENTLY = PORTS + "/api/movement/been";// 最近一次运动接口
     String CONTENT = "http://121.18.239.124:81/index.php/Home/index/get_about";// 关于我们，说明
     String OPINION = "http://121.18.239.124:81/index.php/Home/index/addfeedback";// 意见反馈
     String TECHNOLOGY = "http://www.yuanhuiit.com";// 官网
     String HISTORYMON = PORTS + "/api/movement/monsts";// 运动历史月份接口
     String HISTORYDAY = PORTS + "/api/movement/monstinfo";// 运动历史每天接口
     String HISTORYDAYS = PORTS + "/api/movement/note";// 运动历史每天运动次数接口
     String HISTORYMAP = PORTS + "/api/movement/getpath";// 运动历史每天运动轨迹接口
    String GETWEATHER = "http://121.18.239.124:81/index.php/home/news/get_weather"; //天气
    String XMLWEATHER = "http://wthrcdn.etouch.cn/WeatherApi?city=保定";

}
