package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
* @ClassName: HealthNewsData
* @Description: todo(健康资讯接口)
* @author xiong
* @date 2016/9/1
*/
public class HealthNewsData {
    private List<HealthNewsDataList> list;

    public List<HealthNewsDataList> getList() {
        return list;
    }

    public void setList(List<HealthNewsDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HealthNewsData{" +
                "list=" + list +
                '}';
    }
}
