package com.nuocf.yshuobang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.bean.HospitalDoctorsDataList;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.imagecache.BitmapOptions;
import com.nuocf.yshuobang.myview.ZQImageViewRoundOval;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * @author xiong
 * @ClassName: HospitalDoctorsAdapter
 * @Description: todo(医生列表适配器)
 * @date 2016/9/21
 */

public class HospitalDoctorsAdapter extends BaseAdapter
{
    List<HospitalDoctorsDataList> list;
    BaseActivity act;
    LayoutInflater inflater;
    BitmapOptions options;
    ViewHolder holder = null;

    public HospitalDoctorsAdapter(List<HospitalDoctorsDataList> list, BaseActivity act)
    {
        this.list = list;
        this.act = act;
        options = new BitmapOptions();
        options.initMyOptions(CENTER_CROP
                , CENTER_CROP,
                R.mipmap.head_normal,
                R.mipmap.head_normal);
        inflater = LayoutInflater.from(act);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public HospitalDoctorsDataList getItem(int i)
    {
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.doctors_item, null);
            x.view().inject(holder, view);
            view.setTag(holder);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.doctorImg.getLayoutParams();
            params.width = ScreenInfo.screenWidth / 5;
            params.height = params.width;
            holder.doctorImg.setLayoutParams(params);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        HospitalDoctorsDataList dataList = list.get(i);
        holder.doctorName.setText(dataList.getDoctor_name());
        holder.doctorPosition.setText(dataList.getDoctor_position());
        holder.doctorHospital.setText(dataList.getHospital_name());
        holder.doctorDetails.setText(dataList.getDoctor_des());
        x.image().bind(holder.doctorImg, dataList.getDoctor_head(), options.getOptions());
        return view;
    }

    class ViewHolder
    {
        @ViewInject(R.id.iv_doctor_icon)
        private ZQImageViewRoundOval doctorImg;
        @ViewInject(R.id.tv_doctor_name)
        private TextView doctorName;
        @ViewInject(R.id.tv_doctor_potision)
        private TextView doctorPosition;
        @ViewInject(R.id.tv_doctor_hospital)
        private TextView doctorHospital;
        @ViewInject(R.id.tv_doctor_goodat)
        private TextView doctorDetails;
    }
}
