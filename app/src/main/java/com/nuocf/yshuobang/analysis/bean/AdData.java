package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
* @ClassName: AdData
* @Description: todo(广播轮播接口data类)
* @author xiong
* @date 2016/9/1
*/

public class AdData {
    private List<AdDataList> list;

    public List<AdDataList> getList() {
        return list;
    }

    public void setList(List<AdDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AdData{" +
                "list=" + list +
                '}';
    }
}
