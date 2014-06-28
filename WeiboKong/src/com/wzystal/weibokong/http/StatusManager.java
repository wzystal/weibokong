package com.wzystal.weibokong.http;

import java.util.HashMap;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.wzystal.weibokong.model.Status;

public class StatusManager extends DataManager<Status> {
	public static final String STATUSES_SHOW = "statuses/show.json";// 根据微博ID获取单条微博内容
	public static final String STATUSES_UPDATE = "statuses/update.json"; // 发布一条新微博
	public static final String STATUS_ID = "id";
	public static final String STATUS = "status";
	private static StatusManager instance = null;

	public static StatusManager getInstance() {
		if (instance == null) {
			synchronized (StatusManager.class) {
				if (instance == null) {
					instance = new StatusManager();
				}
			}
		}
		return instance;
	}
	
	// 根据微博ID获取单条微博内容
	public <T> void getStatusByID(Listener<Status> listener,
			ErrorListener errorListener, String id) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(STATUS_ID, id);
		getData(listener, errorListener, STATUSES_SHOW, parameters, Method.GET);
	}

	// 发布一条新微博
	public <T> void updateStatus(Listener<Status> listener,
			ErrorListener errorListener, String status){
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(STATUS, status);
		getData(listener, errorListener, STATUSES_UPDATE, parameters, Method.GET);
	}
	
	@Override
	public Class<Status> getClassType() {
		// TODO Auto-generated method stub
		return Status.class;
	}
}
