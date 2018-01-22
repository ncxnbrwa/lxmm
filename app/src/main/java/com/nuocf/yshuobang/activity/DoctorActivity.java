package com.nuocf.yshuobang.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.DoctorDetailsBean;
import com.nuocf.yshuobang.analysis.bean.DoctorDetailsData;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.imagecache.BitmapOptions;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ImageUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author xiong
 * @ClassName: DoctorActivity
 * @Description: todo(医生详情界面)
 * @date 2016/8/29
 */
@ContentView(R.layout.activity_doctor)
public class DoctorActivity extends BaseActivity {
    @ViewInject(R.id.rl_doctor_header)
    private RelativeLayout header;
    @ViewInject(R.id.iv_doctor)
    private ImageView doctorImg;
    @ViewInject(R.id.tv_doctor_name)
    private TextView doctorName;
    @ViewInject(R.id.tv_doctor_position)
    private TextView doctorPosition;
    @ViewInject(R.id.tv_doctor_hospital)
    private TextView doctorHospital;
    @ViewInject(R.id.tv_good_at_content)
    private TextView goodAt;
    @ViewInject(R.id.tv_honor_content)
    private TextView honor;
    @ViewInject(R.id.tv_experiennce_content)
    private TextView experience;
    BitmapOptions options = null;

    @Override
    protected void initView() {
        if (options == null) {
            options = new BitmapOptions();
            options.initMyOptions(ImageView.ScaleType.CENTER_CROP,ImageView.ScaleType.CENTER_CROP,
                    R.mipmap.head_normal,R.mipmap.head_normal);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //头部布局宽高比
        ViewGroup.LayoutParams headerParams = header.getLayoutParams();
        headerParams.height = ScreenInfo.screenWidth * 5 / 9;
        //头部图片宽高大小
        ViewGroup.LayoutParams imgParams = doctorImg.getLayoutParams();
        imgParams.width = ScreenInfo.screenWidth / 4;
        imgParams.height = imgParams.width;
        doctorImg.setLayoutParams(imgParams);
        String doctorID = getIntent().getExtras().getString("doctor_id");
        getDoctorDetails(doctorID);
    }

    @Event(R.id.header_left_tv)
    private void click(View v) {
        DoctorActivity.this.finish();
    }

    /**
     * @param 参数
     * @Description: todo(访问医生详情接口)
     */
    private void getDoctorDetails(String doctorID) {
        final RequestParams params = new RequestParams(APIConstants.GET_DOCTOR_DETAILS);
        params.addQueryStringParameter("doctor_id", doctorID);
        sendGet(params, false, new CusCallBack<String>() {
            @Override
            protected void onCusStart() {
                showIndicator();
            }
            @Override
            public void onSuccess(String result) {
                JSONParser<DoctorDetailsBean> parser = new JSONParser<DoctorDetailsBean>(
                        DoctorDetailsBean.class,
                        new TypeToken<DoctorDetailsBean>() {
                        }.getType(),
                        result);
                DoctorDetailsBean doctorDetailsBean = parser.doSimpleParse();
                DLog.d("doctorDetailsBean", doctorDetailsBean.toString());
                setUI(doctorDetailsBean);
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

    /**
     * @Description: todo(把相应数据设置到指定控件位置)
     */
    private void setUI(DoctorDetailsBean doctorDetailsBean) {
        if (doctorDetailsBean != null) {
            DoctorDetailsData data = doctorDetailsBean.getData();
            doctorName.setText(data.getDoctor_name());
            doctorPosition.setText(data.getDoctor_position());
            doctorHospital.setText(data.getHospital_name() + "  "
                    + data.getSection_name());
            goodAt.setText(data.getGood_at());
            honor.setText(data.getHonor());
            experience.setText(data.getWork_experience());
            x.image().bind(doctorImg, data.getDoctor_head(), options.getOptions()
                    , new Callback.CommonCallback<Drawable>() {
                        @Override
                        public void onSuccess(Drawable result) {
                            Bitmap bmp = ImageUtil.toRoundBitmap(
                                    ImageUtil.convertDrawable2BitmapByCanvas(result));
                            doctorImg.setImageBitmap(bmp);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
        }
    }
}
