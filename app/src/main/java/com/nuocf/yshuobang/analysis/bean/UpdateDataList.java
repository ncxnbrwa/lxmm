package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: UpdateDataList
 * @Description: todo(检查更新接口实体类list数据)
 * @date 2016/9/21
 */

public class UpdateDataList
{
    //版本ID
    private String version_id;
    //版本名
    private String version_name;
    //版本更新描述
    private String version_design;
    //版本下载地址
    private String version_url;

    public String getVersion_id()
    {
        return version_id;
    }

    public void setVersion_id(String version_id)
    {
        this.version_id = version_id;
    }

    public String getVersion_name()
    {
        return version_name;
    }

    public void setVersion_name(String version_name)
    {
        this.version_name = version_name;
    }

    public String getVersion_design()
    {
        return version_design;
    }

    public void setVersion_design(String version_design)
    {
        this.version_design = version_design;
    }

    public String getVersion_url()
    {
        return version_url;
    }

    public void setVersion_url(String version_url)
    {
        this.version_url = version_url;
    }

    @Override
    public String toString()
    {
        return "UpdateDataList{" +
                "version_id='" + version_id + '\'' +
                ", version_name='" + version_name + '\'' +
                ", version_design='" + version_design + '\'' +
                ", version_url='" + version_url + '\'' +
                '}';
    }
}
