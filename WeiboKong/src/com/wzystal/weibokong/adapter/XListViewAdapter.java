package com.wzystal.weibokong.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.BaseAdapter;

public abstract class XListViewAdapter extends BaseAdapter{
	protected Fragment context = null;
	protected List dataList = null;
	protected int currentPage = 1;
	
	public abstract void initDataList();
	
	public abstract void getRefreshData();//获取刷新数据

	public abstract void getMoreData(int page);//获取更多数据
	
	public XListViewAdapter(Fragment context){
		this.context = context;
		this.initDataList();
	}
	
	/**
	 * 下拉刷新
	 */
	public void refresh() {
		currentPage = 1;
		getRefreshData();
	}
	
	/**
	 * 加载更多数据
	 */
	public void loadMore() {
		currentPage = currentPage + 1;
		getMoreData(currentPage);
	}
	
	@Override
	public int getCount() {
		return dataList.size();
	}
	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
}
