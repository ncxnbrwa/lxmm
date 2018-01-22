package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: HospitalDetailsSection
 * @Description: todo(医院详情接口实体类section数据)
 * @date 2016/9/21
 */

public class HospitalDetailsSection {
    //科室ID
    private String section_id;
    //科室名
    private String section_name;
    //父级科室id，父级为0代表一级科目如：（0外科，骨科，妇产科、>0手外科，骨关科，足趾科 ）
    private String parent_id;

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "HospitalDetailsSection{" +
                "section_id='" + section_id + '\'' +
                ", section_name='" + section_name + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
