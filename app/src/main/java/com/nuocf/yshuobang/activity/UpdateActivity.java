package com.nuocf.yshuobang.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.bean.UpdateDataList;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.myview.DialogView;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.PathUtil;
import com.zhy.progressbar.HorizontalProgressBarWithNumber;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UpdateActivity extends BaseActivity
{
    //检查更新天数间隔,默认3天
    public static final int UPDATE_DAYS = 3;
    private HorizontalProgressBarWithNumber mRoundProgressBar;
    private UpdateDataList newVersion = null;
    private Callback.Cancelable downCancel = null;
    private String downPath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        newVersion = (UpdateDataList) receiveInternalActivityParam("version");
        if (newVersion != null)
            updateSure(newVersion);
    }

    @Override
    protected void initView()
    {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_update);
        mRoundProgressBar = getView(R.id.update_progress);
    }

    /*
     * 是否更新
     */
    private void updateSure(final UpdateDataList version)
    {
        DialogView view = new DialogView(this, "版本更新 " + version.getVersion_name(), version.getVersion_design(), "取消",
                "确定", new DialogView.DialogCallBack()
        {
            @Override
            public void onClick(int position)
            {
                // TODO Auto-generated method stub
                switch (position)
                {
                    case DialogView.LEFT_BUTTON:// 取消
                        finish();
                        break;
                    case DialogView.RIGHT_BUTTON:// 确定
                        downPath = PathUtil.get()
                                .getRootpath()
                                + version.getVersion_name() + ".apk";
                        DLog.d("download", "path:" + downPath);
                        File f = new File(downPath);
                        if (f.exists())
                        {
                            PathUtil.delFile(f);
                        }
                        download(version.getVersion_url(),
                                downPath);
                        DLog.d("download", version.getVersion_url());
                        break;
                    default:
                        break;
                }
            }
        });
        view.getDialog().setCanceledOnTouchOutside(false);
        view.getDialog().setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    finish();
                }
                return false;
            }
        });
        view.getDialog().show();
    }

    //下载版本
    public void download(String url, String filepath)
    {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(filepath);
        downCancel = downLoadFile(params, false, new Callback.ProgressCallback<File>()
        {

            @Override
            public void onFinished()
            {

            }

            @Override
            public void onWaiting()
            {

            }

            @Override
            public void onStarted()
            {
                // tvInfo.setText("正在连接...");
                mRoundProgressBar.setVisibility(View.VISIBLE);
                mRoundProgressBar.setProgress(0);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading)
            {
                DLog.d("progress", "total:" + total);
                DLog.d("progress", "current:" + current);
                mRoundProgressBar.setMax((int) total);
                if (total > current)
                {
                    NumberFormat formatter = new DecimalFormat("0");
                    Double x = new Double((double) current
                            / (double) total) * 100;
                    DLog.d("progress", "x:" + x);
                    String xx = formatter.format(x);
                    DLog.d("progress", "xx:" + xx);
                    mRoundProgressBar.setProgress(Integer.parseInt(xx));
                } else
                {
                    mRoundProgressBar.setProgress(100);
                }
            }

            @Override
            public void onSuccess(File result)
            {
                showToast("下载成功");
                mRoundProgressBar.setProgress(100);
                installApk(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {

            }

            @Override
            public void onCancelled(CancelledException cex)
            {

            }
        });
    }

    // 安装apk
    protected void installApk(File file)
    {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (mRoundProgressBar.isShown() &&
                    mRoundProgressBar.getProgress() < 100)
            {
                DialogView dview = new DialogView(UpdateActivity.this, "提示", "正在下载更新，确定退出？", "取消",
                        "确定", new DialogView.DialogCallBack()
                {
                    @Override
                    public void onClick(int position)
                    {
                        // TODO Auto-generated method stub
                        switch (position)
                        {
                            case DialogView.LEFT_BUTTON:// 取消
                                break;
                            case DialogView.RIGHT_BUTTON:// 确定
                                //取消下载
                                if(downCancel!=null){
                                    downCancel.cancel();
                                    //保持已下载文件，支持断点续传
                                   // PathUtil.delFile(new File(downPath));
                                }
                                finish();
                                break;
                            default:
                                break;
                        }
                    }
                });
                dview.getDialog().show();
            } else
            {
                finish();
            }
        }
        return false;
    }
}
