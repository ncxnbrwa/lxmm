package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: MineReserveDataList
 * @Description: todo(我的预约订单接口实体类list数组)
 * @date 2016/9/18
 */

public class MineReserveDataList {
    //问诊ID
    private String order_id;
    //预约人姓名
    private String order_name;
    //预约状态:1=待安排，2=待确认，3=待评价，4=已完成，5=已取消
    private int order_state;
    //预定订单号码
    private String order_number;
    //下单时间
    private String order_time;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    @Override
    public String toString() {
        return "MineReserveDataList{" +
                "order_id='" + order_id + '\'' +
                ", order_name='" + order_name + '\'' +
                ", order_state=" + order_state +
                ", order_number='" + order_number + '\'' +
                ", order_time='" + order_time + '\'' +
                '}';
    }
}
