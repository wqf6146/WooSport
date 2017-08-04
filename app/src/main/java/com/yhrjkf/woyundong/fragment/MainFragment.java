package com.yhrjkf.woyundong.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vise.xsnow.event.EventSubscribe;
import com.vise.xsnow.manager.AppManager;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.activity.MainActivity;
import com.yhrjkf.woyundong.bean.LoginBean;
import com.yhrjkf.woyundong.bean.db.CUserBean;
import com.yhrjkf.woyundong.bean.db.DbHelper;
import com.yhrjkf.woyundong.event.JumpForResultFragmentEvent;
import com.yhrjkf.woyundong.event.JumpFragmentEvent;
import com.yhrjkf.woyundong.http.api.ApiSercice;
import com.yhrjkf.woyundong.tools.NoScrollViewPager;
import com.yhrjkf.woyundong.view.BottomBar;
import com.yhrjkf.woyundong.view.BottomBarTab;

import butterknife.Bind;
import me.yokeyword.fragmentation.SupportFragment;

public class MainFragment extends BaseFragment{

	@Bind(R.id.ah_bottombar)
	BottomBar mBottomBar;

	public static final int FIRST = 0;
	public static final int SECOND = 1;
	public static final int THIRD = 2;
	public static final int FOURTH = 3;
	public static final int FIVE = 4;
	private SupportFragment[] mFragments = new SupportFragment[5];
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public SupportFragment getFragment(int position){
		return mFragments[position];
	}

	private ConsultFragment getDiscoverFragmnet(){
		ConsultFragment fragment = ConsultFragment.getInstance();
		Bundle bundle = fragment.getArguments();
		bundle.putString("TITLE","发现");
		bundle.putString("URL",
				ApiSercice.DISNEWS);
		return fragment;
	}

	private ConsultFragment getIntegalFramgment(){
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
			showToast("用户标示意外丢失,请重试~");
			return null;
		}
		ConsultFragment fragment = ConsultFragment.getInstance();
		Bundle bundle = fragment.getArguments();
		bundle.putString("TITLE","积分商城");
		bundle.putString("URL",
				ApiSercice.INTEGRAL + userBean.getId());
		return fragment;
	}

	private ConsultFragment getCirCleFragment(){
		CUserBean cUserBean = DbHelper.getInstance().cUserBeanLongDBManager().load(1L);
		ConsultFragment fragment = ConsultFragment.getInstance();
		Bundle bundle = fragment.getArguments();
		bundle.putString("TITLE","");
		if(cUserBean !=null ){
			String url = String.format("http://121.18.239.124:81/startbbs/index.php/user/login?username=%s&password=%s",cUserBean.getMPhone(),cUserBean.getMSpassword());
			bundle.putString("URL", url);
		}
		return fragment;
	}

	@Override
	protected void initData() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		LoginBean.UserBean userBean = App.newInstance().getUserBean();
		if (userBean == null) {
//			showToast("用户标示意外丢失,请重试~");
			return;
		}
		CUserBean cUserBean = DbHelper.getInstance().cUserBeanLongDBManager().load(1L);
		if (cUserBean!=null){
			String url = String.format("http://121.18.239.124:81/startbbs/index.php/user/login?username=%s&password=%s",cUserBean.getMPhone(),cUserBean.getMSpassword());
			((ConsultFragment)mFragments[FIRST]).setUrl(url);
		}
		((ConsultFragment)mFragments[FOURTH]).setUrl(ApiSercice.INTEGRAL + userBean.getId());
	}

	@Override
	protected void initView(View contentView,Bundle instance) {
		if (instance == null) {
			mFragments[FIRST] = getCirCleFragment();
			mFragments[SECOND] = getDiscoverFragmnet();
			mFragments[THIRD] = JianBuXingFragment.getInstance();
			mFragments[FOURTH] = getIntegalFramgment();
			mFragments[FIVE] = MyFragment.getInstance();

			loadMultipleRootFragment(R.id.ah_container, THIRD,
					mFragments[FIRST],
					mFragments[SECOND],
					mFragments[THIRD],
					mFragments[FOURTH],
					mFragments[FIVE]);
		} else {
			// 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

			// 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
//			mFragments[FIRST] = findChildFragment(WechatFirstTabFragment.class);
//			mFragments[SECOND] = findChildFragment(WechatSecondTabFragment.class);
//			mFragments[THIRD] = findChildFragment(WechatThirdTabFragment.class);
		}
		mBottomBar.addItem(new BottomBarTab(_mActivity,R.drawable.circle_1,"圈子"))
				.addItem(new BottomBarTab(_mActivity,R.drawable.discover_1,"发现"))
				.addItem(new BottomBarTab(_mActivity,R.drawable.run,""))
				.addItem(new BottomBarTab(_mActivity,R.drawable.store_1,"商城"))
				.addItem(new BottomBarTab(_mActivity,R.drawable.woo_1,"沃"));

		mBottomBar.setCurrentItem(2);
		mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position, int prePosition) {
				showHideFragment(mFragments[position], mFragments[prePosition]);
				if (position == FIVE){
					((MyFragment)mFragments[position]).updateUi();
				}
			}

			@Override
			public void onTabUnselected(int position) {

			}

			@Override
			public void onTabReselected(int position) {
				// 这里推荐使用EventBus来实现 -> 解耦
				// 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
				// 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
//				EventBus.getDefault().post(new TabSelectedEvent(position));
			}
		});
	}

	@Override
	protected void bindEvent() {

	}

	public static MainFragment getInstance() {
		MainFragment fragment = new MainFragment();
		Bundle bundle = new Bundle();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setOnResumeRegisterBus(true);
	}



	private final int RES_CODE = 0x1;

	@EventSubscribe
	public void starForResultJumpEvent(JumpForResultFragmentEvent jumpFragmentEvent){
		startForResult(jumpFragmentEvent.getFragment(),RES_CODE);
	}

	@EventSubscribe
	public void starJumpEvent(JumpFragmentEvent jumpFragmentEvent){
		start(jumpFragmentEvent.getFragment());
	}


	@Override
	protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
		super.onFragmentResult(requestCode, resultCode, data);
		if (requestCode == 1){
			if (mFragments[FIVE]!=null){
				((MyFragment)mFragments[FIVE]).updateUi();
			}
		}
	}

	public void tabBarAnimateIn(){
		Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.tabbar_in);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mBottomBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		mBottomBar.startAnimation(animation);
	}

	public void tabBarAnimateOut(){
		Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.tabbar_out);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mBottomBar.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		mBottomBar.startAnimation(animation);
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.activity_home;
	}
}
