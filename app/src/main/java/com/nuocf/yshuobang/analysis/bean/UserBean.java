package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: UserBean
* @Description: todo(登录接口和注册接口的解析数据实体类)
* @author xiong
* @date 2016/8/30
*/
public class UserBean extends StateMsg{

    //具体的json结构数据
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "data=" + data +
                '}';
    }
}
