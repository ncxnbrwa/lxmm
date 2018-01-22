package com.nuocf.yshuobang.frament;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eeb.pickpiclib.Bimp;
import com.eeb.pickpiclib.FileUtils;
import com.eeb.pickpiclib.ListPicActivity;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.activity.AboutUsActivity;
import com.nuocf.yshuobang.activity.CommonProblemActivity;
import com.nuocf.yshuobang.activity.InterrogationActivity;
import com.nuocf.yshuobang.activity.LoginActivity;
import com.nuocf.yshuobang.activity.MineSettingActivity;
import com.nuocf.yshuobang.activity.ReserveListActivity;
import com.nuocf.yshuobang.activity.UserActivity;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.analysis.bean.UploadFileBean;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.common.CommonPopupWindow;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.imagecache.BitmapOptions;
import com.nuocf.yshuobang.myview.ZQImageViewRoundOval;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.ELS;
import com.nuocf.yshuobang.utils.PathUtil;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * @author xiong
 * @ClassName: MineFragment
 * @Description: todo(我的碎片界面)
 * @date 2016/8/26
 */

@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment
{
    @ViewInject(R.id.user_rl)
    private RelativeLayout rl_user;
    @ViewInject(R.id.user_iv)
    private ZQImageViewRoundOval user_head;
    @ViewInject(R.id.user_name)
    private TextView mineName;
    @ViewInject(R.id.user_name_number)
    private TextView mineNumber;

    ELS els;
    BitmapOptions options;
    private boolean isUpdateHeadimg = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        LinearLayout.LayoutParams userItemParams = (LinearLayout.LayoutParams) rl_user.getLayoutParams();
        userItemParams.height = ScreenInfo.screenWidth * 5 / 9;
        user_head.setType(ZQImageViewRoundOval.TYPE_CIRCLE);
        //头部图片宽高大小
        ViewGroup.LayoutParams imgParams = user_head.getLayoutParams();
        imgParams.width = ScreenInfo.screenWidth / 4;
        imgParams.height = imgParams.width;
        user_head.setLayoutParams(imgParams);
        rl_user.setLayoutParams(userItemParams);
        els = ELS.getInstance(getBaseActivity());
        options = new BitmapOptions();
        options.initMyOptions(ImageView.ScaleType.CENTER_CROP
                , ImageView.ScaleType.CENTER_CROP, R.mipmap.head_normal,
                R.mipmap.head_normal);
    }

    @Override
    protected void lazyLoad()
    {
        if (!injected || !isVisible)
        {
            return;
        }
        //填充各控件的数据
        setUserInfo();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!Bimp.drr.isEmpty())
        {
            if (isUpdateHeadimg && !Bimp.drr.isEmpty())
            {
                isUpdateHeadimg = false;
                new ImageTask(Bimp.drr.get(0), getBaseActivity()).execute();
            } else
            {
                setUserInfo();
            }
        }
    }

    @Event(value = {R.id.interrogation_record_rl, R.id.reserve_rl, R.id.common_problem_rl
            , R.id.about_us_rl, R.id.mine_header_right_bt, R.id.user_name
            , R.id.user_iv, R.id.user_name_number})
    private void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.interrogation_record_rl:
                if (!TextUtils.isEmpty(els.getStringData(ELS.SESSION_KEY)))
                {
                    getBaseActivity().toActivity(InterrogationActivity.class, null);
                } else
                {
                    getBaseActivity().toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.reserve_rl:
                if (!TextUtils.isEmpty(els.getStringData(ELS.SESSION_KEY)))
                {
                    getBaseActivity().toActivity(ReserveListActivity.class, null);
                } else
                {
                    getBaseActivity().toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.common_problem_rl:
                startActivity(new Intent(getActivity(), CommonProblemActivity.class));
                break;
            case R.id.about_us_rl:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.mine_header_right_bt:
                startActivity(new Intent(getActivity(), MineSettingActivity.class));
                break;
            case R.id.user_iv:
                if (!TextUtils.isEmpty(els.getStringData(ELS.SESSION_KEY)))
                {
                    pickPic();
                } else
                {
                    getBaseActivity().toActivity(LoginActivity.class, null);
                }
                break;
            case R.id.user_name:
                toUserOrNot();
                break;
            case R.id.user_name_number:
                toUserOrNot();
                break;
        }
    }

    private void toUserOrNot()
    {
        if (TextUtils.isEmpty(els.getStringData(ELS.SESSION_KEY)))
        {
            getBaseActivity().toActivity(LoginActivity.class, null);
        } else
        {
            getBaseActivity().toActivity(UserActivity.class, null);
        }
    }

    private void setUserInfo()
    {
        String userImg = els.getStringData(ELS.USER_IMG);
        x.image().bind(user_head, userImg, options.getOptions());
        if (!TextUtils.isEmpty(els.getStringData(ELS.SESSION_KEY)))
        {
            String userName = els.getStringData(ELS.USERNAME);
            String userPhone = els.getStringData(ELS.PHONE);
            mineName.setText(userName);
            mineNumber.setText(userPhone);
        } else
        {
            mineName.setText("游客");
            mineNumber.setText("");
        }
    }

    //更换头像
    public void pickPic()
    {
        CommonPopupWindow cpw = new CommonPopupWindow(getBaseActivity(),
                R.layout.item_popupwindows);
        final PopupWindow pw = cpw
                .getPopupWindow(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = pw.getContentView();
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        LinearLayout ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
        ll_bg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pw.dismiss();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                photo();
                pw.dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                isUpdateHeadimg = true;
                Bimp.number = 1;
                Intent intent = new Intent(getBaseActivity(),
                        ListPicActivity.class);
                startActivity(intent);
                pw.dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                pw.dismiss();
            }
        });
        pw.showAtLocation(getBaseActivity().getCurrentFocus(), Gravity.BOTTOM, 0, 0);
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void photo()
    {
        isUpdateHeadimg = false;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(PathUtil.get().getUploadImagePath(),
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        path = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case TAKE_PICTURE:
                    if (resultCode == -1)
                    {
                        new ImageTask(path, getBaseActivity()).execute();
                    }
                    break;
            }
        }
    }

    //上传图片
    public void upLoadFile(String path)
    {
        RequestParams params = new RequestParams(APIConstants.UPLOAD_FILE); // 默认编码UTF-8
        File file = new File(FileUtils.getSDPath(getBaseActivity())
                , "" + path + ".jpg");
        DLog.d("fileString", file.toString());
        // 添加文件
        params.addBodyParameter("filetype", Config.FILE_HEAD);
        params.addBodyParameter("fileName", file);
        getBaseActivity().showIndicator();
        getBaseActivity().setIndicatorMessage("正在上传");
        upLoadFile(params, true, new CusCallBack<String>()
        {

            @Override
            public void onSuccess(String result)
            {
                super.onSuccess(result);
                JSONParser<UploadFileBean> parser = new JSONParser<UploadFileBean>(
                        UploadFileBean.class, result);
                StateMsg state = parser.getState(getBaseActivity());
                if (state.getState().equalsIgnoreCase("0"))
                {
                    UploadFileBean bean = parser.doParse();
                    els.saveStringData(ELS.USER_IMG, bean.getfileurl());
                    setUserInfo();
                    showToast("头像更改成功");
                } else
                {
                    showToast("上传文件失败，请重试");
                }
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
                getBaseActivity().dismissIndicator();
            }
        });
    }

    //图片传完后重置相应数据，并清空相应集合
    private void resetAndClear()
    {
        Bimp.number = 3;
        Bimp.drr.clear();
        Bimp.bmp.clear();
        Bimp.max = 0;
    }

    class ImageTask extends AsyncTask<String, Void, String>
    {
        String path;
        Context mContext;

        public ImageTask(String path, Context mContext)
        {
            this.path = path;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            Bitmap bm = null;
            String afterPath = "";
            try
            {
                bm = Bimp.revitionImageSize(path);
                DLog.d("filePath", "path:" + path);
                afterPath = path.substring(
                        path.lastIndexOf("/") + 1,
                        path.lastIndexOf("."));
                FileUtils.saveBitmap(bm, "" + afterPath, mContext);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return afterPath;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            upLoadFile(s);
            resetAndClear();
        }

        @Override
        protected void onCancelled(String s)
        {
            super.onCancelled(s);
            resetAndClear();
        }
    }
}
