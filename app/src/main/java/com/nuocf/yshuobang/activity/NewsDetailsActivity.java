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
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author xiong
 * @ClassName: NewsDetailsActivity
 * @Description: todo(资讯详情界面)
 * @date 2016/8/26
 */
@ContentView(R.layout.activity_newsdetails)
public class NewsDetailsActivity extends BaseActivity {
    @ViewInject(R.id.progressBar1)
    private ProgressBar progressBar1;
    @ViewInject(R.id.wb_newsdetail)
    private WebView wbNewsDetail;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getExtras().getString("news_url");
        DLog.d("news_url", url);
        wbNewsDetail.setVisibility(View.GONE);
        wbNewsDetail.getSettings().setJavaScriptEnabled(true);
        wbNewsDetail.getSettings().setBuiltInZoomControls(false);
        wbNewsDetail.getSettings().setDefaultTextEncodingName("utf-8");
        wbNewsDetail.getSettings().setAppCacheEnabled(true);
        //优先使用缓存
        wbNewsDetail.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wbNewsDetail.loadUrl(url);
        wbNewsDetail.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar1.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });
        wbNewsDetail.setWebViewClient(new WebViewClient() {

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
                wbNewsDetail.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }


        });
    }

    @Override
    protected void initView() {

    }

    @Event(R.id.header_left_tv)
    private void click(View v) {
        NewsDetailsActivity.this.finish();
    }
}
