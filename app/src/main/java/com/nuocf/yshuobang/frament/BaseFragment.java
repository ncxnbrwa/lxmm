package com.nuocf.yshuobang.frament;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.xutils.http.RequestParams;
import org.xutils.x;

import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.http.RequestMethod;
import com.nuocf.yshuobang.utils.DLog;

/**
 * @author luquan
 *
 * 2014-8-28 上午11:22:56
 */
public abstract class BaseFragment extends Fragment {
	public RequestMethod mRequestMethod = new RequestMethod();;
	boolean injected = false;
	// 标志位，标志已经初始化完成。

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mRequestMethod = new RequestMethod();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		injected = true;
		return x.view().inject(this,inflater,container);
	}

	//XUtils必写方法，初始view
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(!injected){
			x.view().inject(this,this.getView());
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	public void showToast(String msg) {
		Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		return (T) this.getActivity().findViewById(viewId);
	}
	public BaseActivity getBaseActivity(){
		return (BaseActivity)getActivity();
	}
	@Override
	public void onPause() {
		DLog.d(getClass().getSimpleName(), "onPause");
		super.onPause();
	}

	@Override
	public void onDestroy() {
		DLog.d(getClass().getSimpleName(), "onDestroy");
		super.onDestroy();
	}

	protected boolean isVisible;
	/**
	 * 在这里实现Fragment数据的缓加载.
	 * @param isVisibleToUser
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}
	protected void onVisible(){
		lazyLoad();
	}
	protected abstract void lazyLoad();
	protected void onInvisible(){}

	/**
	 * @param params     请求接口所需参数
	 * @param ifAuth     是否需要签名
	 * @param cusCallback 请求回调监听
	 */
	public void sendPost(RequestParams params,boolean ifAuth,CusCallBack<String> cusCallback) {
		mRequestMethod.sendPost(params,ifAuth,cusCallback);
	}

	/**
	 * @param params     请求接口所需参数
	 * @param ifAuth     是否需要签名
	 * @param cusCallback 请求回调监听
	 */
	public void sendGet(RequestParams params,boolean ifAuth,CusCallBack<String> cusCallback) {
		mRequestMethod.sendGet(params,ifAuth,cusCallback);
	}


	/**
	 * @param params     上传接口所需参数
	 * @param ifAuth     是否需要签名
	 * @param cusCallback 请求回调监听
	 */
	public void upLoadFile(RequestParams params,boolean ifAuth,CusCallBack<String> cusCallback) {
		mRequestMethod.upLoadFile(params,ifAuth,cusCallback);
	}

	
}
