/**
 * 
 */ 
package com.nuocf.yshuobang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.imagecache.BitmapOptions;

import org.xutils.x;

import java.util.List;

/** 
 * 类说明 :
 * @author  luquan
 * 创建时间：2014-11-3 下午5:12:16 
 */

public class DiseaseImageAdapter extends CommonAdapter<String>{

	/**
	 * @param context
	 * @param datas
	 */
	private Context mContext;
	public BitmapOptions options;
	public DiseaseImageAdapter(Context context, List<String> datas) {
		super(context, datas);
		mContext = context;
		options = new BitmapOptions();
		options.initDefaultOptions();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_published_grida, null);
		}
		ImageView imageView = ViewHolder.get(convertView,R.id.item_grida_image,(ScreenInfo.screenWidth-30)/5-10);
		x.image().bind(imageView,mDatas.get(position), options.getOptions());
		return convertView;
	}

	public int getCount() {
		return mDatas.size();
	}

}
 