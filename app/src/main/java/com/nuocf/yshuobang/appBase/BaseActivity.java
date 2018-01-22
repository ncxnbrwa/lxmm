package com.nuocf.yshuobang.appBase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.activity.LoginActivity;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.http.RequestMethod;
import com.nuocf.yshuobang.myview.Indicator;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ELS;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


//基础activity
public abstract class BaseActivity extends FragmentActivity {
    public ElfApplication mElfApp = null;
    public RequestMethod mRequestMethod = null;
    public Indicator mIndicator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //输出Debug信息
        DLog.d(getClass().getSimpleName(), "onCreate");
        x.view().inject(this);
        initView();
        mRequestMethod = new RequestMethod();
        mElfApp = ElfApplication.getInstance();
        //加activity
        mElfApp.pushActivity(this);
        //初始化分辨率
        ScreenInfo.initScreent(this);
    }

    @Override
    protected void onResume() {
        DLog.d(getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    protected abstract void initView();

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        return (T) this.findViewById(viewId);
    }

    /**
     * 获取当前Activity
     *
     * @return BaseActivity
     */
    public BaseActivity getBaseActivity() {
        return this;
    }

    /**
     * 跳转至其他Activity
     *
     * @param cls    Activity class
     * @param bundle android.os.bundle
     */
    //页面跳转的方法
    public void toActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    @Override
    protected void onPause() {
        DLog.d(getClass().getSimpleName(), "onPause");
        super.onPause();
    }

    @Override
    public void finish() {
        //关闭加载指示器
        dismissIndicator();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        DLog.d(getClass().getSimpleName(), "onDestroy");
        //activity销毁时弹出栈
        mElfApp.popActivity(this);
        DLog.d(".mActivityStack.size", "" + mElfApp.mActivityStack.size());
        //移除所有映射关系
        mRequestMethod = null;
        super.onDestroy();
    }

    /**
     * @param params      请求接口所需参数
     * @param ifAuth      是否需要签名
     * @param cusCallback 请求回调监听
     */
    public void sendPost(RequestParams params, boolean ifAuth, CusCallBack<String> cusCallback) {
        mRequestMethod.sendPost(params, ifAuth, cusCallback);
    }

    /**
     * @param params      请求接口所需参数
     * @param ifAuth      是否需要签名
     * @param cusCallback 请求回调监听
     */
    public void sendGet(RequestParams params, boolean ifAuth, CusCallBack<String> cusCallback) {
        mRequestMethod.sendGet(params, ifAuth, cusCallback);
    }


    /**
     * @param params      上传接口所需参数
     * @param ifAuth      是否需要签名
     * @param cusCallback 请求回调监听
     */
    public void upLoadFile(RequestParams params, boolean ifAuth, CusCallBack<String> cusCallback) {
        mRequestMethod.upLoadFile(params, ifAuth, cusCallback);
    }

    /**
     * @param params      下载所需参数
     * @param ifAuth      是否需要签名
     * @param cusCallback 请求回调监听
     */
    public <T> Callback.Cancelable downLoadFile(RequestParams params, boolean ifAuth, Callback.CommonCallback<File> cusCallback){
        return mRequestMethod.downLoadFile(params, ifAuth, cusCallback);
    }

    /**
     * sessionkey 失效
     */
    public void sessionKeyInvalid() {
        showToast(getResources().getString(R.string.login_invalid));
        ELS.getInstance(this).clearUserInfo();
        Bundle b = new Bundle();
        b.putBoolean("back", true);
        toActivity(LoginActivity.class, b);
    }

    //放入传值的参数
    public void setInternalActivityParam(String key, Object object) {
        mElfApp.setInternalActivityParam(key, object);
    }

    //获取传值的参数
    public Object receiveInternalActivityParam(String key) {
        return mElfApp.receiveInternalActivityParam(key);
    }
    //显示页面加载指示器
    public void showIndicator(){
        if(mIndicator==null)
            mIndicator = new Indicator(this);
        if(!mIndicator.isShowing()){
            mIndicator.show();
        }
    }
    //设置页面加载指示器提示内容
    public void setIndicatorMessage(String message){
        if(mIndicator!=null){
            mIndicator.setMessage(message);
        }
    }
    //关闭页面加载指示器
    public void dismissIndicator(){
        if(mIndicator!=null){
            if(mIndicator.isShowing())
                mIndicator.dismiss();
        }
        mIndicator = null;
    }
}
