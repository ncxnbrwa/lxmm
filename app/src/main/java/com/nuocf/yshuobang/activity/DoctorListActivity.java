package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.HospitalDoctorsAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.HospitalDoctorsBean;
import com.nuocf.yshuobang.analysis.bean.HospitalDoctorsDataList;
import com.nuocf.yshuobang.analysis.bean.SectionBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.common.CommonPopupWindow;
import com.nuocf.yshuobang.common.NcfFilter;
import com.nuocf.yshuobang.common.RegetionPopWindow;
import com.nuocf.yshuobang.common.SectionPopWindow;
import com.nuocf.yshuobang.db.bean.CityEntity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.swipeListView.LoadMoreListView;
import com.nuocf.yshuobang.myview.swipeListView.RefreshAndLoadMoreView;
import com.nuocf.yshuobang.utils.PageControl;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiong
 * @ClassName: DoctorListActivity
 * @Description: todo(医生列表界面)
 * @date 2016/8/29
 */
@ContentView(R.layout.activity_doctor_list)
public class DoctorListActivity extends BaseActivity implements SectionPopWindow.OnSectionSelectListener,
        RegetionPopWindow.OnCitySelectListener,SwipeRefreshLayout.OnRefreshListener,LoadMoreListView.OnLoadMoreListener
{
    @ViewInject(R.id.lv_doctors)
    private LoadMoreListView lvDoctors;
    @ViewInject(R.id.refresh_and_load_more)
    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
    @ViewInject(R.id.tv_section)
    private TextView sectionTv;
    @ViewInject(R.id.tv_sort)
    private TextView sortTv;

    private PageControl mPageControl;
    private NcfFilter mDocFilter;
    private HospitalDoctorsAdapter adapter = null;
    private List<HospitalDoctorsDataList> list = null;
    private SectionPopWindow mSectionPopwindow = null;
    private String mHospitalID = null;
    private String mSectionId = null;

    @Override
    protected void initView()
    {
        //设置刷新，加载
        mRefreshAndLoadMoreView.setLoadMoreListView(lvDoctors);
        lvDoctors.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
        mRefreshAndLoadMoreView.setOnRefreshListener(this);
        lvDoctors.setOnLoadMoreListener(this);

        mPageControl = new PageControl();
        mDocFilter = new NcfFilter();
        Bundle bundle = getIntent().getExtras();
        String cSectionName = bundle.getString("section_name");
        mSectionId = bundle.getString("section_id");
        mHospitalID = bundle.getString("hospital_id");
        if (!TextUtils.isEmpty(cSectionName))
        {
            sectionTv.setText(cSectionName);
        } else
        {
            sectionTv.setText("全部科室");
        }
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
            getDoctorsList();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        adapter = null;
    }

    private void getDoctorsList()
    {
        RequestParams params = new RequestParams(APIConstants.HOSPITAL_DOCTORS_LIST);
        params.addQueryStringParameter("hospital_id", mHospitalID);
        params.addQueryStringParameter("section_id", mDocFilter.getSectionId());
        if (!TextUtils.isEmpty(mDocFilter.getDeseaseId()))
        {
            params.addQueryStringParameter("desease_id", mDocFilter.getDeseaseId());
        }
        params.addQueryStringParameter("doctor_sort", mDocFilter.getOrderBy());
        params.addQueryStringParameter("page", mPageControl.getPage() + "");
        params.addQueryStringParameter("size", "10");
        sendGet(params, false, new CusCallBack<String>()
        {
            @Override
            protected void onCusStart()
            {
                //首页显示加载中
                if (mPageControl.getPage() == PageControl.START_PAGE_INDEX)
                {
                    showIndicator();
                }
            }

            @Override
            public void onSuccess(String result)
            {
                mRefreshAndLoadMoreView.setRefreshing(false);
                lvDoctors.onLoadComplete();
                JSONParser<HospitalDoctorsBean> parser = new JSONParser<HospitalDoctorsBean>(
                        HospitalDoctorsBean.class,
                        new TypeToken<HospitalDoctorsBean>()
                        {
                        }.getType(),
                        result);
                HospitalDoctorsBean doctorsBean = parser.doSimpleParse();
                if (doctorsBean != null) {
                    if (doctorsBean.getState().equals("0")) {
                        if (doctorsBean.getData() != null) {
                            addDoctorsData(doctorsBean);
                            setListAdapter();
                        }else{
                            showToast(getResources().getString(R.string.load_fail));
                        }
                    } else {
                        showToast(doctorsBean.getMessage());
                    }
                }else{
                    showToast(getResources().getString(R.string.load_fail));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                super.onError(ex, isOnCallback);
                mRefreshAndLoadMoreView.setRefreshing(false);
                lvDoctors.onLoadComplete();
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

    //添加解析的数据给list集合
    private void addDoctorsData(HospitalDoctorsBean bean)
    {
        if (list == null)
        {
            list = new ArrayList<>(0);
        }
        if (mPageControl.getPage() == PageControl.START_PAGE_INDEX)
        {
            list.clear();
        }
        if (bean.getData().getList() != null)
        {
            list.addAll(bean.getData().getList());
            //不够10条，说明已经加载完成所有，没有更多数据
            if (bean.getData().getList().size() < 10)
            {
                lvDoctors.setHaveMoreData(false);
            } else
            {
                lvDoctors.setHaveMoreData(true);
            }
        } else {
            //无更多数据
            showToast("暂无更多数据");
            lvDoctors.setHaveMoreData(false);
        }
    }

    //设置适配器
    private void setListAdapter()
    {
        if (adapter == null)
        {
            adapter = new HospitalDoctorsAdapter(list, getBaseActivity());
            lvDoctors.setAdapter(adapter);
        } else
        {
            adapter.notifyDataSetChanged();
            if (lvDoctors.getAdapter() == null)
            {
                lvDoctors.setAdapter(adapter);
            }
        }
    }

    //列表item项点击事件
    @Event(value = R.id.lv_doctors
            , type = AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Bundle bundle = new Bundle();
        bundle.putString("doctor_id", ((HospitalDoctorsDataList) parent.getAdapter().getItem(position)).getDoctor_id());
        toActivity(DoctorActivity.class, bundle);
    }

    @Event(value = {R.id.header_left_tv, R.id.rl_sections, R.id.rl_sort})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                DoctorListActivity.this.finish();
                break;
            //科室选择
            case R.id.rl_sections:
                if (mSectionPopwindow == null)
                {
                    mSectionPopwindow = new SectionPopWindow(getBaseActivity(), this);
                    mSectionPopwindow.onStart(mHospitalID);
                }
                if (mSectionPopwindow.isWindowShowing())
                {
                    mSectionPopwindow.closeRegionWindow();
                } else
                {
                    mSectionPopwindow.showRegionPopwindow(v);
                }
                break;
            //排序选择
            case R.id.rl_sort:
                showSortPopWindow(v);
                break;
        }

    }

    private PopupWindow mSortPopwindow = null;

    public void showSortPopWindow(View dropView)
    {
        if (mSortPopwindow == null)
        {
            mSortPopwindow = new CommonPopupWindow(this,
                    R.layout.doctor_list_sort)
                    .getPopupWindow(ViewGroup.LayoutParams.MATCH_PARENT);

            View view = mSortPopwindow.getContentView();
            final Button bt1 = (Button) view.findViewById(R.id.item_syn_sort);
            Button bt2 = (Button) view.findViewById(R.id.item_three_a_sort);
            final Button bt3 = (Button) view.findViewById(R.id.item_positon_sort);
            LinearLayout ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
            ll_bg.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mSortPopwindow.dismiss();
                }
            });
            bt1.setSelected(true);
            bt1.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    bt1.setSelected(true);
                    bt3.setSelected(false);
                    mSortPopwindow.dismiss();
                    setCurrentDoctorSort(Config.SORT_SYN);
                }
            });
            bt2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    mSortPopwindow.dismiss();
                    setCurrentDoctorSort(Config.SORT_THREE_A);
                }
            });
            bt3.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    bt1.setSelected(false);
                    bt3.setSelected(true);
                    mSortPopwindow.dismiss();
                    setCurrentDoctorSort(Config.SORT_POSTION);
                }
            });
        }
        mSortPopwindow.showAsDropDown(dropView);
    }

    @Override
    public void onSectionSelected(SectionBean section)
    {
        //选择科室
        sectionTv.setText(section.getSection_name());
        mDocFilter.setSectionId(section.getSection_id());
        mPageControl.resetPage();
        getDoctorsList();
    }
    //设置当前排序方式
    private void setCurrentDoctorSort(String sort)
    {
        mDocFilter.setOrderBy(sort);
        if (sort.equals(Config.SORT_SYN))
        {
            sortTv.setText("综合排序");
        } else if (sort.equals(Config.SORT_THREE_A))
        {
            sortTv.setText("三甲优先");
        } else
        {
            sortTv.setText("职位从高到低");
        }
        mPageControl.resetPage();
        getDoctorsList();
    }

    @Override
    public void onCitySelected(CityEntity city) {

    }

    @Override
    public void onLoadMore() {
        mPageControl.plusPage();
        getDoctorsList();
    }

    @Override
    public void onRefresh() {
        mPageControl.resetPage();
        getDoctorsList();
    }
}
