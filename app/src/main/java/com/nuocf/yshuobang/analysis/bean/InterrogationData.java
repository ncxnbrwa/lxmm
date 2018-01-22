package com.nuocf.yshuobang.analysis.bean;

/**
 * @author Administrator
 * @ClassName: InterrogationData
 * @Description: todo()
 * @date 2016/9/19
 */

public class InterrogationData {
    //所属用户
    private int user_id;
    //问诊时间
    private String inquiry_time;
    //问诊人姓名
    private String inquiry_name;
    //问诊人性别,1表示男，2表示女
    private String inquiry_gender;
    //问诊人号码
    private String inquiry_phone;
    //问诊人年龄
    private int inquiry_age;
    //疾病名称，未确诊可不填
    private String disease_name;
    //疾病描述
    private String disease_des;
    //1=问诊中，2=已回复，3=已取消
    private int inquiry_state;
    //问诊回复
    private String inquiry_reanwser;
    //回复时间
    private String inquiry_retime;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getInquiry_time() {
        return inquiry_time;
    }

    public void setInquiry_time(String inquiry_time) {
        this.inquiry_time = inquiry_time;
    }

    public String getInquiry_name() {
        return inquiry_name;
    }

    public void setInquiry_name(String inquiry_name) {
        this.inquiry_name = inquiry_name;
    }

    public String getInquiry_gender() {
        return inquiry_gender;
    }

    public void setInquiry_gender(String inquiry_gender) {
        this.inquiry_gender = inquiry_gender;
    }

    public String getInquiry_phone() {
        return inquiry_phone;
    }

    public void setInquiry_phone(String inquiry_phone) {
        this.inquiry_phone = inquiry_phone;
    }

    public int getInquiry_age() {
        return inquiry_age;
    }

    public void setInquiry_age(int inquiry_age) {
        this.inquiry_age = inquiry_age;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDisease_des() {
        return disease_des;
    }

    public void setDisease_des(String disease_des) {
        this.disease_des = disease_des;
    }

    public int getInquiry_state() {
        return inquiry_state;
    }

    public void setInquiry_state(int inquiry_state) {
        this.inquiry_state = inquiry_state;
    }

    public String getInquiry_reanwser() {
        return inquiry_reanwser;
    }

    public void setInquiry_reanwser(String inquiry_reanwser) {
        this.inquiry_reanwser = inquiry_reanwser;
    }

    public String getInquiry_retime() {
        return inquiry_retime;
    }

    public void setInquiry_retime(String inquiry_retime) {
        this.inquiry_retime = inquiry_retime;
    }

    @Override
    public String toString() {
        return "InterrogationData{" +
                "user_id=" + user_id +
                ", inquiry_time='" + inquiry_time + '\'' +
                ", inquiry_name='" + inquiry_name + '\'' +
                ", inquiry_gender='" + inquiry_gender + '\'' +
                ", inquiry_phone='" + inquiry_phone + '\'' +
                ", inquiry_age=" + inquiry_age +
                ", disease_name='" + disease_name + '\'' +
                ", disease_des='" + disease_des + '\'' +
                ", inquiry_state=" + inquiry_state +
                ", inquiry_reanwser='" + inquiry_reanwser + '\'' +
                ", inquiry_retime='" + inquiry_retime + '\'' +
                '}';
    }
}
