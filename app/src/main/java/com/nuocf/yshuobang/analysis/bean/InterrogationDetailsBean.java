package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: InterrogationDetailsBean
 * @Description: todo(问诊详情实体类)
 * @date 2016/9/20
 */

public class InterrogationDetailsBean extends StateMsg {
    private InterrogationDetailsData data;

    public InterrogationDetailsData getData() {
        return data;
    }

    public void setData(InterrogationDetailsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InterrogationDetailsBean{" +
                "data=" + data +
                '}';
    }
}
