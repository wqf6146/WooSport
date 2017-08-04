package com.yhrjkf.woyundong.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.vise.log.ViseLog;
import com.vise.xsnow.cache.SpCache;
import com.vise.xsnow.net.api.ViseApi;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.*;
import com.vise.xsnow.net.exception.ApiException;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.activity.LoginActivity;
import com.yhrjkf.woyundong.activity.MainActivity;
import com.yhrjkf.woyundong.bean.CNewsBean;
import com.yhrjkf.woyundong.bean.InvitationBean;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.PicBean;
import com.yhrjkf.woyundong.bean.db.CUserBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.http.api.ApiSercice;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.utils.WooConfig;
import com.yhrjkf.woyundong.view.CookieBar;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

//import static com.vise.xsnow.net.mode.CacheMode.CACHE_AND_REMOTE;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.os.Build.VERSION_CODES.KITKAT;
import static com.vise.xsnow.net.mode.CacheMode.FIRST_CACHE;
import static com.vise.xsnow.net.mode.CacheMode.FIRST_REMOTE;

/**
 * Created by Administrator on 2017/3/9.
 */

public class WooSportApi {
    private static WooSportApi wooSportApi;

    KProgressHUD mHud;
    private WooSportApi(){
    }

    public static WooSportApi getInstance() {
        if (wooSportApi == null){
            synchronized (WooSportApi.class){
                if (wooSportApi == null){
                    return new WooSportApi();
                }
            }
        }
        return wooSportApi;
    }

    public void showTip(Activity activity, @DrawableRes int icon, String str){
        if (Build.VERSION.SDK_INT  < KITKAT ){
            new CookieBar.Builder(activity)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
            return;
        }
        if (CommonUtils.areNotificationsEnabled(activity)){
            Toast.makeText(activity,str,Toast.LENGTH_SHORT).show();
        }else{
            new CookieBar.Builder(activity)
                    .setIcon(icon)
                    .setBackgroundColor(R.color.toastbg)
                    .setLayoutGravity(Gravity.BOTTOM)
                    .setMessageColor(R.color.white)
                    .setMessage(str)
                    .show();
        }
    }

    /***
     * 数据更新
     */
    public <T> void startUpdateUser(final Context context,ApiCallback<T> apicallback){
        ViseApi api = new ViseApi.Builder(context).build();
        CUserBean userBean = DbHelper.getInstance().cUserBeanLongDBManager().load(1L);
        if(userBean==null) {
            Toast.makeText(context,"用户数据丢失,请重新登陆!",Toast.LENGTH_SHORT);
            return;
        }
        String phone = userBean.getMPhone();
        String pwd = userBean.getMPassword();
        if (phone.equals("") || pwd.equals("")){
//            Intent intent = new Intent();
//            intent.setClass(context, LoginActivity.class);
//            context.startActivity(intent);
            Toast.makeText(context,"数据意外丢失!",Toast.LENGTH_SHORT);
        }else{
            HashMap hashMap = new HashMap();
            hashMap.put("phone",phone);
            hashMap.put("password",pwd);
            api.post(ApiSercice.URL_LOGIN, hashMap, apicallback);
        }

    }

    /***
     * 登陆
     * @param context
     * @param phone
     */
    public <T> void startLogin(final Context context,String phone, String pwd,ApiCallback<T> apicallback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("phone",phone);
        hashMap.put("password",pwd);
        api.post(ApiSercice.URL_LOGIN, hashMap, apicallback);
    }

