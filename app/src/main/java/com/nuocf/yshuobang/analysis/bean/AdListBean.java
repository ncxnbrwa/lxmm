package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: AdListBean
 * @Description: todo(其它接口实体类)
 * @date 2016/8/31
 */
public class AdListBean extends StateMsg {
    private AdData data;

    public AdData getData() {
        return data;
    }

    public void setData(AdData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AdListBean{" +
                "data=" + data +
                '}';
    }
}
