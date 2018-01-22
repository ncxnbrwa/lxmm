package com.nuocf.yshuobang.myview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nuocf.yshuobang.R;

/**
 * 提示框控件*/
public final class DialogView {

	private View mView;
	private TextView tv_title;
	private TextView tv_msg;
	private Button btn_left;
	private Button btn_right;
	private Dialog mDialog;
	private DialogCallBack mCallBack;
	public static final int LEFT_BUTTON = 1;
	public static final int RIGHT_BUTTON = 2;

	public DialogView(Context context, int title, int msg, int btnLeft,
					  int btnRight, DialogCallBack callBack) {
		this(context, context.getString(title), context.getString(msg), context
				.getString(btnLeft), context.getString(btnRight), callBack);
	}

	public DialogView(Context context, String title, String msg,
					  String btnLeft, String btnRight, DialogCallBack callBack) {
		mView = LayoutInflater.from(context).inflate(R.layout.common_dialog,
				null);
		tv_title = (TextView) mView.findViewById(R.id.tv_title);
		tv_msg = (TextView) mView.findViewById(R.id.tv_content);
		btn_left = (Button) mView.findViewById(R.id.btn_positive);
		btn_right = (Button) mView.findViewById(R.id.btn_negative);
		mCallBack = callBack;
		tv_title.setText(title);
		tv_msg.setText(msg);
		btn_left.setText(btnLeft);
		btn_left.setOnClickListener(clickListener);
		btn_right.setText(btnRight);
		btn_right.setOnClickListener(clickListener);
		mDialog = new Dialog(context, R.style.myDialogStyle);
		mDialog.setContentView(mView);
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_positive:
				if (mCallBack != null) {
					mCallBack.onClick(LEFT_BUTTON);
				}
				if (mDialog != null)
					mDialog.dismiss();
				break;

			case R.id.btn_negative:
				if (mCallBack != null) {
					mCallBack.onClick(RIGHT_BUTTON);
				}
				if (mDialog != null)
					mDialog.dismiss();
				break;

			default:
				break;
			}
		}
	};

	public Dialog getDialog() {
		return mDialog;
	}

	public interface DialogCallBack {
		public void onClick(int position);
	}
}
