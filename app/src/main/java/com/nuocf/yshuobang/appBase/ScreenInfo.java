/**
 * 
 */
package com.nuocf.yshuobang.appBase;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.nuocf.yshuobang.utils.DLog;


/**
 * @author luquan
 *
 * 2014-6-18 下午2:58:10
 * 屏幕分辨率的参数设置类
 */
public class ScreenInfo {
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	public static float screenDensity = 0;
	public static void initScreent(Activity act){
		if(screenHeight==0||screenWidth==0){
			DisplayMetrics metrics = new DisplayMetrics();
			Display display = act.getWindowManager().getDefaultDisplay();
			display.getMetrics(metrics);
			screenWidth = metrics.widthPixels;
			screenHeight = metrics.heightPixels;
		    screenDensity = metrics.density;
		    DLog.d("initScreent", "initScreent");
		}
	}

}
