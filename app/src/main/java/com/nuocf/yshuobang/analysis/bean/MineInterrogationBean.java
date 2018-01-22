package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: MineInterrogationBean
* @Description: todo(我的问诊接口实体类)
* @author xiong
* @date 2016/9/18
*/

public class MineInterrogationBean extends StateMsg {
    private MineInterrogationData data;

    public MineInterrogationData getData() {
        return data;
    }

    public void setData(MineInterrogationData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MineInterrogationBean{" +
                "data=" + data +
                '}';
    }
}
