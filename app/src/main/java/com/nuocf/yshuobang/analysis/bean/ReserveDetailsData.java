package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
 * @author xiong
 * @ClassName: ReserveDetailsData
 * @Description: todo(预约详情接口实体类data数据)
 * @date 2016/9/20
 */

public class ReserveDetailsData {
    //用户名
    private String user_name;
    //疾病名称
    private String disease_name;
    //病情描述
    private String disease_des;
    //用户提交预约时间
    private String order_time;
    //病情资料
    private List<String> orderfile_url;
    //状态
    private int order_state;
    //备注
    private String note;
    //预约状态更新时间
    private String order_update_time;
    //预定订单号码
    private String order_number;
    //1=手术预约，2=精准预约，3=海外医疗
    private int order_type;
    //城市编码
    private String order_city;
    //期望就诊时间
    private String order_date;

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

    public String getOrder_city() {
        return order_city;
    }

    public void setOrder_city(String order_city) {
        this.order_city = order_city;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public List<String> getOrderfile_url() {
        return orderfile_url;
    }

    public void setOrderfile_url(List<String> orderfile_url) {
        this.orderfile_url = orderfile_url;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrder_update_time() {
        return order_update_time;
    }

    public void setOrder_update_time(String order_update_time) {
        this.order_update_time = order_update_time;
    }

    @Override
    public String toString() {
        return "ReserveDetailsData{" +
                "user_name='" + user_name + '\'' +
                ", disease_name='" + disease_name + '\'' +
                ", disease_des='" + disease_des + '\'' +
                ", order_time='" + order_time + '\'' +
                ", orderfile_url='" + orderfile_url + '\'' +
                ", order_state=" + order_state +
                ", note='" + note + '\'' +
                ", order_update_time='" + order_update_time + '\'' +
                '}';
    }
}
