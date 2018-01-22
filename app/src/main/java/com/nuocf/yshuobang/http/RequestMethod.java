/**
 *
 */
package com.nuocf.yshuobang.http;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ELS;
import com.nuocf.yshuobang.utils.HTimeUtils;
import com.nuocf.yshuobang.utils.MD5_32bit;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
* @ClassName: RequestMethod
* @Description: todo(网络请求基础类)
* @author yunfeng
* @date 2016/8/31
*/
//请求数据的类
public class RequestMethod {

    /**
    * @Title:
    * @Description: todo(设置应用请求header)
    * @param params xutils3.0 requestParams
    * @param isAuth 是否需要签名
    */
    protected void setRequestHeader(RequestParams params,boolean isAuth) {
        //客户端类型：android
        params.addHeader("clienttype", Config.MOBILE_TYPE);
        //客户端版本号
        params.addHeader("clientversion", Config.CLIENT_VERSION);
        //服务端接口版本号
        params.addHeader("apiversion", Config.API_VERSION);
        //用户登录标识
        //params.addHeader("sessionkey",ELS.getInstance(x.app()).getStringData(ELS.SESSION_KEY));
        //客户端请求当前时间戳
        String currentDatetime = String.valueOf(HTimeUtils.getUnixTimestamp());
        //认证签名
        if (isAuth){
            params.addHeader("auth",generateAuth(currentDatetime));
        }
        printlnRequestInfo(params);
    }

    /**
    * @Title: generateAuth
    * @Description: todo(生成接口认证签名)
    * @param datetime 系统当前时间戳
    * @return String
    */
    private String generateAuth(String datetime){
        StringBuffer sbf = new StringBuffer(ELS.getInstance(x.app()).getStringData(ELS.SESSION_KEY));
        sbf.append(datetime);
        String preSignature = new String(sbf.toString());
        String md5Signature = MD5_32bit.md5(sbf.substring(4, sbf.length() - 4));
        sbf.delete(0,sbf.length()-1);
        return  preSignature+md5Signature;
    }

    /**
     * @param params     请求接口所需参数
     * @param ifAuth     是否需要签名
     * @param cusCallback 请求回调监听
     */
    public <T> Callback.Cancelable sendPost(RequestParams params,boolean ifAuth,CusCallBack<T> cusCallback) {
        setRequestHeader(params, ifAuth);
        //回调开始请求
        cusCallback.onCusStart();
        return x.http().post(params, cusCallback);
    }

    /**
     * @param params     请求接口所需参数
     * @param ifAuth     是否需要签名
     * @param cusCallback  请求回调监听
     */

    public <T> Callback.Cancelable sendGet(RequestParams params,boolean ifAuth,CusCallBack<T> cusCallback) {
        setRequestHeader(params,ifAuth);
        //回调开始请求
        cusCallback.onCusStart();
        return x.http().get(params, cusCallback);
    }

    /**
     * @param params     上传接口所需参数
     * @param ifAuth     是否需要签名
     * @param cusCallback  请求回调监听
     */

    public <T> Callback.Cancelable upLoadFile(RequestParams params,boolean ifAuth,CusCallBack<T> cusCallback) {
        setRequestHeader(params,ifAuth);
        params.setMultipart(true);
        cusCallback.onCusStart();
        return x.http().post(params, cusCallback);
    }

    /**
     * @param params     下载接口所需参数
     * @param ifAuth     是否需要签名
     * @param cusCallback  请求回调监听
     */

    public <T> Callback.Cancelable downLoadFile(RequestParams params,boolean ifAuth,Callback.CommonCallback<T> cusCallback) {
        setRequestHeader(params,ifAuth);
        //设置断点续传
        params.setAutoResume(true);
        return x.http().post(params,cusCallback);
    }

    private void printlnRequestInfo(RequestParams params) {
        DLog.i("-------url>>",params.getUri());
        if(params.getHeaders()!=null){
            for (RequestParams.Header item : params.getHeaders()) {
                DLog.i("-------header>>",
                        item.key+":"+item.getValueStr());
            }
        }
        List<KeyValue> list = params.getStringParams();
        if (list != null) {
            for (KeyValue pair : list) {
                DLog.v("-------params>>", pair.key+":"+pair.getValueStr() );
            }
        }
    }
}
