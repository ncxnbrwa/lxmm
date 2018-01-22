/**
 * 
 */
package com.nuocf.yshuobang.adapter;

/**
 * @author luquan
 *
 * 2014-8-29 下午4:04:35
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected List<T> mDatas;

	public CommonAdapter(Context context, List<T> datas) {
		mInflater = LayoutInflater.from(context);
		if(datas == null){
			mDatas = new ArrayList<T>();
		}else
			this.mDatas = datas;
	}
	
	public void replayDatas(List<T> datas){
		this.mDatas.clear();
		addDatas(datas);
	}
	
	public void addDatas(List<T> datas){
		this.mDatas.addAll(datas);
		notifyDataSetChanged();
	}
	
	public void addData(T data){
		this.mDatas.add(data);
		notifyDataSetChanged();
	}
	
	public void removeData(int position){
		this.mDatas.remove(position-1);
		notifyDataSetChanged();
	}
	
	public void removeAllData(){
		this.mDatas.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
