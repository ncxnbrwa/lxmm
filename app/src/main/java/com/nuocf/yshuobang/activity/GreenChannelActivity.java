package com.nuocf.yshuobang.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.utils.ELS;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_green_channel)
public class GreenChannelActivity extends BaseActivity {
    @ViewInject(R.id.progressBar1)
    private ProgressBar progressBar1;
    @ViewInject(R.id.wv_green)
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁用缩放
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(false);
        //页面文字编译格式
        wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.getSettings().setAppCacheEnabled(true);
        //优先使用缓存
        wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wv.loadUrl(APIConstants.GREEN_CHANNEL);
        wv.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar1.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                progressBar1.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                progressBar1.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Event(value = {R.id.header_left_tv, R.id.register_now})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.header_left_tv:
                finish();
                break;
            case R.id.register_now:
                if (!TextUtils.isEmpty(ELS.getInstance(getBaseActivity()).getStringData(ELS.SESSION_KEY))) {
                    Bundle b = new Bundle();
                    //绿色通道
                    b.putInt(AppointmentActivity.INTENT_BUNDLE_ORDER, Config.ORDER_LVSE);
                    toActivity(AppointmentActivity.class, b);
                } else {
                    getBaseActivity().toActivity(LoginActivity.class, null);
                }
                break;
        }
    }
}
