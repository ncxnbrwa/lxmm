package com.nuocf.yshuobang.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
* @ClassName: WrapListView 
* @Description: TODO(按内容占据手机屏幕，解决与 ScrollView滑动冲突的问题) 
* @author: efun
* @date 2014-7-26 下午1:16:40
 */
public class WrapListView extends ListView {

	public WrapListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public WrapListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
