package com.yhrjkf.woyundong.event;

import com.vise.xsnow.event.IEvent;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/15.
 */

public class JumpForResultFragmentEvent implements IEvent {
    SupportFragment mFragment;
    public JumpForResultFragmentEvent(SupportFragment fragment){
        mFragment = fragment;
    }

    public SupportFragment getFragment() {
        return mFragment;
    }
}
