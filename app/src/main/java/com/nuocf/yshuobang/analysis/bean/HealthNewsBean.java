package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: HealthNewsBean
 * @Description: todo(健康资讯接口数据实体类)
 * @date 2016/9/1
 */
public class HealthNewsBean extends StateMsg {
    private HealthNewsData data;

    public HealthNewsData getData() {
        return data;
    }

    public void setData(HealthNewsData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HealthNewsBean{" +
                "data=" + data +
                '}';
    }
}
