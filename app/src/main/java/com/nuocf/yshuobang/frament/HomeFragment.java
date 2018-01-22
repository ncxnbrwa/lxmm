package com.nuocf.yshuobang.frament;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.activity.AppointmentActivity;
import com.nuocf.yshuobang.activity.DoctorActivity;
import com.nuocf.yshuobang.activity.GreenChannelActivity;
import com.nuocf.yshuobang.activity.InterrogationSpeedActivity;
import com.nuocf.yshuobang.activity.LoginActivity;
import com.nuocf.yshuobang.activity.NewsDetailsActivity;
import com.nuocf.yshuobang.activity.OverseaActivity;
import com.nuocf.yshuobang.activity.PreciseReserveActivity;
import com.nuocf.yshuobang.activity.SearchActivity;
import com.nuocf.yshuobang.adapter.HealthNewsAdapter;
import com.nuocf.yshuobang.adapter.HospitalDoctorsAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.AdDataList;
import com.nuocf.yshuobang.analysis.bean.AdListBean;
import com.nuocf.yshuobang.analysis.bean.HealthNewsBean;
import com.nuocf.yshuobang.analysis.bean.HealthNewsDataList;
import com.nuocf.yshuobang.analysis.bean.HospitalDoctorsBean;
import com.nuocf.yshuobang.analysis.bean.HospitalDoctorsDataList;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.imagecache.BitmapOptions;
import com.nuocf.yshuobang.myview.WrapListView;
import com.nuocf.yshuobang.myview.swipeRefresh.RefreshLayout;
import com.nuocf.yshuobang.myview.swipeRefresh.RefreshScrollView;
import com.nuocf.yshuobang.myview.swipeRefresh.ScrollLinerLayout;
import com.nuocf.yshuobang.utils.ELS;
import com.nuocf.yshuobang.utils.PageControl;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xiong
 * @ClassName: HomeFragment
 * @Description: todo(主页碎片)
 * @date 2016/8/25
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements RefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener
{
    @ViewInject(R.id.home_pager)
    private Banner vp_home;
    @ViewInject(R.id.home_ll_center)
    private LinearLayout ll_center;
    @ViewInject(R.id.lv_famous_doctors)
    private WrapListView lv_famDoctors;
    @ViewInject(R.id.lv_hot_news)
    private WrapListView lv_hotNews;

    @ViewInject(R.id.refresh_layout)
    private RefreshLayout refresh_layout;
    @ViewInject(R.id.refresh_scroll)
    private RefreshScrollView refresh_scroll;
    @ViewInject(R.id.scroll_liner)
    private ScrollLinerLayout scroll_liner;

    private List<HospitalDoctorsDataList> doctorsList = null;
    private List<HealthNewsDataList> newsList = null;
    private List<AdDataList> mAdList;
    private HospitalDoctorsAdapter mFamousDoctorsListAdapter = null;
    private HealthNewsAdapter mHealthNewsAdapter = null;
    private PageControl mPageControl = null;
    BitmapOptions imageOptions;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    protected void lazyLoad()
    {
        //填充各控件的数据
        if (mAdList == null || mAdList.isEmpty())
        {
            //获取顶部广告图片
            getAdImage();
        } else
        {
            configBanner(mAdList);
            if (doctorsList != null && !doctorsList.isEmpty())
            {
                setDoctorAdapter();
                if (newsList != null && !newsList.isEmpty())
                {
                    //获取名医推荐
                    setHotNewsAdapter();
                } else
                {
                    //获取热门资讯
                    mPageControl.resetPage();
                    getHotNews(mPageControl.getPage(), PageControl.ONE_PAGE_SIZE);
                }
            } else
            {
                //获取名医数据后触发启动获取热门资讯请求
                getDoctor();
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void init()
    {
        imageOptions = new BitmapOptions();
        imageOptions.initMyOptions(ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_XY,
                R.mipmap.doctor_img, R.mipmap.doctor_img);
        //设置图片轮播的宽高比
        ViewGroup.LayoutParams vp_homeLayoutParams = vp_home.getLayoutParams();
        vp_homeLayoutParams.height = ScreenInfo.screenWidth * 26 / 75;
        vp_home.setLayoutParams(vp_homeLayoutParams);

        //设置中间静态布局的宽高比
        ViewGroup.LayoutParams llParams = ll_center.getLayoutParams();
        llParams.height = ScreenInfo.screenWidth * 5 / 9;
        ll_center.setLayoutParams(llParams);

        mPageControl = new PageControl();
        refresh_layout.setIsOpenLoad(true);
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadListener(this);
    }

    @Event(value = {R.id.appointment_rl, R.id.interrogation_rl, R.id.oversea_rl
            , R.id.green_channel_rl, R.id.precise_reservation_rl, R.id.et_search})
    private void onclick(View view)
    {
        switch (view.getId())
        {
            //搜索界面跳转
            case R.id.et_search:
                getBaseActivity().toActivity(SearchActivity.class, null);
                break;
            case R.id.appointment_rl:
                //手术预约
                if (!TextUtils.isEmpty(ELS.getInstance(getBaseActivity()).getStringData(ELS.SESSION_KEY)))
                {
                    Bundle b = new Bundle();
                    b.putInt(AppointmentActivity.INTENT_BUNDLE_ORDER, Config.ORDER_SHOUSHU);
                    getBaseActivity().toActivity(AppointmentActivity.class, b);
                } else
                {
                    getBaseActivity().toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.interrogation_rl:
                //快速问诊
                if (!TextUtils.isEmpty(ELS.getInstance(getBaseActivity()).getStringData(ELS.SESSION_KEY)))
                {
                    startActivity(new Intent(getActivity(), InterrogationSpeedActivity.class));
                } else
                {
                    getBaseActivity().toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.oversea_rl:
                //海外医疗
                startActivity(new Intent(getActivity(), OverseaActivity.class));
                break;
            case R.id.green_channel_rl:
                //绿色通道
                startActivity(new Intent(getActivity(), GreenChannelActivity.class));
                break;
            case R.id.precise_reservation_rl:
                //精准预约
                if (!TextUtils.isEmpty(ELS.getInstance(getBaseActivity()).getStringData(ELS.SESSION_KEY)))
                {
                    startActivity(new Intent(getActivity(), PreciseReserveActivity.class));
                } else
                {
                    getBaseActivity().toActivity(LoginActivity.class, null);
                }
                break;
        }
    }

    /**
     * @Description: todo(获取广告轮播图片url)
     */
    public void getAdImage()
    {
        RequestParams params = new RequestParams(APIConstants.GET_ADVERTISEMENT);
        sendGet(params, true, new CusCallBack<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                JSONParser<AdListBean> adParser = new JSONParser<AdListBean>(AdListBean.class
                        , new TypeToken<AdListBean>()
                {
                }.getType(), result);
                addImgList(adParser.doSimpleParse());
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
                refresh_layout.setRefreshing(false);
                //获取名医推荐数据
                getDoctor();
            }
        });
    }

    /**
     * @param 参数
     * @Description: todo()
     */
    private void addImgList(AdListBean adBean)
    {
        if (Integer.parseInt(adBean.getState()) == 0)
        {
            if (adBean != null && adBean.getData() != null)
            {
                List<AdDataList> data = adBean.getData().getList();
                if (data != null && !data.isEmpty())
                {
                    if (mAdList == null)
                    {
                        mAdList = new ArrayList<>(0);
                    }
                    mAdList.clear();
                    mAdList.addAll(data);
                    configBanner(data);
                }
            }
        }
    }

    /**
     * @param 参数
     * @Description: todo(配置banner属性, 实现广告轮播)
     */
    private void configBanner(final List<AdDataList> data)
    {
        vp_home.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        vp_home.setImageLoader(new ImageLoader()
        {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView)
            {
                x.image().bind(imageView, path.toString(), imageOptions.getOptions());
            }
        });
        List<String> imageUrls = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
        {
            imageUrls.add(data.get(i).getAd_img());
        }
        vp_home.setImages(imageUrls);
        vp_home.setOnBannerClickListener(new OnBannerClickListener()
        {
            @Override
            public void OnBannerClick(int position)
            {
                String url = data.get(position - 1).getAd_url();
                Bundle bundle = new Bundle();
                bundle.putString("news_url", url);
                getBaseActivity().toActivity(NewsDetailsActivity.class, bundle);
            }
        });
        vp_home.setBannerAnimation(Transformer.Default);
        vp_home.isAutoPlay(true);
        vp_home.setDelayTime(3000);
        vp_home.setIndicatorGravity(BannerConfig.CENTER);
        vp_home.start();
    }

    /**
     * @Description: todo(获取名医推荐接口数据，并设置在主页相应列表位置)
     */
    public void getDoctor()
    {
        RequestParams params = new RequestParams(APIConstants.GET_FAMOUS_DOCTOR);
        sendGet(params, true, new CusCallBack<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                JSONParser<HospitalDoctorsBean> doctorParser = new JSONParser<HospitalDoctorsBean>(HospitalDoctorsBean.class
                        , new TypeToken<HospitalDoctorsBean>()
                {
                }.getType(), result);
                HospitalDoctorsBean doctorBean = doctorParser.doSimpleParse();
                if (doctorBean != null)
                {
                    if (doctorBean.getState().equals("0"))
                    {
                        if (doctorBean.getData() != null && doctorBean.getData().getList() != null)
                        {
                            addDoctorList(doctorBean.getData().getList());
                            setDoctorAdapter();
                        }
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
                //获取热门资讯数据
                mPageControl.resetPage();
                getHotNews(mPageControl.getPage(), 10);
            }
        });
    }

    /**
     * @param 参数
     * @Description: todo(添加名医数据)
     */
    private void addDoctorList(List<HospitalDoctorsDataList> doctors)
    {
        if (doctorsList == null)
        {
            doctorsList = new ArrayList<HospitalDoctorsDataList>(0);
        } else
        {
            doctorsList.clear();
        }
        doctorsList.addAll(doctors);
    }

    /**
     * @Description: todo(设置名医列表适配器)
     */
    private void setDoctorAdapter()
    {
        if (mFamousDoctorsListAdapter == null)
        {
            mFamousDoctorsListAdapter = new HospitalDoctorsAdapter(doctorsList
                    , getBaseActivity());
            lv_famDoctors.setAdapter(mFamousDoctorsListAdapter);
        } else
        {
            mFamousDoctorsListAdapter.notifyDataSetChanged();
            if (lv_famDoctors.getAdapter() == null)
            {
                lv_famDoctors.setAdapter(mFamousDoctorsListAdapter);
            }
        }
    }

    /**
     * @Description: todo(获取热门资讯接口数据)
     */
    public void getHotNews(int page, int size)
    {
        RequestParams params = new RequestParams(APIConstants.GET_HEALTH_NEWS);
        params.addQueryStringParameter("is_top", 1 + "");
        params.addQueryStringParameter("page", Integer.toString(page));
        params.addQueryStringParameter("size", Integer.toString(size));
        sendGet(params, true, new CusCallBack<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                JSONParser<HealthNewsBean> parser = new JSONParser<HealthNewsBean>(HealthNewsBean.class
                        , new TypeToken<HealthNewsBean>()
                {
                }.getType(), result);
                HealthNewsBean newsBean = parser.doSimpleParse();
                if (newsBean != null)
                {
                    if (newsBean.getState().equals("0"))
                    {
                        if (newsBean.getData() != null && newsBean.getData().getList() != null)
                        {
                            addNewsList(newsBean.getData().getList());
                            setHotNewsAdapter();
                        } else
                        {
                            showToast(getString(R.string.netdata_over));
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                super.onError(ex, isOnCallback);
                mPageControl.reducePage();
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
                refresh_layout.setLoading(false);
            }
        });
    }

    /**
     * @param 参数
     * @Description: todo(添加热门资讯数据)
     */
    private void addNewsList(List<HealthNewsDataList> news)
    {
        if (newsList == null)
        {
            newsList = new ArrayList<>(0);
        }
        //如果当前是第一页，则先清空已存在数据
        if (mPageControl.getPage() == PageControl.START_PAGE_INDEX)
        {
            newsList.clear();
        }
        if (!news.isEmpty())
        {
            newsList.addAll(news);
            if (news.size() < 10)
            {
                //少于请求页数，表示无更多数据
                refresh_layout.setIsOpenLoad(false);
            } else
            {
                refresh_layout.setIsOpenLoad(true);
            }
        } else
        {
            //没有更多数据
            refresh_layout.setIsOpenLoad(false);
        }
    }

    /**
     * @Description: todo(设置热门资讯的适配器)
     */
    private void setHotNewsAdapter()
    {
        if (mHealthNewsAdapter == null)
        {
            mHealthNewsAdapter = new HealthNewsAdapter(getActivity(), newsList);
            lv_hotNews.setAdapter(mHealthNewsAdapter);
        } else
        {
            mHealthNewsAdapter.notifyDataSetChanged();
            if (lv_hotNews.getAdapter() == null)
            {
                lv_hotNews.setAdapter(mHealthNewsAdapter);
            }
        }
    }

    /**
     * @Description: todo(名医list和热门资讯list项的单击事件)
     */
    @Event(value = {R.id.lv_famous_doctors, R.id.lv_hot_news}
            , type = AdapterView.OnItemClickListener.class)
    private void onListItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        switch (parent.getId())
        {
            case R.id.lv_famous_doctors:
                if (doctorsList != null)
                {
                    Intent doctorsIntent = new Intent(getActivity(), DoctorActivity.class);
                    Bundle doctorBundle = new Bundle();
                    doctorBundle.putString("doctor_id", doctorsList.get(position).getDoctor_id());
                    doctorsIntent.putExtras(doctorBundle);
                    startActivity(doctorsIntent);
                }
                break;
            case R.id.lv_hot_news:
                if (newsList != null)
                {
                    Intent newsIntent = new Intent(getActivity(), NewsDetailsActivity.class);
                    Bundle newsBundle = new Bundle();
                    newsBundle.putString("news_url", newsList.get(position).getNews_url());
                    newsIntent.putExtras(newsBundle);
                    startActivity(newsIntent);
                }
                break;
        }
    }

    @Override
    public void onLoad()
    {
        //上拉加载资讯下一页
        mPageControl.plusPage();
        getHotNews(mPageControl.getPage(), 10);
    }

    @Override
    public void onRefresh()
    {
        getAdImage();
    }

}
