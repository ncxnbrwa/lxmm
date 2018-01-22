package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.BaseActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_start)
public class StartActivity extends BaseActivity {
    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
//            if (TextUtils.isEmpty(ELS.getInstance(getBaseActivity())
//                    .getStringData(ELS.SESSION_KEY))) {
//                toActivity(LoginActivity.class, null);
//            } else {
                toActivity(MainActivity.class, null);
//            }
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    protected void initView() {

    }
}
