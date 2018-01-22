package com.nuocf.yshuobang.activity;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.UpdateBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.DialogView;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ELS;
import com.nuocf.yshuobang.utils.PathUtil;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.DecimalFormat;


/**
 * @author xiong
 * @ClassName: MineSettingActivity
 * @Description: todo(设置界面)
 * @date 2016/8/26
 */
@ContentView(R.layout.activity_mine_setting)
public class MineSettingActivity extends BaseActivity
{
    AlertDialog.Builder logoutDialog;
    AlertDialog mLogoutDialog;
    ELS els;
    @ViewInject(R.id.login_rl)
    private RelativeLayout loginLayout;
    @ViewInject(R.id.logout_rl)
    private RelativeLayout logoutLayout;
    @ViewInject(R.id.clear_cache)
    private RelativeLayout cacheLayout;
    @ViewInject(R.id.cache_tv)
    private TextView cacheText;
    @ViewInject(R.id.version_tv)
    private TextView versionText;
    // 缓存大小
    private long mCacheSize = 0;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView()
    {
        els = ELS.getInstance(this);
        if (TextUtils.isEmpty(els.getStringData(ELS.SESSION_KEY)))
        {
            logoutLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        } else
        {
            logoutLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.logout_rl
            , R.id.checkupdate_rl, R.id.login_rl,R.id.clear_cache})
    private void onclick(View view)
    {
        switch (view.getId())
        {
            //左上角按钮回退事件
            case R.id.header_left_tv:
                MineSettingActivity.this.finish();
                break;
            //注销，跳转登录界面
            case R.id.logout_rl:
                logout();
                break;
            //检查更新
            case R.id.checkupdate_rl:
                chechUpdate();
                break;
            //未登录时出现登录
            case R.id.login_rl:
                toActivity(LoginActivity.class, null);
                break;
            case R.id.clear_cache:
                clean(mCacheSize);
                break;
        }
    }

    //注销
    private void logout()
    {
        View view = LayoutInflater.from(getBaseActivity())
                .inflate(R.layout.common_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        title.setText("注销");
        TextView content = (TextView) view.findViewById(R.id.tv_content);
        content.setText("你确定要注销吗？");
        Button btnConfirm = (Button) view.findViewById(R.id.btn_positive);
        btnConfirm.setText("确定");
        btnConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //清除当前用户信息
                mLogoutDialog.dismiss();
                els.clearUserInfo();
                mElfApp.finishApplication();
                toActivity(LoginActivity.class, null);
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btn_negative);
        btnCancel.setText("取消");
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mLogoutDialog.dismiss();
            }
        });
        logoutDialog = new AlertDialog.Builder(getBaseActivity());
        mLogoutDialog = logoutDialog.create();
        mLogoutDialog.show();
        mLogoutDialog.getWindow().setContentView(view);
    }

    //检查更新
    private void chechUpdate()
    {
        RequestParams params = new RequestParams(APIConstants.CHECK_UPDATE);
//        params.addQueryStringParameter("version", Config.CLIENT_VERSION);
        params.addQueryStringParameter("terminal", "android");
        sendGet(params, false, new CusCallBack<String>()
        {
            @Override
            protected void onCusStart()
            {
                showIndicator();
            }

            @Override
            public void onSuccess(String result)
            {
                JSONParser<UpdateBean> parser = new JSONParser<UpdateBean>(UpdateBean.class
                        , new TypeToken<UpdateBean>()
                {
                }.getType(), result);
                UpdateBean updateBean = parser.doSimpleParse();
                DLog.d("updateBean", updateBean.toString());
                if (updateBean.getState().equals("0"))
                {
                    if (updateBean != null && updateBean.getData() != null)
                    {
                        if (!updateBean.getData().getVersion_name().equals(Config.CLIENT_VERSION))
                        {
                            setInternalActivityParam("version", updateBean.getData());
                            toActivity(UpdateActivity.class, null);
                        } else
                        {
                            showToast("已是最新版本");
                        }
                    }
                } else
                {
                    showToast(updateBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex)
            {
                super.onCancelled(cex);
            }

            @Override
            public void onFinished()
            {
                super.onFinished();
                dismissIndicator();
            }
        });
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        try {
            //计算该缓存目录下缓存大小
            mCacheSize = getFolderSize(new File(PathUtil.get().getRootpath()));
            cacheText.setText(FormetFileSize(mCacheSize));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            cacheText.setText("读取失败");
        }
        try {
            versionText.setText("V"
                    + getApplication().getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * @Title: setCacheSizeText
     * @Description: TODO(设置缓存大小显示文本)
     * @param txt
     *            文本内容
     * @return void
     */
    public void setCacheSizeText(String txt) {
        cacheText.setText(txt);
    }

    /**
     *
     * @Title: setCacheSize
     * @Description: TODO(设置缓存大小)
     * @param size
     *            大小
     */
    public void setCacheSize(long size) {
        mCacheSize = size;
    }
    /**
     *
     * @Title: clean
     * @Description: TODO(清理)
     * @param size 缓存大小
     */
    public void clean(long size) {
        if (size > 0) {
            cleanCacheData();
        } else {
            Toast.makeText(this, "目前没有可清理的缓存", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /** 清理缓存 */
    private void cleanCacheData() {
        DialogView view = new DialogView(this, R.string.remind,
                R.string.msg_delete_cache_data, R.string.cancel,
                R.string.confirm, new DialogView.DialogCallBack() {

            @Override
            public void onClick(int position) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 1:// 取消
                        break;
                    case 2:// 确定
                        realCleanCache();
                        break;
                    default:
                        break;
                }
            }
        });
        view.getDialog().show();
    }

    /** 执行清理缓存 */
    private void realCleanCache() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                deleteCacheFile(PathUtil.get().getRootpath());
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        setCacheSize(0);
                        setCacheSizeText("0k");
                        Toast.makeText(MineSettingActivity.this, "成功清理缓存",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    // 删除缓存文件
    private void deleteCacheFile(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory())
                    deleteCacheFile(f.getPath());
                else
                    f.delete();
            }
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file
     *            File实例
     * @return long 单位为字节
     * @throws Exception
     */
    public static long getFolderSize(java.io.File file) throws Exception {
        long size = 0;
        java.io.File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size = size + getFolderSize(fileList[i]);
            } else {
                size = size + fileList[i].length();
            }
        }
        return size;
    }

    public String FormetFileSize(long fileS) {
        if(fileS<=0){
            return "0k";
        }
        // 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
