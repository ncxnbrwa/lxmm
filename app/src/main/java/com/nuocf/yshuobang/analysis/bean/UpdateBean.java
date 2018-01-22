package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: UpdateBean
 * @Description: todo(检查更新接口实体类)
 * @date 2016/9/21
 */

public class UpdateBean extends StateMsg {
    private UpdateDataList data;

    public UpdateDataList getData() {
        return data;
    }

    public void setData(UpdateDataList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "data=" + data +
                '}';
    }
}
