package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: HospitalDoctorsDataList
 * @Description: todo(医生列表接口实体类list数据)
 * @date 2016/9/21
 */
public class HospitalDoctorsDataList {
    //医生ID
    private String doctor_id;
    //医生名
    private String doctor_name;
    //医院名称
    private String hospital_name;
    //医生职位
    private String doctor_position;
    //医院等级（暂时没用上）
    private String hospital_level;
    //医生简介
    private String doctor_des;
    //医生头像照片地址
    private String doctor_head;

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

    public String getHospital_level() {
        return hospital_level;
    }

    public void setHospital_level(String hospital_level) {
        this.hospital_level = hospital_level;
    }

    public String getDoctor_des() {
        return doctor_des;
    }

    public void setDoctor_des(String doctor_des) {
        this.doctor_des = doctor_des;
    }

    public String getDoctor_head() {
        return doctor_head;
    }

    public void setDoctor_head(String doctor_head) {
        this.doctor_head = doctor_head;
    }

    @Override
    public String toString() {
        return "HospitalDoctorsDataList{" +
                "doctor_id='" + doctor_id + '\'' +
                ", doctor_name='" + doctor_name + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", doctor_position='" + doctor_position + '\'' +
                ", hospital_level='" + hospital_level + '\'' +
                ", doctor_des='" + doctor_des + '\'' +
                ", doctor_head='" + doctor_head + '\'' +
                '}';
    }
}
