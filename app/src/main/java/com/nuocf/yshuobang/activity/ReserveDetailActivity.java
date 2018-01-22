package com.nuocf.yshuobang.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eeb.pickpiclib.Bimp;
import com.eeb.pickpiclib.NoScrollGridView;
import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.DiseaseImageAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.ReserveDetailsBean;
import com.nuocf.yshuobang.analysis.bean.ReserveDetailsData;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.db.AppDBmanager;
import com.nuocf.yshuobang.db.bean.CityEntity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.nuocf.yshuobang.db.AppDBmanager.code2City;
import static com.nuocf.yshuobang.db.AppDBmanager.initCityDatabase;

/**
 * @author xiong
 * @ClassName: ReserveDetailActivity
 * @Description: todo(预约详情的界面)
 * @date 2016/8/26
 */

@ContentView(R.layout.activity_reserve_detail)
public class ReserveDetailActivity extends BaseActivity
{
    @ViewInject(R.id.list_type_msg)
    private TextView listType;
    @ViewInject(R.id.list_code_msg)
    private TextView listCode;
    @ViewInject(R.id.rl_city)
    private RelativeLayout cityLayout;
    @ViewInject(R.id.tv_city_msg)
    private TextView expectCity;
    @ViewInject(R.id.rl_time)
    private RelativeLayout timeLayout;
    @ViewInject(R.id.tv_time_msg)
    private TextView expectTime;
    @ViewInject(R.id.tv_patient_reservetime)
    private TextView reserveTime;
    @ViewInject(R.id.et_patient_name)
    private TextView patientName;
    @ViewInject(R.id.tv_disease_namename)
    private TextView diseaseName;
    @ViewInject(R.id.tv_disease_details_content)
    private TextView diseaseDetails;
    @ViewInject(R.id.tv_state_time)
    private TextView stateTime;
    @ViewInject(R.id.tv_state_content)
    private TextView state;
    @ViewInject(R.id.ps_content)
    private TextView PSnote;
    @ViewInject(R.id.disease_gv)
    private NoScrollGridView disease_gv;
    @ViewInject(R.id.rl_button)
    private RelativeLayout buttonLayout;
    @ViewInject(R.id.btn_confirm)
    private Button btnConfirm;
    @ViewInject(R.id.btn_evaluate)
    private Button btnEvaluate;
    @ViewInject(R.id.btn_cancel)
    private TextView tvCancel;

