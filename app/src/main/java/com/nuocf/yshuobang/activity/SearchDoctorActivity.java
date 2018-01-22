package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.SearchDoctorAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.SearchAllBean;
import com.nuocf.yshuobang.analysis.bean.SearchAllData;
import com.nuocf.yshuobang.analysis.bean.SearchDoctorList;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiong
 * @ClassName: SearchDoctorActivity
 * @Description: todo(搜索医生界面)
 * @date 2016/9/27
 */

@ContentView(R.layout.activity_search_doctor)
public class SearchDoctorActivity extends BaseActivity
{
    @ViewInject(R.id.lv_search_doctor)
    private ListView lvDoctor;
    @ViewInject(R.id.et_search)
    private EditText search;

    List<SearchDoctorList> doctorList = null;
    SearchDoctorAdapter doctorAdapter = null;
    String key;

    @Override
    protected void initView()
    {
        key = getIntent().getExtras().getString("key");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (doctorList != null && !doctorList.isEmpty())
        {
            setDoctorAdpter();
        } else
        {
            if (!key.isEmpty())
            {
                search.setText(key);
                getSearchResult(key);
            }
        }
    }

    //访问搜索接口
    private void getSearchResult(String key)
    {
        RequestParams params = new RequestParams(APIConstants.SERACH_ALL);
        params.addQueryStringParameter("key_name", key);
        params.addQueryStringParameter("more", 2 + "");
        sendGet(params, false, new CusCallBack<String>()
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
                DLog.d("SearchAllBean", "result:" + result);
                JSONParser<SearchAllBean> parser = new JSONParser<SearchAllBean>(SearchAllBean.class
                        , new TypeToken<SearchAllBean>()
                {
                }.getType(), result);
                SearchAllBean allBean = parser.doSimpleParse();
                DLog.d("SearchAllBean", allBean.toString());
                addDoctorList(allBean);
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

    private void addDoctorList(SearchAllBean bean)
    {
        if (doctorList == null)
        {
            doctorList = new ArrayList<>(0);
        } else
        {
            doctorList.clear();
        }
        if (bean != null)
        {
            if (Integer.parseInt(bean.getState()) == 0)
            {
                SearchAllData data = bean.getData();
                if (data.getListdoc() != null && !data.getListdoc().isEmpty())
                {
                    doctorList.addAll(data.getListdoc());
                    setDoctorAdpterToScroll();
                } else
                {
                    setDoctorAdpterToScroll();
                }
            } else if (Integer.parseInt(bean.getState()) == -2)
            {
                showToast("登录失效，请重新登录");
                toActivity(LoginActivity.class, null);
            } else
            {
                showToast(bean.getMessage());
            }
        }
    }

    //设置医生的适配器
    private void setDoctorAdpter()
    {
        if (doctorAdapter == null)
        {
            doctorAdapter = new SearchDoctorAdapter(doctorList
                    , getBaseActivity(), key);
            lvDoctor.setAdapter(doctorAdapter);
        } else
        {
            doctorAdapter.setKey(key);
            doctorAdapter.notifyDataSetChanged();
            if (lvDoctor.getAdapter() == null)
            {
                lvDoctor.setAdapter(doctorAdapter);
            }
        }
    }

    private void setDoctorAdpterToScroll()
    {
        setDoctorAdpter();
        lvDoctor.smoothScrollToPosition(0);
    }

    @Event(value = {R.id.tv_cancel, R.id.iv_search})
    private void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_search:
                key = search.getText().toString();
                DLog.d("SearchAllBean", "key:" + key);
                if (key != null && !key.isEmpty())
                {
                    getSearchResult(key);
                } else
                {
                    showToast("输入内容不能为空");
                }
                break;
            case R.id.tv_cancel:
                SearchDoctorActivity.this.finish();
                break;
        }
    }

    //listView项点击事件
    @Event(value = R.id.lv_search_doctor
            , type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Bundle doctorBundle = new Bundle();
        doctorBundle.putString("doctor_id", doctorList.get(position).getDoctor_id());
        toActivity(DoctorActivity.class, doctorBundle);
    }
}
