package com.nuocf.yshuobang.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.db.bean.CityEntity;

/**
 * @author yangyf
 * @ClassName: CityAdapter
 * @Description: todo(加载城市的适配器)
 * @date 2016/9/8
 */

public class ProvinceAdapter extends BaseAdapter {
    private List<CityEntity> dataLists;
    private BaseActivity mBaseActivity;
    private int mCurrentSelect = -1;

    public ProvinceAdapter(BaseActivity activity, List<CityEntity> dataLists) {
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
    public CityEntity getItem(int i) {
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
        CityEntity item = dataLists.get(i);
        holder.nameTv.setText(item.getName());
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
