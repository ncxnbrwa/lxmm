package com.nuocf.yshuobang.myview.photoWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


import com.nuocf.yshuobang.R;

import java.util.ArrayList;
import java.util.List;

/**
 /**
 * =================yangyf================
 *
 * @Author: yangyf
 * @Time : 2016/10/12 16:18
 * @Action :PhotoTouchView,图片缩放朱控件
 */
public class PhotoTouchView extends LinearLayout implements TouchViewPager.OnPageChangeListener {
    private Context mContext;
    private TouchViewPager mViewPager;
    private ImageTouchAdapter mAdapter;

    private ArrayList<String> mImageList;
    private int mCurrentIndex;

    /*<=========================================共有方法===============================================>*/

    /**
     * 点击和长按监听
     *
     * @param listener
     */
    public void addListener(PhotoListener listener) {
        mAdapter.addListener(listener);
    }

    /**
     * 获取当前浏览的图片下标
     *
     * @return
     */
    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    /**
     * 设置当前浏览的下标
     *
     * @param currentIndex
     */
    public void setCurrentIndex(int currentIndex) {
        if (mImageList != null && mImageList.size() > currentIndex) {
            mCurrentIndex = currentIndex;
            mViewPager.setCurrentItem(mCurrentIndex);
        }
    }

    /**
     * 显示一组图片,当前展示第一张
     *
     * @param imageList
     */
    public void showImages(ArrayList<String> imageList) {
        mImageList.clear();
        mImageList.addAll(imageList);
        mAdapter.notifyDataSetChanged();
        mCurrentIndex = 0;
        mViewPager.setCurrentItem(mCurrentIndex);
    }

    /**
     * 显示一组图片,当前展示第一张
     *
     * @param beanList 图片的数据源,由一组Bean组成,bean必须实现ImageUrl接口
     */
    public void showImages(List<? extends ImageUrl> beanList) {
        mImageList.clear();
        for (ImageUrl bean : beanList) {
            mImageList.add(bean.getUrl());
        }
        mAdapter.notifyDataSetChanged();
        if (mImageList != null && mImageList.size() > mCurrentIndex)
            mViewPager.setCurrentItem(mCurrentIndex);
    }

    /**
     * 显示一组图片并指定当前显示位置
     *
     * @param beanList
     * @param currentIndex
     */
    public void showImages(List<? extends ImageUrl> beanList, int currentIndex) {
        mImageList.clear();
        for (ImageUrl bean : beanList) {
            mImageList.add(bean.getUrl());
        }
        mAdapter.notifyDataSetChanged();
        if (mImageList != null && mImageList.size() > currentIndex) {
            mCurrentIndex = currentIndex;
            mViewPager.setCurrentItem(mCurrentIndex);
        }
    }

    /**
     * 显示一组图片并指定当前显示位置
     *
     * @param imageList
     * @param currentIndex
     */
    public void showImages(ArrayList<String> imageList, int currentIndex) {
        mImageList.clear();
        mImageList.addAll(imageList);
        mAdapter.notifyDataSetChanged();
        if (mImageList != null && mImageList.size() > currentIndex) {
            mCurrentIndex = currentIndex;
            mViewPager.setCurrentItem(mCurrentIndex);
        }
    }

    public void showOneImage(String url) {
        mImageList.clear();
        mImageList.add(url);
        mAdapter.notifyDataSetChanged();
        mCurrentIndex = 0;
        mViewPager.setCurrentItem(mCurrentIndex);
    }

    /**
     * 更改数据源后可以手动刷新
     */
    public void update() {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 保存当前浏览的图片到本地相册
     */
    public void saveCurrentImage(SaveImageCall imageCall) {
        saveCurrent2local(imageCall);
    }


    /*<========================================================================================>*/
    public PhotoTouchView(Context context) {
        this(context, null);
        this.mContext = context;
        includeLayout();
        initViewPager();
    }

    public PhotoTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        includeLayout();
        initViewPager();
    }

    private void initViewPager() {
        mImageList = new ArrayList<>();
        mAdapter = new ImageTouchAdapter(mContext, mImageList, mViewPager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    private void includeLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_photo, null);
        mViewPager = (TouchViewPager) view.findViewById(R.id.viewpager);
        this.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentIndex = position;
        if(mPhotoChangeListener!=null){
            mPhotoChangeListener.onPhotoSelected(mCurrentIndex);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void saveCurrent2local(final SaveImageCall imageCall) {
        String url = mImageList.get(getCurrentIndex());
//        Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        BitmapUtil.saveImage2Gallery(mContext, resource, imageCall);
//                    }
//                }).start();
//
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                if (imageCall != null) imageCall.onFault();
//            }
//        });
    }

    public interface PhotoChangeListener{
        void onPhotoSelected(int position);
    }

    public void setOnPhotoChangeListener(PhotoChangeListener listener){
        mPhotoChangeListener=listener;
    }
    private PhotoChangeListener mPhotoChangeListener;
}  