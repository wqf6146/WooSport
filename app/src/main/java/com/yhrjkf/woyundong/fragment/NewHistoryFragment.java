package com.yhrjkf.woyundong.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.vise.log.ViseLog;
import com.vise.xsnow.net.callback.ApiCallback;
import com.vise.xsnow.net.exception.ApiException;
import com.vise.xsnow.net.mode.CacheResult;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;

import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.VMonthBean;
import com.yhrjkf.woyundong.bean.VMonthSportBean;
import com.yhrjkf.woyundong.http.WooSportApi;
import com.yhrjkf.woyundong.view.CookieBar;

import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
public class NewHistoryFragment extends BaseFragment {

	@Bind(R.id.fhn_img_back)
	ImageView mImgBack;

	@Bind(R.id.fhn_calendarView)
	MaterialCalendarView mCalendarView;

	@Bind(R.id.fhn_tv_sporttimes)
	TextView mTvSportTimes;

	@Bind(R.id.fhn_tv_sporthour)
	TextView mTvSportHour;

	@Bind(R.id.fhn_tv_distance)
	TextView mTvSportDistance;

	@Bind(R.id.fhn_tv_carlorie)
	TextView mTvSportCarloris;

	@Bind(R.id.fhn_soprt_chart)
	LineChartView mSportChart;

	@Bind(R.id.fhn_tv_month)
	TextView mTvMonth;

	@Bind(R.id.fhn_tv_charttitle)
	TextView mChartTitle;

	@Bind(R.id.hfn_tv_bgtip)
	TextView mBgTip;

	private List<String> mMonthList = new ArrayList<>();
	private Double[] dayInfo;
	private String[] day;
	private List<PointValue> mPointValues = new ArrayList<PointValue>();
	private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

	public static NewHistoryFragment getInstance() {
		NewHistoryFragment fragment = new NewHistoryFragment();
		fragment.setArguments(new Bundle());
		return fragment;
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.fragment_history_new;
	}

	@Override
	protected void initView(View contentView, Bundle savedInstanceState) {

	}

	@Override
	public boolean onBackPressedSupport() {
		pop();
		return true;
	}

	@Override
	protected void initData() {
		getMonData();
	}

	boolean mOnceSwitch=true;


	private void setMonthTextUi(String month){
		mTvMonth.setText(month+ "月");
		mChartTitle.setText(month +"月份运动折线图");
	}

