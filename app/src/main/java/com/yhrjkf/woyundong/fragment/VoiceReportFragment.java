package com.yhrjkf.woyundong.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.suke.widget.SwitchButton;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.BaseFragment;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.db.CSportStatusBean;
import com.yhrjkf.woyundong.utils.VoiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.util.DateUtils;

/**
 * Created by Administrator on 2017/3/22.
 */

public class VoiceReportFragment extends BaseFragment {

    @Bind(R.id.fv_btn_switch)
    SwitchButton mBtnSwitch;

    @Bind(R.id.fv_ll_report)
    LinearLayout mLlReport;

    @Bind(R.id.fv_rl_bbpl)
    RelativeLayout mRlbbpl;

    @Bind(R.id.fv_btn_distance)
    SwitchButton mBtnDistance;

    @Bind(R.id.fv_btn_time)
    SwitchButton mBtnTime;

    @Bind(R.id.fv_btn_speed)
    SwitchButton mBtnSpeed;

    @Bind(R.id.fv_img_back)
    ImageView mImgBack;

    @Bind(R.id.fv_tv_bbpl)
    TextView mTvbbpl;

    @Bind(R.id.fv_btn_tryreport)
    Button mBtnTryReport;

    public static VoiceReportFragment getInstance() {
        VoiceReportFragment fragment=  new VoiceReportFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        CSportStatusBean cSportStatusBean = App.newInstance().getSportStatus();
        mBtnSwitch.setChecked(cSportStatusBean.getBVoiceSports());
        if (cSportStatusBean.getBVoiceSports()){
            mLlReport.setVisibility(View.VISIBLE);
        }else{
            mLlReport.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(cSportStatusBean.getMDistance())){
            mTvbbpl.setText(cSportStatusBean.getMDistance());
        }else{
            mTvbbpl.setText(cSportStatusBean.getMTime());
        }
        mBtnDistance.setChecked(cSportStatusBean.getBVoiceDistance());
        mBtnTime.setChecked(cSportStatusBean.getBVoiceTime());
        mBtnSpeed.setChecked(cSportStatusBean.getBVoiceSpeed());
    }


