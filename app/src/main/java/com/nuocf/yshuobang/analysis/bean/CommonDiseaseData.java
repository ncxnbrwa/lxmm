package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
 * @ClassName: CommonDiseaseBean
 * @Description: todo(常见疾病实体类data数据)
 * @author xiong
 * @date 2016/9/22
 */

public class CommonDiseaseData {
    private List<CommonDiseaseDataList> list;

    public List<CommonDiseaseDataList> getDiseaselist() {
        return list;
    }

    public void setDiseaselist(List<CommonDiseaseDataList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CommonDiseaseData{" +
                "list=" + list +
                '}';
    }
}
