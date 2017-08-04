package com.yhrjkf.woyundong.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.http.api.AppConfig;
import com.yhrjkf.woyundong.tools.WheelViews;
import com.yhrjkf.woyundong.utils.CommonUtils;
import com.yhrjkf.woyundong.utils.FileUtils;
import com.yhrjkf.woyundong.utils.SystemBarTintManager;
import com.yhrjkf.woyundong.view.CookieBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

import static android.os.Build.VERSION_CODES.KITKAT;

public class PersonalActivity extends TakePhotoActivity implements OnClickListener {

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	private static String path = "/sdcard/myHead/";// sd路径
	Toast mToast;
	private File tempFile;
	HttpUtils httpUtils = new HttpUtils();
	private String[] PLANETS = new String[100];
	private String[] weight = new String[955];

	String shengao = "50";
	String tizhong = "20";

	@Bind(R.id.img_my_touxiang)
	ImageView img_my_touxiang;

	@Bind(R.id.rl_my_touxiang)
	RelativeLayout rl_my_touxiang;

	@Bind(R.id.rl_my_qm)
	RelativeLayout rl_my_qm;

	@Bind(R.id.rl_my_nicheng)
	RelativeLayout rl_my_nicheng;

	@Bind(R.id.rl_my_yx)
	RelativeLayout rl_my_yx;

	@Bind(R.id.rl_my_sex)
	RelativeLayout rl_my_sex;

	@Bind(R.id.rl_my_sg)
	RelativeLayout rl_my_sg;


	@Bind(R.id.rl_my_tz)
	RelativeLayout rl_my_tz;

	@Bind(R.id.rl_my_csny)
	RelativeLayout rl_my_csny;

	@Bind(R.id.tv_my_nicheng)
	TextView tv_my_nicheng;

	@Bind(R.id.tv_my_sjh)
	TextView tv_my_sjh;

	@Bind(R.id.tv_my_yx)
	TextView tv_my_yx;

	@Bind(R.id.tv_my_sex)
	TextView tv_my_sex;

	@Bind(R.id.tv_my_sg)
	TextView tv_my_sg;

	@Bind(R.id.tv_my_tz)
	TextView tv_my_tz;

	@Bind(R.id.tv_my_csny)
	TextView tv_my_csny;

	@Bind(R.id.tv_my_bmi)
	TextView tv_my_bmi;

	@Bind(R.id.tv_my_qm)
	TextView tv_my_qm;

	@Bind(R.id.ap_btn_save)
	TextView tv_save_btn;

	@Bind(R.id.ap_img_back)
	ImageView mImgBack;

	private KProgressHUD mHud;


	@Override
	public void takeCancel() {
		super.takeCancel();
	}

	@Override
	public void takeFail(TResult result, String msg) {
		super.takeFail(result, msg);
	}

