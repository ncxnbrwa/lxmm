package com.nuocf.yshuobang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eeb.pickpiclib.Bimp;
import com.eeb.pickpiclib.FileUtils;
import com.eeb.pickpiclib.ListPicActivity;
import com.eeb.pickpiclib.NoScrollGridView;
import com.eeb.pickpiclib.PhotoActivity;
import com.nuocf.yshuobang.Process.AppointmentObj;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.UploadImageAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.analysis.bean.UploadFileBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.common.CommonPopupWindow;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.PathUtil;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;

/**
 * @author xiong
 * @ClassName: PreciseReserveActivity
 * @Description: todo(精准预约界面)
 * @date 2016/8/29
 */
@ContentView(R.layout.activity_precise_reserve)
public class PreciseReserveActivity extends BaseActivity
{
    public static final String INTERNAL_PARAM_RESERVE = "Reserve";
    @ViewInject(R.id.tv_count)
    private TextView tv_count;
    @ViewInject(R.id.et_disease_details)
    private EditText et_diseaseDetails;
    @ViewInject(R.id.et_disease_name)
    private EditText et_diseaseName;
    @ViewInject(R.id.disease_gv)
    private NoScrollGridView pick_pic_gv;

    private UploadImageAdapter mUploadAdapter;

    private CharSequence countChar;
    private AppointmentObj mAppointment;

    @Override
    protected void initView()
    {
        //初始化图片上传控件
        pick_pic_gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mUploadAdapter = new UploadImageAdapter(this, null);
        pick_pic_gv.setAdapter(mUploadAdapter);
        pick_pic_gv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3)
            {
                if (arg2 == Bimp.bmp.size())
                {
                    pickPic();
                } else
                {
                    Intent intent = new Intent(PreciseReserveActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        et_diseaseDetails.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                countChar = charSequence;
                tv_count.setText("(" + countChar.length() + "/300)");
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
//                if (countChar.length() > 300) {
//                    showToast("您输入的字数已经超出了上限");
//                    editable.delete(et_diseaseDetails.getSelectionStart() - 1
//                            , et_diseaseDetails.getSelectionEnd());
//                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mAppointment = (AppointmentObj) receiveInternalActivityParam(INTERNAL_PARAM_RESERVE);
        if (mAppointment == null)
        {
            mAppointment = new AppointmentObj();
            //设置精准预约类型
            mAppointment.setType(Config.ORDER_JINGZHUN);
        } else
        {
            //若有传值，则设置相应数据
            et_diseaseName.setText(mAppointment.getCdiseaseName());
            et_diseaseDetails.setText(mAppointment.getDiseaseDetails());
        }
        mUploadAdapter.update();
        pick_pic_gv.setAdapter(mUploadAdapter);
    }

    @Event(value = {R.id.header_left_tv, R.id.next_step})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                PreciseReserveActivity.this.finish();
                break;
            case R.id.next_step:
                setUIData();
                String diseaseDetails = et_diseaseDetails.getText().toString();
                String diseaseName = et_diseaseName.getText().toString();
                if (diseaseName != null)
                {
                    if (diseaseName.length() > 12)
                    {
                        showToast("疾病名称不得大于12位");
                        break;
                    }
                }
                if (!TextUtils.isEmpty(mAppointment.getDiseaseDetails()))
                {
                    if (diseaseDetails.length() <= 300)
                    {
                        //判断是否有图片需要上传
                        if (mUploadAdapter.getCount() > 1)
                        {
                            toPreUploadFile();
                        } else
                        {
                            setInternalActivityParam(INTERNAL_PARAM_RESERVE, mAppointment);
                            toActivity(AddPrecisePatientActivity.class, null);
                        }
                    } else
                    {
                        showToast("疾病描述不能大于300个字");
                    }
                } else
                {
                    showToast("请输入疾病描述");
                }
        }
    }

    /**
     * @Description: todo(获取用户输入的数据)
     */
    private void setUIData()
    {
        mAppointment.setCdiseaseName(et_diseaseName.getText().toString());
        mAppointment.setDiseaseDetails(et_diseaseDetails.getText().toString());
    }

