package com.nuocf.yshuobang.frament;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.activity.HospitalActivity;
import com.nuocf.yshuobang.adapter.RecHospitalAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.HospitalBean;
import com.nuocf.yshuobang.analysis.bean.HospitalList;
import com.nuocf.yshuobang.common.NcfFilter;
import com.nuocf.yshuobang.common.RegetionPopWindow;
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

/**
 * Created by xiong on 2016/8/25.
 * 医院推荐
 */

@ContentView(R.layout.fragment_recommend)
public class RecommendFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,LoadMoreListView.OnLoadMoreListener, RegetionPopWindow.OnCitySelectListener {
    @ViewInject(R.id.recommend_header_right_bt)
    private TextView cityTextview;
    @ViewInject(R.id.lv_recommend)
    private LoadMoreListView mListRecommend;
    @ViewInject(R.id.refresh_and_load_more)
    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;

    private RecHospitalAdapter mRecHospitalAdapter = null;

    private NcfFilter mFilter = null;
    private PageControl mPageControl = null;
    private ArrayList<HospitalBean> mHospitals = null;
    private RegetionPopWindow mRegionPopwindow = null;
//    //声明AMapLocationClient类对象
//    public AMapLocationClient mLocationClient = null;
//    //声明AMapLocationClientOption对象
//    public AMapLocationClientOption mLocationOption = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPageControl = new PageControl();
        mFilter = new NcfFilter();
        //设置刷新，加载
        mRefreshAndLoadMoreView.setLoadMoreListView(mListRecommend);
        mListRecommend.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
        mRefreshAndLoadMoreView.setOnRefreshListener(this);
        mListRecommend.setOnLoadMoreListener(this);
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getBaseActivity());
//        mLocationOption = new AMapLocationClientOption();
//        setLocationOption();
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mLocationClient.startLocation();
//        //设置监听
//        mLocationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                if (aMapLocation != null) {
//                    if (aMapLocation.getErrorCode() == 0) {
//                        //定位成功
//                        String localCity = aMapLocation.getCity();
//                        String localCityCode = aMapLocation.getCityCode();
//                        cityTextview.setText(localCity);
//                        getHospitalList(localCityCode,mPageControl.getPage(),10);
//                        mLocationClient.stopLocation();
//                        mLocationClient.onDestroy();
//                    } else {
//                        DLog.d("AmapError", "location Error, ErrCode:"
//                                + aMapLocation.getErrorCode() + ", errInfo:"
//                                + aMapLocation.getErrorInfo());
//                    }
//                }else {
//                    showToast("定位失败");
//                }
//            }
//        });
    }

    @Override
    protected void lazyLoad() {
        if (!injected || !isVisible) {
            return;
        }
        //填充各控件的数据
        if (mHospitals != null && !mHospitals.isEmpty()) {
            setHospitalAdapter();
        } else {
            mPageControl.resetPage();
            getHospitalList(mFilter.getAreaId(), mPageControl.getPage(), 10);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecHospitalAdapter = null;
    }

    @Event(value = R.id.recommend_header_right_bt)
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.recommend_header_right_bt:
                if (mRegionPopwindow == null) {
                    mRegionPopwindow = new RegetionPopWindow(getBaseActivity(), this);
                }
                if (mRegionPopwindow.isWindowShowing()) {
                    mRegionPopwindow.closeRegionWindow();
                } else {
                    mRegionPopwindow.showRegionPopwindow(view);
                }
                break;
            default:
                break;
        }
    }

    //ListView的item点击事件注解操作
    @Event(type = AdapterView.OnItemClickListener.class, value = R.id.lv_recommend)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HospitalBean hospital = (HospitalBean) parent.getAdapter().getItem(position);
        Bundle b = new Bundle();
        b.putString("hospital_id", hospital.getHospital_id());
        getBaseActivity().toActivity(HospitalActivity.class, b);
    }

    private void getHospitalList(String regionId, int page, int size) {
        RequestParams params = new RequestParams(APIConstants.GET_HOSPITAL);
        params.addQueryStringParameter("district_id", regionId);
        params.addQueryStringParameter("page", Integer.toString(page));
        params.addQueryStringParameter("size", Integer.toString(size));
        sendGet(params, false, new CusCallBack<String>() {
            @Override
            protected void onCusStart() {
                //首页显示加载中
                if (mPageControl.getPage() == PageControl.START_PAGE_INDEX) {
                    getBaseActivity().showIndicator();
                }
            }

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                mRefreshAndLoadMoreView.setRefreshing(false);
                mListRecommend.onLoadComplete();
                JSONParser<HospitalList> parser = new JSONParser<HospitalList>(HospitalList.class,
                        new TypeToken<HospitalBean>() {
                        }.getType(), result);
                HospitalList data = parser.doParse();
                if (data != null) {
                    setHospitalData(data);
                    setHospitalAdapter();

                } else
                    getBaseActivity().showToast(getResources().getString(R.string.load_fail));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mRefreshAndLoadMoreView.setRefreshing(false);
                mListRecommend.onLoadComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                super.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                getBaseActivity().dismissIndicator();
            }
        });

    }

    private void setHospitalData(HospitalList data) {

        if (mHospitals == null) {
            mHospitals = new ArrayList<HospitalBean>(0);
        }
        if (mPageControl.getPage() == PageControl.START_PAGE_INDEX) {
            mHospitals.clear();
        }
        if (data.list != null) {
            mHospitals.addAll(data.list);
            //不够10条，说明已经加载完成所有，没有更多数据
            if (data.list.size() < 10) {
                mListRecommend.setHaveMoreData(false);
            } else {
                mListRecommend.setHaveMoreData(true);
            }
        } else {
            //无更多数据
            mListRecommend.setHaveMoreData(false);
            if (isVisible) {
                getBaseActivity().showToast("暂无更多数据");
            }
        }
    }

    private void setHospitalAdapter() {
        if (mRecHospitalAdapter == null) {
            mRecHospitalAdapter = new RecHospitalAdapter(getBaseActivity(), mHospitals);
            mListRecommend.setAdapter(mRecHospitalAdapter);
        } else {
            mRecHospitalAdapter.notifyDataSetChanged();
            if (mListRecommend.getAdapter() == null) {
                mListRecommend.setAdapter(mRecHospitalAdapter);
            }
        }
    }

    @Override
    public void onCitySelected(CityEntity city) {
        cityTextview.setText(city.getName());
        mFilter.setAreaId(city.getiD());
        mPageControl.resetPage();
        getHospitalList(mFilter.getAreaId(), mPageControl.getPage(), 10);
    }

    @Override
    public void onRefresh() {
        mPageControl.resetPage();
        getHospitalList(mFilter.getAreaId(), mPageControl.getPage(), 10);
    }

    @Override
    public void onLoadMore() {
        mPageControl.plusPage();
        getHospitalList(mFilter.getAreaId(), mPageControl.getPage(), 10);
    }
}
