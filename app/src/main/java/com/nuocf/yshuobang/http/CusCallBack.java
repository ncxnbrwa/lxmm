package com.nuocf.yshuobang.http;

import android.widget.Toast;

import com.nuocf.yshuobang.analysis.ErrorAnalysis;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.x;

/**
* @ClassName: CusCallBack
* @Description: todo(企业应用统一请求逻辑处理类)
* @author yunfeng
* @date 2016/8/31
*/
public class CusCallBack<ResultType> implements  Callback.CommonCallback<ResultType> {

    /**
    * @Title:
    * @Description: todo(应用内部开始请求回调)
    */
    protected void onCusStart(){

    }
    @Override
    public void onSuccess(ResultType result) {
        DLog.d("Cuscallback","error");
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        DLog.d("Cuscallback","error");
//        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
        if (ex instanceof HttpException) { // 网络错误
            HttpException httpEx = (HttpException) ex;
            int responseCode = httpEx.getCode();
            String responseMsg = httpEx.getMessage();
            String errorResult = httpEx.getResult();
            //
            JSONParser<ErrorAnalysis> parser = new JSONParser(ErrorAnalysis.class,errorResult);
            ErrorAnalysis errorAnalysis = parser.doSimpleParse();
            if(errorAnalysis!=null){
                Toast.makeText(x.app(), errorAnalysis.getMessage(), Toast.LENGTH_SHORT).show();
                DLog.d("errorMessage","errorMessage"+"errorAnalysis.getMessage()");
            } else {
                // 其他错误
                //Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(x.app(),"当前网络不可用，请检查你的网络设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        DLog.d("Cuscallback","cancelled");
 //     Toast.makeText(x.app(),"user cancel", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFinished() {
        DLog.d("Cuscallback","finished");
    }
}