    /***
     * 登陆
     * @param context
     * @param phone
     * @param passwd
     * @param key
     */
    public void startLogin(final Activity context,final String phone,final String passwd,final String key){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("phone",phone);
        hashMap.put("password",passwd);
        mHud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        mHud.show();
        api.post(ApiSercice.URL_LOGIN, hashMap, new ApiCallback<LoginBean>(){
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(ApiException e) {
                showTip(context,R.drawable.warning_red,"网络错误，请重试!");
//                Toast.makeText(context,"网络错误，请重试!",Toast.LENGTH_SHORT).show();
                mHud.dismiss();
                ViseLog.e(e);
            }

            @Override
            public void onNext(LoginBean loginBean) {
                if (loginBean.getRecode().equals("200"))
                    doInvitation(context,phone,passwd,loginBean,key);
                else{
                    showTip(context,R.drawable.warning_red,loginBean.getMsg());
//                    Toast.makeText(context, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                    mHud.dismiss();
                }
            }

            @Override
            public void onStart() {
                ViseLog.i("StartLogin");
            }
        });
    }
    //邀请码
    private void doInvitation(final Activity context,
                              final String phone,final String pwd,final LoginBean loginbean,String key){
        HashMap hashMap = new HashMap();
        hashMap.put("uid",loginbean.getData().getId());
        hashMap.put("key",key);
        ViseApi api = new ViseApi.Builder(context).build();
        api.form(ApiSercice.URL_CHECKKEY, hashMap, new ApiCallback<InvitationBean>(){
            @Override
            public void onCompleted() {
                ViseLog.i("Login success!");
                mHud.dismiss();

            }

            @Override
            public void onError(ApiException e) {
//                Toast.makeText(context,"网络错误,请重试!",Toast.LENGTH_SHORT).show();
                showTip(context,R.drawable.warning_red,"网络错误,请重试!");
                mHud.dismiss();
            }

            @Override
            public void onNext(InvitationBean invitationBean) {
                mHud.dismiss();
                if (invitationBean.getRecode().equals("200")){
                    DbHelper.getInstance().cUserBeanLongDBManager().deleteAll();
                    DbHelper.getInstance().cUserBeanLongDBManager().insert(new CUserBean(phone,pwd,loginbean.getData().getPassword()));
                    App.newInstance().saveUserBean(loginbean);
//                    sp.put(WooConfig.USER.ZH, Base64.encodeToString(phone.getBytes(), Base64.DEFAULT));
//                    sp.put(WooConfig.USER.PWD,Base64.encodeToString(pwd.getBytes(), Base64.DEFAULT));
                    Intent intent = new Intent();
                    intent.setClass(context, MainActivity.class);
                    context.startActivity(intent);
                    ((LoginActivity)context).finish();
                }else{
                    showTip(context,R.drawable.warning_red,invitationBean.getMsg());
//                    Toast.makeText(context,invitationBean.getMsg(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onStart() {

            }
        });
    }

    /**
     * 获取咨询列表
     */

    public <T> void startGetCatList(Context context,String type, ApiCallback<T> callback){
        HashMap hashMap = new HashMap();
        hashMap.put("type",type);
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.URL_CATLIST+type).cacheMode(FIRST_REMOTE).build();
        api.cachePost(ApiSercice.URL_CATLIST, hashMap, callback);
    }

    /**
     *  //咨询列表
        String URL_NEWSLIST = URL + "/api/news/list";
     */
    public <T> void startGetNewInfo(Context context, String cat,ApiCallback<T> callback){
        HashMap hashMap = new HashMap();
        hashMap.put("cat",cat);
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.URL_NEWSLIST+cat).cacheMode(FIRST_REMOTE).build();
        api.cachePost(ApiSercice.URL_NEWSLIST, hashMap, callback);
    }

    /**
     * 更新用户信息
     */
    public <T> void startUpdateUserInfo(Context context,HashMap hashmap,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.post(ApiSercice.USER, hashmap, callback);
    }

    /***
     * 发现
     */
    public <T> void startGetDiscover(Context context ,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.EVENT).cacheMode(FIRST_REMOTE).build();
        api.cachePost(ApiSercice.EVENT,  new HashMap(), callback);
    }


    /***
     * 获取天气信息
     */
    public <T> void startGetWeather(Context context, ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.get(ApiSercice.GETWEATHER,new HashMap(), callback);
    }
    public <T> void startGetXmlWeather(Context context, ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.get(ApiSercice.XMLWEATHER,new HashMap<String, String>(), callback);
    }

    /**
     * 获取rank
     */
    public <T> void startGetRankInfo(Context context,String type,String timetype,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.RANKING + type + timetype)
                .cacheMode(FIRST_REMOTE).build();
        HashMap hashMap = new HashMap();
        hashMap.put("limit_1",type);
        hashMap.put("limit_2",timetype);
        hashMap.put("limit_3","0");
        api.cachePost(ApiSercice.RANKING,hashMap, callback);
    }

    /***
     * 上传跑步数据
     */
    public <T> void startPostSportInfo(Context context,HashMap hashMap,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS).build();
        api.post(ApiSercice.ADDED,hashMap, callback);
    }

    /***
     * 日志
     */
    public void startPushException(Context context,String str){
//        LoginBean.UserBean userBean = App.newInstance().getUserBean();
//        if (userBean == null) {
//            Toast.makeText(context,"用户标示意外丢失",Toast.LENGTH_SHORT);
//            return;
//        }
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
//        hashMap.put("uid",String.valueOf(userBean.getId()));
        hashMap.put("remark",str);
        api.post(ApiSercice.EXCEPTION, hashMap, new ApiCallback<String>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ApiException e) {
                ViseLog.e(e);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onNext(String s) {
                ViseLog.e(s);
            }
        });
    }

    /**
     * 获取月数
     */
    public <T> void startGetSportMonthByCache(Context context,String uid,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.HISTORYMON + uid)
                .cacheMode(FIRST_REMOTE).build();
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        api.cachePost(ApiSercice.HISTORYMON,hashMap, callback);
    }
    public <T> void startGetSportMonth(Context context,String uid,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        api.post(ApiSercice.HISTORYMON,hashMap, callback);
    }

    /**
     * 获取每天运动历史
     */
    public <T> void startGetSportDay(Context context,String uid,String monst,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.HISTORYDAY + uid + monst)
                .cacheMode(FIRST_REMOTE).build();
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        hashMap.put("monst",monst);
        api.cachePost(ApiSercice.HISTORYDAY,hashMap, callback);
    }

    /**
     * 获取每天运动历史具体
     */
    public <T> void startGetSportDayDetail(Context context,String uid,String monst,String day,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.HISTORYDAYS + uid + monst+day)
                .cacheMode(FIRST_REMOTE).build();
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        hashMap.put("monst",monst);
        hashMap.put("day",day);
        api.cachePost(ApiSercice.HISTORYDAYS,hashMap, callback);
    }

    /**
     * 获取回放点数组
     */
    public <T> void startGetSportPoint(Context context,String mid,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).cacheKey(ApiSercice.HISTORYMAP + mid)
                .cacheMode(FIRST_REMOTE).build();
        HashMap hashMap = new HashMap();
        hashMap.put("mid",mid);
        api.cachePost(ApiSercice.HISTORYMAP,hashMap, callback);
    }
