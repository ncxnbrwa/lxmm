package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: ReserveDetailsBean
* @Description: todo(预约详情接口实体类)
* @author xiong
* @date 2016/9/20
*/

public class ReserveDetailsBean extends StateMsg{
    private ReserveDetailsData data;

    public ReserveDetailsData getData() {
        return data;
    }

    public void setData(ReserveDetailsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReserveDetailsBean{" +
                "data=" + data +
                '}';
    }
}
