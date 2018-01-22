package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.MainPagerAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.UpdateBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ELS;
import com.nuocf.yshuobang.utils.HTimeUtils;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @ClassName: MainActivity
 * @Description: todo(主界面)
 * @author xiong
 * @date 2016/8/26
 */

/**
 * @ClassName: MainActivity
 * @Description: todo(主界面)
 * @author yunfeng
 * @date 2016/8/31
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity
{
    @ViewInject(R.id.rg_whole)
    RadioGroup radioGroup;
    @ViewInject(R.id.rb_home)
    private RadioButton rb_home;
    @ViewInject(R.id.rb_recommend)
    private RadioButton rb_commend;
    @ViewInject(R.id.rb_health_news)
    private RadioButton rb_news;
    @ViewInject(R.id.rb_mine)
    private RadioButton rb_mine;
    @ViewInject(R.id.vp_whole)
    private ViewPager vp_whole;

    private static final int MESSAGE_UPDATE = 1601;
    /**
     * @Fields  :todo(用一句话描述这个变量表示什么)
     */
    //主页切换四个界面的viewPager
    MainPagerAdapter AMainPagerAdapter;

    /**
     * @Title:
     * @Description: todo(这里用一句话描述这个方法的作用)
     * @param     参数
     * @return 返回类型
     * @throws
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 检测新版本
        handler.sendEmptyMessageDelayed(MESSAGE_UPDATE, 2000);
    }

    @Override
    protected void initView()
    {
        rb_home.setChecked(true);
        AMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        vp_whole.setAdapter(AMainPagerAdapter);
        vp_whole.setOffscreenPageLimit(3);
        //主页面ViewPager的监听事件
        vp_whole.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        rb_home.setChecked(true);
                        break;
                    case 1:
                        rb_commend.setChecked(true);
                        break;
                    case 2:
                        rb_news.setChecked(true);
                        break;
                    case 3:
                        rb_mine.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        //底部radioGroup的监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.rb_home:
                        vp_whole.setCurrentItem(0, false);
                        break;
                    case R.id.rb_recommend:
                        vp_whole.setCurrentItem(1, false);
                        break;
                    case R.id.rb_health_news:
                        vp_whole.setCurrentItem(2, false);
                        break;
                    case R.id.rb_mine:
                        vp_whole.setCurrentItem(3, false);
                        break;
                }
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * @Fields  :todo(按两次退出的计数)
     */
    int clickCount = 1;
    /**
     * @Fields  :todo(按两次退出时传值的handler)
     */
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0x123:
                    clickCount--;
                    break;
                case MESSAGE_UPDATE:
                    int today = HTimeUtils.getDate()[2];
                    int updateDay = ELS.getInstance(getBaseActivity()).getIntData(
                            ELS.UPDATE_VERSION_DATE, 0);
                    // 3天检查一次
                    int differ = today - updateDay;
                    if (differ >= UpdateActivity.UPDATE_DAYS || differ < 0)
                    {
                        checkAppUpdate();
                    }
                    break;
            }
        }
    };

    /**
     * @Description: todo(按两次退出APP)
     */
    private void clickTwiceExit()
    {
        if (clickCount == 1)
        {
            clickCount++;
            showToast("快速按两次返回键退出");
            handler.sendEmptyMessageDelayed(0x123, 2000);
        } else if (clickCount == 2)
        {
            handler.removeMessages(0x123);
            mElfApp.finishApplication();
        }
    }

    //键盘的监听事件
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            clickTwiceExit();
        }
        return false;
    }

    //检查更新
    private void checkAppUpdate()
    {
        RequestParams params = new RequestParams(APIConstants.CHECK_UPDATE);
        params.addQueryStringParameter("version", Config.CLIENT_VERSION);
        sendGet(params, false, new CusCallBack<String>()
        {

            @Override
            public void onSuccess(String result)
            {
                JSONParser<UpdateBean> parser = new JSONParser<UpdateBean>(UpdateBean.class
                        , new TypeToken<UpdateBean>()
                {
                }.getType(), result);
                UpdateBean updateBean = parser.doSimpleParse();
                DLog.d("updateBean", updateBean.toString());
                if (updateBean.getState().equals("0"))
                {
                    if (updateBean != null && updateBean.getData() != null)
                    {
                        if (!updateBean.getData().getVersion_name().equals(Config.CLIENT_VERSION))
                        {
                            setInternalActivityParam("version", updateBean.getData());
                            toActivity(UpdateActivity.class, null);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex)
            {
                super.onCancelled(cex);
            }

            @Override
            public void onFinished()
            {
                super.onFinished();
                ELS.getInstance(getBaseActivity()).saveIntData(
                        ELS.UPDATE_VERSION_DATE,
                        HTimeUtils.getDate()[2]);
            }
        });
    }
}
