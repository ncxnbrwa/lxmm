package com.nuocf.yshuobang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.bean.HealthNewsDataList;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.imagecache.BitmapOptions;
import com.nuocf.yshuobang.myview.ZQImageViewRoundOval;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author xiong
 * @ClassName: HealthNewsAdapter
 * @Description: todo(加载健康资讯的适配器)
 * @date 2016/9/1
 */

public class HealthNewsAdapter extends BaseAdapter {
    public List<HealthNewsDataList> dataLists;
    public Context context;
    public LayoutInflater inflater;
    public BitmapOptions options;
    String imgUrl = "http://i1.s1.dpfile.com/pc/dfb048ece4c40afeed85110f65de47a5%28700x700%29/thumb.jpg";

    public HealthNewsAdapter(Context context, List<HealthNewsDataList> dataLists) {
        this.context = context;
        this.dataLists = dataLists;
        options = new BitmapOptions();
        options.initDefaultOptions();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataLists.size();
    }

    @Override
    public HealthNewsDataList getItem(int i) {
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
            view = inflater.inflate(R.layout.news_item, null);
            holder = new ViewHolder();
            x.view().inject(holder, view);
            view.setTag(holder);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.img.getLayoutParams();
            params.width = ScreenInfo.screenWidth / 4;
            holder.img.setLayoutParams(params);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        HealthNewsDataList dataList = dataLists.get(i);
        holder.title.setText(dataList.getNews_title());
        holder.msg.setText(dataList.getNews_content().trim());
        holder.img.setType(ZQImageViewRoundOval.TYPE_ROUND);
        holder.img.setRoundRadius(15);
        x.image().bind(holder.img, dataList.getNews_photo(), options.getOptions());
        DLog.d("datalist", "资讯图片URL：" + dataList.getNews_photo());
        return view;
    }

    class ViewHolder {
        @ViewInject(R.id.fragment_news_iv)
        private ZQImageViewRoundOval img;

        @ViewInject(R.id.fragment_news_title)
        private TextView title;

        @ViewInject(R.id.fragment_news_msg)
        private TextView msg;
    }
}
