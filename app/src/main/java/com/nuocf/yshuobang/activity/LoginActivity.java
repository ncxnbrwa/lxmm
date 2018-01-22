package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.UserBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ELS;
import com.nuocf.yshuobang.utils.MD5_32bit;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author xiong
 * @ClassName: LoginActivity
 * @Description: todo(登录界面)
 * @date 2016/8/26
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity
{
    @ViewInject(R.id.login_header_left)
    private TextView tv_header_left;
    @ViewInject(R.id.et_phonenumber)
    private EditText et_phonenumber;
    @ViewInject(R.id.et_password)
    private EditText et_password;
    @ViewInject(R.id.tv_registerrightnow)
    private TextView tv_registernow;
    @ViewInject(R.id.iv_header)
    private ImageView imgHeader;
    @ViewInject(R.id.tv_forget_pasword)
    private TextView forgtPassword;
    ELS els;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView()
    {
        els = ELS.getInstance(this);
        //设置头部图片的宽高比
        ScreenInfo.initScreent(getBaseActivity());
        ViewGroup.LayoutParams imgParams = imgHeader.getLayoutParams();
        imgParams.height = ScreenInfo.screenWidth * 2 / 3;
        imgHeader.setLayoutParams(imgParams);
    }

    @Event(value = {R.id.login_header_left, R.id.btn_login
            , R.id.tv_registerrightnow, R.id.tv_forget_pasword})
    private void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login_header_left:
                LoginActivity.this.finish();
                break;
            case R.id.btn_login:
                login();
                break;
            //跳转注册页面
            case R.id.tv_registerrightnow:
                toActivity(RegisterActivity.class, null);
                finish();
                break;
            case R.id.tv_forget_pasword:
                toActivity(FindPasswordActivity.class, null);
                break;
        }
    }

    /**
     * @Description: todo(账号登录)
     */
    private void login()
    {
        String number = et_phonenumber.getText().toString();
        String password = et_password.getText().toString();
        if (!TextUtils.isEmpty(number))
        {
            if (!TextUtils.isEmpty(password))
            {
                RequestParams params = new RequestParams(APIConstants.LOGIN);
                params.addBodyParameter("user_mobile", number);
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
                        DLog.d("userBean", "state:" + userBean.getState());
                        DLog.d("userBean", userBean.toString());
                        if (Integer.parseInt(userBean.getState()) == 0)
                        {
                            els.saveStringData(ELS.SESSION_KEY,
                                    userBean.getData().getSession_key());
                            DLog.d("userBean", userBean.getData().getSession_key());
                            els.saveStringData(ELS.PHONE,
                                    userBean.getData().getUser_mobile());
                            DLog.d("userBean", "phone:" + userBean.getData().getUser_mobile());
                            els.saveStringData(ELS.USER_IMG,
                                    userBean.getData().getUser_img());
                            DLog.d("userBean", "img:" + userBean.getData().getUser_img());
                            els.saveStringData(ELS.USERNAME,
                                    userBean.getData().getUser_name());
                            DLog.d("userBean", "username:" + userBean.getData().getUser_name());
                            showToast("登录成功");
                            toActivity(MainActivity.class, null);
                        } else if (Integer.parseInt(userBean.getState()) == -1)
                        {
                            showToast("用户或密码错误");
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
                showToast("密码不能为空");
            }
        } else
        {
            showToast("手机号不能为空");
        }
    }
}
