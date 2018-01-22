package com.nuocf.yshuobang.myview.photoWidget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuocf.yshuobang.R;

import org.xutils.x;

import java.util.ArrayList;


/**
 /**
 * =================yangyf================
 *
 * @Author: yangyf
 * @Time : 2016/10/12 16:18
 * @Action : viewpager适配器
 */
class ImageTouchAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> mDatas;
    private LayoutInflater layoutInflater;
    private View mCurrentView;
    private final TouchViewPager mPager;
    private PhotoListener mListener;

    public void addListener(PhotoListener listener) {
        mListener = listener;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }


    public ImageTouchAdapter(Context context, ArrayList<String> datas, TouchViewPager pager) {
        mContext = context;
        mDatas = datas;
        layoutInflater = LayoutInflater.from(this.mContext);
        mPager = pager;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
        PinchImageView piv = (PinchImageView) mCurrentView.findViewById(R.id.img);
        mPager.setMainPinchImageView(piv);
    }


    @Override
    public View instantiateItem(ViewGroup container, int position) {
        String imageUrl = mDatas.get(position);
        View view = layoutInflater.inflate(R.layout.item_image, container, false);
        PinchImageView imageView = (PinchImageView) view.findViewById(R.id.img);
        imageView.setOnClickListener(new callBack(position, imageUrl));//处理点击事件
        imageView.setOnLongClickListener(new callBack(position, imageUrl));//处理长按事件
        x.image().bind(imageView,imageUrl);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 事件回调
     */
    private class callBack implements View.OnClickListener, View.OnLongClickListener {
        private int position;
        private String url;

        callBack(int pos, String url) {
            position = pos;
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) mListener.photoClick(position, url);
        }

        @Override
        public boolean onLongClick(View v) {
            if (mListener != null) mListener.photoLongClick(position, url);
            return false;
        }
    }

}
