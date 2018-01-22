package com.nuocf.yshuobang.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.analysis.bean.HospitalBean;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.ScreenInfo;
import com.nuocf.yshuobang.imagecache.BitmapOptions;
import com.nuocf.yshuobang.myview.ZQImageViewRoundOval;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * @author yangyf
 * @ClassName: RecHospitalAdapter
 * @Description: todo(加载推荐医院的适配器)
 * @date 2016/9/8
 */

public class RecHospitalAdapter extends BaseAdapter
{
    public List<HospitalBean> dataLists;
    public BaseActivity mBaseActivity;
    public BitmapOptions options;

    public RecHospitalAdapter(BaseActivity activity, List<HospitalBean> dataLists)
    {
        this.mBaseActivity = activity;
        this.dataLists = dataLists;
        options = new BitmapOptions();
        options.initDefaultOptions();
    }

    @Override
    public int getCount()
    {
        return dataLists.size();
    }

    @Override
    public HospitalBean getItem(int i)
    {
        return dataLists.get(i);
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
            view = mBaseActivity.getLayoutInflater().inflate(R.layout.hospital_item, null);
            holder = new ViewHolder();
            x.view().inject(holder, view);
            view.setTag(holder);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.iconIv.getLayoutParams();
            params.width = ScreenInfo.screenWidth / 4;
            params.height = params.width * 2 / 3;
            holder.iconIv.setLayoutParams(params);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        HospitalBean htItem = dataLists.get(i);
        holder.nameTv.setText(htItem.getHospital_name());
        holder.levelTv.setText(htItem.getHospital_level());
        holder.sectionNumTv.setText(htItem.getSection_num());
        holder.doctorNumTv.setText(htItem.getDoctors_num());
        holder.iconIv.setType(ZQImageViewRoundOval.TYPE_ROUND);
        holder.iconIv.setRoundRadius(15);
        x.image().bind(holder.iconIv, htItem.getHospital_big_img(), options.getOptions());
        return view;
    }

    class ViewHolder
    {
        @ViewInject(R.id.hospital_iv)
        private ZQImageViewRoundOval iconIv;

        @ViewInject(R.id.hospital_name)
        private TextView nameTv;

        @ViewInject(R.id.hospital_level)
        private TextView levelTv;

        @ViewInject(R.id.hospital_section_num)
        private TextView sectionNumTv;

        @ViewInject(R.id.hospital_doctor_num)
        private TextView doctorNumTv;
    }
}