    @Override
    protected void bindEvent() {

        mBtnTryReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSportStatusBean cSportStatusBean = App.newInstance().getSportStatus();
                Boolean bDistance = cSportStatusBean.getBVoiceDistance();
                Boolean bTime = cSportStatusBean.getBVoiceTime();
                Boolean bSpeed = cSportStatusBean.getBVoiceSpeed();
                String str = "";
                if (bDistance && bTime && bSpeed) {
                    //你已经跑步9.8公里 用时5分钟 平均速度为
                    str = "你已经跑步9.8公里 用时10分钟 平均速度为7米每秒";
                }else if (bTime && bSpeed){
                    //你已经跑步5分钟 平均速度为
                    str = "你已经跑步5分钟平均速度为7米每秒";
                }else if (bDistance && bSpeed){
                    //你已经跑步9.8公里 平均速度为
                    str = "你已经跑步9.8公里平均速度为7米每秒";
                }else if (bDistance && bTime){
                    //你已经跑步9.8公里 用时5分钟
                    str = "你已经跑步9.8公里用时5分钟";
                }else if (bDistance){
                    //你已经跑步9.8公里
                    str = "你已经跑步9.8公里";
                }else if (bTime){
                    //你已经跑步5分钟
                    str = "你已经跑步5分钟";
                }else if (bSpeed){
                    //你当前的平均速度为
                    str = "你当前的平均速度为7米每秒";
                }
                VoiceUtils.getInstance().reportVoice(str);
            }
        });

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentResult(1,null);
                pop();

            }
        });

        mBtnSpeed.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                //速度
                App.newInstance().getSportStatus().setBVoiceSpeed(b);
                App.newInstance().saveSportStatus();
                if (!b && !mBtnTime.isChecked() && !mBtnDistance.isChecked()){
                    mBtnSwitch.setChecked(false);
                    mLlReport.setVisibility(View.INVISIBLE);
                    App.newInstance().getSportStatus().setBVoiceSports(false);
                    App.newInstance().saveSportStatus();
                }
            }
        });

        mBtnTime.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                //时间
                App.newInstance().getSportStatus().setBVoiceTime(b);
                App.newInstance().saveSportStatus();
                if (!b && !mBtnSpeed.isChecked() && !mBtnDistance.isChecked()){
                    mBtnSwitch.setChecked(false);
                    mLlReport.setVisibility(View.INVISIBLE);
                    App.newInstance().getSportStatus().setBVoiceSports(false);
                    App.newInstance().saveSportStatus();
                }
            }
        });

        mBtnDistance.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                //距离
                App.newInstance().getSportStatus().setBVoiceDistance(b);
                App.newInstance().saveSportStatus();
                if (!b && !mBtnSpeed.isChecked() && !mBtnTime.isChecked()){
                    mBtnSwitch.setChecked(false);
                    mLlReport.setVisibility(View.INVISIBLE);
                    App.newInstance().getSportStatus().setBVoiceSports(false);
                    App.newInstance().saveSportStatus();
                }
            }
        });

        mRlbbpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播报频率
                onReportPicker();
            }
        });

        mBtnSwitch.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                if (b){
                    mLlReport.setVisibility(View.VISIBLE);
                    CSportStatusBean cSportStatusBean = App.newInstance().getSportStatus();
                    cSportStatusBean.setBVoiceSports(true);
                    mBtnDistance.setChecked(cSportStatusBean.getBVoiceDistance());
                    mBtnSpeed.setChecked(cSportStatusBean.getBVoiceSpeed());
                    mBtnTime.setChecked(cSportStatusBean.getBVoiceTime());
                    if (!cSportStatusBean.getBVoiceDistance() && !cSportStatusBean.getBVoiceSpeed()
                            && !cSportStatusBean.getBVoiceTime()){
                        mBtnDistance.setChecked(true);
                    }
                }else{
                    mLlReport.setVisibility(View.INVISIBLE);
                    App.newInstance().getSportStatus().setBVoiceSports(false);
                }
                App.newInstance().saveSportStatus();
            }
        });
    }
    public void onReportPicker() {
        LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

            @Override
            public boolean isOnlyTwo() {
                return true;
            }

            @Override
            public List<String> provideFirstData() {
                ArrayList<String> firstList = new ArrayList<>();
                firstList.add("距离");
                firstList.add("时间");
                return firstList;
            }

            @Override
            public List<String> provideSecondData(int firstIndex) {
                ArrayList<String> secondList = new ArrayList<>();
                if (firstIndex == 0){
                    //distance
                    secondList.add("0.1公里");
                    secondList.add("0.2公里");
                    secondList.add("0.3公里");
                    secondList.add("0.4公里");
                    secondList.add("0.5公里");
                    secondList.add("1.0公里");
                    secondList.add("2.0公里");
                    secondList.add("3.0公里");
                }else{
                    secondList.add("5分钟");
                    secondList.add("10分钟");
                    secondList.add("15分钟");
                    secondList.add("20分钟");
                }
                return secondList;
            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {
                return null;
            }

        };
        LinkagePicker picker = new LinkagePicker(getActivity(), provider);
        picker.setCycleDisable(true);
        picker.setAnimationStyle(R.style.Animation_CustomPopup);
        picker.setLabel("", "");
        picker.setSelectedIndex(0, 0);
        picker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {

            @Override
            public void onPicked(String first, String second, String third) {
                CSportStatusBean cSportStatusBean = App.newInstance().getSportStatus();
                if (first.equals("距离")){
                    String distance = String.valueOf(second.subSequence(0,second.indexOf("公")));

                    cSportStatusBean.setMDistance(second);
                    cSportStatusBean.setMTime("");
                    cSportStatusBean.setMStrReportStatus(String.format(getString(R.string.reportdistance),distance));
//                    showToast();
                    mTvbbpl.setText(second);
                }else {
                    //时间
                    String ms = String.valueOf(second.subSequence(0,second.indexOf("分")));
                    cSportStatusBean.setMTime(second);
                    cSportStatusBean.setMDistance("");
                    cSportStatusBean.setMStrReportStatus(String.format(getString(R.string.reporttime),ms));
                    //                    showToast();
                    mTvbbpl.setText(second);
                }

                App.newInstance().saveSportStatus();
            }
        });
        picker.show();
    }
    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_voicereport;
    }
}
