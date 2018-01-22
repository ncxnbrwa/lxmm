package com.nuocf.yshuobang.myview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nuocf.yshuobang.appBase.ElfApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xiong
 * @ClassName: TimeButton
 * @Description: todo(到计时按钮)
 * @date 2016/10/14
 */

public class TimeButton extends Button implements View.OnClickListener
{
    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String textafter = "秒后重新发送";
    private String textbefore = "获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    Map<String, Long> map = new HashMap<String, Long>();

    public TimeButton(Context context)
    {
        super(context);
        setOnClickListener(this);

    }

    public TimeButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            TimeButton.this.setText("剩余" + time / 1000 + "秒");
            time -= 1000;
            if (time < 0)
            {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textbefore);
                clearTimer();
            }
        }

        ;
    };

    private void initTimer()
    {
        time = lenght;
        t = new Timer();
        tt = new TimerTask()
        {

            @Override
            public void run()
            {
                Log.e("yung", time / 1000 + "");
                han.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer()
    {
        if (tt != null)
        {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l)
    {
        if (l instanceof TimeButton)
        {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v)
    {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
        // t.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy()
    {
        if (ElfApplication.timeMap == null)
            ElfApplication.timeMap = new HashMap<String, Long>();
        ElfApplication.timeMap.put(TIME, time);
        ElfApplication.timeMap.put(CTIME, System.currentTimeMillis());
        clearTimer();
        Log.e("yung", "onDestroy");
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Bundle bundle)
    {
        Log.e("yung", ElfApplication.timeMap + "");
        if (ElfApplication.timeMap == null)
            return;
        if (ElfApplication.timeMap.size() <= 0)// 这里表示没有上次未完成的计时
            return;
        long time = System.currentTimeMillis() - ElfApplication.timeMap.get(CTIME)
                - ElfApplication.timeMap.get(TIME);
        ElfApplication.timeMap.clear();
        if (time > 0)
            return;
        else
        {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }

    /**
     * 设置计时时候显示的文本
     */
    public TimeButton setTextAfter(String text1)
    {
        this.textafter = text1;
        return this;
    }

    /**
     * 设置点击之前的文本
     */
    public TimeButton setTextBefore(String text0)
    {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param lenght 时间 默认毫秒
     * @return
     */
    public TimeButton setLenght(long lenght)
    {
        this.lenght = lenght;
        return this;
    }

}
