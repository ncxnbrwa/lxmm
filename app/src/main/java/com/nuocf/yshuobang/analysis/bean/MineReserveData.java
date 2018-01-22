package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
* @ClassName: MineReserveData
* @Description: todo(我的预约订单接口实体类data数据)
* @author xiong
* @date 2016/9/18
*/

public class MineReserveData {
    private List<MineReserveDataList> list;

    public List<MineReserveDataList> getList() {
        return list;
    }

    public void setList(List<MineReserveDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MineReserveData{" +
                "list=" + list +
                '}';
    }
}
