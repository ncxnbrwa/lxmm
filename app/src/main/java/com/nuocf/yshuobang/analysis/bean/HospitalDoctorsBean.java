package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: HospitalDoctorsBean
* @Description: todo(医生列表接口实体类)
* @author xiong
* @date 2016/9/21
*/

public class HospitalDoctorsBean extends StateMsg {
    private HospitalDoctorsData data;

    public HospitalDoctorsData getData() {
        return data;
    }

    public void setData(HospitalDoctorsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HospitalDoctorsBean{" +
                "data=" + data +
                '}';
    }
}
