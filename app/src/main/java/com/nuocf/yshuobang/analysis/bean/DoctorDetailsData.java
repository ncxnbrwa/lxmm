package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: DoctorDetailsData
 * @Description: todo(医生详情实体类data数据)
 * @date 2016/9/16
 */

public class DoctorDetailsData {
    //医生名
    private String doctor_name;
    //医生头像照片地址
    private String doctor_head;
    //医生职位
    private String doctor_position;
    //所属医院
    private String hospital_name;
    //所属科目
    private String section_name;
    //擅长
    private String good_at;
    //荣誉
    private String honor;
    //执业经历
    private String work_experience;

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_head() {
        return doctor_head;
    }

    public void setDoctor_head(String doctor_head) {
        this.doctor_head = doctor_head;
    }

    public String getDoctor_position() {
        return doctor_position;
    }

    public void setDoctor_position(String doctor_position) {
        this.doctor_position = doctor_position;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getGood_at() {
        return good_at;
    }

    public void setGood_at(String good_at) {
        this.good_at = good_at;
    }

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }

    @Override
    public String toString() {
        return "DoctorDetailsData{" +
                "doctor_name='" + doctor_name + '\'' +
                ", doctor_head='" + doctor_head + '\'' +
                ", doctor_position='" + doctor_position + '\'' +
                ", hospital_name='" + hospital_name + '\'' +
                ", section_name='" + section_name + '\'' +
                ", good_at='" + good_at + '\'' +
                ", honor='" + honor + '\'' +
                ", work_experience='" + work_experience + '\'' +
                '}';
    }
}
