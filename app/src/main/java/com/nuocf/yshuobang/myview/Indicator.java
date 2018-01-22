package com.nuocf.yshuobang.myview;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.utils.DLog;

/**
* @ClassName: Indicator
* @Description: todo(应用统一加载指示器)
* @author yunfeng
* @date 2016/10/7
*/
public class Indicator extends Dialog {

	private LayoutInflater mInflater;
	private View mLayoutView;
	private int mlayout;
	private int mMessageViewID;
	private String mProgressMessage;

	public Indicator(Context context) {
		super(context, R.style.indicator_dialog);
		mlayout = R.layout.prodialog_default;
		mMessageViewID = R.id.progress_message;
	}

	public Indicator(Context context, int layout, int messageViewID) {
		super(context, R.style.indicator_dialog);
		mlayout = layout;
		mMessageViewID = messageViewID;
	}

	protected void initMessageViewID(Context context, String id) {
		Resources resources = context.getResources();
		final String pkgName = context.getPackageName();
		mMessageViewID = resources.getIdentifier(id, "id", pkgName);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		DLog.d("Dialog", "onCreate");
		mInflater = this.getLayoutInflater();
		mLayoutView = mInflater.inflate(mlayout, null);
		setContentView(mLayoutView);
		setDialogSize();
		setMessage(mProgressMessage);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		this.setCanceledOnTouchOutside(false);
		super.show();
		DLog.d("Dialog", "show");
	}

	//设置对话框大小
	private void setDialogSize() {
		LayoutParams layoutParams = getWindow().getAttributes();
		layoutParams.width = (int) (ScreenInfo.screenWidth * 0.75);
		layoutParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		layoutParams.dimAmount=0f;
		getWindow().setAttributes(layoutParams);
	}

	/**
	 * @param layout
	 *            TextView的ID
	 * @param title
	 * 		对话框的标题
	 */
	public void setTitle(int layout, CharSequence title) {
		if (mLayoutView != null) {
			TextView titleTV = (TextView) mLayoutView.findViewById(layout);
			if (!TextUtils.isEmpty(title)) {
				titleTV.setText(title);
			}
		}
	}

	/**
	 * @param message
	 * 设置的对话框内容
	 * 有内容则出现，无内容就隐藏
	 */
	public void setMessage(String message) {
		mProgressMessage = (message != null) ? message : "";
		if (mLayoutView != null) {
			TextView messageTV = (TextView) mLayoutView
					.findViewById(mMessageViewID);
			if (!TextUtils.isEmpty(mProgressMessage)) {
				messageTV.setText(mProgressMessage);
				messageTV.setVisibility(View.VISIBLE);
			} else {
				messageTV.setVisibility(View.GONE);
			}
		}
	}

}
