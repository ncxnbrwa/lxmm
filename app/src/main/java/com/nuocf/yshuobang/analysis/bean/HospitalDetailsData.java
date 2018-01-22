package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
 * @author xiong
 * @ClassName: HospitalDetailsData
 * @Description: todo(医院详情接口实体类data数据)
 * @date 2016/9/21
 */
public class HospitalDetailsData {
    //医院ID
    private String hospital_id;
    //医院图片
    private String hospital_big_img;
    //医院名
    private String hospital_name;
    //医院等级
    private String hospital_level;
    //医院简介(规模，荣誉，地位)
    private String hospital_des;
    //医院科室数量
    private String hospital_sections;
    //医院医生人数
    private String hospital_doctors;
    private List<HospitalDetailsSection> section;

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospital_big_img() {
        return hospital_big_img;
    }

    public void setHospital_big_img(String hospital_big_img) {
        this.hospital_big_img = hospital_big_img;
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

    public String getHospital_des() {
        return hospital_des;
    }

    public void setHospital_des(String hospital_des) {
        this.hospital_des = hospital_des;
    }

    public String getHospital_sections() {
        return hospital_sections;
    }

    public void setHospital_sections(String hospital_sections) {
        this.hospital_sections = hospital_sections;
    }

    public String getHospital_doctors() {
        return hospital_doctors;
    }

    public void setHospital_doctors(String hospital_doctors) {
        this.hospital_doctors = hospital_doctors;
    }

    public List<HospitalDetailsSection> getSection() {
        return section;
    }

    public void setSection(List<HospitalDetailsSection> section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "HospitalDetailsData{" +
                "hospital_id='" + hospital_id + '\'' +
                ", hospital_big_img='" + hospital_big_img + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", hospital_level='" + hospital_level + '\'' +
                ", hospital_des='" + hospital_des + '\'' +
                ", hospital_sections='" + hospital_sections + '\'' +
                ", hospital_doctors='" + hospital_doctors + '\'' +
                ", section=" + section +
                '}';
    }
}
