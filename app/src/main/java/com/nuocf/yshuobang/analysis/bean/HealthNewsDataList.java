package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: HealthNewsDataList
 * @Description: todo(健康资讯接口list数据)
 * @date 2016/9/1
 */

public class HealthNewsDataList {
    //资讯ID
    private String news_id;
    //资讯标题
    private String news_title;
    //资讯内容
    private String news_content;
    //资讯图片
    private String news_photo;
    //资讯h5详情网页链接
    private String news_url;

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getNews_photo() {
        return news_photo;
    }

    public void setNews_photo(String news_photo) {
        this.news_photo = news_photo;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    @Override
    public String toString() {
        return "HealthNewsDataList{" +
                "news_id=" + news_id +
                ", news_title='" + news_title + '\'' +
                ", news_content='" + news_content + '\'' +
                ", news_photo='" + news_photo + '\'' +
                ", news_url='" + news_url + '\'' +
                '}';
    }
}
