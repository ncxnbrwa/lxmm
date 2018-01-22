/**
 * 
 */
package com.nuocf.yshuobang.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * @author luquan
 *
 * 2014-9-29 上午11:19:25
 */
public class CommonPopupWindow {
	
	private View contentView;
	private PopupWindow mPopupWindow;
	public CommonPopupWindow(Activity context,int layoutId){
		LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		contentView =  inflater.inflate(layoutId, null);
	}

	@SuppressWarnings("deprecation")
	public PopupWindow getPopupWindow(int width) {
		if(mPopupWindow==null){
			mPopupWindow = new PopupWindow(contentView,width,ViewGroup.LayoutParams.FILL_PARENT);  
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());  //1 指定popupwindow的背景   2 popupwindow能够获得焦点  
			mPopupWindow.setFocusable(true);
		}
		return mPopupWindow;  
	}
	
	public View getContentView(){
		return contentView;
	}

}
