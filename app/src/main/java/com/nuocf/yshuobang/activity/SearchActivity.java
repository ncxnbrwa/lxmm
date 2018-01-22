package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.SearchDoctorAdapter;
import com.nuocf.yshuobang.adapter.SearchHospitalAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.SearchAllBean;
import com.nuocf.yshuobang.analysis.bean.SearchAllData;
import com.nuocf.yshuobang.analysis.bean.SearchDoctorList;
import com.nuocf.yshuobang.analysis.bean.SearchHospitalList;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.WrapListView;
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
 * @Description: todo(搜索界面)
 * @date 2016/9/27
 */

@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity
{
    @ViewInject(R.id.et_search)
    private EditText searchView;
    @ViewInject(R.id.iv_search)
    private ImageView imgSearch;
    @ViewInject(R.id.lv_search_doctor)
    private WrapListView lvDoctor;
    @ViewInject(R.id.more_doctor)
    private TextView restDoctor;
    @ViewInject(R.id.lv_search_hospital)
    private WrapListView lvHospital;
    @ViewInject(R.id.more_hospital)
    private TextView restHospital;
    @ViewInject(R.id.rl_search_doctor)
    private RelativeLayout rl_search_doctor;
    @ViewInject(R.id.rl_search_hospital)
    private RelativeLayout rl_search_hospital;
    @ViewInject(R.id.rl_more_hospital)
    private RelativeLayout rl_more_hospital;
    @ViewInject(R.id.rl_more_doctor)
    private RelativeLayout rl_more_doctor;

    String key;
    List<SearchDoctorList> doctorList = null;
    List<SearchHospitalList> hospitalList = null;
    SearchHospitalAdapter hospitalAdapter = null;
    SearchDoctorAdapter doctorAdapter = null;

    @Override
    protected void initView()
    {
    }

    @Event(value = {R.id.tv_cancel, R.id.rl_search_doctor, R.id.rl_more_doctor
            , R.id.rl_search_hospital, R.id.rl_more_hospital, R.id.iv_search})
    private void onClick(View v)
    {
        Bundle doctorBundle = new Bundle();
        Bundle hospitalBundle = new Bundle();
        switch (v.getId())
        {
            case R.id.iv_search:
                key = searchView.getText().toString();
                DLog.d("SearchAllBean", "key:" + key);
                if (!TextUtils.isEmpty(key))
                {
                    getSearchResult(key);
                } else
                {
                    showToast("输入内容不能为空");
                }
                break;
            case R.id.tv_cancel:
                SearchActivity.this.finish();
                break;
            case R.id.rl_search_doctor:
                key = searchView.getText().toString();
                doctorBundle.putString("key", key);
                toActivity(SearchDoctorActivity.class, doctorBundle);
                SearchActivity.this.finish();
                break;
            case R.id.rl_more_doctor:
                key = searchView.getText().toString();
                doctorBundle.putString("key", key);
                toActivity(SearchDoctorActivity.class, doctorBundle);
                SearchActivity.this.finish();
                break;
            case R.id.rl_search_hospital:
                key = searchView.getText().toString();
                hospitalBundle.putString("key", key);
                toActivity(SearchHospitalActivity.class, hospitalBundle);
                SearchActivity.this.finish();
                break;
            case R.id.rl_more_hospital:
                key = searchView.getText().toString();
                hospitalBundle.putString("key", key);
                toActivity(SearchHospitalActivity.class, hospitalBundle);
                SearchActivity.this.finish();
                break;
        }
    }

    //访问搜索接口
    private void getSearchResult(String key)
    {
        RequestParams params = new RequestParams(APIConstants.SERACH_ALL);
        params.addQueryStringParameter("key_name", key);
        params.addQueryStringParameter("more", 1 + "");
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
                setUIData(allBean);
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

    private void setUIData(SearchAllBean bean)
    {
        if (doctorList == null)
        {
            doctorList = new ArrayList<>(0);
        } else
        {
            doctorList.clear();
        }
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
                if (data.getListdoc() != null && !data.getListdoc().isEmpty())
                {
                    doctorList.addAll(data.getListdoc());
                    if (doctorList.size() >= 3)
                    {
                        rl_more_doctor.setVisibility(View.VISIBLE);
                    } else
                    {
                        rl_more_doctor.setVisibility(View.GONE);
                    }
                    restDoctor.setText("更多医生（共" + data.getDoctor_count() + "条）");
                    setDoctorAdpter();
                } else
                {
                    setDoctorAdpter();
                    rl_more_doctor.setVisibility(View.GONE);
                    restDoctor.setText("更多医生（共0条）");
                }
                if (data.getListhos() != null && !data.getListhos().isEmpty())
                {
                    hospitalList.addAll(data.getListhos());
                    if (hospitalList.size() >= 3)
                    {
                        rl_more_hospital.setVisibility(View.VISIBLE);
                    } else
                    {
                        rl_more_hospital.setVisibility(View.GONE);
                    }
                    restHospital.setText("更多医院（共" + data.getHospital_count() + "条）");
                    setHospitalAdapter();
                } else
                {
                    setHospitalAdapter();
                    rl_more_hospital.setVisibility(View.GONE);
                    restHospital.setText("更多医院（共0条）");
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
        rl_search_doctor.setVisibility(View.VISIBLE);
        rl_search_hospital.setVisibility(View.VISIBLE);
    }

    //设置医生的适配器
    private void setDoctorAdpter()
    {
        if (doctorAdapter == null)
        {
            doctorAdapter = new SearchDoctorAdapter(doctorList,
                    getBaseActivity(), key);
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

    //设置医院的适配器
    private void setHospitalAdapter()
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

    //listView项点击事件
    @Event(value = {R.id.lv_search_doctor, R.id.lv_search_hospital}
            , type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getId())
        {
            case R.id.lv_search_doctor:
                Bundle doctorBundle = new Bundle();
                doctorBundle.putString("doctor_id", doctorList.get(position).getDoctor_id());
                toActivity(DoctorActivity.class, doctorBundle);
                break;
            case R.id.lv_search_hospital:
                Bundle hospitalBundle = new Bundle();
                hospitalBundle.putString("hospital_id", hospitalList.get(position).getHospital_id());
                toActivity(HospitalActivity.class, hospitalBundle);
                break;
        }
    }
}
