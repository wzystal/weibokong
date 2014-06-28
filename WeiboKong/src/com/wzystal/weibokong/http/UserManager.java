package com.wzystal.weibokong.http;

import java.util.HashMap;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.wzystal.weibokong.model.User;

public class UserManager extends DataManager<User> {
	public final static String USERS_SHOW = "users/show.json";// 根据用户ID获取用户信息
	public final static String UID = "uid";
	private static UserManager instance = null;

	public static UserManager getInstance() {
		if (instance == null) {
			synchronized (UserManager.class) {
				if (instance == null) {
					instance = new UserManager();
				}
			}
		}
		return instance;
	}

	public <T> void getUserByUid(Listener<User> listener,
			ErrorListener errorListener, String uid) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(UID, uid);
		getData(listener, errorListener, USERS_SHOW, parameters, Method.GET);
	}

	@Override
	public Class<User> getClassType() {
		// TODO Auto-generated method stub
		return User.class;
	}
}
