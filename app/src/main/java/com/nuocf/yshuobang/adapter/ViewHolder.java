package com.nuocf.yshuobang.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;

import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.imagecache.ImageHolder;


@SuppressWarnings("unchecked")
public class ViewHolder {
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
	
	public static ImageHolder get(Activity context,View view,boolean showDialog) {
		ImageHolder imageHolder = (ImageHolder) view.getTag(-10);
		if (imageHolder == null) {
			imageHolder = new ImageHolder(context,showDialog);
			LayoutParams param = (LayoutParams) imageHolder.downloadImgIV.getLayoutParams(); 
			param.width = ScreenInfo.screenWidth/2;
			param.height = param.width;
			imageHolder.downloadImgIV.setLayoutParams(param);
			view.setTag(-10,imageHolder);
		}
		return imageHolder;
	}
	
	public static ImageHolder get(View view,boolean showDialog) {
		ImageHolder imageHolder = (ImageHolder) view.getTag(-10);
		if (imageHolder == null) {
			imageHolder = new ImageHolder(view,showDialog);
			LayoutParams param = (LayoutParams) imageHolder.downloadImgIV.getLayoutParams(); 
			param.width = ScreenInfo.screenWidth/2;
			param.height = param.width;
			imageHolder.downloadImgIV.setLayoutParams(param);
			view.setTag(-10,imageHolder);
		}
		if(showDialog){
			imageHolder.downloadProgressTV.setVisibility(View.VISIBLE);
			imageHolder.downloadProgressTV.setText("0%");
		}
		return imageHolder;
	}
	
	/**imageViewWidth 小于0，不处理*/
	public static ImageHolder get(View view,int imageViewWidth,boolean showDialog) {
		ImageHolder imageHolder = (ImageHolder) view.getTag(-10);
		if (imageHolder == null) {
			imageHolder = new ImageHolder(view,showDialog);
			if(imageViewWidth>0){
					LayoutParams param = (LayoutParams) imageHolder.downloadImgIV.getLayoutParams(); 
					param.width = imageViewWidth;
					param.height =imageViewWidth;
					imageHolder.downloadImgIV.setLayoutParams(param);
			}else{
				imageHolder.downloadImgIV.setScaleType(ScaleType.FIT_CENTER);
			}
			view.setTag(-10,imageHolder);
		}
		if(showDialog){
			imageHolder.downloadProgressTV.setVisibility(View.VISIBLE);
			imageHolder.downloadProgressTV.setText("0%");
		}
		return imageHolder;
	}
	
	public static <T extends View> T get(View view, int id,int width) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
			LayoutParams param =  (LayoutParams) childView.getLayoutParams(); 
			param.width = width;
			param.height =width;
			childView.setLayoutParams(param);
		}
		return (T) childView;
	}
}
