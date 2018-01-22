package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: MineInterrogationDataList
 * @Description: todo(我的问诊接口实体类data数据list数组)
 * @date 2016/9/18
 */

public class MineInterrogationDataList {
    //问诊ID
    private String inquiry_id;
    //问诊人姓名
    private String inquiry_name;
    //疾病描述（前20字)
    private String disease_design;
    //问诊时间
    private String inquiry_time;
    //1=未回复，2=已回复
    private int inquiry_state ;

    public String getInquiry_id() {
        return inquiry_id;
    }

    public void setInquiry_id(String inquiry_id) {
        this.inquiry_id = inquiry_id;
    }

    public String getInquiry_name() {
        return inquiry_name;
    }

    public void setInquiry_name(String inquiry_name) {
        this.inquiry_name = inquiry_name;
    }

    public String getDesease_design() {
        return disease_design;
    }

    public void setDesease_design(String desease_design) {
        this.disease_design = desease_design;
    }

    public String getInquiry_time() {
        return inquiry_time;
    }

    public void setInquiry_time(String inquiry_time) {
        this.inquiry_time = inquiry_time;
    }

    public int getInquiry_state() {
        return inquiry_state;
    }

    public void setInquiry_state(int inquiry_state) {
        this.inquiry_state = inquiry_state;
    }

    @Override
    public String toString() {
        return "MineInterrogationDataList{" +
                "inquiry_id='" + inquiry_id + '\'' +
                ", inquiry_name='" + inquiry_name + '\'' +
                ", desease_design='" + disease_design + '\'' +
                ", inquiry_time='" + inquiry_time + '\'' +
                ", inquiry_state=" + inquiry_state +
                '}';
    }
}
