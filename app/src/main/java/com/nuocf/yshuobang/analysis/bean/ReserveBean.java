package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: ReserveBean
 * @Description: todo(预约通用实体类)
 * @date 2016/9/18
 */

public class ReserveBean extends StateMsg {
    private ReserveData data;

    @Override
    public String toString() {
        return "ReserveBean{" +
                "data=" + data +
                '}';
    }

    public ReserveData getData() {
        return data;
    }

    public void setData(ReserveData data) {
        this.data = data;
    }
}
