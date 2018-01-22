package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
* @ClassName: SearchAllData
* @Description: todo(搜索所有的返回结果实体类data数据)
* @author xiong
* @date 2016/9/27
*/

public class SearchAllData {
    private List<SearchDoctorList> listdoc;
    private List<SearchHospitalList> listhos;
    private String doctor_count;
    private String hospital_count;

    public List<SearchDoctorList> getListdoc() {
        return listdoc;
    }

    public void setListdoc(List<SearchDoctorList> listdoc) {
        this.listdoc = listdoc;
    }

    public List<SearchHospitalList> getListhos() {
        return listhos;
    }

    public void setListhos(List<SearchHospitalList> listhos) {
        this.listhos = listhos;
    }

    public String getDoctor_count() {
        return doctor_count;
    }

    public void setDoctor_count(String doctor_count) {
        this.doctor_count = doctor_count;
    }

    public String getHospital_count() {
        return hospital_count;
    }

    public void setHospital_count(String hospital_count) {
        this.hospital_count = hospital_count;
    }

    @Override
    public String toString() {
        return "SearchAllData{" +
                "listdoc=" + listdoc +
                ", listhos=" + listhos +
                ", doctor_count='" + doctor_count + '\'' +
                ", hospital_count='" + hospital_count + '\'' +
                '}';
    }
}
