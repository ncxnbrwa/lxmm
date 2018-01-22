package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: ReserveData
 * @Description: todo(预约data用户预约表)
 * @date 2016/9/18
 */

public class ReserveData {
    //所属用户
    private int user_id;
    //所属医院
    private int hospital_id;
    //预定订单号码
    private String order_number;
    //1=手术预约，2=精准预约，3=海外医疗
    private int order_type;
    //下单时间
    private String order_time;
    //就诊人姓名
    private String order_name;
    //就诊人性别
    private String order_gender;
    //就诊人号码
    private String order_phone;
    //就诊人年龄
    private int order_age;
    //城市编码，type=2时必填
    private int order_city;
    //期望就诊时间，type=2时必填
    private String order_date;
    //疾病名称，未确诊内容为空
    private String disease_name;
    //疾病描述
    private String disease_des;
    //预约状态:1=待安排，2=待确认，3=待评价，4=已完成，5=已取消
    private int order_state;
    //预约状态更新时间
    private String order_update_time;
    //预约状态备注
    private String order_design;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_gender() {
        return order_gender;
    }

    public void setOrder_gender(String order_gender) {
        this.order_gender = order_gender;
    }

    public String getOrder_phone() {
        return order_phone;
    }

    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    public int getOrder_age() {
        return order_age;
    }

    public void setOrder_age(int order_age) {
        this.order_age = order_age;
    }

    public int getOrder_city() {
        return order_city;
    }

    public void setOrder_city(int order_city) {
        this.order_city = order_city;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
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

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getOrder_update_time() {
        return order_update_time;
    }

    public void setOrder_update_time(String order_update_time) {
        this.order_update_time = order_update_time;
    }

    public String getOrder_design() {
        return order_design;
    }

    public void setOrder_design(String order_design) {
        this.order_design = order_design;
    }

    @Override
    public String toString() {
        return "ReserveData{" +
                "user_id=" + user_id +
                ", hospital_id=" + hospital_id +
                ", order_number='" + order_number + '\'' +
                ", order_type=" + order_type +
                ", order_time='" + order_time + '\'' +
                ", order_name='" + order_name + '\'' +
                ", order_gender='" + order_gender + '\'' +
                ", order_phone='" + order_phone + '\'' +
                ", order_age=" + order_age +
                ", order_city=" + order_city +
                ", order_date='" + order_date + '\'' +
                ", disease_name='" + disease_name + '\'' +
                ", disease_des='" + disease_des + '\'' +
                ", order_state=" + order_state +
                ", order_update_time='" + order_update_time + '\'' +
                ", order_design='" + order_design + '\'' +
                '}';
    }
}
