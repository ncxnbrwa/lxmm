package com.nuocf.yshuobang.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.APIConstants;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
* @ClassName: CommonProblemActivity
* @Description: todo(常见问题页面)
* @author xiong
* @date 2016/8/28
*/
@ContentView(R.layout.activity_common_problem)
public class CommonProblemActivity extends BaseActivity {
    @ViewInject(R.id.progressBar1)
    private ProgressBar progressBar1;
    @ViewInject(R.id.wv_problem)
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(false);
        wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.getSettings().setAppCacheEnabled(true);
        //优先使用缓存
        wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wv.loadUrl(APIConstants.COMMON_PROBLEM);
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

    @Event(R.id.header_left_tv)
    private void click(View v){
        CommonProblemActivity.this.finish();
    }
}
