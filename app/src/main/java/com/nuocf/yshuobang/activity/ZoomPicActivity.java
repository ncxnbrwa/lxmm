package com.nuocf.yshuobang.activity;

import android.view.View;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.myview.PicIndexView;
import com.nuocf.yshuobang.myview.photoWidget.PhotoListener;
import com.nuocf.yshuobang.myview.photoWidget.PhotoTouchView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
/**
* @ClassName: ZoomPicActivity
* @Description: todo(大图浏览器，支持移动，缩放，双击放大及缩小效果)
* @author nichuxiong us yunfeng
* @date 2016/10/12
*/
@ContentView(R.layout.activity_zoom_pic)
public class ZoomPicActivity extends BaseActivity implements PhotoListener,PhotoTouchView.PhotoChangeListener
{
    public static final String INTENTAL_PARAMS_PICURLS="pic_urls";
    public static final String INTENTAL_PARAMS_PIC_INDEX="pic_index";
    @ViewInject(R.id.pic_index)
    private PicIndexView picIndex;
    @ViewInject(R.id.pic_pager)
    private PhotoTouchView mTouchView;
    //当前显示图片的位置
    private int position;
    //存放展示图片的地址(网络地址，本地图片路径)
    public ArrayList<String> drr = null;

    @Event(R.id.header_left_tv)
    private void onClick(View v)
    {
        finish();
    }
    @Override
    protected void initView()
    {
        mTouchView.addListener(this);//点击事件
        mTouchView.setOnPhotoChangeListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        position = getIntent().getExtras().getInt(INTENTAL_PARAMS_PIC_INDEX, 0);
        drr = (ArrayList<String>) receiveInternalActivityParam(INTENTAL_PARAMS_PICURLS);
        if(drr!=null&&!drr.isEmpty()){
            mTouchView.showImages(drr,position);
            picIndex.setTotalPage(drr.size());
            picIndex.setCurrentPage(position);
        }
    }

    @Override
    public void photoClick(int index, String url) {

    }

    @Override
    public void photoLongClick(int index, String url) {

    }

    @Override
    public void onPhotoSelected(int position) {
        picIndex.setCurrentPage(position);
    }
}
