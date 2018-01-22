package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.InterrogationDetailsData;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author xiong
 * @ClassName: InterrogationDetailsActivity
 * @Description: todo(问诊详情界面)
 * @date 2016/8/29
 */

@ContentView(R.layout.activity_interrogation_details)
public class InterrogationDetailsActivity extends BaseActivity
{
    @ViewInject(R.id.rl_reanswer)
    private RelativeLayout reanswerLayout;
    @ViewInject(R.id.tv_disease_name)
    private TextView diseaseName;
    @ViewInject(R.id.tv_inquiry_time)
    private TextView interrogationTime;
    @ViewInject(R.id.tv_disease_details)
    private TextView diseaseDetails;
    @ViewInject(R.id.tv_inquiry_state)
    private TextView tv_state;
    @ViewInject(R.id.tv_inquiry_retime)
    private TextView reTime;
    @ViewInject(R.id.tv_inquiry_reanwser)
    private TextView reAnswer;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView()
    {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("inquiry_id");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        RequestParams params = new RequestParams(APIConstants.INTERROGATION_DETAILS);
        params.addQueryStringParameter("inquiry_id", id);
        sendGet(params, true, new CusCallBack<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                JSONParser<InterrogationDetailsData> parser = new JSONParser<InterrogationDetailsData>(
                        InterrogationDetailsData.class,
                        new TypeToken<InterrogationDetailsData>()
                        {
                        }.getType(), result);
                StateMsg stateMsg = parser.getState(getBaseActivity());
                if (stateMsg.getState().equals("0"))
                {
                    InterrogationDetailsData detailsData = parser.doParse();
                    DLog.d("detailsBean", "state" + stateMsg.getState());
                    DLog.d("detailsBean", "message" + stateMsg.getMessage());
                    DLog.d("detailsBean", detailsData.toString());
                    setUIData(detailsData);
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
    }

    private void setUIData(InterrogationDetailsData data)
    {
        if (data != null)
        {
            String name = data.getDisease_name();
            if (!TextUtils.isEmpty(name))
            {
                diseaseName.setText(name);
            } else
            {
                diseaseName.setText("未确诊病情");
            }
            interrogationTime.setText(data.getInquiry_time());
            diseaseDetails.setText(data.getDisease_des());
            int state = data.getInquiry_state();
            if (state == 1)
            {
                tv_state.setText("未回复");
                reanswerLayout.setVisibility(View.GONE);
                reAnswer.setVisibility(View.GONE);
            } else if (state == 2)
            {
                tv_state.setText("已回复");
                reTime.setText(data.getInquiry_retime());
                reAnswer.setVisibility(View.VISIBLE);
                reAnswer.setText(data.getInquiry_reanswer());
                reanswerLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Event(value = {R.id.header_left_tv})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                InterrogationDetailsActivity.this.finish();
                break;
//            case R.id.btn_cancel_q:
//                RequestParams params = new RequestParams(APIConstants.INTERROGATION_CANCEL);
//                params.addBodyParameter("inquiry_id", id);
//                sendPost(params, true, new CusCallBack<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        JSONParser<StateMsg> parser = new JSONParser<StateMsg>(StateMsg.class
//                                , new TypeToken<StateMsg>() {
//                        }.getType(), result);
//                        StateMsg stateMsg = parser.getState(getBaseActivity());
//                        if (stateMsg != null) {
//                            if (Integer.parseInt(stateMsg.getState()) == 0) {
//                                showToast("取消提问成功");
//                                toActivity(InterrogationActivity.class, null);
//                            } else if (Integer.parseInt(stateMsg.getState()) == -2) {
//                                showToast("登录过期，请重新登录");
//                            } else {
//                                showToast("其它错误");
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                        super.onError(ex, isOnCallback);
//                    }
//
//                    @Override
//                    public void onCancelled(CancelledException cex) {
//                        super.onCancelled(cex);
//                    }
//
//                    @Override
//                    public void onFinished() {
//                        super.onFinished();
//                    }
//                });
//                break;
        }
    }
}
