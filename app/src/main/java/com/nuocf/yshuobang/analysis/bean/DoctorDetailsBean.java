package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: DoctorDetailsBean
 * @Description: todo(医生详情实体类)
 * @date 2016/9/16
 */

public class DoctorDetailsBean extends StateMsg {
    private DoctorDetailsData data;

    public DoctorDetailsData getData() {
        return data;
    }

    public void setData(DoctorDetailsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DoctorDetailsBean{" +
                "data=" + data +
                '}';
    }
}
