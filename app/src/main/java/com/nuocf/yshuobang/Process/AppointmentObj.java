package com.nuocf.yshuobang.Process;

/**
 * Created by yunfeng on 2016/9/26.
 */
public class AppointmentObj {

    private int type;
    private String cdiseaseName;
    private String diseaseDetails;
    private String diseasePhotos;
    private String fpatientName;
    private String fpatientAge;
    private String fpatientNumber;
    private String cityCode;
    private String cityName;
    private long date;
    private int sex = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCdiseaseName() {
        return cdiseaseName;
    }

    public void setCdiseaseName(String cdiseaseName) {
        this.cdiseaseName = cdiseaseName;
    }

    public String getDiseaseDetails() {
        return diseaseDetails;
    }

    public void setDiseaseDetails(String diseaseDetails) {
        this.diseaseDetails = diseaseDetails;
    }

    public String getDiseasePhotos() {
        return diseasePhotos;
    }

    public void setDiseasePhotos(String diseasePhotos) {
        this.diseasePhotos = diseasePhotos;
    }

    public String getFpatientName() {
        return fpatientName;
    }

    public void setFpatientName(String fpatientName) {
        this.fpatientName = fpatientName;
    }

    public String getFpatientAge() {
        return fpatientAge;
    }

    public void setFpatientAge(String fpatientAge) {
        this.fpatientAge = fpatientAge;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getFpatientNumber() {
        return fpatientNumber;
    }

    public void setFpatientNumber(String fpatientNumber) {
        this.fpatientNumber = fpatientNumber;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
