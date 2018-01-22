package com.nuocf.yshuobang.analysis.bean;

import java.util.List;

/**
 * Created by yunfeng on 2016/9/27.
 */
public class SectionList extends StateMsg{

    public SectionData data;

    public class SectionData{
        private List<SectionBean> list;

        public List<SectionBean> getList() {
            return list;
        }

        public void setList(List<SectionBean> list) {
            this.list = list;
        }
    }
}
