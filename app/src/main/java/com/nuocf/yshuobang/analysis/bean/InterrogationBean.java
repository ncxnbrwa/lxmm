package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: InterrogationBean
* @Description: todo(快速问诊接口实体类)
* @author xiong
* @date 2016/9/19
*/

public class InterrogationBean extends StateMsg {
    private InterrogationData data;

    public InterrogationData getData() {
        return data;
    }

    public void setData(InterrogationData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InterrogationBean{" +
                "data=" + data +
                '}';
    }
}