	@Override
	protected void bindEvent() {
		mImgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop();
			}
		});

		mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
			@Override
			public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
				setMonthTextUi(String.valueOf(date.getMonth() +1));
				if (!mOnceSwitch){
					getDayData(date.getYear()+"-"+String.valueOf(date.getMonth()+1));
				}
				mOnceSwitch=false;

			}
		});
		mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
			@Override
			public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
				HistoryDetailFragment fragment = HistoryDetailFragment.getInstance();
				fragment.getArguments().putString("monst",date.getYear() + "-" + String.valueOf(date.getMonth()+1));
				fragment.getArguments().putString("day",String.valueOf(date.getDay()));
				start(fragment);
			}
		});

	}

	private class PrimeDayDisableDecorator implements DayViewDecorator {

		public PrimeDayDisableDecorator(){
		}

		@Override
		public boolean shouldDecorate(CalendarDay day) {
			int d = day.getDay()-1;
			if ( dayInfo == null || d >= dayInfo.length || dayInfo[d]==null )
				return true;
			return dayInfo[d] == 0;
		}

		@Override
		public void decorate(DayViewFacade view) {
			view.setDaysDisabled(true);
		}
	}

	/**
	 * 获取哪几个月运动了
	 */
	public void getMonData() {

		mHud = KProgressHUD.create(getContext())
				.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
				.setLabel("加载中")
				.setCancellable(true);
		mHud.show();
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		WooSportApi.getInstance().startGetSportMonth(mContext, String.valueOf(userBean.getId()),
				new ApiCallback<VMonthBean>() {

			@Override
			public void onStart() {
			}

			@Override
			public void onError(ApiException e) {
				ViseLog.e(e);
//				showToast("数据获取失败~请重试!");
//				new CookieBar.Builder(_mActivity)
//						.setIcon(R.drawable.warning_red)
//						.setBackgroundColor(R.color.white)
//						.setMessageColor(R.color.grayfont)
//						.setMessage("获取数据失败~请重试")
//						.show();
				showTip(R.drawable.warning_red,"获取数据失败~请重试");
				mHud.dismiss();
			}

			@Override
			public void onCompleted() {
			}

			@Override
			public void onNext(VMonthBean result) {

				if (result !=null && result.getData()!=null ) {
					mMonthList = result.getData();
					if (mMonthList.size() > 0){
						getDayData(mMonthList.get(mMonthList.size() - 1));
					}else{
						mHud.dismiss();
						CalendarDay calendarDay = CalendarDay.today();
						getDayData(String.valueOf(calendarDay.getYear()) + "-" + String.valueOf(calendarDay.getMonth()));
					}
				}else{
					mHud.dismiss();
					CalendarDay calendarDay = CalendarDay.today();
					getDayData(String.valueOf(calendarDay.getYear()) + "-" + String.valueOf(calendarDay.getMonth()+1));
					mMonthList.add(String.valueOf(calendarDay.getYear()) + "-" + String.valueOf(calendarDay.getMonth()+1));
//					showToast("暂无数据,快去跑步吧!");
					showTip(R.drawable.warning_blue,"暂无数据,快去跑步吧!");
				}
			}
		});

	}

	/**
	 * 获取某一个月份运动的数据
	 */

	private void getDayData(String monString) {
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return;
		}
		WooSportApi.getInstance().startGetSportDay(mContext, String.valueOf(userBean.getId()), monString,
				new ApiCallback<CacheResult<VMonthSportBean>>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onError(ApiException e) {
						ViseLog.e(e);
						showTip(R.drawable.warning_red,"获取数据失败~请重试");
						mHud.dismiss();
					}

					@Override
					public void onCompleted() {
						mHud.dismiss();
					}

					@Override
					public void onNext(CacheResult<VMonthSportBean> vMonthSportBeanCacheResult) {
						if (vMonthSportBeanCacheResult==null)
							return;
						VMonthSportBean bean = vMonthSportBeanCacheResult.getCacheData();
						mTvSportTimes.setText(String.valueOf(bean.getSum().getCount()));
						mTvSportHour.setText(String.valueOf(bean.getSum().getTime()));
						mTvSportDistance.setText(String.valueOf(bean.getSum().getDistance()));
						mTvSportCarloris.setText(String.valueOf(bean.getSum().getCalorie()));

						dayInfo = new Double[bean.getData().size()];
						day = new String[bean.getData().size()];
						for (int i = 0; i < bean.getData().size(); i++) {
							dayInfo[i] = bean.getData().get(i);
							day[i] = i + 1 + "";
						}

						initCalendar();

						getAxisXLables();// 获取x轴的标注
						getAxisPoints();// 获取坐标点
						initLineChart();// 初始化

						mHud.dismiss();
					}
				});
	}

	private boolean bInitCalendar = false;
	private void initCalendar(){
		if (bInitCalendar){
			mCalendarView.addDecorator(new PrimeDayDisableDecorator());
			return;
		}
		bInitCalendar = true;
		mCalendarView.setTransverter(mMonthList);
		mCalendarView.show();
		mCalendarView.setTileSize(LinearLayout.LayoutParams.MATCH_PARENT);
//					mCalendarView.setTopbarVisible(false);
		mCalendarView.setSelectionColor(getResources().getColor(R.color.bluefont));
		mCalendarView.addDecorator(new PrimeDayDisableDecorator());
		setMonthTextUi(String.valueOf(mCalendarView.getCurrentDate().getMonth()+1));
	}

	/**
	 * 初始化LineChart的一些设置
	 */
	private void initLineChart() {
		Line line = new Line(mPointValues)
				.setColor(Color.parseColor("#FFCD41")); // 折线的颜色
		List<Line> lines = new ArrayList<Line>();
		line.setShape(ValueShape.CIRCLE);// 折线图上每个数据点的形状 这里是圆形 （有三种
		line.setCubic(false);// 曲线是否平滑
		line.setStrokeWidth(2);// 线条的粗细，默认是3
		line.setFilled(true);// 是否填充曲线的面积
		line.setHasLabels(true);// 曲线的数据坐标是否加上备注
		line.setHasLines(true);// 是否用直线显示。如果为false 则没有曲线只有点显示
		line.setHasPoints(true);// 是否显示圆点 如果为false 则没有原点只有点显示
		lines.add(line);

		LineChartData data = new LineChartData();
		data.setLines(lines);

		// 坐标轴
		Axis axisX = new Axis(); // X轴
		axisX.setHasTiltedLabels(false); // X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
		// axisX.setTextColor(Color.WHITE); //设置字体颜色
		axisX.setTextColor(Color.parseColor("#D6D6D9"));// 灰色
		// axisX.setName("月度统计表"); // 表格名称
		axisX.setTextSize(11);// 设置字体大小
		axisX.setMaxLabelChars(1); // 最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
		axisX.setValues(mAxisXValues); // 填充X轴的坐标名称
		data.setAxisXBottom(axisX); // x 轴在底部
		// data.setAxisXTop(axisX); //x 轴在顶部

		Axis axisY = new Axis(); // Y轴

		axisY.setName("km");// y轴标注
		axisY.setInside(false);
		axisY.setTextSize(11);// 设置字体大小
		data.setAxisYLeft(axisY); // Y轴设置在左边
		axisY.setHasLines(true); // Y轴分割线
		// data.setAxisYRight(axisY); //y轴设置在右边
		// 设置行为属性，支持缩放、滑动以及平移
		mSportChart.setInteractive(true);
		mSportChart.setZoomType(ZoomType.HORIZONTAL); // 缩放类型，水平
		// lineChart.setMaxZoom((float) 3);// 缩放比例
		mSportChart.setMaxZoom((float) 7);
		mSportChart.setLineChartData(data);
		mSportChart.setVisibility(View.VISIBLE);

		/**
		 * 注：下面的7，10只是代表一个数字去类比而已
		 * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools
		 * /programming/library-hellocharts-charting-library-t2904456/page2）;
		 * 下面几句可以设置X轴数据的显示个数
		 * （x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
		 * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
		 * 若设置axisX.setMaxLabelChars(int count)这句话, 33个数据点测试，若
		 * axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
		 * 刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10 若小于v.right=
		 * 7;中的7,反正我感觉是这两句都好像失效了的样子 - -! 并且Y轴是根据数据的大小自动设置Y轴上限 若这儿不设置 v.right= 7;
		 * 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
		 */
		Viewport v = new Viewport(mSportChart.getMaximumViewport());
		v.left = 0;
		v.right = 7;
		mSportChart.setCurrentViewport(v);
	}

	/**
	 * X 轴的显示
	 */
	private void getAxisXLables() {
		mAxisXValues.clear();
		for (int i = 0; i < day.length; i++) {
			mAxisXValues.add(new AxisValue(i).setLabel(day[i]));
		}
	}

	/**
	 * 图表的每个点的显示
	 */
	private void getAxisPoints() {
		mPointValues.clear();
		for (int i = 0; i < dayInfo.length; i++) {
			PointValue pointValue = new PointValue(i, Float.parseFloat(dayInfo[i].toString()));
			pointValue.setLabel(dayInfo[i] + "");
			mPointValues.add(pointValue);
		}
	}
}
