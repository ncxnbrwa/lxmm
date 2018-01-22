package com.nuocf.yshuobang.Process;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.nuocf.yshuobang.appBase.BaseActivity;

/**
* @ClassName: LocationClientUtils
* @Description: todo(定位的工具类)
* @author xiong
* @date 2016/10/9
*/

public class LocationClientUtils {
    BaseActivity act;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;

    public LocationClientUtils() {
        mLocationClient = new AMapLocationClient(act);
        mLocationOption = new AMapLocationClientOption();
    }

    public AMapLocationClient getLocationClient(){
        return mLocationClient;
    }

    public void initDefaultOptions(){
        //设置定位模式为低功耗模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：该方法默认为false。
        mLocationOption.setOnceLocation(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setWifiActiveScan(false);
        mLocationClient.setLocationOption(mLocationOption);
    }
}
