package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.SearchHospitalAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.SearchAllBean;
import com.nuocf.yshuobang.analysis.bean.SearchAllData;
import com.nuocf.yshuobang.analysis.bean.SearchHospitalList;
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
 * @ClassName: SearchActivity
 * @Description: todo(搜索医院界面)
 * @date 2016/9/27
 */

@ContentView(R.layout.activity_search_hospital)
public class SearchHospitalActivity extends BaseActivity
{
    @ViewInject(R.id.lv_search_hospital)
    private ListView lvHospital;
    @ViewInject(R.id.et_search)
    private EditText search;

    List<SearchHospitalList> hospitalList = null;
    SearchHospitalAdapter hospitalAdapter = null;
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
        if (hospitalList != null && !hospitalList.isEmpty())
        {
            setHospitalAdpter();
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
        params.addQueryStringParameter("more", 3 + "");
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
                addHospitalList(allBean);
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

    private void addHospitalList(SearchAllBean bean)
    {
        if (hospitalList == null)
        {
            hospitalList = new ArrayList<>(0);
        } else
        {
            hospitalList.clear();
        }
        if (bean != null)
        {
            if (Integer.parseInt(bean.getState()) == 0)
            {
                SearchAllData data = bean.getData();
                if (data.getListhos() != null && !data.getListhos().isEmpty())
                {
                    hospitalList.addAll(data.getListhos());
                    setHospitalAdpterToScroll();
                } else
                {
                    setHospitalAdpterToScroll();
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
    private void setHospitalAdpter()
    {
        if (hospitalAdapter == null)
        {
            hospitalAdapter = new SearchHospitalAdapter(hospitalList
                    , getBaseActivity(), key);
            lvHospital.setAdapter(hospitalAdapter);
        } else
        {
            hospitalAdapter.setKey(key);
            hospitalAdapter.notifyDataSetChanged();
            if (lvHospital.getAdapter() == null)
            {
                lvHospital.setAdapter(hospitalAdapter);
            }
        }
    }

    private void setHospitalAdpterToScroll()
    {
        setHospitalAdpter();
        lvHospital.smoothScrollToPosition(0);
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
                SearchHospitalActivity.this.finish();
                break;
        }
    }

    //listView项点击事件
    @Event(value = R.id.lv_search_hospital
            , type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Bundle hospitalBundle = new Bundle();
        hospitalBundle.putString("hospital_id", hospitalList.get(position).getHospital_id());
        toActivity(HospitalActivity.class, hospitalBundle);
    }
}
