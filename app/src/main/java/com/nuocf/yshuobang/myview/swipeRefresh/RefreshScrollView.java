package com.nuocf.yshuobang.myview.swipeRefresh;

/**
 * Created by yunfeng on 2016/10/31.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class RefreshScrollView extends ScrollView
{
    private OnScrollToBottomListener onScrollToBottom;

    public RefreshScrollView(Context paramContext)
    {
        super(paramContext);
    }

    public RefreshScrollView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
        super.onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
        if ((paramInt2 <= 0) || (this.onScrollToBottom == null))
            return;
        this.onScrollToBottom.onScrollBottomListener(paramBoolean2);
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener paramOnScrollToBottomListener)
    {
        this.onScrollToBottom = paramOnScrollToBottomListener;
    }

    public static abstract interface OnScrollToBottomListener
    {
        public abstract void onScrollBottomListener(boolean paramBoolean);
    }
}
