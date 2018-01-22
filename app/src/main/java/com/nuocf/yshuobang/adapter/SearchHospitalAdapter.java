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
import com.nuocf.yshuobang.analysis.bean.SearchHospitalList;
import com.nuocf.yshuobang.appBase.BaseActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author xiong
 * @ClassName: SearchHospitalAdapter
 * @Description: todo(搜索医院数据的适配器)
 * @date 2016/9/27
 */
public class SearchHospitalAdapter extends BaseAdapter
{
    List<SearchHospitalList> list;
    BaseActivity act;
    LayoutInflater inflater;
    String key;

    public SearchHospitalAdapter(List<SearchHospitalList> list
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
            view = inflater.inflate(R.layout.search_hospital_item, null);
            x.view().inject(holder, view);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        SearchHospitalList hospitalList = list.get(i);
        String name = hospitalList.getHospital_name();
        if (!TextUtils.isEmpty(name))
        {
            int index = name.indexOf(key);
            if (index >= 0)
            {
                int color = act.getResources().getColor(R.color.blue);
                SpannableString ss = new SpannableString(name);
                ss.setSpan(new ForegroundColorSpan(color), index
                        , index + key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.hospitalName.setText(ss);
            } else
            {
                holder.hospitalName.setText(name);
            }
        }
        return view;
    }

    class ViewHolder
    {
        @ViewInject(R.id.tv_hospital_name)
        private TextView hospitalName;
    }
}
