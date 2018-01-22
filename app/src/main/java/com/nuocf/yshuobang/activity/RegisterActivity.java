package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.analysis.bean.UserBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.TimeButton;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ELS;
import com.nuocf.yshuobang.utils.MD5_32bit;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.nuocf.yshuobang.appBase.Config.telRegex;

/**
 * @author Administrator
 * @ClassName: RegisterActivity
 * @Description: todo(注册界面)
 * @date 2016/8/26
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity
{
    @ViewInject(R.id.et_printnumber)
    private EditText et_phone_number;
    @ViewInject(R.id.et_print_code)
    private EditText et_print_code;
    @ViewInject(R.id.et_printpassword)
    private EditText et_print_password;
    @ViewInject(R.id.et_confirmpassword)
    private EditText et_confirm;
    @ViewInject(R.id.tv_identifying_code)
    private TimeButton btnCode;

    String phoneNumber, password, confirmPassword, verifyCode;
    ELS els;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        btnCode.setEnabled(false);
        btnCode.setTextColor(getResources().getColor(R.color.gray));
        btnCode.setLenght(60 * 1000);
        et_phone_number.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                phoneNumber = et_phone_number.getText().toString();
                if (!TextUtils.isEmpty(phoneNumber))
                {
                    if (phoneNumber.matches(Config.telRegex))
                    {
                        btnCode.setEnabled(true);
                        btnCode.setTextColor(getResources().getColor(R.color.blue2));
                    } else
                    {
                        btnCode.setEnabled(false);
                        btnCode.setTextColor(getResources().getColor(R.color.gray));
                    }
                } else
                {
                    btnCode.setEnabled(false);
                    btnCode.setTextColor(getResources().getColor(R.color.gray));
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    @Override
    protected void initView()
    {
        els = ELS.getInstance(getBaseActivity());
    }

    @Event(value = {R.id.tv_identifying_code, R.id.btn_register
            , R.id.tv_loginnow, R.id.header_left_tv})
    private void onclick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_identifying_code:
                getVerifyCode();
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.tv_loginnow:
                finish();
                toActivity(LoginActivity.class, null);
                break;
            case R.id.header_left_tv:
                finish();
                break;
        }
    }

    /**
     * @Description: todo(获取验证码)
     */
    private void getVerifyCode()
    {
        phoneNumber = et_phone_number.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber))
        {
            if (phoneNumber.matches(telRegex))
            {
                RequestParams params = new RequestParams(APIConstants.VERIFY_CODE);
                params.addQueryStringParameter("type", Config.SMS_CODE_REGISTE);
                params.addQueryStringParameter("user_mobile", phoneNumber);
                sendGet(params, false, new CusCallBack<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        JSONParser<StateMsg> parser = new JSONParser<StateMsg>(StateMsg.class
                                , new TypeToken<StateMsg>()
                        {
                        }.getType(), result);
                        StateMsg state = parser.getState(getBaseActivity());
                        DLog.d("state", state.toString());
                        if (Integer.parseInt(state.getState()) == 0)
                        {
                            showToast("验证码已发送，请您留意手机短信通知");
                            //                  tv_getcode.setClickable(false);
                        } else
                        {
                            showToast("获取验证码失败，请重试");
                            //                  tv_getcode.setClickable(true);
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
                    }
                });
            } else
            {
                showToast("请输入正确的手机号码");
            }
        } else
        {
            showToast("手机号码不能为空");
        }
    }

    /**
     * @Description: todo(访问注册的接口)
     */
    private void register()
    {
        phoneNumber = et_phone_number.getText().toString();
        verifyCode = et_print_code.getText().toString();
        password = et_print_password.getText().toString();
        confirmPassword = et_confirm.getText().toString();
        if (!TextUtils.isEmpty(phoneNumber))
        {
            if (phoneNumber.matches(telRegex))
            {
                if (!TextUtils.isEmpty(verifyCode))
                {
                    if (!TextUtils.isEmpty(confirmPassword))
                    {
                        if (!TextUtils.isEmpty(password))
                        {
                            if (password.length() >= 6)
                            {
                                if (confirmPassword.equals(password))
                                {
                                    RequestParams params = new RequestParams(APIConstants.REGISTER);
                                    params.addBodyParameter("user_mobile", phoneNumber);
                                    params.addBodyParameter("smscode", verifyCode);
                                    params.addBodyParameter("user_pass", MD5_32bit.md5(password));
                                    sendPost(params, false, new CusCallBack<String>()
                                    {
                                        @Override
                                        public void onCusStart()
                                        {
                                            showIndicator();
                                        }

                                        @Override
                                        public void onSuccess(String result)
                                        {
                                            JSONParser<UserBean> parser = new JSONParser<UserBean>(
                                                    UserBean.class
                                                    , new TypeToken<UserBean>()
                                            {
                                            }.getType(), result);
                                            UserBean userBean = parser.doSimpleParse();
                                            DLog.d("register", userBean.getState());
                                            DLog.d("register", userBean.toString());
                                            if (Integer.parseInt(userBean.getState()) == 0)
                                            {
                                                els.saveStringData(ELS.SESSION_KEY,
                                                        userBean.getData().getSession_key());
                                                toActivity(MainActivity.class, null);
                                            } else if (Integer.parseInt(userBean.getState()) == -2)
                                            {
                                                showToast("验证码无效");
                                            } else
                                            {
                                                showToast(userBean.getMessage());
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
                                    showToast("两次输入密码不一致");
                                }
                            } else
                            {
                                showToast("密码不能少于6位");
                            }
                        } else
                        {
                            showToast("密码不能为空");
                        }
                    } else
                    {
                        showToast("确认密码不能为空");
                    }
                } else
                {
                    showToast("验证码不能为空");
                }
            } else
            {
                showToast("请输入正确的手机号码");
            }
        } else
        {
            showToast("手机号不能为空");
        }
    }

}
