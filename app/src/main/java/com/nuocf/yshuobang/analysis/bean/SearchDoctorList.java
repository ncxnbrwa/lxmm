package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: SearchDoctorList
 * @Description: todo(医生搜索结果数据)
 * @date 2016/9/27
 */

public class SearchDoctorList {
    //医生ID
    private String doctor_id;
    //医生名
    private String doctor_name;
    //医院名称
    private String hospital_name;
    //医生职位
    private String doctor_position;
    //医生科室
    private String section_name;

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getDoctor_position() {
        return doctor_position;
    }

    public void setDoctor_position(String doctor_position) {
        this.doctor_position = doctor_position;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    @Override
    public String toString() {
        return "SearchDoctorList{" +
                "doctor_id='" + doctor_id + '\'' +
                ", doctor_name='" + doctor_name + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", doctor_position='" + doctor_position + '\'' +
                ", section_name='" + section_name + '\'' +
                '}';
    }
}
