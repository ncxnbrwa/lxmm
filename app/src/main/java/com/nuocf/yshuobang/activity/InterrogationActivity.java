package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.MineInterrogationAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.MineInterrogationData;
import com.nuocf.yshuobang.analysis.bean.MineInterrogationDataList;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * @author xiong
 * @ClassName: InterrogationActivity
 * @Description: todo(问诊记录界面)
 * @date 2016/8/26
 */
@ContentView(R.layout.activity_interrogation)
public class InterrogationActivity extends BaseActivity
{
    @ViewInject(R.id.lv_mine_interrogation)
    private ListView lv_interrogation;
    private MineInterrogationAdapter adapter = null;
    private ArrayList<MineInterrogationDataList> list = null;

    @Override
    protected void initView()
    {

    }

    @Event(R.id.header_left_tv)
    private void click(View v)
    {
        InterrogationActivity.this.finish();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (list != null && !list.isEmpty())
        {
            setListAdapter();
        } else
        {
            getData();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        adapter = null;
    }

    private void getData()
    {
        RequestParams params = new RequestParams(APIConstants.MINE_INTERROGATION);
        sendGet(params, true, new CusCallBack<String>()
        {
            @Override
            protected void onCusStart()
            {
                super.onCusStart();
                showIndicator();
            }

            @Override
            public void onSuccess(String result)
            {
                JSONParser<MineInterrogationData> parser = new JSONParser<MineInterrogationData>(
                        MineInterrogationData.class
                        , new TypeToken<MineInterrogationData>()
                {
                }.getType(), result);
                StateMsg stateMsg = parser.getState(getBaseActivity());
                if (Integer.parseInt(stateMsg.getState()) == 0)
                {
                    MineInterrogationData interrogationData = parser.doParse();
                    DLog.d("reserveData", "state:" + stateMsg.getState());
                    DLog.d("reserveData", "message:" + stateMsg.getMessage());
                    DLog.d("reserveData", interrogationData.toString());
                    addDataList(interrogationData);
                    setListAdapter();
                } else if (Integer.parseInt(stateMsg.getState()) != 10004)
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

    //添加解析数据到指定list集合
    private void addDataList(MineInterrogationData data)
    {
        if (list == null)
        {
            list = new ArrayList<MineInterrogationDataList>(0);
        }
        if (data != null)
            list.addAll(data.getList());
    }

    //设置相应适配器
    private void setListAdapter()
    {
        if (adapter == null)
        {
            adapter = new MineInterrogationAdapter(list, getBaseActivity());
            lv_interrogation.setAdapter(adapter);
        } else
        {
            adapter.notifyDataSetChanged();
            if (lv_interrogation.getAdapter() == null)
            {
                lv_interrogation.setAdapter(adapter);
            }
        }
    }

    @Event(value = R.id.lv_mine_interrogation
            , type = AdapterView.OnItemClickListener.class)
    private void listItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (list != null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("inquiry_id", list.get(position).getInquiry_id());
            toActivity(InterrogationDetailsActivity.class, bundle);
        }
    }
}
