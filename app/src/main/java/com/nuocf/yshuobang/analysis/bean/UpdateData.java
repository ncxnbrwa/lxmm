package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
* @ClassName: UpdateData
* @Description: todo(检查更新接口实体类data数据)
* @author xiong
* @date 2016/9/21
*/

public class UpdateData {
    private List<UpdateDataList> list;

    public List<UpdateDataList> getList() {
        return list;
    }

    public void setList(List<UpdateDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UpdateData{" +
                "list=" + list +
                '}';
    }
}
