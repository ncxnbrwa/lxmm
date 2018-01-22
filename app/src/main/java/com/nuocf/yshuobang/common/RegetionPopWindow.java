package com.nuocf.yshuobang.common;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.CityAdapter;
import com.nuocf.yshuobang.adapter.ProvinceAdapter;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.db.AppDBmanager;
import com.nuocf.yshuobang.db.bean.CityEntity;


/**
* @ClassName: RegetionPopWindow
* @Description: todo(城市选择)
* @author yunfeng
* @date 2016/9/19
*/
public class RegetionPopWindow implements View.OnClickListener{
    private PopupWindow mPopupwindow;
    private BaseActivity mActivity;
    private ListView mProvinceLv;
    private ListView mCityLv;
    private View contentView;
    private ProvinceAdapter mProviceAdapter;
    private CityAdapter mCityAdapter;
    private List<CityEntity> mProvinces;
    private List<CityEntity> mCitys;
    private OnCitySelectListener mOncityListener;

    @Override
    public void onClick(View v) {
        closeRegionWindow();
    }

    //城市选择回调接口
    public interface OnCitySelectListener{
        public void onCitySelected(CityEntity city);
    }
    /**
    * @Title:
    * @Description: todo(初始化窗口视图)
    * @param     BaseAcitivity
    * @param     OnCitySelectListener
    */
    public RegetionPopWindow(BaseActivity actvitiy,OnCitySelectListener listener){
        mActivity = actvitiy;
        mOncityListener = listener;
        contentView = mActivity.getLayoutInflater().inflate(R.layout.windows_city,null);
        contentView.findViewById(R.id.region_city_bg).setOnClickListener(this);
        mProvinceLv = (ListView)contentView.findViewById(R.id.province_lv);
        mCityLv = (ListView)contentView.findViewById(R.id.city_lv);
        mProvinceLv.setOnItemClickListener(new OnProviceItemClickLitsener());
        mCityLv.setOnItemClickListener(new OnCityItemClickLitsener());
        onStart();
    }

    private class OnProviceItemClickLitsener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProvinceAdapter adapter = (ProvinceAdapter)parent.getAdapter();
            CityEntity province = adapter.getItem(position);
            adapter.freshCurrentSelect(position);
            if(province!=null){
                if(province.getiD().equals("0")){
                    mPopupwindow.dismiss();
                    setCityData(null);
                    setCityAdapter();
                    //选择全国
                    if(mOncityListener!=null) {
                        mOncityListener.onCitySelected(province);
                    }
                }else {
                    //加载当前省份下的城市数据
                    new getCityAsyncTask().execute(province.getiD());
                }
            }
        }
    }

    private class OnCityItemClickLitsener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CityEntity curentCity = ((CityAdapter)parent.getAdapter()).getItem(position);
            if(curentCity!=null){
                mPopupwindow.dismiss();
                //回调城市选择
                if(mOncityListener!=null) {
                    mOncityListener.onCitySelected(curentCity);
                }
            }
        }
    }

    public void onStart(){
        //100000 中国地区
        new getProviceAsyncTask().execute("100000");
    }

    /**
    * @Title:
    * @Description: todo(打开地区选择窗口)
    * @param     View 窗口显示位置控件
    */
    public void showRegionPopwindow(View downView){
        if(mPopupwindow==null){
            mPopupwindow=new PopupWindow(contentView,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mPopupwindow.setOutsideTouchable(true);
            mPopupwindow.setFocusable(true);
            mPopupwindow.setBackgroundDrawable(new BitmapDrawable());
        }
        mPopupwindow.showAsDropDown(downView);
    }

    //关闭地区选择窗口
    public void closeRegionWindow(){
        if(mPopupwindow!=null){
            mPopupwindow.dismiss();
        }
    }
    //窗口是否正在打开状态
    public boolean isWindowShowing(){
        if(mPopupwindow!=null){
            return mPopupwindow.isShowing();
        }
        return false;
    }
    /**
    * @ClassName: getProviceAsyncTask
    * @Description: todo(获取省份)
    * @date 2016/9/19
    */
    private class getProviceAsyncTask extends  AsyncTask<String,String,List<CityEntity>>{
        @Override
        protected List<CityEntity> doInBackground(String... params) {

            return AppDBmanager.queryCitys(params[0]);
        }

        @Override
        protected void onPostExecute(List<CityEntity> cityEntities) {
            super.onPostExecute(cityEntities);
            setProvinceData(cityEntities);
            setProvinceAdapter();
        }
    }

    private void setProvinceData(List<CityEntity> cityEntities){
        if(mProvinces==null){
            mProvinces = new ArrayList<>(0);
        }
        mProvinces.clear();
        //全国
        CityEntity allCity = new CityEntity();
        allCity.setParentId("100000");
        allCity.setiD("0");
        allCity.setName("全国");
        mProvinces.add(allCity);
        if(cityEntities!=null&&!cityEntities.isEmpty()){
            for(CityEntity city:cityEntities){
                mProvinces.add(city);
            }
        }
    }

    private void setProvinceAdapter(){
        if(mProviceAdapter==null){
            //初始化省份适配器
            mProviceAdapter = new ProvinceAdapter(mActivity,mProvinces);
            mProvinceLv.setAdapter(mProviceAdapter);
        }else{
            mProviceAdapter.notifyDataSetChanged();
            if(mProvinceLv.getAdapter()==null){
                mProvinceLv.setAdapter(mProviceAdapter);
            }
        }
    }
    /**
     * @ClassName: getCityAsyncTask
     * @Description: todo(获取城市)
     * @author yunfeng
     * @date 2016/9/19
     */
    private class getCityAsyncTask extends  AsyncTask<String,String,List<CityEntity>>{
        @Override
        protected List<CityEntity> doInBackground(String... params) {

            return AppDBmanager.queryCitys(params[0]);
        }

        @Override
        protected void onPostExecute(List<CityEntity> cityEntities) {
            super.onPostExecute(cityEntities);
            setCityData(cityEntities);
            setCityAdapter();
        }
    }

    private void setCityData(List<CityEntity> cityEntities){
        if(mCitys==null){
            mCitys = new ArrayList<>(0);
        }
        mCitys.clear();
        if(cityEntities!=null&&!cityEntities.isEmpty()){
            for(CityEntity city:cityEntities){
                mCitys.add(city);
            }
        }
    }

    private void setCityAdapter(){
        if(mCityAdapter==null){
            //初始化城市适配器
            mCityAdapter=new CityAdapter(mActivity,mCitys);
            mCityLv.setAdapter(mCityAdapter);
        }else{
            mCityAdapter.notifyDataSetChanged();
            if(mCityLv.getAdapter()==null){
                mCityLv.setAdapter(mCityAdapter);
            }
        }
    }

}
