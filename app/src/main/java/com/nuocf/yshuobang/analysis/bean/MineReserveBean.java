package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: MineReserveBean
* @Description: todo(我的预约订单接口实体类)
* @author xiong
* @date 2016/9/18
*/
public class MineReserveBean extends StateMsg {
    private MineReserveData data;

    public MineReserveData getData() {
        return data;
    }

    public void setData(MineReserveData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MineReserveBean{" +
                "data=" + data +
                '}';
    }
}
