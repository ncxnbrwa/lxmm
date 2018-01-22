package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
* @ClassName: HospitalDoctorsData
* @Description: todo(医生列表接口实体类data数据)
* @author xiong
* @date 2016/9/21
*/

public class HospitalDoctorsData {
    private List<HospitalDoctorsDataList> list;

    public List<HospitalDoctorsDataList> getList() {
        return list;
    }

    public void setList(List<HospitalDoctorsDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HospitalDoctorsData{" +
                "list=" + list +
                '}';
    }
}
