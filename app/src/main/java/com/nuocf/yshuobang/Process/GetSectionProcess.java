package com.nuocf.yshuobang.Process;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.xutils.http.RequestParams;

import java.lang.ref.SoftReference;
import java.util.List;

import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.SectionBean;
import com.nuocf.yshuobang.analysis.bean.SectionList;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;

/**
* @ClassName: GetSectionProcess
* @Description: todo(科目管理类)
* @author yunfeng
* @date 2016/9/27
*/
public class GetSectionProcess {
    //保持对象弱引用
    private SoftReference<BaseActivity> mSoftRef;
    //数据监听
    private GetSectionListener mSectionListnenr;
    //科目类型
    private SectionTypeEnum mSectionType= SectionTypeEnum.SECTION;
    //父级科目，子科目，疾病
    public enum SectionTypeEnum{
        SECTION,SUB_SECTION,DISEASE;
    }
    public interface GetSectionListener {
        public void getSectionSuccess(List<SectionBean> sections);

        public void getSubSectionSuccess(List<SectionBean> sections);

        public void getFaile();
    }

    /**
    * @Title:
    * @Description: todo(初始化科目加载类)
    * @param  BaseActivity
     * @param  GetSectionListener
    */
    public GetSectionProcess(BaseActivity activity,GetSectionListener listener){
        mSoftRef=new SoftReference<BaseActivity>(activity);
        mSectionListnenr=listener;
    }
    //释放当前引用
    public void releasProcess(){
        mSoftRef.clear();
        mSoftRef=null;
        mSectionListnenr = null;
    }

    public void getNetworkSectionList(String hospitalID,String sectionID){
        RequestParams params = new RequestParams(APIConstants.GET_SECTIOM);
        //是否获取医院下的科目列表
        if(!TextUtils.isEmpty(hospitalID)) {
            params.addQueryStringParameter("hospital_id", hospitalID);
        }
        //是否获取子科目
        if(!TextUtils.isEmpty(sectionID)) {
            mSectionType= SectionTypeEnum.SUB_SECTION;
            params.addQueryStringParameter("section_id", sectionID);
        }else{
            mSectionType= SectionTypeEnum.SECTION;
        }
        mSoftRef.get().sendGet(params, false, new CusCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                JSONParser<SectionList> parser = new JSONParser<SectionList>(SectionList.class
                        , new TypeToken<SectionList>() {
                }.getType(), result);
                SectionList sectionList = parser.doSimpleParse();
                if (sectionList != null && sectionList.data != null) {
                    if (mSectionListnenr != null) {
                        if(mSectionType==SectionTypeEnum.SECTION) {
                            mSectionListnenr.getSectionSuccess(sectionList.data.getList());
                        }else if(mSectionType==SectionTypeEnum.SUB_SECTION) {
                            mSectionListnenr.getSubSectionSuccess(sectionList.data.getList());
                        }
                    }
                }else{
                    if (mSectionListnenr != null) {
                        mSectionListnenr.getFaile();
                    }
                }
            }

            @Override
            public void onError (Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if (mSectionListnenr != null) {
                    mSectionListnenr.getFaile();
                }
            }
        });
    }

    public void saveSectionInDB(List<SectionBean> sections){

    }

    public void getSectionFromLocalDB(String parentId){

    }
}
