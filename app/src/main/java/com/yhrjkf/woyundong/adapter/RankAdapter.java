package com.yhrjkf.woyundong.adapter;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yhrjkf.woyundong.App;
import com.yhrjkf.woyundong.R;
import com.yhrjkf.woyundong.bean.RankBean;
import com.yhrjkf.woyundong.view.CircleImageView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/14.
 */

public class RankAdapter extends BaseQuickAdapter<RankBean.DataBean,BaseViewHolder> {

    public RankAdapter( List<RankBean.DataBean> dataBeen){
        super(R.layout.item_rank,dataBeen);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RankBean.DataBean dataBean) {
        int pos = baseViewHolder.getLayoutPosition()+1;
        baseViewHolder.getView(R.id.ir_img_rank).setVisibility(View.VISIBLE);
        if (pos == 1){
            baseViewHolder.setImageResource(R.id.ir_img_rank,R.drawable.theone);
        }else if (pos == 2 ){
            baseViewHolder.setImageResource(R.id.ir_img_rank,R.drawable.thetwo);
        }else if (pos == 3){
            baseViewHolder.setImageResource(R.id.ir_img_rank,R.drawable.thethree);
        }else {
            baseViewHolder.getView(R.id.ir_img_rank).setVisibility(View.INVISIBLE);
        }

        baseViewHolder.setText(R.id.ir_tv_rankid,String.valueOf(pos));

//        Glide.with(mContext).load(dataBean.getAvatar()).into(
//                (ImageView) baseViewHolder.getView(R.id.ir_img_myimg));
        final ImageView img = baseViewHolder.getView(R.id.ir_img_myimg);
        Glide.with(mContext).load(dataBean.getAvatar()).asBitmap().centerCrop()
                .placeholder(R.drawable.df).into(new BitmapImageViewTarget(img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        img.setImageDrawable(circularBitmapDrawable);
                    }
                });
        String name = dataBean.getName();
        if (!TextUtils.isEmpty(name)){
            if ((isNumeric(name) && getWordCount(name) > 11)){
                name = name.substring(0,name.length() > 11 ? 11 : name.length()) + "...";
            }else if (getWordCount(name) > 16){
                name = name.substring(0,name.length() > 8 ? 8 : name.length()) + "...";
            }

            baseViewHolder.setText(R.id.ir_tv_myname,name);
//					name = name.substring(0,11) + "...";
        }

        baseViewHolder.setText(R.id.ir_tv_mydistance,String.valueOf(dataBean.getDistance()));
    }
    ///-=--------------
    public  int getWordCount(String s)
    {
        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return length;
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    ///-=--------------
}
