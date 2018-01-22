package com.nuocf.yshuobang.myview.photoWidget;

/**
 * =================yangyf================
 *
 * @Author: yangyf
 * @Time : 2016/10/12 16:18
 * @Action :监听图片单机，长按事件回调
 */
public interface PhotoListener {
    void photoClick(int index, String url);//单击

    void photoLongClick(int index, String url);//长按

}  