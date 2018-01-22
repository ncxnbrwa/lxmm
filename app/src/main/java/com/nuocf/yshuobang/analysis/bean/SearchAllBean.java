package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: SearchAllBean
* @Description: todo(搜索所有的返回结果实体类)
* @author xiong
* @date 2016/9/27
*/

public class SearchAllBean extends StateMsg {
    private SearchAllData data;

    public SearchAllData getData() {
        return data;
    }

    public void setData(SearchAllData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchAllBean{" +
                "data=" + data +
                '}';
    }
}
