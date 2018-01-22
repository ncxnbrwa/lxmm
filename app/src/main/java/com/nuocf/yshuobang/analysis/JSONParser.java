package com.nuocf.yshuobang.analysis;

/**
 * @author JUNJ
 */

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;


/**
 * 解析类，采用json解析 1.doParse()对单个对象解析 ，返回对应的bean类
 * 2.doParseToList()多个对象解析，返回对应的ArrayList<bean>
 */

public class JSONParser<T> {

    private String rsp = null;

    private Class<?> cls = null;

    private Type type = null;

    private boolean isParser = true;

    public JSONParser(InputStream inputStream) {
        isParser = false;
        rsp = getString(inputStream);
    }

    /**
     * new TypeToken<List<SubUser>>() { // }.getType()
     */
    public JSONParser(Class<?> cls, Type type, InputStream inputStream) {
        rsp = getString(inputStream);
        this.cls = cls;
        this.type = type;
    }

    /**
     * new TypeToken<List<SubUser>>() { // }.getType()
     */
    public JSONParser(Class<?> cls, Type type, String json) {
        this.rsp = getString(json);
        this.cls = cls;
        this.type = type;
    }

    public JSONParser(Class<?> cls, String json) {
        this.rsp = getString(json);
        this.cls = cls;
    }

    @SuppressWarnings("unchecked")
    public T onParse(String json) {
        Gson gson = new Gson();
        return (T) gson.fromJson(json, cls);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<T> onParseToList(String json) {
        Gson gson = new Gson();
        return (ArrayList<T>) gson.fromJson(json, type);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<T> onParseToList(String json, Type type) {
        Gson gson = new Gson();
        return (ArrayList<T>) gson.fromJson(json, type);
    }

    //获取状态码的方法
    public StateMsg getState(BaseActivity act) {
        try {
            JSONObject jsonObject = new JSONObject(rsp);
            StateMsg stateMsg = new StateMsg();
            stateMsg.setState(jsonObject.getString("state"));
            stateMsg.setMessage(jsonObject.getString("message"));
            if (Integer.parseInt(stateMsg.getState()) == 10004) {
                act.dismissIndicator();
                act.sessionKeyInvalid();
            }
            return stateMsg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getOtherIntField(BaseActivity act, String key) {
        try {
            JSONObject jsonObject = new JSONObject(rsp);
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 解析单个对象（不含数组，只有单个类的情况）
     *
     * @param null
     * @return T extends ErrorBean
     * @throw DataParserException
     */
    public T doParse() {

        try {
            JSONObject jsonObject = new JSONObject(rsp);
            if (isParser)
                return onParse(jsonObject.getString("data"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析多个对象（即包含数组，数组中有多个类的情况）
     *
     * @param null
     * @return ArrayList<T extends ErrorBean>
     * @throws NetReqException
     */
    public ArrayList<T> doParseToList() {
        try {
            JSONObject jsonObject = new JSONObject(rsp);
            if (isParser)
                return onParseToList(jsonObject.getJSONObject("data").getString("list"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析多个对象（即包含数组，数组中有多个类的情况）
     *
     * @param null
     * @return ArrayList<T extends ErrorBean>
     * @throws NetReqException
     */
    public ArrayList<T> doParseToList(String specialKey, Type type) {
        try {
            JSONObject jsonObject = new JSONObject(rsp);
            if (isParser)
                return onParseToList(jsonObject.getString(specialKey), type);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析简单对象，如：{"state":0,"message":"","字段1":" "，"字段2":""}
     *
     * @param null
     * @return T extends ErrorBean
     * @throw DataParserException
     */
    public T doSimpleParse() {
        if (isParser)
            return onParse(rsp);
        return null;
    }

    /**
     * 解析自定义其他状态，如：{"state":2"}
     *
     * @param null
     * @return T extends ErrorBean
     * @throw DataParserException
     */
    public T doOtherParse(String state) {

        try {
            JSONObject jsonObject = new JSONObject(rsp);
            if (jsonObject.getString("state").equalsIgnoreCase(state)) {
                if (isParser)
                    return onParse(rsp);
            } else {
                return doSimpleParse();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String getString(String json) {
        int i = 1;
        if (json != null)
            while (json.length() > i && !json.startsWith("{")) {
                json = json.substring(i);
                i++;
            }
        return json;

    }

    /**
     * 流码转换为字符串
     *
     * @param InputStream
     * @return String
     */
    public String getString(InputStream is) {
        StringBuffer buffer = new StringBuffer();

        String temp = "";

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            try {
                while ((temp = reader.readLine()) != null) {
                    buffer.append(temp);
                }

                rsp = new String(buffer);

                if (rsp != null
                        && (rsp.startsWith("\ufeff") || rsp.startsWith("-"))) {
                    rsp = rsp.substring(1);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return rsp != null ? rsp.trim() : null;
    }
}