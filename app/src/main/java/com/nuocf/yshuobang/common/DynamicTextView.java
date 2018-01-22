package com.nuocf.yshuobang.common;

import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.BaseActivity;

/**
 * @author xiong
 * @ClassName: DynamicTextView
 * @Description: todo(动态添加textView的类, 一行由一个linearLayout构成)
 * @date 2016/9/23
 */

public class DynamicTextView {
    public BaseActivity act;

    public DynamicTextView(BaseActivity act) {
        this.act = act;
    }

    //设置一行疾病的数据的view
    public LinearLayout creatLineLayout() {
        LinearLayout line = new LinearLayout(act);
        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        line.setOrientation(LinearLayout.HORIZONTAL);
        return line;
    }

    //设置每个疾病项的view
    public TextView creatDiseaseText(String diseaseName) {
        TextView columnTextView = new TextView(act);
        columnTextView.setBackgroundResource(R.drawable.textview_bg);
        columnTextView.setGravity(Gravity.CENTER);
        columnTextView.setPadding(10, 5, 10, 5);
        columnTextView.setText(diseaseName);
        columnTextView.setTextSize(14f);
        int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        columnTextView.measure(width, height);
        return columnTextView;
    }

    public float getTextWidth(TextView tv,String text){
        TextPaint textPaint = tv.getPaint();
        float textPaintWidth = textPaint.measureText(text);
        return textPaintWidth;
    }

}
