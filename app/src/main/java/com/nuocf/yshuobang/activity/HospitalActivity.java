package com.nuocf.yshuobang.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.HospitalDetailsBean;
import com.nuocf.yshuobang.analysis.bean.HospitalDetailsData;
import com.nuocf.yshuobang.analysis.bean.HospitalDetailsSection;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.common.DynamicTextView;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.imagecache.BitmapOptions;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author xiong
 * @ClassName: HospitalActivity
 * @Description: todo(医院详情界面)
 * @date 2016/8/29
 */

@ContentView(R.layout.activity_hospital)
public class HospitalActivity extends BaseActivity{
    @ViewInject(R.id.iv_hospital)
    private ImageView headImg;
    @ViewInject(R.id.tv_hospital_name)
    private TextView hospitalName;
    @ViewInject(R.id.tv_hospital_level)
    private TextView hospitalLevel;
    @ViewInject(R.id.tv_hospital_details)
    private TextView hospitalDetails;
    @ViewInject(R.id.ll_disease)
    private LinearLayout diseasell;

    HospitalDetailsBean hospitalDetailsBean = null;
    private String mHospitalId;
    private BitmapOptions options = null;
    int screenWidth = 0;
    DynamicTextView mDynamicTextView;
    String cSectionName, cSectionId;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        Intent intent = getIntent();
        mHospitalId = intent.getExtras().getString("hospital_id");
        if (options == null) {
            options = new BitmapOptions();
            options.initMyOptions(ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_XY,
                    R.mipmap.doctor_img, R.mipmap.doctor_img);
        }
        mDynamicTextView = new DynamicTextView(getBaseActivity());
    }

    @Event(value = {R.id.header_left_tv, R.id.hospital_section_rl})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.header_left_tv:
                HospitalActivity.this.finish();
                break;
            case R.id.hospital_section_rl:
                Bundle bundle = new Bundle();
                if (TextUtils.isEmpty(cSectionName)) {
                    bundle.putString("section_name", cSectionName);
                }
                if (TextUtils.isEmpty(cSectionId)) {
                    bundle.putString("section_id", cSectionId);
                }
                bundle.putString("hospital_id", mHospitalId);
                toActivity(DoctorListActivity.class, bundle);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        screenWidth = ScreenInfo.screenWidth - 10;
        //    screenWidth = PixelUtil.px2dp(screenWidth);
        //设置头部图片的宽高比
        ViewGroup.LayoutParams headParams = headImg.getLayoutParams();
        headParams.height = ScreenInfo.screenWidth * 5 / 9;
        headImg.setLayoutParams(headParams);
        if (hospitalDetailsBean != null) {
        } else {
            getHospitalDetails();
        }
    }

    //访问医院详情接口
    private void getHospitalDetails() {
        RequestParams params = new RequestParams(APIConstants.HOSPTAL_DETAILS);
        params.addQueryStringParameter("hospital_id", mHospitalId);
        sendGet(params, false, new CusCallBack<String>() {
            @Override
            protected void onCusStart() {
                showIndicator();
            }

            @Override
            public void onSuccess(String result) {
                JSONParser<HospitalDetailsBean> parser = new JSONParser<HospitalDetailsBean>(
                        HospitalDetailsBean.class,
                        new TypeToken<HospitalDetailsBean>() {
                        }.getType()
                        , result);
                hospitalDetailsBean = parser.doSimpleParse();
                setUIData(hospitalDetailsBean);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                super.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                dismissIndicator();
            }
        });
    }

    //设置对应的UI控件
    private void setUIData(HospitalDetailsBean bean) {
        if (bean != null) {
            if (Integer.parseInt(bean.getState()) == 0) {
                HospitalDetailsData data = bean.getData();
                x.image().bind(headImg, data.getHospital_big_img(), options.getOptions());
                hospitalName.setText(data.getHospital_name());
                hospitalLevel.setText(data.getHospital_level());
                hospitalDetails.setText(data.getHospital_des());
                setHotDiseaseColumn(data.getSection());
            } else if (Integer.parseInt(bean.getState()) == 10004) {
                sessionKeyInvalid();
            } else {
                showToast(bean.getMessage());
            }
        }else {
            showToast("其他错误");
        }
    }

    //添加疾病选项
    private void setHotDiseaseColumn(List<HospitalDetailsSection> sections) {
        //判断数据集是否有效
        if (sections != null && !sections.isEmpty()) {
            //初始化第一行布局，并添加到容器的linearlayout
            LinearLayout rowLayout = mDynamicTextView.creatLineLayout();
            diseasell.addView(rowLayout);
            //记录单行已用的宽
            int textWidth = 0;
            final int tId = 0;
            for (int i = 0; i < sections.size(); i++) {
                HospitalDetailsSection section = sections.get(i);
                String diseaseName = section.getSection_name();
                //创建显示的textView
                TextView diseaseView = mDynamicTextView.creatDiseaseText(diseaseName);
                diseaseView.setTag(section.getSection_id());
                //设置点击监听
//                diseaseView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (v.isSelected()) {
//                            v.setSelected(false);
//                            cSectionId = null;
//                            cSectionName = null;
//                        } else {
//                            //遍历所有子控件，设置未选中状态
//                            for (int i = 0; i < diseasell.getChildCount(); i++) {
//                                LinearLayout line = (LinearLayout) diseasell.getChildAt(i);
//                                for (int j = 0; j < line.getChildCount(); j++) {
//                                    TextView tv = (TextView) line.getChildAt(j);
//                                    tv.setSelected(false);
//                                }
//                            }
//                            v.setSelected(true);
//                            cSectionName = ((TextView) v).getText().toString();
//                            cSectionId = (String) v.getTag();
//                        }
//                    }
//                });
                //计算当前textview宽度,加上控件左右边距
                int width = diseaseView.getMeasuredWidth();
                //设置当前textview在布局中的位置
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        width, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 5;
                params.rightMargin = 5;
                params.topMargin = 5;
                //大于屏幕宽，放不下控件则新起一行
                textWidth += width + 20;
                DLog.d("hospitalact", "width:" + width + " usedWidth:" + textWidth + " screenW:" + screenWidth);
                if (textWidth > screenWidth) {
                    //创建下一行布局
                    rowLayout = mDynamicTextView.creatLineLayout();
                    //添加到容器中
                    diseasell.addView(rowLayout);
                    //添加下一行首个textview
                    rowLayout.addView(diseaseView, params);
                    //记录当前所在行已用宽高
                    textWidth = width + 20;
                } else {
                    //当前行添加textview
                    rowLayout.addView(diseaseView, params);
                }
            }
        }
    }
}
