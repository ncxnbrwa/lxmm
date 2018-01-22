package com.nuocf.yshuobang.activity;

import android.os.Bundle;
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
import com.nuocf.yshuobang.analysis.bean.InterrogationBean;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.ElfApplication;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author yunfeng
 * @ClassName: AddInterrogatonPatientActivity
 * @Description: todo(快速问诊就诊人资料页面)
 * @date 2016/9/30
 */
@ContentView(R.layout.activity_add_interrogaton_patient)
public class AddInterrogatonPatientActivity extends BaseActivity
{
    @ViewInject(R.id.et_patient_name)
    private EditText et_patientName;
    @ViewInject(R.id.rg_sex)
    private RadioGroup sexGroup;
    @ViewInject(R.id.rb_man)
    private RadioButton man;
    @ViewInject(R.id.rb_woman)
    private RadioButton woman;
    @ViewInject(R.id.et_patient_age)
    private EditText et_patientAge;

    private AppointmentObj mAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

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
    protected void onStart()
    {
        super.onStart();
        mAppointment = (AppointmentObj) receiveInternalActivityParam(InterrogationSpeedActivity.INTERNAL_PARAM_INTERROGA);
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
        }
        if (mAppointment.getSex() == 1)
        {
            man.setChecked(true);
        } else
        {
            woman.setChecked(true);
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.interrogation_done})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                setValue();
                setInternalActivityParam(InterrogationSpeedActivity.INTERNAL_PARAM_INTERROGA, mAppointment);
                AddInterrogatonPatientActivity.this.finish();
                break;
            case R.id.interrogation_done:
                setValue();
                RequestParams params = new RequestParams(APIConstants.INTERROGATION_SPEED);
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
                params.addBodyParameter("order_gender", Integer.toString(mAppointment.getSex()));
                //疾病名不填时不提交
                if (!TextUtils.isEmpty(mAppointment.getCdiseaseName()))
                {
                    params.addBodyParameter("disease_name", mAppointment.getCdiseaseName());
                }
                //疾病描述必须有内容
                params.addBodyParameter("disease_des", mAppointment.getDiseaseDetails());
                if (!TextUtils.isEmpty(mAppointment.getFpatientAge()))
                {
                    if (mAppointment.getFpatientAge().length() <= 3)
                    {
                        params.addBodyParameter("order_age", mAppointment.getFpatientAge());
                    } else
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
                        JSONParser<InterrogationBean> parser = new JSONParser<InterrogationBean>(InterrogationBean.class
                                , new TypeToken<InterrogationBean>()
                        {
                        }.getType(), result);
                        StateMsg reportState = parser.getState(getBaseActivity());
                        if (reportState.getState().equals("0"))
                        {
                            InterrogationBean interrogationBean = parser.doSimpleParse();
                            if (interrogationBean != null)
                            {
                                showToast("问诊成功");
                                toActivity(MainActivity.class, null);
                                //销毁当前流程活动界面
                                ((ElfApplication) getApplication()).finishSpecialActivity(AddInterrogatonPatientActivity.class);
                                ((ElfApplication) getApplication()).finishSpecialActivity(InterrogationSpeedActivity.class);
                            } else if (!reportState.getState().equalsIgnoreCase("10004"))
                            {
                                //用户会话登录失效不提示失败提示
                                showToast(interrogationBean.getMessage());
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
                        dismissIndicator();
                    }
                });
                break;
        }
    }

    /**
     * @Description: todo(传值)
     */
    private void setValue()
    {
        mAppointment.setFpatientAge(et_patientAge.getText().toString());
        mAppointment.setFpatientName(et_patientName.getText().toString());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == event.KEYCODE_BACK)
        {
            setValue();
            setInternalActivityParam(InterrogationSpeedActivity.INTERNAL_PARAM_INTERROGA, mAppointment);
            AddInterrogatonPatientActivity.this.finish();
        }
        return false;
    }
}
