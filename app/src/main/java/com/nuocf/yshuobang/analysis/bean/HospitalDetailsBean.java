package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: HospitalDetailsBean
 * @Description: todo(医院详情接口实体类)
 * @date 2016/9/21
 */

public class HospitalDetailsBean extends StateMsg {
    private HospitalDetailsData data;

    public HospitalDetailsData getData() {
        return data;
    }

    public void setData(HospitalDetailsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HospitalDetailsBean{" +
                "data=" + data +
                '}';
    }
}
