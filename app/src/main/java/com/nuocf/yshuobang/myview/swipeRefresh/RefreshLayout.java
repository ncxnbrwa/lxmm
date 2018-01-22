package com.nuocf.yshuobang.myview.swipeRefresh;

/**
 * Created by yunfeng on 2016/10/31.
 */

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nuocf.yshuobang.R;

public class RefreshLayout extends SwipeRefreshLayout
{
    private boolean isLoading = false;
    private boolean isOpenLoad = true;
    private LinearLayout llSc;
    private int mLastY;
    private View mListViewFooter;
    private OnLoadListener mOnLoadListener;
    private int mTouchSlop;
    private int mYDown;
    private RefreshScrollView scrollView;
    float lastx = 0;
    float lasty = 0;
    boolean ismovepic = false;

    public RefreshLayout(Context paramContext)
    {
        this(paramContext, null);
    }

    public RefreshLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        Resources res = getResources();
        //通过颜色资源文件设置进度动画的颜色资源
        setColorSchemeColors(res.getColor(R.color.refresh_color_1),
                res.getColor(R.color.refresh_color_2),
                res.getColor(R.color.refresh_color_3),
                res.getColor(R.color.refresh_color_4));
        this.mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
        this.mListViewFooter = LayoutInflater.from(paramContext).inflate(R.layout.pull_to_load_footer, null, false);
    }

    private void getListView()
    {
        if (getChildCount() <= 0)
            return;
        View localView1 = getChildAt(0);
        if (!(localView1 instanceof ScrollView))
            return;
        this.scrollView = ((RefreshScrollView) localView1);
        View localView2 = this.scrollView.getChildAt(0);
        if (localView2 instanceof LinearLayout)
            this.llSc = ((LinearLayout) localView2);
        this.scrollView.setOnScrollToBottomLintener(new RefreshScrollView.OnScrollToBottomListener()
        {
            public void onScrollBottomListener(boolean paramBoolean)
            {
                Log.e("SCROLLVIEW", paramBoolean + "");
                if ((!paramBoolean) || (RefreshLayout.this.mOnLoadListener == null))
                    return;
                RefreshLayout.this.setLoading(true);
            }
        });
        Log.d("View", "### 找到listview");
    }

    private void loadData()
    {
        if (this.mOnLoadListener == null)
            return;
        setLoading(true);
        this.mOnLoadListener.onLoad();
    }

    public boolean isOpenLoad()
    {
        return this.isOpenLoad;
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        if (this.scrollView != null)
            return;
        getListView();
    }

    public void setIsOpenLoad(boolean paramBoolean)
    {
        this.isOpenLoad = paramBoolean;
    }

    public void setLoading(boolean paramBoolean)
    {
        if ((this.isLoading == paramBoolean) || (!this.isOpenLoad))
            return;
        this.isLoading = paramBoolean;
        if (this.isLoading)
        {
            this.llSc.addView(this.mListViewFooter);
            this.mOnLoadListener.onLoad();
            return;
        }
        this.llSc.removeView(this.mListViewFooter);
    }

    public void setOnLoadListener(OnLoadListener paramOnLoadListener)
    {
        this.mOnLoadListener = paramOnLoadListener;
    }

    public static abstract interface OnLoadListener
    {
        public abstract void onLoad();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            lastx = ev.getX();
            lasty = ev.getY();
            ismovepic = false;
            return super.onInterceptTouchEvent(ev);
        }

        int x2 = (int) Math.abs(ev.getX() - lastx);
        int y2 = (int) Math.abs(ev.getY() - lasty);

        //滑动图片最小距离检查
        if (x2 > y2)
        {
            if (x2 >= 100) ismovepic = true;
            return false;
        }

        //是否移动图片(下拉刷新不处理)
        if (ismovepic)
        {
            return false;
        }
        boolean isok = super.onInterceptTouchEvent(ev);

        return isok;
    }
}