package com.yhrjkf.woyundong.utils;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.vise.log.ViseLog;

/**
 * Created by Administrator on 2017/3/30.
 */

public class VoiceUtils implements SpeechSynthesizerListener {

    private static VoiceUtils mInstance;
    private Context mContext;

    public static VoiceUtils init(Context context){
        if (mInstance==null){
            mInstance = new VoiceUtils(context);
        }
        return mInstance;
    }

    public static VoiceUtils getInstance() {
        return mInstance;
    }
    private VoiceUtils(Context context){
        mContext = context;
        startTTS();
    }

    private MyOnAudioFocusChangeListener mListener;
    public class MyOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
            mAudioFoucs = focusChange;
        }
    }

    // 语音合成客户端
    private SpeechSynthesizer mSpeechSynthesizer;

    // 初始化语音合成客户端并启动
    private void startTTS() {
        mAm = (AudioManager) mContext.getSystemService(
                Context.AUDIO_SERVICE);
        mVoiceMax = mAm.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mVoiceVolume = mAm.getStreamVolume(AudioManager.STREAM_MUSIC);
        ViseLog.e("max:"+mVoiceMax+"|current:"+mVoiceVolume);
        mListener = new MyOnAudioFocusChangeListener();
        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(mContext);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey("adZmzKYaseNVlX75tHK09pTX8jWkPAj8", "gPbZKNBT2uWj74eFvnxXWsE1lmWmkADn");
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId("8518195");
        // 设置语音合成文本模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, "your_txt_file_path");
        // 设置语音合成声音模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, "your_speech_file_path");
        // 设置语音合成声音授权文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "your_licence_path");
        // 获取语音合成授权信息
    }

//    Handler mHander = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            mAm.setStreamVolume(AudioManager.STREAM_MUSIC, mVoiceMax-2, 0);
//            return false;
//        }
//    });

    public void onError(String arg0, SpeechError arg1) {
        // 监听到出错，在此添加相关操作
    }
    public void onSpeechFinish(String arg0) {
        // 监听到播放结束，在此添加相关操作
        if (mAudioFoucs == AudioManager.AUDIOFOCUS_REQUEST_GRANTED && mAm!=null){
//            mAm.setStreamVolume(AudioManager.STREAM_MUSIC, mVoiceVolume, 0);
            mAm.abandonAudioFocus(mListener);
            mAudioFoucs = -1;
        }
    }
    public void onSpeechProgressChanged(String arg0, int arg1) {
        // 监听到播放进度有变化，在此添加相关操作
//        ViseLog.e(arg0);
    }
    public void onSpeechStart(String arg0) {
        // 监听到合成并播放开始，在此添加相关操作
//        mHander.sendEmptyMessageDelayed(0,500);
    }
    public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
        // 监听到有合成数据到达，在此添加相关操作
//        ViseLog.e(arg0);
    }
    public void onSynthesizeFinish(String arg0) {
        // 监听到合成结束，在此添加相关操作
//        ViseLog.e(arg0);
    }
    public void onSynthesizeStart(String arg0) {
        // 监听到合成开始，在此添加相关操作
//        ViseLog.e(arg0);
    }

    private int mAudioFoucs;
    private AudioManager mAm;
    private int mVoiceVolume;
    private int mVoiceMax;
    public void reportVoice(String str){
        if (mAm == null){
            mAm = (AudioManager) mContext.getSystemService(
                    Context.AUDIO_SERVICE);
        }
        if(mAm !=null && mAm.isMusicActive()) {
//            mVoiceMax = mAm.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//            mVoiceVolume = mAm.getStreamVolume(AudioManager.STREAM_MUSIC);
            mAudioFoucs = mAm.requestAudioFocus(mListener,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            if (mAudioFoucs == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                //请求到焦点
                mSpeechSynthesizer.initTts(TtsMode.ONLINE);
                mSpeechSynthesizer.speak(str);
                return;
            }
            else {
                ViseLog.e("Lose AudioFoucs");
            }
        }
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);
        mSpeechSynthesizer.speak(str);
    }
}