    String id;
    private DiseaseImageAdapter mDiseaseImageAdapter;
    AlertDialog.Builder mDialog;
    AlertDialog cancelDialog;
    ArrayList<String> picUrlList = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (picUrlList == null)
        {
            picUrlList = new ArrayList<>();
        }
        getReserveDetailsData();
    }

    @Override
    protected void initView()
    {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("order_id");
    }

    private void getReserveDetailsData()
    {
        RequestParams params = new RequestParams(APIConstants.RESERVE_DETAIL);
        params.addQueryStringParameter("order_id", id);
        sendGet(params, true, new CusCallBack<String>()
        {

            @Override
            protected void onCusStart()
            {
                showIndicator();
            }

            @Override
            public void onSuccess(String result)
            {
                JSONParser<ReserveDetailsData> parser = new JSONParser<ReserveDetailsData>(
                        ReserveDetailsData.class
                        , new TypeToken<ReserveDetailsData>()
                {
                }.getType(), result);
                StateMsg stateMsg = parser.getState(getBaseActivity());
                if (stateMsg.getState().equals("0"))
                {
                    ReserveDetailsData detailsData = parser.doParse();
                    DLog.d("reserveDetailsBean", "state:" + stateMsg.getState());
                    DLog.d("reserveDetailsBean", "message" + stateMsg.getMessage());
                    DLog.d("reserveDetailsBean", detailsData.toString());
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
                dismissIndicator();
            }
        });
    }

    private void setUIData(ReserveDetailsData data)
    {
        if (data != null)
        {
            //设置订单编号
            listCode.setText(data.getOrder_number());
            //设置订单类型
            switch (data.getOrder_type())
            {
                case 1:
                    listType.setText("手术预约");
                    cityLayout.setVisibility(View.GONE);
                    timeLayout.setVisibility(View.GONE);
                    break;
                case 2:
                    listType.setText("精准预约");
                    cityLayout.setVisibility(View.VISIBLE);
                    timeLayout.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    listType.setText("海外医疗");
                    cityLayout.setVisibility(View.GONE);
                    timeLayout.setVisibility(View.GONE);
                    break;
                case 4:
                    listType.setText("绿色通道");
                    cityLayout.setVisibility(View.GONE);
                    timeLayout.setVisibility(View.GONE);
                    break;
            }
            //设置期望城市
            String cityCode = data.getOrder_city();
            if (!TextUtils.isEmpty(cityCode))
            {
                CityEntity cityEntity = AppDBmanager.code2City(cityCode);
                expectCity.setText(cityEntity.getName());
            } else
            {
                expectCity.setText("无");
            }
            //设置期望时间
            expectTime.setText(data.getOrder_date());
            //设置订单时间
            reserveTime.setText("订单时间：" + data.getOrder_time());
            //设置疾病名称
            String dname = data.getDisease_name();
            if (dname != null && !dname.equals(""))
            {
                diseaseName.setText(dname);
            } else
            {
                diseaseName.setText("未确诊病情");
            }
            //设置病人名称
            patientName.setText(data.getUser_name());
            //设置疾病描述
            diseaseDetails.setText(data.getDisease_des());
            //设置处理时间
            if (!data.getOrder_update_time().equals(" "))
            {
                stateTime.setText("处理时间：" + data.getOrder_update_time());
            } else
            {
                stateTime.setText("");
            }
            //设置处理状态
            int stateCode = data.getOrder_state();
            switch (stateCode)
            {
                case 1:
                    state.setText("待安排");
                    setBottomButton(false, false, true);
                    break;
                case 2:
                    state.setText("待确认");
                    setBottomButton(true, false, true);
                    break;
                case 3:
                    state.setText("已完成");
                    setBottomButton(false, true, false);
                    break;
                case 4:
                    state.setText("已评价");
                    setBottomButton(false, false, false);
                    break;
                case 5:
                    state.setText("已取消");
                    setBottomButton(false, false, false);
                    break;
            }
            //设置备注
            PSnote.setText(data.getNote());
            //设置病情资料的图片
            if (data.getOrderfile_url() != null
                    && !data.getOrderfile_url().isEmpty())
            {
                picUrlList.addAll(data.getOrderfile_url());
                setmDiseaseImageAdapter(picUrlList);
            }
        }
    }

    //设置按钮是否可点
    private void setBottomButton(boolean button1Flag, boolean button2Flag
            , boolean cancelFlag)
    {
        if (button1Flag)
        {
            buttonLayout.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
        } else
        {
            buttonLayout.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
        }
        if (button2Flag)
        {
            buttonLayout.setVisibility(View.VISIBLE);
            btnEvaluate.setVisibility(View.VISIBLE);
        } else
        {
            buttonLayout.setVisibility(View.GONE);
            btnEvaluate.setVisibility(View.GONE);
        }
        if (cancelFlag)
        {
            tvCancel.setVisibility(View.VISIBLE);
        } else
        {
            tvCancel.setVisibility(View.GONE);
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.btn_cancel
            , R.id.btn_confirm, R.id.btn_evaluate})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                ReserveDetailActivity.this.finish();
                break;
            case R.id.btn_cancel:
                cancelReserve();
                break;
            case R.id.btn_confirm:
                reserveAct(1);
                ReserveDetailActivity.this.finish();
                break;
            case R.id.btn_evaluate:
                Bundle commentBundle = new Bundle();
                commentBundle.putString("id", id);
                toActivity(CommentContentActivity.class, commentBundle);
                break;
        }
    }

    //图片点击放大的监听
    @Event(value = R.id.disease_gv, type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        if (!picUrlList.isEmpty())
        {
            Bundle picBundle = new Bundle();
            picBundle.putInt(ZoomPicActivity.INTENTAL_PARAMS_PIC_INDEX, i);
            setInternalActivityParam(ZoomPicActivity.INTENTAL_PARAMS_PICURLS, picUrlList);
            toActivity(ZoomPicActivity.class, picBundle);
        }
    }

    /**
     * @param int type 访问的类型
     *            String content 评价的内容
     * @Description: todo(访问预约确认/取消/评价接口)
     */
    private void reserveAct(int type)
    {
        RequestParams actParams = new RequestParams(APIConstants.RESERVE_ACT);
        actParams.addBodyParameter("order_id", id);
        actParams.addBodyParameter("handle", type + "");
        sendPost(actParams, true, new CusCallBack<String>()
        {
            @Override
            protected void onCusStart()
            {
                showIndicator();
            }

            @Override
            public void onSuccess(String result)
            {
                JSONParser<StateMsg> actParser = new JSONParser<StateMsg>(StateMsg.class
                        , new TypeToken<StateMsg>()
                {
                }.getType(), result);
                StateMsg stateMsg = actParser.getState(getBaseActivity());
                DLog.d("reserveAct", stateMsg.toString());
                if (stateMsg != null)
                {
                    if (Integer.parseInt(stateMsg.getState()) == 0)
                    {
                        showToast("成功");
                    } else if (Integer.parseInt(stateMsg.getState()) == -2)
                    {
                        showToast("登录失效，请重新登录");
                        toActivity(LoginActivity.class, null);
                    } else
                    {
                        showToast("其他错误");
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
    }

    private void setmDiseaseImageAdapter(List<String> imageStrs)
    {
        if (mDiseaseImageAdapter == null)
        {
            mDiseaseImageAdapter = new DiseaseImageAdapter(this, imageStrs);
            disease_gv.setAdapter(mDiseaseImageAdapter);
        } else
        {
            mDiseaseImageAdapter.notifyDataSetChanged();
        }
    }

    //点击取消弹出对话框
    private void cancelReserve()
    {
        mDialog = new AlertDialog.Builder(
                getBaseActivity());
        cancelDialog = mDialog.create();
        View view = LayoutInflater.from(getBaseActivity())
                .inflate(R.layout.common_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        title.setText("取消");
        TextView content = (TextView) view.findViewById(R.id.tv_content);
        content.setText("你确定要取消此次预约吗？");
        Button btnTrue = (Button) view.findViewById(R.id.btn_positive);
        btnTrue.setText("确定");
        btnTrue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                reserveAct(2);
                cancelDialog.dismiss();
                ReserveDetailActivity.this.finish();
            }
        });
        Button btnFalse = (Button) view.findViewById(R.id.btn_negative);
        btnFalse.setText("取消");
        btnFalse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();
        cancelDialog.setContentView(view);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Bimp.drr.clear();
    }
}
