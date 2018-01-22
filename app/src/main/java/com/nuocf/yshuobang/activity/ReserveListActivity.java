package com.nuocf.yshuobang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nuocf.yshuobang.R;
import com.nuocf.yshuobang.adapter.MineReserveAdapter;
import com.nuocf.yshuobang.analysis.JSONParser;
import com.nuocf.yshuobang.analysis.bean.MineReserveData;
import com.nuocf.yshuobang.analysis.bean.MineReserveDataList;
import com.nuocf.yshuobang.analysis.bean.StateMsg;
import com.nuocf.yshuobang.appBase.BaseActivity;
import com.nuocf.yshuobang.appBase.Config;
import com.nuocf.yshuobang.common.CommonPopupWindow;
import com.nuocf.yshuobang.http.APIConstants;
import com.nuocf.yshuobang.http.CusCallBack;
import com.nuocf.yshuobang.utils.DLog;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.nuocf.yshuobang.R.id.lv_reserve_order;

/**
 * @author xiong
 * @ClassName: ReserveListActivity
 * @Description: todo(预约订单的界面)
 * @date 2016/8/26
 */

@ContentView(R.layout.activity_reserve_list)
public class ReserveListActivity extends BaseActivity
{
    @ViewInject(lv_reserve_order)
    private ListView lv_order;
    @ViewInject(R.id.reserve_type)
    private TextView tvType;
    @ViewInject(R.id.reserve_state)
    private TextView tvState;

    List<MineReserveDataList> reserveDataLists = null;
    MineReserveAdapter adapter = null;
    String type = "0";
    String state = "0";