    public void pickPic()
    {
        CommonPopupWindow cpw = new CommonPopupWindow(this,
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
                Bimp.number = 3;
                Intent intent = new Intent(PreciseReserveActivity.this,
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
        pw.showAtLocation(getCurrentFocus(), Gravity.BOTTOM, 0, 0);
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void photo()
    {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(PathUtil.get().getUploadImagePath(),
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        path = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case TAKE_PICTURE:
                    if (Bimp.drr.size() < 3 && resultCode == -1)
                    {
                        Bimp.drr.add(path);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Bimp.number = 3;
        Bimp.drr.clear();
        Bimp.bmp.clear();
        Bimp.max = 0;
    }

    private ArrayList<String> picNames;
    private StringBuffer picStr;

    //准备上传图片String
    private void toPreUploadFile()
    {
        picNames = new ArrayList<String>(Bimp.drr.size());
        ;
        for (int i = 0; i < Bimp.drr.size(); i++)
        {
            //上传压缩对象
            String Str = Bimp.drr.get(i).substring(
                    Bimp.drr.get(i).lastIndexOf("/") + 1,
                    Bimp.drr.get(i).lastIndexOf("."));
            picNames.add(FileUtils
                    .getSDPath(PreciseReserveActivity.this)
                    + Str
                    + ".jpg");
            DLog.d("url", "" + picNames.get(i));
        }
        if (!picNames.isEmpty())
        {
            picStr = new StringBuffer();
            upLoadFile(0);
        }
    }

    public void upLoadFile(final int position)
    {
        // 设置请求参数的编码
        // RequestParams params = new RequestParams("GBK");
        RequestParams params = new RequestParams(APIConstants.UPLOAD_FILE); // 默认编码UTF-8
        File file = new File(picNames.get(position));
        // 添加文件
        params.addBodyParameter("filetype", Config.FILE_IMAGE);
        params.addBodyParameter("fileName", file);

        // 用于非multipart表单的单文件上传
        // params.setBodyEntity(new FileUploadEntity(file,
        // "binary/octet-stream"));

        // 用于非multipart表单的流上传
        // params.setBodyEntity(new InputStreamUploadEntity(stream ,length));

        upLoadFile(params, true, new CusCallBack<String>()
        {
            @Override
            protected void onCusStart()
            {
                if (position == 0)
                {
                    showIndicator();
                }
            }

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
                    saveUploadFile(bean.getfileid());
                    checkUploadCompleted(position);
                } else if (!state.getState().equalsIgnoreCase("10004"))
                {
                    dismissIndicator();
                    //用户会话登录失效不提示上传失败提示
                    showToast("上传文件失败，请重试");
                }
            }

            @Override
            public void onCancelled(CancelledException cex)
            {
                super.onCancelled(cex);
                dismissIndicator();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                super.onError(ex, isOnCallback);
                dismissIndicator();
            }

            @Override
            public void onFinished()
            {
                super.onFinished();
            }
        });
    }

    private void checkUploadCompleted(int position)
    {
        if (position < picNames.size() - 1)
        {
            upLoadFile(position + 1);
        } else
        {
            dismissIndicator();
            picStr.deleteCharAt(picStr.length() - 1);
            DLog.d("upload", "file id:" + picStr.toString());
            //全部图片上传完毕
            mAppointment.setDiseasePhotos(picStr.toString());
            setInternalActivityParam(INTERNAL_PARAM_RESERVE, mAppointment);
            toActivity(AddPrecisePatientActivity.class, null);
        }
    }

    //保存已上传文件的id，以“，”号分割，如20160926102356.jpg,20160927160945.jpg
    private void saveUploadFile(String fileId)
    {
        if (!TextUtils.isEmpty(fileId))
        {
            DLog.d("upload", "fileId:" + fileId);
//            String fileName = fileId.substring(fileId.lastIndexOf("/")+1);
//            DLog.d("upload","filename:"+fileName);
            picStr.append(fileId);
            picStr.append(",");
        }
    }
}
