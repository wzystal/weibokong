package com.wzystal.weibokong.widget;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.wzystal.weibokong.R;
import com.wzystal.weibokong.StatusActivity;
import com.wzystal.weibokong.adapter.StatusDataAdapter;
import com.wzystal.weibokong.http.StatusDataManager;
import com.wzystal.weibokong.model.Status;
import com.wzystal.weibokong.model.StatusData;
import com.wzystal.weibokong.widget.XListView.IXListViewListener;

public class MentionStatusesFragment extends Fragment implements
IXListViewListener, OnItemClickListener, Listener<StatusData>, ErrorListener{
	private LinearLayout pbLoading;
	private XListView mListView;
	private List<Status> mDataList;
	private StatusDataAdapter mAdapter;
	private Handler mHandler;
	private int page = 1;
	private int flag = 0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home_statuses, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pbLoading = (LinearLayout) getView().findViewById(R.id.pb_loading);
		mListView = (XListView) getView().findViewById(R.id.xlv_home_statuses);
		mListView.setPullLoadEnable(true);
		initAdapter();
		mListView.setOnItemClickListener(this);
		mHandler = new Handler();
	}

	public void initAdapter() {
		Log.i("wzy", "initAdapter(),page="+page);
		StatusDataManager.getInstance().getMentionStatusesByPage(this, this, page);
	}

	private void onLoad() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				flag = 1;
				page = 1;
				Log.i("wzy", "onRefresh(),page="+page);
				StatusDataManager.getInstance().getMentionStatusesByPage(MentionStatusesFragment.this, MentionStatusesFragment.this, page);
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				flag = 2;
				page++;
				Log.i("wzy", "onLoadMore(),page="+page);
				StatusDataManager.getInstance().getMentionStatusesByPage(MentionStatusesFragment.this, MentionStatusesFragment.this, page);
			}
		}, 1000);
	}

	@Override
	public void onResponse(StatusData statusData) {
		if(null != statusData){	
			switch(flag){
				case 0: 
					mDataList = statusData.getStatuses();
					mAdapter = new StatusDataAdapter(MentionStatusesFragment.this, mDataList);
					mListView.setAdapter(mAdapter);
					mListView.setXListViewListener(MentionStatusesFragment.this);
					pbLoading.setVisibility(View.GONE);
					onLoad();
					break;
				case 1: 
					mDataList = statusData.getStatuses();
					mAdapter = new StatusDataAdapter(MentionStatusesFragment.this, mDataList);
					mListView.setAdapter(mAdapter);
					mListView.setXListViewListener(MentionStatusesFragment.this);
					onLoad();
					break;
				case 2:
					mDataList.addAll(statusData.getStatuses());
					mAdapter.notifyDataSetChanged();
					onLoad();
					break;
			}
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		error.printStackTrace();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		StatusDataAdapter.ViewHolder holder = (StatusDataAdapter.ViewHolder)view.getTag();
		if(null != holder){
			Intent intent = new Intent(getActivity(), StatusActivity.class);
			intent.putExtra("status", holder.status);
			startActivity(intent);
		}
	}
}