    @Override
    protected void initView()
    {
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (reserveDataLists != null && !reserveDataLists.isEmpty())
        {
            setListAdapter();
        } else
        {
            getData(type, state);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        adapter = null;
    }

    //访问预约接口
    private void getData(String type, final String state)
    {
        RequestParams params = new RequestParams(APIConstants.MINE_RESERVE);
        params.addBodyParameter("order_type", type);
        params.addBodyParameter("order_state", state);
        sendPost(params, true, new CusCallBack<String>()
        {
            @Override
            protected void onCusStart()
            {
                super.onCusStart();
                showIndicator();
            }

            @Override
            public void onSuccess(String result)
            {
                JSONParser<MineReserveData> parser = new JSONParser<MineReserveData>(
                        MineReserveData.class, new TypeToken<MineReserveData>()
                {
                }.getType(), result);
                StateMsg stateMsg = parser.getState(getBaseActivity());
                if (Integer.parseInt(stateMsg.getState()) == 0)
                {
                    MineReserveData reserveData = parser.doParse();
                    DLog.d("reserveData", "state:" + stateMsg.getState());
                    DLog.d("reserveData", "message:" + stateMsg.getMessage());
                    DLog.d("reserveData", reserveData.toString());
                    addReserveList(reserveData);
                    setListAdapter();
                } else if (Integer.parseInt(stateMsg.getState()) != 10004)
                {
                    showToast(stateMsg.getMessage());
                }
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
                dismissIndicator();
            }
        });
    }

    private void addReserveList(MineReserveData data)
    {
        if (reserveDataLists == null)
        {
            reserveDataLists = new ArrayList<>(0);
        } else
        {
            reserveDataLists.clear();
        }
        if (data != null)
            reserveDataLists.addAll(data.getList());
    }

    private void setListAdapter()
    {
        if (adapter == null)
        {
            adapter = new MineReserveAdapter(reserveDataLists, getBaseActivity());
            lv_order.setAdapter(adapter);
        } else
        {
            adapter.notifyDataSetChanged();
            if (lv_order.getAdapter() == null)
            {
                lv_order.setAdapter(adapter);
            }
        }
    }

    @Event(value = {R.id.header_left_tv, R.id.reserve_type, R.id.reserve_state})
    private void click(View v)
    {
        switch (v.getId())
        {
            case R.id.header_left_tv:
                ReserveListActivity.this.finish();
                break;
            case R.id.reserve_type:
                showTypeWindow(v);
                break;
            case R.id.reserve_state:
                showStateWindow(v);
                break;
        }
    }

    private PopupWindow typeWindow = null;

    //显示类型选择的窗口
    private void showTypeWindow(View dropView)
    {
        if (typeWindow == null)
        {
            typeWindow = new CommonPopupWindow(this,
                    R.layout.window_reserve_type)
                    .getPopupWindow(ViewGroup.LayoutParams.MATCH_PARENT);
            View view = typeWindow.getContentView();
            final Button btnAll = (Button) view.findViewById(R.id.item_all);
            final Button btnAppointment = (Button) view.findViewById(R.id.item_appointment);
            final Button btnOversea = (Button) view.findViewById(R.id.item_oversea);
            final Button btnGreen = (Button) view.findViewById(R.id.item_green);
            final Button btnPrecise = (Button) view.findViewById(R.id.item_precise);
            LinearLayout llNouse = (LinearLayout) view.findViewById(R.id.ll_no_use);
            llNouse.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    typeWindow.dismiss();
                }
            });
            btnAll.setSelected(true);
            btnAll.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    btnAll.setSelected(true);
                    btnAppointment.setSelected(false);
                    btnOversea.setSelected(false);
                    btnGreen.setSelected(false);
                    btnPrecise.setSelected(false);
                    type = Config.ORDER_ALL + "";
                    afterTypeWindow(type);
                    typeWindow.dismiss();
                }
            });
            btnAppointment.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    btnAll.setSelected(false);
                    btnAppointment.setSelected(true);
                    btnOversea.setSelected(false);
                    btnGreen.setSelected(false);
                    btnPrecise.setSelected(false);
                    type = Config.ORDER_SHOUSHU + "";
                    afterTypeWindow(type);
                    typeWindow.dismiss();
                }
            });
            btnOversea.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    btnAll.setSelected(false);
                    btnAppointment.setSelected(false);
                    btnOversea.setSelected(true);
                    btnGreen.setSelected(false);
                    btnPrecise.setSelected(false);
                    type = Config.ORDER_HAIWAI + "";
                    afterTypeWindow(type);
                    typeWindow.dismiss();
                }
            });
            btnGreen.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    btnAll.setSelected(false);
                    btnAppointment.setSelected(false);
                    btnOversea.setSelected(false);
                    btnGreen.setSelected(true);
                    btnPrecise.setSelected(false);
                    type = Config.ORDER_LVSE + "";
                    afterTypeWindow(type);
                    typeWindow.dismiss();
                }
            });
            btnPrecise.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    btnAll.setSelected(false);
                    btnAppointment.setSelected(false);
                    btnOversea.setSelected(false);
                    btnGreen.setSelected(false);
                    btnPrecise.setSelected(true);
                    type = Config.ORDER_JINGZHUN + "";
                    afterTypeWindow(type);
                    typeWindow.dismiss();
                }
            });
        }
        typeWindow.showAsDropDown(dropView);
    }

    //类型弹窗点击后设置文字并加载数据
    private void afterTypeWindow(String type)
    {
        if (type.equals(Config.ORDER_ALL + ""))
        {
            tvType.setText("全部");
        } else if (type.equals(Config.ORDER_SHOUSHU + ""))
        {
            tvType.setText("手术预约");
        } else if (type.equals(Config.ORDER_JINGZHUN + ""))
        {
            tvType.setText("精准预约");
        } else if (type.equals(Config.ORDER_HAIWAI + ""))
        {
            tvType.setText("海外医疗");
        } else if (type.equals(Config.ORDER_LVSE + ""))
        {
            tvType.setText("绿色通道");
        }
        getData(type, state);
    }

    private PopupWindow stateWindow = null;

    //显示状态选择的窗口
    private void showStateWindow(View dropView)
    {
        if (stateWindow == null)
        {
            stateWindow = new CommonPopupWindow(this,
                    R.layout.window_reserve_state)
                    .getPopupWindow(ViewGroup.LayoutParams.MATCH_PARENT);
            View view = stateWindow.getContentView();
            final Button btnAll = (Button) view.findViewById(R.id.item_all);
            final Button btnArrange = (Button) view.findViewById(R.id.item_arrange);
            final Button btnConfirm = (Button) view.findViewById(R.id.item_confirm);
            final Button btnDone = (Button) view.findViewById(R.id.item_done);
            final Button btnComment = (Button) view.findViewById(R.id.item_comment);
            final Button btnCancel = (Button) view.findViewById(R.id.item_cancel);
            LinearLayout nouseLayout = (LinearLayout) view.findViewById(R.id.ll_no_use);
            btnAll.setSelected(true);
            btnAll.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    state = Config.RESERVE_ALL;
                    afterStateWindow(state);
                    stateWindow.dismiss();
                    btnAll.setSelected(true);
                    btnArrange.setSelected(false);
                    btnConfirm.setSelected(false);
                    btnDone.setSelected(false);
                    btnComment.setSelected(false);
                    btnCancel.setSelected(false);
                }
            });
            btnArrange.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    state = Config.RESERVE_ARRANGE;
                    afterStateWindow(state);
                    stateWindow.dismiss();
                    btnAll.setSelected(false);
                    btnArrange.setSelected(true);
                    btnConfirm.setSelected(false);
                    btnDone.setSelected(false);
                    btnComment.setSelected(false);
                    btnCancel.setSelected(false);
                }
            });
            btnConfirm.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    state = Config.RESERVE_CONFIRM;
                    afterStateWindow(state);
                    stateWindow.dismiss();
                    btnAll.setSelected(false);
                    btnArrange.setSelected(false);
                    btnConfirm.setSelected(true);
                    btnDone.setSelected(false);
                    btnComment.setSelected(false);
                    btnCancel.setSelected(false);
                }
            });
            btnDone.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    state = Config.RESERVE_DONE;
                    afterStateWindow(state);
                    stateWindow.dismiss();
                    btnAll.setSelected(false);
                    btnArrange.setSelected(false);
                    btnConfirm.setSelected(false);
                    btnDone.setSelected(true);
                    btnComment.setSelected(false);
                    btnCancel.setSelected(false);
                }
            });
            btnComment.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    state = Config.RESERVE_COMMENT;
                    afterStateWindow(state);
                    stateWindow.dismiss();
                    btnAll.setSelected(false);
                    btnArrange.setSelected(false);
                    btnConfirm.setSelected(false);
                    btnDone.setSelected(false);
                    btnComment.setSelected(true);
                    btnCancel.setSelected(false);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    state = Config.RESERVE_CANCEL;
                    afterStateWindow(state);
                    stateWindow.dismiss();
                    btnAll.setSelected(false);
                    btnArrange.setSelected(false);
                    btnConfirm.setSelected(false);
                    btnDone.setSelected(false);
                    btnComment.setSelected(false);
                    btnCancel.setSelected(true);
                }
            });
            nouseLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    stateWindow.dismiss();
                }
            });
        }
        stateWindow.showAsDropDown(dropView);
    }

    //状态弹窗点击后设置文字并加载数据
    private void afterStateWindow(String state)
    {
        if (state.equals(Config.RESERVE_ALL))
        {
            tvState.setText("全部");
        } else if (state.equals(Config.RESERVE_ARRANGE))
        {
            tvState.setText("待安排");
        } else if (state.equals(Config.RESERVE_CONFIRM))
        {
            tvState.setText("待确认");
        } else if (state.equals(Config.RESERVE_DONE))
        {
            tvState.setText("已完成");
        } else if (state.equals(Config.RESERVE_COMMENT))
        {
            tvState.setText("已评价");
        } else if (state.equals(Config.RESERVE_CANCEL))
        {
            tvState.setText("已取消");
        }
        getData(type, state);
    }

    @Event(value = lv_reserve_order, type = AdapterView.OnItemClickListener.class)
    private void itemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (reserveDataLists != null)
        {
            Bundle bundle = new Bundle();
            bundle.putString("order_id", reserveDataLists.get(position).getOrder_id());
            toActivity(ReserveDetailActivity.class, bundle);
        }
    }
}
