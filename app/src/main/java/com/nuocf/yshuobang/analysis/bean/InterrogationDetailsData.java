package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: InterrogationDetailsData
 * @Description: todo(问诊详情实体类data数据)
 * @date 2016/9/20
 */

public class InterrogationDetailsData
{
    //疾病名称
    private String disease_name;
    //问诊时间
    private String inquiry_time;
    //病情描述
    private String disease_des;
    //问诊状态，1=未回复，2=已回复
    private int inquiry_state;
    //回复时间
    private String inquiry_retime;
    //医生回复内容
    private String inquiry_reanswer;

    public String getDisease_name()
    {
        return disease_name;
    }

    public void setDisease_name(String disease_name)
    {
        this.disease_name = disease_name;
    }

    public String getInquiry_time()
    {
        return inquiry_time;
    }

    public void setInquiry_time(String inquiry_time)
    {
        this.inquiry_time = inquiry_time;
    }

    public String getDisease_des()
    {
        return disease_des;
    }

    public void setDisease_des(String disease_des)
    {
        this.disease_des = disease_des;
    }

    public int getInquiry_state()
    {
        return inquiry_state;
    }

    public void setInquiry_state(int inquiry_state)
    {
        this.inquiry_state = inquiry_state;
    }

    public String getInquiry_retime()
    {
        return inquiry_retime;
    }

    public void setInquiry_retime(String inquiry_retime)
    {
        this.inquiry_retime = inquiry_retime;
    }

    public String getInquiry_reanswer()
    {
        return inquiry_reanswer;
    }

    public void setInquiry_reanswer(String inquiry_reanswer)
    {
        this.inquiry_reanswer = inquiry_reanswer;
    }

    @Override
    public String toString()
    {
        return "InterrogationDetailsData{" +
                "disease_name='" + disease_name + '\'' +
                ", inquiry_time='" + inquiry_time + '\'' +
                ", disease_des='" + disease_des + '\'' +
                ", inquiry_state=" + inquiry_state +
                ", inquiry_retime='" + inquiry_retime + '\'' +
                ", inquiry_reanswer='" + inquiry_reanswer + '\'' +
                '}';
    }
}
