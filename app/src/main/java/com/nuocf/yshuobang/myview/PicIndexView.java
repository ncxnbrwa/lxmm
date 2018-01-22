package com.nuocf.yshuobang.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.nuocf.yshuobang.R;

/**
 * @author Administrator
 * @ClassName: PicIndexView
 * @Description: todo(实现轮播指示点的view)
 * @date 2016/9/7
 */
public class PicIndexView extends View {
    private int mCurrentPage = -1;
    private int mTotalPage = 0;

    public PicIndexView(Context context) {
        super(context);
    }

    public PicIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTotalPage(int nPageNum) {
        mTotalPage = nPageNum;
        if (mCurrentPage >= mTotalPage)
            mCurrentPage = mTotalPage - 1;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int nPageIndex) {
        if (nPageIndex < 0 || nPageIndex >= mTotalPage)
            return;

        if (mCurrentPage != nPageIndex) {
            mCurrentPage = nPageIndex;
            this.invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //    Log.d("TAG", "on draw...");
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        Rect r = new Rect();
        this.getDrawingRect(r);

        int iconWidth = 9;
        int iconHeight = 9;
        int space = 12;

        int x = (r.width() - (iconWidth * mTotalPage + space * (mTotalPage - 1))) / 2;
        int y = (r.height() - iconHeight) / 2;

        for (int i = 0; i < mTotalPage; i++) {

            int resid = R.mipmap.icon01;
            //当前选中状态的点
            if (i == mCurrentPage) {
                resid = R.mipmap.icon02;
            }
            //绘制图片区域
            Rect r1 = new Rect();
            r1.left = x;
            r1.top = y;
            r1.right = x + iconWidth;
            r1.bottom = y + iconHeight;

            Bitmap bmp = BitmapFactory.decodeResource(getResources(), resid);
            canvas.drawBitmap(bmp, null, r1, paint);

            x += iconWidth + space;

        }

    }
}
