package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: DoctorData
* @Description: todo(名医推荐接口data类)
* @author xiong
* @date 2016/9/1
*/
public class HospitalBean {
    private String hospital_id;
    private String hospital_name;
    private String hospital_level;
    private String section_num;
    private String doctors_num;
    private String hospital_big_img;

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

    public String getHospital_level() {
        return hospital_level;
    }

    public void setHospital_level(String hospital_level) {
        this.hospital_level = hospital_level;
    }

    public String getSection_num() {
        return section_num;
    }

    public void setSection_num(String section_num) {
        this.section_num = section_num;
    }

    public String getDoctors_num() {
        return doctors_num;
    }

    public void setDoctors_num(String doctors_num) {
        this.doctors_num = doctors_num;
    }

    public String getHospital_big_img() {
        return hospital_big_img;
    }

    public void setHospital_big_img(String hospital_big_img) {
        this.hospital_big_img = hospital_big_img;
    }
}