	@Override
	public void takeSuccess(TResult result) {
		super.takeSuccess(result);
		TImage image = result.getImage();
		ViseLog.e(result);
		File imgFile = new File(image.getOriginalPath());
		Glide.with(this).load(imgFile).into(img_my_touxiang);
		RequestParams params = new RequestParams();
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		params.addBodyParameter("uid", String.valueOf(userBean.getId()));
		params.addBodyParameter("avatar", imgFile);
		uploadMethod(params, AppConfig.PORTRAIT);
		App.newInstance().setUserFile(imgFile);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AppManager.getInstance().addActivity(this);
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.bluefont);
		setContentView(R.layout.activity_personal);
		ButterKnife.bind(this);
		init();
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		Glide.with(this).load(userBean.getAvatar()).placeholder(R.drawable.df).thumbnail(0.1f).into(img_my_touxiang);
	}

	@Override
	public void onResume() {
		super.onResume();
		init();
	}
	public void showTip(@DrawableRes int icon, String str){
		if (Build.VERSION.SDK_INT  < KITKAT ){
			new CookieBar.Builder(this)
					.setIcon(icon)
					.setBackgroundColor(R.color.toastbg)
					.setLayoutGravity(Gravity.BOTTOM)
					.setMessageColor(R.color.white)
					.setMessage(str)
					.show();
			return;
		}
		if (CommonUtils.areNotificationsEnabled(this)){
			showToast(str);
		}else{
			new CookieBar.Builder(this)
					.setIcon(icon)
					.setBackgroundColor(R.color.toastbg)
					.setLayoutGravity(Gravity.BOTTOM)
					.setMessageColor(R.color.white)
					.setMessage(str)
					.show();
		}
	}
	private void init() {
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		tv_save_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
					showToast("用户标示意外丢失,请重试~");
					return;
				}
				HashMap hashMap = new HashMap();
				hashMap.put("uid",String.valueOf(userBean.getId()));
				hashMap.put("realname",String.valueOf(userBean.getRealname()));
				hashMap.put("email",userBean.getEmail());
				hashMap.put("sex",String.valueOf(userBean.getSex()));
				hashMap.put("height",String.valueOf(userBean.getHeight()));
				hashMap.put("weight",String.valueOf(userBean.getWeight()));
				hashMap.put("motto",String.valueOf(userBean.getMotto()));
				hashMap.put("com_level",String.valueOf(userBean.getCom_level()));
				hashMap.put("birthday",userBean.getBirthday());
				hashMap.put("employee_num",String.valueOf(userBean.getEmployee_num()));

				mHud = KProgressHUD.create(PersonalActivity.this)
						.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
						.setCancellable(true);
				mHud.show();
				WooSportApi.getInstance().startPostUserMeg(PersonalActivity.this, hashMap, new ApiCallback<LoginBean>() {
					@Override
					public void onStart() {
					}

					@Override
					public void onNext(LoginBean loginBean) {
						if (loginBean.getRecode().equals("200")){
							App.newInstance().saveUserBean(loginBean);
//							showToast("保存成功！");
//							new CookieBar.Builder(PersonalActivity.this)
//									.setIcon(R.drawable.warning_blue)
//									.setBackgroundColor(R.color.white)
//									.setMessageColor(R.color.grayfont)
//									.setMessage("保存成功")
//									.show();
							showTip(R.drawable.warning_blue,"保存成功");
						}
						mHud.dismiss();
					}

					@Override
					public void onError(ApiException e) {
						ViseLog.e(e);
						mHud.dismiss();
//						showToast("网络错误,请重试~");
//						new CookieBar.Builder(PersonalActivity.this)
//								.setIcon(R.drawable.warning_red)
//								.setBackgroundColor(R.color.white)
//								.setMessageColor(R.color.grayfont)
//								.setMessage("网络错误,请重试")
//								.show();
						showTip(R.drawable.warning_red,"网络错误,请重试");
					}

					@Override
					public void onCompleted() {
						mHud.dismiss();
					}
				});
			}
		});



		tv_my_nicheng.setText(userBean.getRealname());
		tv_my_sjh.setText(userBean.getPhone());
		tv_my_yx.setText(userBean.getEmail());
		if (userBean.getSex()==1) {
			tv_my_sex.setText("男");
		} else if (userBean.getSex()==2) {
			tv_my_sex.setText("女");
		}else{
			tv_my_sex.setText("未知");
		}
		if (userBean.getHeight()==0){
			tv_my_sg.setText("未知");
		}else{
			tv_my_sg.setText(userBean.getHeight() + "cm");
		}
		if (userBean.getWeight()==0){
			tv_my_tz.setText("未知");
		}else{
			tv_my_tz.setText(userBean.getWeight() + "kg");
		}
		if (TextUtils.isEmpty(userBean.getBirthday())){
			tv_my_csny.setText("未知");
		}else{
			tv_my_csny.setText(userBean.getBirthday());
		}

		tv_my_bmi.setText(String.valueOf(userBean.getBmi()));
		tv_my_qm.setText(userBean.getMotto());

		rl_my_touxiang.setOnClickListener(this);
		rl_my_nicheng.setOnClickListener(this);
		rl_my_yx.setOnClickListener(this);
		rl_my_sex.setOnClickListener(this);
		rl_my_sg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog2();
			}
		});

		rl_my_tz.setOnClickListener(this);
		rl_my_qm.setOnClickListener(this);
		rl_my_csny.setOnClickListener(this);

		mImgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_my_touxiang:
			showDialog();
			break;

		case R.id.rl_my_nicheng:
			Intent intent2 = new Intent();
			intent2.setClass(this, NickNameActivity.class);
			startActivityForResult(intent2, 6);
			break;
		case R.id.rl_my_yx:
			Intent intent3 = new Intent();
			intent3.setClass(this, EmailActivity.class);
			startActivityForResult(intent3, 7);
			break;
		case R.id.rl_my_sex:
			showDialog1();
			break;
		case R.id.rl_my_tz:
			showDialog3();
			break;
		case R.id.rl_my_qm:
			Intent intent4 = new Intent();
			intent4.setClass(this, SignatureActivity.class);
			startActivityForResult(intent4, 8);
			break;
		case R.id.rl_my_csny:
			showDataPop();
		default:
			break;
		}

	}

	private void showDataPop() {
		final DatePicker picker = new DatePicker(this);
		picker.setTopPadding(2);

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		picker.setRangeStart(year-70, month, day);
		picker.setAnimationStyle(R.style.Animation_CustomPopup);
		picker.setRangeEnd(year, month, day);
		picker.setSelectedItem(year-30, month, day);
		picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
			@Override
			public void onDatePicked(String year, String month, String day) {
				String str = year+"-"+month+"-"+day;
				tv_my_csny.setText(str);
				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
					showToast("用户标示意外丢失,请重试~");
					return;
				}
				userBean.setBirthday(str);
			}
		});
		picker.setOnWheelListener(new DatePicker.OnWheelListener() {
			@Override
			public void onYearWheeled(int index, String year) {
				picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
			}

			@Override
			public void onMonthWheeled(int index, String month) {
				picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
			}

			@Override
			public void onDayWheeled(int index, String day) {
				picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
			}
		});
		picker.show();
	}

	private void showDialog3() {
		// TODO Auto-generated method stub
		String[] sts = new String[200];
		for (int i = 0; i<200; i++){
			sts[i]=20+i+"";
		}
		OptionPicker picker = new OptionPicker(this, sts);
		picker.setAnimationStyle(R.style.Animation_CustomPopup);
		picker.setCycleDisable(false);
		picker.setLineVisible(false);
		picker.setTextSize(14);
		picker.setSelectedItem("60");
		picker.setLabel("kg");
		picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
			@Override
			public void onOptionPicked(int index, String item) {
				tv_my_tz.setText(item + "kg");
				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
					showToast("用户标示意外丢失,请重试~");
					return;
				}
				userBean.setWeight(Integer.parseInt(item));
			}
		});
		picker.show();

	}


	private void showDialog2() {
		// TODO Auto-generated method stub
		String[] sts = new String[200];
		for (int i = 0; i<200; i++){
			sts[i]=50+i+"";
		}
		OptionPicker picker = new OptionPicker(this, sts);
		picker.setAnimationStyle(R.style.Animation_CustomPopup);
		picker.setCycleDisable(false);
		picker.setLineVisible(false);
//        picker.setShadowVisible(true);
		picker.setTextSize(14);
		picker.setLabel("cm");
		picker.setSelectedItem("160");
		picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
			@Override
			public void onOptionPicked(int index, String item) {
				tv_my_sg.setText(item + "cm");
				LoginBean.UserBean userBean = App.newInstance().getUserBean();
				if (userBean == null) {
					showToast("用户标示意外丢失,请重试~");
					return;
				}
				userBean.setHeight(Integer.parseInt(item));
			}
		});
		picker.show();

	}
	//获取裁剪参数
	private CropOptions getCropOptions(){
		int height= 800;
		int width= 800;
		boolean withWonCrop=true;

		CropOptions.Builder builder=new CropOptions.Builder();

		builder.setAspectX(width).setAspectY(height);
//
		builder.setWithOwnCrop(withWonCrop);
		return builder.create();
	}

	private Uri getLocalImgUri(){
		File file=new File(FileUtils.getCacheDirectory(this,Environment.DIRECTORY_PICTURES), "/temp/"+System.currentTimeMillis() + ".jpg");
		if (!file.getParentFile().exists())file.getParentFile().mkdirs();
		return Uri.fromFile(file);
	}

	private void showDialog() {
		// TODO Auto-generated method stub
		bCanBack = true;
		new AlertView("上传头像", null, "取消", null,
				new String[]{"拍照", "从相册中选择"},
				this, AlertView.Style.ActionSheet, new OnItemClickListener() {
			@Override
			public void onItemClick(Object o, int i) {
				if (i == 0){
					//拍照
//					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//					// 判断存储卡是否可以用，可用进行存储
//					if (hasSdcard()) {
//						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//								Environment.getExternalStorageDirectory(),
//								IMAGE_FILE_NAME)));
//					}
//					startActivityForResult(intent, CAMERA_REQUEST_CODE);
					getTakePhoto().onPickFromCaptureWithCrop(getLocalImgUri(),getCropOptions());
				}else if (i == 1){
					//相册
					getTakePhoto().onPickFromGalleryWithCrop(getLocalImgUri(),getCropOptions());
//					Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
//					albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//					startActivityForResult(albumIntent, IMAGE_REQUEST_CODE);
				}else{

				}
				bCanBack = false;
			}
		}).show();

	}

	private boolean bCanBack=false;
	private void showDialog1() {
		// TODO Auto-generated method stub
//
		bCanBack = true;
		new AlertView("性别", null, "取消", null,
				new String[]{"男","女"},
				this, AlertView.Style.ActionSheet, new OnItemClickListener() {
			@Override
			public void onItemClick(Object o, int i) {
				if (i == 0){
					tv_my_sex.setText("男");
					LoginBean.UserBean userBean = App.newInstance().getUserBean();
					if (userBean == null) {
						showToast("用户标示意外丢失,请重试~");
						return;
					}
					userBean.setSex(1);
				}else if (i == 1){
					tv_my_sex.setText("女");
					LoginBean.UserBean userBean = App.newInstance().getUserBean();
					if (userBean == null) {
						showToast("用户标示意外丢失,请重试~");
						return;
					}
					userBean.setSex(2);
				}else{

				}
				bCanBack = false;
			}
		}).setCancelable(true).show();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 结果码不等于取消时候
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:

				if (hasSdcard()) {
					tempFile = new File(
							Environment.getExternalStorageDirectory(),
							IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
//					showToast("");
//					new CookieBar.Builder(PersonalActivity.this)
//							.setIcon(R.drawable.warning_red)
//							.setBackgroundColor(R.color.white)
//							.setMessageColor(R.color.grayfont)
//							.setMessage("未找到存储卡，无法存储照片！")
//							.show();
					showTip(R.drawable.warning_red,"未找到存储卡，无法存储照片");
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			case 6:
				tv_my_nicheng.setText(userBean.getRealname());
				break;
			case 7:
				tv_my_yx.setText(userBean.getEmail());
				break;
			case 8:
				tv_my_qm.setText(userBean.getMotto());
				break;
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void showToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(this, text,
					Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	@SuppressWarnings("deprecation")
	private void getImageToView(Intent data) {
//		Bundle extras = data.getExtras();
//		if (extras != null) {
//			photo = extras.getParcelable("data");
//			drawable = new BitmapDrawable(photo);
//			img_my_touxiang.setImageDrawable(drawable);
//			setPicToView(photo);// 保存在SD卡中
//		}
	}

//	private void setPicToView(Bitmap mBitmap) {
//		// TODO Auto-generated method stub
//		String sdStatus = Environment.getExternalStorageState();
//		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//			return;
//		}
//		FileOutputStream b = null;
//		File file = new File(path);
//		file.mkdirs();// 创建文件夹
//		String fileName = path + "head.jpg";// 图片名字
//		try {
//			b = new FileOutputStream(fileName);
//			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				// 关闭流
//				b.flush();
//				b.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		RequestParams params = new RequestParams();
//		params.addBodyParameter("uid", String.valueOf(App.newInstance().getUserBean().getId()));
//		params.addBodyParameter("avatar", new File(fileName));
//		uploadMethod(params, AppConfig.PORTRAIT);
//	}

	private void uploadMethod(final RequestParams params,
			final String uploadHost) {
		// TODO Auto-generated method stub
		httpUtils.send(HttpMethod.POST, uploadHost, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						Log.i("result", responseInfo.result);
						try {
							JSONObject jsonObject = new JSONObject(
									responseInfo.result);
							String avatar = jsonObject.getString("Data");
							LoginBean.UserBean userBean = App.newInstance().getUserBean();
							if (userBean == null) {
								showToast("用户标示意外丢失,请重试~");
								return;
							}
							userBean.setAvatar(avatar);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}


//	private View getDataPick() {
//		Calendar c = Calendar.getInstance();
//		int curYear = c.get(Calendar.YEAR);
//		int curMonth = c.get(Calendar.MONTH) + 1;// 通过Calendar算出的月数要+1
//		int curDate = c.get(Calendar.DATE);
//		final View view = inflater.inflate(R.layout.datapick, null);
//
//		year = (WheelViews) view.findViewById(R.id.year);
//		year.setAdapter(new NumericWheelAdapter(1950, curYear));
//		year.setLabel("年");
//		year.setCyclic(true);
//		year.addScrollingListener(scrollListener);
//
//		month = (WheelViews) view.findViewById(R.id.month);
//		month.setAdapter(new NumericWheelAdapter(1, 12));
//		month.setLabel("月");
//		month.setCyclic(true);
//		month.addScrollingListener(scrollListener);
//
//		day = (WheelViews) view.findViewById(R.id.day);
//		initDay(curYear, curMonth);
//		day.setLabel("日");
//		day.setCyclic(true);
//
//		year.setCurrentItem(curYear - 1950);
//		month.setCurrentItem(curMonth - 1);
//		day.setCurrentItem(curDate - 1);
//
//		Button bt = (Button) view.findViewById(R.id.set);
//		bt.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String str = (year.getCurrentItem() + 1950) + "-"
//						+ (month.getCurrentItem() + 1) + "-"
//						+ (day.getCurrentItem() + 1);
//				tv_my_csny.setText(str);
//				App.newInstance().getUserBean().setBirthday(str);
//				menuWindow.dismiss();
//			}
//		});
//		Button cancel = (Button) view.findViewById(R.id.cancel);
//		cancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				menuWindow.dismiss();
//			}
//		});
//		return view;
//	}

//	private void initDay(int arg1, int arg2) {
//		day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
//	}
//
//	private int getDay(int year, int month) {
//		int day = 30;
//		boolean flag = false;
//		switch (year % 4) {
//		case 0:
//			flag = true;
//			break;
//		default:
//			flag = false;
//			break;
//		}
//		switch (month) {
//		case 1:
//		case 3:
//		case 5:
//		case 7:
//		case 8:
//		case 10:
//		case 12:
//			day = 31;
//			break;
//		case 2:
//			day = flag ? 29 : 28;
//			break;
//		default:
//			day = 30;
//			break;
//		}
//		return day;
//	}
//
//	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
//
//		@Override
//		public void onScrollingStarted(WheelViews wheel) {
//
//		}
//
//		@Override
//		public void onScrollingFinished(WheelViews wheel) {
//			// TODO Auto-generated method stub
//			int n_year = year.getCurrentItem() + 1950;
//			int n_month = month.getCurrentItem() + 1;
//			initDay(n_year, n_month);
//		}
//	};

//	@SuppressWarnings({ "deprecation" })
//	private void showPopwindow(View view) {
//		menuWindow = new PopupWindow(view, LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		menuWindow.setFocusable(true);
//		menuWindow.setBackgroundDrawable(new BitmapDrawable());
//		menuWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
//		menuWindow.setOnDismissListener(new OnDismissListener() {
//			@Override
//			public void onDismiss() {
//				menuWindow = null;
//			}
//		});
//	}

}
