package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
 * @author xiong
 * @ClassName: CommonListBean
 * @Description: todo(其它接口实体类)
 * @date 2016/8/31
 */
public class CommonListBean extends StateMsg {
    private List<AdDataList> list;

    public CommonListBean() {
    }

    public List<AdDataList> getList() {
        return list;
    }

    public void setList(List<AdDataList> list) {
        this.list = list;
    }
}
