package com.nuocf.yshuobang.myview.swipeRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by yunfeng on 2016/10/31.
 */
public class ScrollLinerLayout extends LinearLayout
{
    private Scroller mScroller;
    private boolean pressed = true;

    public ScrollLinerLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    private void init(Context paramContext)
    {
        this.mScroller = new Scroller(paramContext);
    }

    public void computeScroll()
    {
        if (!this.mScroller.computeScrollOffset())
            return;
        scrollTo(this.mScroller.getCurrX(), 0);
        postInvalidate();
    }

    public int getToX()
    {
        return this.mScroller.getCurrX();
    }

    public void onDown()
    {
        if (this.mScroller.isFinished())
            return;
        this.mScroller.abortAnimation();
    }

    public void setPressed(boolean paramBoolean)
    {
        if (this.pressed)
        {
            super.setPressed(paramBoolean);
            return;
        }
        super.setPressed(this.pressed);
    }

    public void setSingleTapUp(boolean paramBoolean)
    {
        this.pressed = paramBoolean;
    }

    public void snapToScreen(int paramInt)
    {
        this.mScroller.startScroll(paramInt, 0, 0, 0, 50);
        invalidate();
    }
}