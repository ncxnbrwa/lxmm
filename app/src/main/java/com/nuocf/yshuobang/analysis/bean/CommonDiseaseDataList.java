package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: CommonDiseaseBean
 * @Description: todo(常见疾病实体类diseaselist数据)
 * @date 2016/9/22
 */

public class CommonDiseaseDataList {
    //疾病id
    private String desease_id;
    //疾病名称
    private String desease_name;

    public String getDesease_id() {
        return desease_id;
    }

    public void setDesease_id(String desease_id) {
        this.desease_id = desease_id;
    }

    public String getDesease_name() {
        return desease_name;
    }

    public void setDesease_name(String desease_name) {
        this.desease_name = desease_name;
    }

    @Override
    public String toString() {
        return "CommonDiseaseDataList{" +
                "desease_id='" + desease_id + '\'' +
                ", desease_name='" + desease_name + '\'' +
                '}';
    }
}
