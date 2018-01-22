package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: CommonDiseaseBean
* @Description: todo(常见疾病实体类)
* @author xiong
* @date 2016/9/22
*/

public class CommonDiseaseBean extends StateMsg {
    private CommonDiseaseData data;

    public CommonDiseaseData getData() {
        return data;
    }

    public void setData(CommonDiseaseData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonDiseaseBean{" +
                "data=" + data +
                '}';
    }
}
