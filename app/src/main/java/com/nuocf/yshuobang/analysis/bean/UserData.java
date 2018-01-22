package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: UserData
* @Description: todo(数据解析实体类（不含数组数据）)
* @author xiong
* @date 2016/8/30
*/

public class UserData {
    //用户ID
    private String user_id;
    //用户名
    private String user_name;
    //密码
    private String user_pass;
    //头像
    private String user_img;
    //手机
    private String user_mobile;
    //角色默认0（0=用户、1=医生、2=医院管理者）
    private String role;
    //登录Session串
    private String session_key;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_pass='" + user_pass + '\'' +
                ", user_img='" + user_img + '\'' +
                ", user_mobile='" + user_mobile + '\'' +
                ", role='" + role + '\'' +
                ", session_key='" + session_key + '\'' +
                '}';
    }
}
