package com.nuocf.yshuobang.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
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
 * @ClassName: CommentContentActivity
 * @Description: todo(评论页面)
 * @date 2016/9/29
 */

@ContentView(R.layout.activity_comment_content)
public class CommentContentActivity extends BaseActivity {
    @ViewInject(R.id.et_comment_content)
    private EditText etContent;

    String id;

    @Override
    protected void initView() {
        id = getIntent().getExtras().getString("id");
    }

    private void commitComment() {
        RequestParams actParams = new RequestParams(APIConstants.RESERVE_ACT);
        actParams.addBodyParameter("order_id", id);
        actParams.addBodyParameter("handle", 3 + "");
        String content = etContent.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            actParams.addBodyParameter("comment", content);
            sendPost(actParams, true, new CusCallBack<String>() {
                @Override
                public void onSuccess(String result) {
                    JSONParser<StateMsg> actParser = new JSONParser<StateMsg>(StateMsg.class
                            , new TypeToken<StateMsg>() {
                    }.getType(), result);
                    StateMsg stateMsg = actParser.getState(getBaseActivity());
                    DLog.d("commitComment", stateMsg.toString());
                    if (stateMsg != null) {
                        if (Integer.parseInt(stateMsg.getState()) == 0) {
                            showToast("评价成功");
                            finish();
                        } else if (Integer.parseInt(stateMsg.getState()) == -2) {
                            showToast("登录失效，请重新登录");
                            toActivity(LoginActivity.class, null);
                        } else {
                            showToast(stateMsg.getMessage());
                        }
                    }
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
                }
            });
        } else {
            showToast("评论内容不能为空");
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.btn_comment_done})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_left_tv:
                finish();
                break;
            case R.id.btn_comment_done:
                commitComment();
                break;
        }
    }
}
