package com.nuocf.yshuobang.activity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.Process.AppointmentObj;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.ReserveBean;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.appBase.ElfApplication;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author xiong
 * @ClassName: AddPatientActivity
 * @Description: todo(手术预约，海外医疗，绿色通道 添加就诊人界面)
 * @date 2016/8/29
 */
@ContentView(R.layout.activity_add_patient)
public class AddPatientActivity extends BaseActivity
{
    @ViewInject(R.id.et_patient_name)
    private EditText et_patientName;
    @ViewInject(R.id.et_patient_age)
    private EditText et_patientAge;
    @ViewInject(R.id.et_patient_number)
    private EditText et_patientNumber;
    @ViewInject(R.id.rg_sex)
    private RadioGroup sexGroup;
    @ViewInject(R.id.rb_man)
    private RadioButton man;
    @ViewInject(R.id.rb_woman)
    private RadioButton woman;

    private AppointmentObj mAppointment;

    @Override
    protected void initView()
    {
        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.rb_man:
                        mAppointment.setSex(1);
                        break;
                    case R.id.rb_woman:
                        mAppointment.setSex(2);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mAppointment = (AppointmentObj) receiveInternalActivityParam(AppointmentActivity.INTERNAL_PARAM_APPOINT);
        if (mAppointment == null)
        {
            mAppointment = new AppointmentObj();
        } else
        {
            //若有传值，则设置相应数据
            if (!TextUtils.isEmpty(mAppointment.getFpatientName()))
            {
                et_patientName.setText(mAppointment.getFpatientName());
            }
            if (!TextUtils.isEmpty(mAppointment.getFpatientAge()))
            {
                et_patientAge.setText(mAppointment.getFpatientAge());
            }
            if (!TextUtils.isEmpty(mAppointment.getFpatientNumber()))
            {
                et_patientNumber.setText(mAppointment.getFpatientNumber());
            }
        }
        if (mAppointment.getSex() == 1)
        {
            man.setChecked(true);
        } else
        {
            woman.setChecked(true);
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.reserve_done})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                setValue();
                setInternalActivityParam(AppointmentActivity.INTERNAL_PARAM_APPOINT, mAppointment);
                AddPatientActivity.this.finish();
                break;
            case R.id.reserve_done:
                setValue();
                RequestParams params = new RequestParams(APIConstants.ORDER_OPERATION);
                params.addBodyParameter("order_gender", Integer.toString(mAppointment.getSex()));
                params.addBodyParameter("order_type", Integer.toString(mAppointment.getType()));
                params.addBodyParameter("disease_des", mAppointment.getDiseaseDetails());
                if (!TextUtils.isEmpty(mAppointment.getFpatientName()))
                {
                    if (mAppointment.getFpatientName().length() <= 6)
                    {
                        params.addBodyParameter("order_name", mAppointment.getFpatientName());
                    } else
                    {
                        showToast("姓名不能大于6位");
                        break;
                    }
                } else
                {
                    showToast("输入姓名");
                    break;
                }
                if (!TextUtils.isEmpty(mAppointment.getCdiseaseName()))
                {
                    params.addBodyParameter("disease_name", mAppointment.getCdiseaseName());
                }
                if (!TextUtils.isEmpty(mAppointment.getFpatientNumber()))
                {
                    if (mAppointment.getFpatientNumber().matches(Config.telRegex))
                    {
                        params.addBodyParameter("order_phone", mAppointment.getFpatientNumber());
                    } else
                    {
                        showToast("请输入正确的电话号码");
                        break;
                    }
                }
                if (!TextUtils.isEmpty(mAppointment.getFpatientAge()))
                {
                    if (mAppointment.getFpatientAge().length() <= 3)
                    {
                        params.addBodyParameter("order_age", mAppointment.getFpatientAge());
                    }else
                    {
                        showToast("年龄不能大于3位");
                        break;
                    }
                }
                if (!TextUtils.isEmpty(mAppointment.getDiseasePhotos()))
                {
                    params.addBodyParameter("disease_photos", mAppointment.getDiseasePhotos());
                }
                sendPost(params, true, new CusCallBack<String>()
                {
                    @Override
                    protected void onCusStart()
                    {
                        showIndicator();
                    }

                    @Override
                    public void onSuccess(String result)
                    {
                        JSONParser<ReserveBean> parser = new JSONParser<ReserveBean>(ReserveBean.class
                                , new TypeToken<ReserveBean>()
                        {
                        }.getType(), result);
                        StateMsg state = parser.getState(getBaseActivity());
                        if (state.getState().equals("0"))
                        {
                            //                        ReserveBean reserveBean = parser.doSimpleParse();
                            showToast("预约成功");
                            toActivity(MainActivity.class, null);
                            //销毁当前流程活动界面
                            ((ElfApplication) getApplication()).finishSpecialActivity(AddPatientActivity.class);
                            ((ElfApplication) getApplication()).finishSpecialActivity(AppointmentActivity.class);
                        } else if (!state.getState().equalsIgnoreCase("10004"))
                        {
                            //用户会话登录失效不提示失败提示
                            showToast(state.getMessage());
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
                        dismissIndicator();
                    }
                });
        }

    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            setValue();
            setInternalActivityParam(AppointmentActivity.INTERNAL_PARAM_APPOINT, mAppointment);
            AddPatientActivity.this.finish();
        }
        return false;
    }

    /**
     * @Description: todo(传值)
     */
    private void setValue()
    {
        mAppointment.setFpatientAge(et_patientAge.getText().toString());
        mAppointment.setFpatientName(et_patientName.getText().toString());
        mAppointment.setFpatientNumber(et_patientNumber.getText().toString());
    }
}
