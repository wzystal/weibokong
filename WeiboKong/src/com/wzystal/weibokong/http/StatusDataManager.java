package com.wzystal.weibokong.http;

import java.util.HashMap;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.wzystal.weibokong.model.StatusData;

public class StatusDataManager extends DataManager<StatusData> {
	private final static String STATUSES_USER_TIMELINE = "statuses/user_timeline.json";// 获取某个用户最新发表的微博列表
	private final static String STATUSES_HOME_TIMELINE = "statuses/home_timeline.json";// 获取当前登录用户及其所关注用户的最新微博
	private final static String STATUSES_MENTIONS = "statuses/mentions.json";// 获取最新的提到登录用户的微博列表，即@我的微博
	private static StatusDataManager instance = null;

	public static StatusDataManager getInstance() {
		if (instance == null) {
			synchronized (StatusDataManager.class) {
				if (instance == null) {
					instance = new StatusDataManager();
				}
			}
		}
		return instance;
	}

	@Override
	public Class<StatusData> getClassType() {
		return StatusData.class;
	}

	// 获取某个用户最新发表的微博列表
	public <T> void getStatuesByUid(Listener<StatusData> listener,
			ErrorListener errorListener, String uid) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("uid", uid);
		getData(listener, errorListener, STATUSES_USER_TIMELINE, parameters,
				Method.GET);
	}

	// 分页获取当前登录用户及其所关注用户的最新微博
	public <T> void getHomeStatusesByPage(Listener<StatusData> listener,
			ErrorListener errorListener, int page) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("page", "" + page);
		getData(listener, errorListener, STATUSES_HOME_TIMELINE, parameters,
				Method.GET);
	}

	// 获取最新的提到登录用户的微博列表，即@我的微博
	public <T> void getMentionStatusesByPage(Listener<StatusData> listener,
			ErrorListener errorListener, int page) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("page", "" + page);
		getData(listener, errorListener, STATUSES_MENTIONS, parameters,
				Method.GET);
	}
}
