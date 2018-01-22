package com.nuocf.yshuobang.analysis.bean;

/**
 * @author xiong
 * @ClassName: AdDataList
 * @Description: todo(广告轮播数据解析实体类（包含数组数据）)
 * @date 2016/8/31
 */

public class AdDataList {
    private String ad_id;
    private String ad_img;
    private String ad_title;
    private String ad_url;

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_img() {
        return ad_img;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    @Override
    public String toString() {
        return "AdDataList{" +
                "ad_id='" + ad_id + '\'' +
                ", ad_img='" + ad_img + '\'' +
                ", ad_title='" + ad_title + '\'' +
                ", ad_url='" + ad_url + '\'' +
                '}';
    }
}
