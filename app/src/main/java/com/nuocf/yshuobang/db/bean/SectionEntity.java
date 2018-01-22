package com.nuocf.yshuobang.db.bean;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Author: wyouflf
 * Date: 13-7-25
 * Time: 下午7:06
 */
@Table(name = "section")
public class SectionEntity {

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "name")
    public String name;

    @Column(name = "sectionId")
    private String sId;

    public List<SectionEntity> getSections(DbManager db,String parentID) throws DbException {
        return db.selector(SectionEntity.class).where("parentId", "=",parentID).findAll();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + sId +
                '}';
    }
}
