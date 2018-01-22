package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: SearchHospitalList
* @Description: todo(医院搜索结果数据)
* @author xiong
* @date 2016/9/27
*/

public class SearchHospitalList {
    //医院ID
    private String hospital_id;
    //医院名
    private String hospital_name;

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    @Override
    public String toString() {
        return "SearchHospitalList{" +
                "hospital_id='" + hospital_id + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                '}';
    }
}
