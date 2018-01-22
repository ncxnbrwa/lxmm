package com.nuocf.yshuobang.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.ClearEditText;
import com.nuocf.yshuobang.utils.ELS;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author xiong
 * @ClassName: UserActivity
 * @Description: todo(用户中心页面)
 * @date 2016/9/18
 */
@ContentView(R.layout.activity_user)
public class UserActivity extends BaseActivity
{
    @ViewInject(R.id.tv_user_phone)
    private TextView tvUserPhone;
    @ViewInject(R.id.tv_user_name)
    private ClearEditText etUserName;
    @ViewInject(R.id.btn_done)
    private Button done;

    ELS els;
    String finishName;
    String phone;
    String userName;

    @Override
    protected void initView()
    {
        els = ELS.getInstance(getBaseActivity());
        phone = els.getStringData(ELS.PHONE);
        tvUserPhone.setText(phone);
        userName = els.getStringData(ELS.USERNAME);
        etUserName.setText(userName);
    }

    @Event(value = {R.id.header_left_tv, R.id.btn_done})
    private void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.header_left_tv:
                finish();
                break;
            case R.id.btn_done:
                editUser();
                break;
        }
    }

    //修改用户名
    private void editUser()
    {
        finishName = etUserName.getText().toString();
        if (!TextUtils.isEmpty(finishName))
        {
            if (!finishName.equals(userName))
            {
                RequestParams params = new RequestParams(APIConstants.EDIT_USER);
                params.addBodyParameter("user_name", finishName);
                sendPost(params, true, new CusCallBack<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        JSONParser<StateMsg> parser = new JSONParser<StateMsg>(StateMsg.class,
                                new TypeToken<StateMsg>()
                                {
                                }.getType(), result);
                        StateMsg stateMsg = parser.getState(getBaseActivity());
                        if (stateMsg.getState().equals("0"))
                        {
                            showToast("编辑用户资料成功");
                            els.saveStringData(ELS.USERNAME, finishName);
                            finish();
                        } else if (!stateMsg.getState().equals("10004"))
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
                showToast("新用户名不能与原用户名相同");
            }
        } else
        {
            showToast("修改的用户名不能为空");
        }
    }
}
