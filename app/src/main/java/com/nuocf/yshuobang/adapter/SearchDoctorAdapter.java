package com.nuocf.yshuobang.adapter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.bean.SearchDoctorList;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author xiong
 * @ClassName: SearchDoctorAdapter
 * @Description: todo(搜索医生数据的适配器)
 * @date 2016/9/27
 */

public class SearchDoctorAdapter extends BaseAdapter
{
    List<SearchDoctorList> list;
    BaseActivity act;
    LayoutInflater inflater;
    String key;

    public SearchDoctorAdapter(List<SearchDoctorList> list
            , BaseActivity act, String key)
    {
        this.list = list;
        this.act = act;
        this.key = key;
        inflater = LayoutInflater.from(act);
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int i)
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
        ViewHolder holder = null;
        if (view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_doctor_item, null);
            x.view().inject(holder, view);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        SearchDoctorList doctorList = list.get(i);
        String name = doctorList.getDoctor_name();
        if (!TextUtils.isEmpty(name))
        {
            DLog.d("searchDoctorAdapter", "docotrName:" + name);
            int index = name.indexOf(key);
            if (index >= 0)
            {
                DLog.d("searchDoctorAdapter", "index:" + index);
                SpannableString ss = new SpannableString(name);
                int textColor = act.getResources().getColor(R.color.blue);
                ss.setSpan(new ForegroundColorSpan(textColor), index,
                        index + key.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.doctorName.setText(ss);
            } else
            {
                holder.doctorName.setText(name);
            }
        }
        holder.doctorHospital.setText(doctorList.getHospital_name());
        holder.doctorDetails.setText(doctorList.getDoctor_position()
                + "  " + doctorList.getSection_name());
        return view;
    }

    class ViewHolder
    {
        @ViewInject(R.id.tv_doctor_name)
        private TextView doctorName;
        @ViewInject(R.id.tv_doctor_hospital)
        private TextView doctorHospital;
        @ViewInject(R.id.tv_doctor_details)
        private TextView doctorDetails;
    }
}
