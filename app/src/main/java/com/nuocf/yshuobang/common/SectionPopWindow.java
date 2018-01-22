package com.nuocf.yshuobang.common;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import com.nuocf.yshuobang.Process.GetSectionProcess;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.SectionAdapter;
import com.nuocf.yshuobang.adapter.SubSectionAdapter;
import com.nuocf.yshuobang.analysis.bean.SectionBean;
import com.nuocf.yshuobang.appBase.BaseActivity;


/**
* @ClassName: SectionPopWindow
* @Description: todo(科目选择)
* @author yunfeng
* @date 2016/9/19
*/
public class SectionPopWindow implements GetSectionProcess.GetSectionListener,View.OnClickListener{
    private PopupWindow mPopupwindow;
    private BaseActivity mActivity;
    private ListView mSectionLv;
    private ListView mSubsectionLv;
    private View contentView;
    private SectionAdapter mSectionAdapter;
    private SubSectionAdapter mSubsectionAdapter;
    private List<SectionBean> mSections;
    private List<SectionBean> mSubSections;
    private OnSectionSelectListener mOnSectionListener;
    private GetSectionProcess mGetsectionPro = null;
    private String mHospitalID;
    private boolean mIsloading=false;

    @Override
    public void onClick(View v) {
        closeRegionWindow();
    }
    @Override
    public void getSectionSuccess(List<SectionBean> sections) {
        mIsloading=false;
        setSectionData(sections);
        setSectionAdapter();
    }

    @Override
    public void getSubSectionSuccess(List<SectionBean> sections) {
        mIsloading=false;
        setSubsectionData(sections);
        setSubSectionAdapter();
    }

    @Override
    public void getFaile() {
        mIsloading=true;
    }

    //城市选择回调接口
    public interface OnSectionSelectListener{
        public void onSectionSelected(SectionBean section);
    }
    /**
    * @Title:
    * @Description: todo(初始化窗口视图)
    * @param     BaseAcitivity
    * @param     OnCitySelectListener
    */
    public SectionPopWindow(BaseActivity actvitiy, OnSectionSelectListener listener){
        mActivity = actvitiy;
        mOnSectionListener = listener;
        mGetsectionPro = new GetSectionProcess(actvitiy,this);
        contentView = mActivity.getLayoutInflater().inflate(R.layout.windows_city,null);
        contentView.findViewById(R.id.region_city_bg).setOnClickListener(this);
        mSectionLv = (ListView)contentView.findViewById(R.id.province_lv);
        mSubsectionLv = (ListView)contentView.findViewById(R.id.city_lv);
        mSectionLv.setOnItemClickListener(new OnSectionItemClickLitsener());
        mSubsectionLv.setOnItemClickListener(new OnSubSectionItemClickLitsener());
    }

    private class OnSectionItemClickLitsener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SectionAdapter adapter = (SectionAdapter)parent.getAdapter();
            SectionBean section = adapter.getItem(position);
            adapter.freshCurrentSelect(position);
            if(section!=null){
                if(section.getSection_id().equals("0")){
                    mPopupwindow.dismiss();
                    setSubsectionData(null);
                    setSubSectionAdapter();
                    //选择全部
                    if(mOnSectionListener !=null) {
                        mOnSectionListener.onSectionSelected(section);
                    }
                }else {
                    //加载当前科目下的子数据
                    mIsloading=true;
                    mGetsectionPro.getNetworkSectionList(mHospitalID,section.getSection_id());
                }
            }
        }
    }

    private class OnSubSectionItemClickLitsener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SectionBean section = ((SubSectionAdapter)parent.getAdapter()).getItem(position);
            if(section!=null){
                mPopupwindow.dismiss();
                //回调选择
                if(mOnSectionListener !=null) {
                    mOnSectionListener.onSectionSelected(section);
                }
            }
        }
    }

    public void onStart(String hospitalID){
        //所有科目
        mHospitalID = hospitalID;
        mIsloading=true;
        mGetsectionPro.getNetworkSectionList(mHospitalID, null);
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
        //无数据时默认加载数据
        if(mSections==null||mSections.isEmpty()){
            if(!mIsloading) {
                onStart(mHospitalID);
            }
        }
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

    private void setSectionData(List<SectionBean> cityEntities){
        if(mSections ==null){
            mSections = new ArrayList<>(0);
        }
        mSections.clear();
        //全部科室
        SectionBean allCity = new SectionBean();
        allCity.setSection_id("0");
        allCity.setSection_name("全部科室");
        mSections.add(allCity);
        if(cityEntities!=null&&!cityEntities.isEmpty()){
            for(SectionBean city:cityEntities){
                mSections.add(city);
            }
        }
    }

    private void setSectionAdapter(){
        if(mSectionAdapter ==null){
            //初始化科室适配器
            mSectionAdapter = new SectionAdapter(mActivity, mSections);
            mSectionLv.setAdapter(mSectionAdapter);
        }else{
            mSectionAdapter.notifyDataSetChanged();
            if(mSectionLv.getAdapter()==null){
                mSectionLv.setAdapter(mSectionAdapter);
            }
        }
    }

    private void setSubsectionData(List<SectionBean> cityEntities){
        if(mSubSections ==null){
            mSubSections = new ArrayList<>(0);
        }
        mSubSections.clear();
        if(cityEntities!=null&&!cityEntities.isEmpty()){
            for(SectionBean city:cityEntities){
                mSubSections.add(city);
            }
        }
    }

    private void setSubSectionAdapter(){
        if(mSubsectionAdapter ==null){
            //初始化子科室适配器
            mSubsectionAdapter =new SubSectionAdapter(mActivity, mSubSections);
            mSubsectionLv.setAdapter(mSubsectionAdapter);
        }else{
            mSubsectionAdapter.notifyDataSetChanged();
            if(mSubsectionLv.getAdapter()==null){
                mSubsectionLv.setAdapter(mSubsectionAdapter);
            }
        }
    }

}
