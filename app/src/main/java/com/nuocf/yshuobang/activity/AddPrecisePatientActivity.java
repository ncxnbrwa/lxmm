package com.nuocf.yshuobang.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.Process.AppointmentObj;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.ReserveBean;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.appBase.ElfApplication;
import com.nuocf.yshuobang.common.RegetionPopWindow;
import com.nuocf.yshuobang.db.bean.CityEntity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.CalendarDialog;
import com.nuocf.yshuobang.utils.HTimeUtils;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.Locale;

/**
 * @ClassName: AddPrecisePatientActivity
 * @Description: todo(精准预约就诊人资料页面)
 * @date 2016/9/30
 */

@ContentView(R.layout.activity_add_precise_patient)
public class AddPrecisePatientActivity extends BaseActivity implements RegetionPopWindow.OnCitySelectListener
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
    @ViewInject(R.id.et_patient_number)
    private EditText et_patientNumber;
    @ViewInject(R.id.et_order_city)
    private TextView et_orderCity;
    @ViewInject(R.id.et_order_date)
    private TextView et_orderDate;

    private AppointmentObj mAppointment;
    private RegetionPopWindow mRegionPopwindow = null;

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
        mCurrentCalendar = getCurrentDate();
        setCurrentDate();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAppointment = (AppointmentObj) receiveInternalActivityParam(PreciseReserveActivity.INTERNAL_PARAM_RESERVE);
        if (mAppointment == null)
        {
            mAppointment = new AppointmentObj();
        } else
        {
            //若有传值，则设置相应数据
            //姓名
            if (!TextUtils.isEmpty(mAppointment.getFpatientName()))
            {
                et_patientName.setText(mAppointment.getFpatientName());
            }
            //年龄
            if (!TextUtils.isEmpty(mAppointment.getFpatientAge()))
            {
                et_patientAge.setText(mAppointment.getFpatientAge());
            }
            //号码
            if (!TextUtils.isEmpty(mAppointment.getFpatientNumber()))
            {
                et_patientNumber.setText(mAppointment.getFpatientNumber());
            }
            //城市
            if (!TextUtils.isEmpty(mAppointment.getCityCode()))
            {
                et_orderCity.setText(mAppointment.getCityName());
            }
            //日期
            if (mAppointment.getDate() > 0)
            {
                et_orderDate.setText(HTimeUtils.unixTime3Date(mAppointment.getDate()));
            }
        }
        //性别
        if (mAppointment.getSex() == 1)
        {
            man.setChecked(true);
        } else
        {
            woman.setChecked(true);
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.precise_reservation_done, R.id.et_order_city, R.id.et_order_date})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                setUIData();
                setInternalActivityParam(PreciseReserveActivity.INTERNAL_PARAM_RESERVE, mAppointment);
                AddPrecisePatientActivity.this.finish();
                break;
            case R.id.precise_reservation_done:
                toPrecisePatient();
                break;
            case R.id.et_order_date:
                showCalendarPicker();
                break;
            case R.id.et_order_city:
                if (mRegionPopwindow == null)
                {
                    mRegionPopwindow = new RegetionPopWindow(getBaseActivity(), this);
                }
                if (mRegionPopwindow.isWindowShowing())
                {
                    mRegionPopwindow.closeRegionWindow();
                } else
                {
                    mRegionPopwindow.showRegionPopwindow(v);
                }
                break;
        }
    }

    private void toPrecisePatient()
    {
        setUIData();
        String phoneNumber = et_patientNumber.getText().toString();
        RequestParams params = new RequestParams(APIConstants.ORDER_OPERATION);
        if (!TextUtils.isEmpty(mAppointment.getFpatientName()))
        {
            if (mAppointment.getFpatientName().length() <= 6)
            {
                params.addBodyParameter("order_name", mAppointment.getFpatientName());
                if (!TextUtils.isEmpty(mAppointment.getFpatientAge()))
                {
                    if (mAppointment.getFpatientAge().length() <= 3)
                    {
                        params.addBodyParameter("order_age", mAppointment.getFpatientAge());
                        if (!TextUtils.isEmpty(mAppointment.getFpatientNumber()))
                        {
                            if (phoneNumber.matches(Config.telRegex))
                            {
                                params.addBodyParameter("order_phone", mAppointment.getFpatientNumber());
                                if (!TextUtils.isEmpty(mAppointment.getCityCode()))
                                {
                                    params.addBodyParameter("order_city", mAppointment.getCityCode());
                                    if (mAppointment.getDate() > 0)
                                    {
                                        params.addBodyParameter("order_date", Long.toString(mAppointment.getDate()));
                                        //精准预约
                                        params.addBodyParameter("order_type", mAppointment.getType() + "");
                                        params.addBodyParameter("order_gender", mAppointment.getSex() + "");
                                        //疾病名不填时不提交
                                        if (!TextUtils.isEmpty(mAppointment.getCdiseaseName()))
                                        {
                                            params.addBodyParameter("disease_name", mAppointment.getCdiseaseName());
                                        }
                                        //是否有疾病相片
                                        if (!TextUtils.isEmpty(mAppointment.getDiseasePhotos()))
                                        {
                                            params.addBodyParameter("disease_photos", mAppointment.getDiseasePhotos());
                                        }
                                        //疾病描述
                                        params.addBodyParameter("disease_des", mAppointment.getDiseaseDetails());
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
                                                    ReserveBean reserveBean = parser.doSimpleParse();
                                                    showToast("精准预约成功");
                                                    toActivity(MainActivity.class, null);
                                                    //销毁当前流程活动界面
                                                    ((ElfApplication) getApplication()).finishSpecialActivity(AddPrecisePatientActivity.class);
                                                    ((ElfApplication) getApplication()).finishSpecialActivity(PreciseReserveActivity.class);
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
                                    } else
                                    {
                                        showToast("输入日期");
                                    }
                                } else
                                {
                                    showToast("输入城市");
                                }
                            } else
                            {
                                showToast("请输入正确的手机号码");
                            }
                        } else
                        {
                            showToast("输入号码");
                        }
                    } else
                    {
                        showToast("年龄不能大于3位");
                    }
                } else
                {
                    showToast("输入年龄");
                }
            } else
            {
                showToast("姓名不能大于6位");
            }
        } else
        {
            showToast("输入姓名");
        }
    }

    /**
     * @Description: todo(获取UI数据及传值数据)
     */

    private void setUIData()
    {
        mAppointment.setFpatientName(et_patientName.getText().toString());
        mAppointment.setFpatientAge(et_patientAge.getText().toString());
        mAppointment.setFpatientNumber(et_patientNumber.getText().toString());
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            setUIData();
            setInternalActivityParam(PreciseReserveActivity.INTERNAL_PARAM_RESERVE, mAppointment);
            AddPrecisePatientActivity.this.finish();
        }
        return false;
    }

    @Override
    public void onCitySelected(CityEntity city)
    {
        mAppointment.setCityCode(city.getCityCode());
        mAppointment.setCityName(city.getName());
        et_orderCity.setText(mAppointment.getCityName());
    }

    private Dialog mDialog = null;
    private Calendar mCurrentCalendar = null;

    /**
     * 显示月历
     */
    private void showCalendarPicker()
    {
        mDialog = CalendarDialog.getInstance().getCalendarDialog(getBaseActivity(), new CalendarDialog.OnSelectedDateListener()
        {

            @Override
            public void selectDate(int year, int month, int day)
            {
                // TODO Auto-generated method stub
                if (mDialog != null)
                {
                    mDialog.dismiss();
                }
                mCurrentCalendar.set(year, month, day);
                setCurrentDate();
                mAppointment.setDate(mCurrentCalendar.getTimeInMillis() / 1000);
            }
        }, getCurrentDate());
    }

    //月份加1，Calendar月份从一月到十二月对应的值是0 - 11所以都要+1
    private Calendar getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    private void setCurrentDate()
    {
        int cYear = mCurrentCalendar.get(Calendar.YEAR);
        int cMonth = mCurrentCalendar.get(Calendar.MONTH) + 1;
        int cDay = mCurrentCalendar.get(Calendar.DAY_OF_MONTH);
        et_orderDate.setText(cYear + "-" + cMonth + "-" + cDay);
    }
}
