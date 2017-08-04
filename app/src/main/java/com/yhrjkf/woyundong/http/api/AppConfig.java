package com.yhrjkf.woyundong.http.api;

public class AppConfig {
	public static final String PORTS = "http://121.18.239.124:8181";
//	public static final String PORTS = "http://101.200.40.54";// 端口

	public static final String RANKING = PORTS + "/api/movement/ranking";// 排名接口
	public static final String EVENT = PORTS + "/api/event/list";// 活动接口
	public static final String LOGIN = PORTS + "/api/user/login";// 登录接口
	public static final String REGISTER = PORTS + "/api/user/register";// 注册接口
	public static final String SIGN = PORTS + "/api/user/sign";// 签到接口
	public static final String NEWS = PORTS + "/api/news/list";// 新闻接口
	public static final String MEDAL = PORTS + "/api/user/medal";// 勋章接口
	public static final String RECORD = PORTS + "/api/movement/record";// 用户记录接口
	public static final String PORTRAIT = PORTS + "/api/user/avatar";// 头像上传接口
	public static final String USER = PORTS + "/api/user/update";// 修改用户信息接口
	public static final String HISTORY = PORTS + "/api/movement/note";// 运动历史接口
	public static final String ADDED = PORTS + "/api/movement/new";// 新增用户记录接口
	public static final String ACTIVATE = PORTS + "/api/user/checkkey";// 邀请码接口
	public static final String STARTSHINE = "http://c.eqxiu.com/s/bffprQTL";// 星光大道接口
	public static final String INTEGRAL = "http://121.18.239.124:81/?key=b7d0bb6e7e9762cd846309efc21d3c01&uid=";// 积分商城接口
	public static final String NOTE = "http://121.18.239.124:81/index.php/home/user/index";// 短信接口
	public static final String CATLIST = PORTS + "/api/cat/list";// 新闻分类接口
	public static final String RECENTLY = PORTS + "/api/movement/been";// 最近一次运动接口
	public static final String CONTENT = "http://121.18.239.124:81/index.php/Home/index/get_about";// 关于我们，说明
	public static final String OPINION = "http://121.18.239.124:81/index.php/Home/index/addfeedback";// 意见反馈
	public static final String TECHNOLOGY = "http://www.yuanhuiit.com";// 官网
	public static final String HISTORYMON = PORTS + "/api/movement/monsts";// 运动历史月份接口
	public static final String HISTORYDAY = PORTS + "/api/movement/monstinfo";// 运动历史每天接口
	public static final String HISTORYDAYS = PORTS + "/api/movement/note";// 运动历史每天运动次数接口
	public static final String HISTORYMAP = PORTS + "/api/movement/getpath";// 运动历史每天运动轨迹接口

}
