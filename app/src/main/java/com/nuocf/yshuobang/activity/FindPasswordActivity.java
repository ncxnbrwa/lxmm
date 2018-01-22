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
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.TimeButton;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.MD5_32bit;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import static com.nuocf.yshuobang.R.id.et_print_code;
import static com.nuocf.yshuobang.appBase.Config.telRegex;

/**
 * @author xiong
 * @ClassName: FindPasswordActivity
 * @Description: todo(重置密码界面)
 * @date 2016/9/24
 */

@ContentView(R.layout.activity_find_password)
public class FindPasswordActivity extends BaseActivity
{
    @ViewInject(R.id.tv_identifying_code)
    private TimeButton btnCode;
    @ViewInject(R.id.et_printnumber)
    private EditText et_phoneNumber;
    @ViewInject(et_print_code)
    private EditText et_printCode;
    @ViewInject(R.id.et_printpassword)
    private EditText et_printPassword;
    @ViewInject(R.id.et_confirmpassword)
    private EditText et_confirm;

    String phoneNumber, password, confirmPassword, verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        btnCode.setEnabled(false);
        btnCode.setTextColor(getResources().getColor(R.color.gray));
        btnCode.setLenght(60 * 1000);
        et_phoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                phoneNumber = et_phoneNumber.getText().toString();
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

    }

    @Event(value = {R.id.header_left_tv, R.id.btn_reset
            , R.id.tv_identifying_code})
    private void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                finish();
                break;
            //重置密码
            case R.id.btn_reset:
                resetPassword();
                break;
            //获取验证码
            case R.id.tv_identifying_code:
                getVerifyCode();
                break;
        }
    }

    /**
     * @Description: todo(获取验证码)
     */
    private void getVerifyCode()
    {
        phoneNumber = et_phoneNumber.getText().toString();
        RequestParams params = new RequestParams(APIConstants.VERIFY_CODE);
        params.addQueryStringParameter("type", Config.SMS_CODE_FORGETPWD);
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
    }

    //重置密码
    private void resetPassword()
    {
        phoneNumber = et_phoneNumber.getText().toString();
        verifyCode = et_printCode.getText().toString();
        password = et_printPassword.getText().toString();
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
                                    RequestParams params = new RequestParams(APIConstants.RESET_PASSWORD);
                                    params.addBodyParameter("user_mobile", phoneNumber);
                                    params.addBodyParameter("smscode", verifyCode);
                                    params.addBodyParameter("user_pass", MD5_32bit.md5(password));
                                    sendPost(params, false, new CusCallBack<String>()
                                    {
                                        @Override
                                        public void onSuccess(String result)
                                        {
                                            JSONParser<StateMsg> parser = new JSONParser<StateMsg>(
                                                    StateMsg.class
                                                    , new TypeToken<StateMsg>()
                                            {
                                            }.getType(), result);
                                            StateMsg stateMsg = parser.getState(getBaseActivity());
                                            DLog.d("resetpassword", stateMsg.getState());
                                            DLog.d("resetpassword", stateMsg.toString());
                                            if (Integer.parseInt(stateMsg.getState()) == 0)
                                            {
                                                toActivity(LoginActivity.class, null);
                                                showToast("修改密码成功，请重新登录");
                                                FindPasswordActivity.this.finish();
                                            } else if (Integer.parseInt(stateMsg.getState()) == -2)
                                            {
                                                showToast("验证码无效");
                                            } else
                                            {
                                                showToast(stateMsg.getMessage());
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
