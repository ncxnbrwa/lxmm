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
import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.Process.AppointmentObj;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.UploadImageAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.CommonDiseaseBean;
import com.nuocf.yshuobang.analysis.bean.CommonDiseaseDataList;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.analysis.bean.UploadFileBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.common.CommonPopupWindow;
import com.nuocf.yshuobang.common.DynamicTextView;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.myview.ClearEditText;
import com.nuocf.yshuobang.utils.DLog;
import com.nuocf.yshuobang.utils.PathUtil;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiong
 * @ClassName: InterrogationSpeedActivity
 * @Description: todo(快速问诊)
 * @date 2016/8/29
 */
@ContentView(R.layout.activity_interrogation_speed)
public class InterrogationSpeedActivity extends BaseActivity
{

    public static final String INTERNAL_PARAM_INTERROGA = "Interrogation";

    @ViewInject(R.id.tv_count)
    private TextView tv_count;
    @ViewInject(R.id.et_disease_details)
    private EditText et_diseaseDetails;
    @ViewInject(R.id.ll_common_disease)
    private LinearLayout commonDisease;
    @ViewInject(R.id.et_disease_name)
    private ClearEditText et_diseaseName;
    @ViewInject(R.id.disease_gv)
    private NoScrollGridView pick_pic_gv;

    private CharSequence countChar;
    private AppointmentObj mAppointment;
    private UploadImageAdapter mUploadAdapter;

    int screenWidth = 0;
    private List<CommonDiseaseDataList> mDiseaseList = null;
    DynamicTextView mDynamicTextView;

    @Override
    protected void initView()
    {
        mDynamicTextView = new DynamicTextView(getBaseActivity());
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
                    Intent intent = new Intent(InterrogationSpeedActivity.this,
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
        screenWidth = ScreenInfo.screenWidth - 10;
        //疾病详情编辑框的监听事件
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
        if (mDiseaseList == null || mDiseaseList.isEmpty())
        {
            getCommonDisease();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mAppointment = (AppointmentObj) receiveInternalActivityParam(INTERNAL_PARAM_INTERROGA);
        if (mAppointment == null)
        {
            mAppointment = new AppointmentObj();
            //设置问诊类型
            mAppointment.setType(Config.ORDER_ALL);
        } else
        {
            //若有传值，则设置相应数据
            et_diseaseName.setText(mAppointment.getCdiseaseName());
            et_diseaseDetails.setText(mAppointment.getDiseaseDetails());
        }
        mUploadAdapter.update();
        pick_pic_gv.setAdapter(mUploadAdapter);
    }

    /**
     * @Description: todo(获取常见疾病)
     */
    private void getCommonDisease()
    {
        RequestParams params = new RequestParams(APIConstants.COMMON_DISEASE);
        sendGet(params, false, new CusCallBack<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                JSONParser<CommonDiseaseBean> parser = new JSONParser<CommonDiseaseBean>(
                        CommonDiseaseBean.class
                        , new TypeToken<CommonDiseaseBean>()
                {
                }.getType(), result);
                CommonDiseaseBean diseaseBean = parser.doSimpleParse();
                DLog.d("CommonDiseaseBean", diseaseBean.toString());
                addDiseaseItem(diseaseBean);
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
            }
        });
    }

    //把解析出来的疾病名称动态的加载到指定位置
    private void addDiseaseItem(CommonDiseaseBean bean)
    {
        if (bean != null)
        {
            if (Integer.parseInt(bean.getState()) == 0)
            {
                if (bean.getData() != null)
                {
                    mDiseaseList = bean.getData().getDiseaselist();
                    setHotDiseaseColumn(mDiseaseList);
                }
            } else
            {
                showToast(bean.getMessage());
            }
        }
    }

    private void setHotDiseaseColumn(List<CommonDiseaseDataList> data)
    {
        //判断数据集是否有效
        if (data != null && !data.isEmpty())
        {
            //初始化第一行布局，并添加到容器的linearlayout
            LinearLayout rowLayout = mDynamicTextView.creatLineLayout();
            commonDisease.addView(rowLayout);
            //记录单行已用的宽
            int textWidth = 0;
            for (int i = 0; i < data.size(); i++)
            {
                CommonDiseaseDataList disease = data.get(i);
                //创建显示的textView
                TextView diseaseView = mDynamicTextView.creatDiseaseText(disease.getDesease_name());
                //设置点击监听
                diseaseView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (v.isSelected())
                        {
                            v.setSelected(false);
                            et_diseaseName.setText("");
                        } else
                        {
                            //遍历所有子控件，设置未选中状态
                            for (int i = 0; i < commonDisease.getChildCount(); i++)
                            {
                                LinearLayout line = (LinearLayout) commonDisease.getChildAt(i);
                                for (int j = 0; j < line.getChildCount(); j++)
                                {
                                    TextView tv = (TextView) line.getChildAt(j);
                                    tv.setSelected(false);
                                }
                            }
                            v.setSelected(true);
                            et_diseaseName.setText(((TextView) v).getText().toString());
                        }
                    }
                });
                //计算当前textview宽度
                int width = diseaseView.getMeasuredWidth();
                //设置当前textview在布局中的位置
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        width, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 5;
                params.rightMargin = 5;
                params.topMargin = 5;
                //大于屏幕宽，放不下控件则新起一行
                textWidth += width + 20;
                if (textWidth >= screenWidth)
                {
                    //创建下一行布局
                    rowLayout = mDynamicTextView.creatLineLayout();
                    //添加到容器中
                    commonDisease.addView(rowLayout);
                    //添加下一行首个textview
                    rowLayout.addView(diseaseView, params);
                    //记录当前所在行已用宽高
                    textWidth = width + 20;
                } else
                {
                    //当前行添加textview
                    rowLayout.addView(diseaseView, params);
                }
            }
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.next_step})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                InterrogationSpeedActivity.this.finish();
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
                if (TextUtils.isEmpty(mAppointment.getDiseaseDetails()))
                {
                    showToast("请输入疾病描述");
                } else
                {
                    if (diseaseDetails.length() <= 300)
                    {
                        //判断是否有图片需要上传
                        if (mUploadAdapter.getCount() > 1)
                        {
                            toPreUploadFile();
                        } else
                        {
                            setInternalActivityParam(INTERNAL_PARAM_INTERROGA, mAppointment);
                            toActivity(AddInterrogatonPatientActivity.class, null);
                        }
                    } else
                    {
                        showToast("疾病描述不能大于300个字");
                    }
                }
                break;
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
                Intent intent = new Intent(InterrogationSpeedActivity.this,
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
                    .getSDPath(InterrogationSpeedActivity.this)
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
            setInternalActivityParam(INTERNAL_PARAM_INTERROGA, mAppointment);
            toActivity(AddInterrogatonPatientActivity.class, null);
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