//
//    /**
//     * 上传图片
//     */
//    public <T> void startPostPic(Context context, String uid, File file){
//        ViseApi api = new ViseApi.Builder(context).build();
//        RequestBody requestBody =
//                RequestBody.create(MediaType.parse("multipart/form-data"),file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("uid",uid,requestBody);
//        api.uploadFile(ApiSercice.PORTRAIT,requestBody,part, PicBean.class);
//    }

    /***
     * 签到
     */
    public <T> void startPostSign(Context context,String uid,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        api.post(ApiSercice.SIGN,hashMap, callback);
    }

    /***
     * 修改用户信息
     */
    public <T> void startPostUserMeg(Context context,HashMap hashMap ,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.post(ApiSercice.USER,hashMap, callback);
    }

    /***
     * 获取奖章
     */
    public <T> void startGetMedal(Context context,int id ,ApiCallback<T> callback){
        HashMap hashMap = new HashMap();
        hashMap.put("uid",String.valueOf(id));
        ViseApi api = new ViseApi.Builder(context).cacheMode(FIRST_REMOTE).cacheKey(ApiSercice.MEDAL).build();
        api.cachePost(ApiSercice.MEDAL,hashMap, callback);
    }

    /***
     * 获取记录
     */
    public <T> void startGetRecord(Context context,int id ,ApiCallback<T> callback){
        HashMap hashMap = new HashMap();
        hashMap.put("uid",String.valueOf(id));
        ViseApi api = new ViseApi.Builder(context).cacheMode(FIRST_REMOTE).cacheKey(ApiSercice.MEDAL).build();
        api.cachePost(ApiSercice.RECORD,hashMap, callback);
    }

    //获取验证码
    public <T> void startGetAuthCode(Context context,String type,String phone, ApiCallback<T> callback){
        HashMap hashMap = new HashMap();
        hashMap.put("phone",phone);
        hashMap.put("type",type);
        ViseApi api = new ViseApi.Builder(context).build();
        api.get(ApiSercice.AUTHCODE,hashMap,callback);
    }

    //修改密码
    public <T> void startUpdatePwd(Context context,String uid,String oldpassword, String password, ApiCallback<T> callback){
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        hashMap.put("oldpassword",oldpassword);
        hashMap.put("password",password);
        ViseApi api = new ViseApi.Builder(context).build();
        api.post(ApiSercice.UPDATEPWD,hashMap,callback);
    }

    //提交密码
    public <T> void startResetPwd(Context context,String uid,String password, ApiCallback<T> callback){
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        hashMap.put("password",password);
        hashMap.put("secretkey","c8837b23ff8aaa8a2dde915473ce0991");
        ViseApi api = new ViseApi.Builder(context).build();
        api.post(ApiSercice.RESETPWD,hashMap,callback);
    }

    //获取公告
    public <T> void startGetNotice(Context context,ApiCallback<T> callback){
        ViseApi api = new ViseApi.Builder(context).build();
        api.get(ApiSercice.NOTICE,new HashMap<String, String>(),callback);
    }

    //获取自己排名
    public <T> void startGetMyRank(Context context,ApiCallback<T> callback){
        LoginBean.UserBean userBean = App.newInstance().getUserBean();
        if (userBean == null) {
            Toast.makeText(context,"用户标示意外丢失,请重试~",Toast.LENGTH_SHORT);
            return;
        }
        ViseApi api = new ViseApi.Builder(context).build();
        HashMap hashMap = new HashMap();
        hashMap.put("uid",String.valueOf(userBean.getId()));
        hashMap.put("type","0");
        hashMap.put("time","0");
        api.post(ApiSercice.MYRANK,hashMap,callback);
    }

}
