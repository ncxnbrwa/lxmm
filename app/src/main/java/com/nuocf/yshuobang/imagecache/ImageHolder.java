/**
 * 
 */
package com.nuocf.yshuobang.imagecache;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuocf.yshuobang.R;


/**
 * @author luquan
 *
 * 2014-6-13 上午10:44:11
 */
public class ImageHolder {

	public ImageView downloadImgIV;
	public TextView downloadProgressTV;
	public ImageHolder(Activity act,boolean showProgress) {
		downloadImgIV = (ImageView) act.findViewById(R.id.image_download_iv);
		downloadProgressTV = (TextView) act.findViewById(R.id.image_download_tv);
		if(showProgress){
			downloadProgressTV.setVisibility(View.VISIBLE);
		}
	}

	public ImageHolder(View view,boolean showProgress) {
		downloadImgIV = (ImageView) view.findViewById(R.id.image_download_iv);
		downloadProgressTV = (TextView) view.findViewById(R.id.image_download_tv);
		if(showProgress){
			downloadProgressTV.setVisibility(View.VISIBLE);
		}
	}

	public ImageHolder(Context context){
		downloadImgIV = new ImageView(context);
	}

}
