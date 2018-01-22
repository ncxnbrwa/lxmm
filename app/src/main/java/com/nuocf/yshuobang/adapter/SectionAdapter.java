package com.nuocf.yshuobang.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.bean.SectionBean;
import com.nuocf.yshuobang.appBase.BaseActivity;

/**
 * @author yangyf
 * @ClassName: CityAdapter
 * @Description: todo(加载科目的适配器)
 * @date 2016/9/8
 */

public class SectionAdapter extends BaseAdapter {
    private List<SectionBean> dataLists;
    private BaseActivity mBaseActivity;
    private int mCurrentSelect = -1;

    public SectionAdapter(BaseActivity activity, List<SectionBean> dataLists) {
        this.mBaseActivity = activity;
        this.dataLists = dataLists;
    }

    public void freshCurrentSelect(int position){
        mCurrentSelect = position;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return dataLists.size();
    }

    @Override
    public SectionBean getItem(int i) {
        return dataLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = mBaseActivity.getLayoutInflater().inflate(R.layout.item_province, null);
            holder = new ViewHolder();
            x.view().inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        SectionBean item = dataLists.get(i);
        holder.nameTv.setText(item.getSection_name());
        if(mCurrentSelect==i){
            holder.nameTv.setSelected(true);
        }else{
            holder.nameTv.setSelected(false);
        }
        return view;
    }

    class ViewHolder {
        @ViewInject(R.id.item_province_name)
        TextView nameTv;
    }
}
