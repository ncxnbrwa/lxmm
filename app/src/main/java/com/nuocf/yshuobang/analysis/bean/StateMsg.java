package com.nuocf.yshuobang.analysis.bean;

/**
* @ClassName: StateMsg
* @Description: todo()
* @author xiong
* @date 2016/8/30
*/
public class StateMsg {
    //接口状态码
    private String state;
    //具体的错误信息
    private String message;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "StateMsg{" +
                "state=" + state +
                ", message='" + message + '\'' +
                '}';
    }
}
