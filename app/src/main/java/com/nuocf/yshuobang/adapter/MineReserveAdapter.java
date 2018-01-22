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
import com.nuocf.yshuobang.analysis.bean.MineReserveDataList;

/**
 * @author xiong
 * @ClassName: MineReserveAdapter
 * @Description: todo(我的预约订单接口适配器)
 * @date 2016/9/18
 */

public class MineReserveAdapter extends BaseAdapter {
    List<MineReserveDataList> list;
    Context context;
    LayoutInflater inflater;

    public MineReserveAdapter(List<MineReserveDataList> list, Context context) {
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
            view = inflater.inflate(R.layout.reserve_list_item, null);
            x.view().inject(holder, view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MineReserveDataList dataList = list.get(i);
        holder.number.setText("订单编号  " + dataList.getOrder_number());
        int state = dataList.getOrder_state();
        switch (state) {
            case 1:
                holder.state.setText("待安排");
                break;
            case 2:
                holder.state.setText("待确认");
                break;
            case 3:
                holder.state.setText("已完成");
                break;
            case 4:
                holder.state.setText("已评价");
                break;
            case 5:
                holder.state.setText("已取消");
                break;
        }
        holder.name.setText(dataList.getOrder_name());
        holder.date.setText("下单时间  " + dataList.getOrder_time());
        return view;
    }

    class ViewHolder {
        @ViewInject(R.id.tv_order_number)
        private TextView number;
        @ViewInject(R.id.tv_order_state)
        private TextView state;
        @ViewInject(R.id.tv_name)
        private TextView name;
        @ViewInject(R.id.tv_reserve_date)
        private TextView date;
    }
}
