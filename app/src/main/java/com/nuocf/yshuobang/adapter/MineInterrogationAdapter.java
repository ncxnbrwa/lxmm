package com.nuocf.yshuobang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.bean.MineInterrogationDataList;

/**
 * @author xiong
 * @ClassName: MineInterrogationAdapter
 * @Description: todo(我的问诊数据加载适配器)
 * @date 2016/9/18
 */

public class MineInterrogationAdapter extends BaseAdapter {
    public List<MineInterrogationDataList> list;
    public LayoutInflater inflater;
    public Context context;

    public MineInterrogationAdapter(List<MineInterrogationDataList> list
            , Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.mine_interrogation_item, null);
            x.view().inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MineInterrogationDataList dataList = list.get(i);
        holder.inquiryName.setText(dataList.getInquiry_name());
        holder.interrogationMsg.setText(dataList.getDesease_design());
        holder.date.setText(dataList.getInquiry_time());
        int state = dataList.getInquiry_state();
        if (state == 1) {
            holder.state.setText("未回复");
            holder.state.setTextColor(context.getResources().getColor(R.color.orange));
        } else if (state == 2) {
            holder.state.setText("已回复");
            holder.state.setTextColor(context.getResources().getColor(R.color.blue));
        }
        return view;
    }

    class ViewHolder {
        @ViewInject(R.id.tv_inquiry_name)
        private TextView inquiryName;

        @ViewInject(R.id.tv_interrogation_msg)
        private TextView interrogationMsg;

        @ViewInject(R.id.tv_date)
        private TextView date;

        @ViewInject(R.id.tv_state)
        private TextView state;
    }
}
