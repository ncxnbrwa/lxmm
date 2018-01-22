package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
 * @author xiong
 * @ClassName: MineInterrogationData
 * @Description: todo(我的问诊接口实体类data数据)
 * @date 2016/9/18
 */

public class MineInterrogationData {
    private List<MineInterrogationDataList> list;

    public List<MineInterrogationDataList> getList() {
        return list;
    }

    public void setList(List<MineInterrogationDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MineInterrogationData{" +
                "list=" + list +
                '}';
    }
}
