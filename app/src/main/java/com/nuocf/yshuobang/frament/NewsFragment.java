package com.nuocf.yshuobang.frament;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.activity.NewsDetailsActivity;
import com.nuocf.yshuobang.adapter.HealthNewsAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.HealthNewsBean;
import com.nuocf.yshuobang.analysis.bean.HealthNewsDataList;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.swipeListView.LoadMoreListView;
import com.nuocf.yshuobang.myview.swipeListView.RefreshAndLoadMoreView;
import com.nuocf.yshuobang.utils.PageControl;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xiong
 * @ClassName: NewsFragment
 * @Description: todo(健康资讯界面碎片)
 * @date 2016/8/25
 */
@ContentView(R.layout.fragment_news)
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,LoadMoreListView.OnLoadMoreListener{
    @ViewInject(R.id.refresh_lv_news)
    private LoadMoreListView lv_newsList;
    @ViewInject(R.id.refresh_and_load_more)
    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;

    List<HealthNewsDataList> newsList = null;
    HealthNewsAdapter mHealthNewsAdapter = null;
    //健康资讯请求时的页数
    PageControl mPageControl = null;

    @Override
    protected void lazyLoad() {
        if (!injected || !isVisible) {
            return;
        }
        //填充各控件的数据
        if (newsList != null && !newsList.isEmpty()) {
            if (lv_newsList.getAdapter() == null) {
                lv_newsList.setAdapter(mHealthNewsAdapter);
            }
        } else {
            mPageControl.resetPage();
            loadList(mPageControl.getPage(), 10);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPageControl = new PageControl();
        //设置刷新，加载
        mRefreshAndLoadMoreView.setLoadMoreListView(lv_newsList);
        lv_newsList.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
        mRefreshAndLoadMoreView.setOnRefreshListener(this);
        lv_newsList.setOnLoadMoreListener(this);
        //刷新的监听
        lv_newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HealthNewsDataList news = (HealthNewsDataList) adapterView.getAdapter().getItem(i);
                Intent newsIntent = new Intent(getActivity(), NewsDetailsActivity.class);
                newsIntent.putExtra("news_url", news.getNews_url());
                startActivity(newsIntent);

            }
        });
    }

    /**
     * @Description: todo(获取健康资讯数据)
     */
    public void loadList(int page, int size) {
        RequestParams params = new RequestParams(APIConstants.GET_HEALTH_NEWS);
        params.addQueryStringParameter("is_top", 0 + "");
        params.addQueryStringParameter("page", page + "");
        params.addQueryStringParameter("size", size + "");
//        params.addQueryStringParameter("size", 3 + "");
        sendGet(params, true, new CusCallBack<String>() {
            @Override
            protected void onCusStart() {
                //首页显示加载中
                if (mPageControl.getPage() == PageControl.START_PAGE_INDEX) {
                    getBaseActivity().showIndicator();
                }
            }

            @Override
            public void onSuccess(String result) {
                mRefreshAndLoadMoreView.setRefreshing(false);
                lv_newsList.onLoadComplete();
                JSONParser<HealthNewsBean> parser = new JSONParser<HealthNewsBean>(HealthNewsBean.class
                        , new TypeToken<HealthNewsBean>() {
                }.getType(), result);
                HealthNewsBean newsBean = parser.doSimpleParse();
                if (newsBean != null){
                    if(Integer.parseInt(newsBean.getState()) == 0) {
                        addNewsList(newsBean);
                        setNewsAdapter();
                    }else{
                        showToast(newsBean.getMessage());
                    }
                }else{
                    showToast(getResources().getString(R.string.load_fail));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                mRefreshAndLoadMoreView.setRefreshing(false);
                lv_newsList.onLoadComplete();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                getBaseActivity().dismissIndicator();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                super.onCancelled(cex);
            }
        });
    }

    /**
     * @param 参数
     * @Description: todo(添加健康咨询数据)
     */
    private void addNewsList(HealthNewsBean newsBean) {
        if (newsList == null) {
            newsList = new ArrayList<HealthNewsDataList>(0);
        }
        if (mPageControl.getPage() == PageControl.START_PAGE_INDEX) {
            newsList.clear();
        }
        if (newsBean != null) {
            if (newsBean.getData()!=null&&newsBean.getData().getList()!=null) {
                newsList.addAll(newsBean.getData().getList());
                //不够10条，说明已经加载完成所有，没有更多数据
                if (newsBean.getData().getList().size() < 10) {
                    lv_newsList.setHaveMoreData(false);
                }else{
                    lv_newsList.setHaveMoreData(true);
                }

            } else {
                showToast(getResources().getString(R.string.netdata_over));
                lv_newsList.setHaveMoreData(false);
            }
        }
    }

    /**
     * @Description: todo(设置健康咨询适配器)
     */
    private void setNewsAdapter() {
        if (mHealthNewsAdapter == null) {
            mHealthNewsAdapter = new HealthNewsAdapter(getActivity(), newsList);
            lv_newsList.setAdapter(mHealthNewsAdapter);
        } else {
            mHealthNewsAdapter.notifyDataSetChanged();
            if (lv_newsList.getAdapter() == null) {
                lv_newsList.setAdapter(mHealthNewsAdapter);
            }
        }
    }

    @Override
    public void onLoadMore() {
        mPageControl.plusPage();
        loadList(mPageControl.getPage(), 10);
    }

    @Override
    public void onRefresh() {
        mPageControl.resetPage();
        loadList(mPageControl.getPage(), 10);
    }
}
