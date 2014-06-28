package com.wzystal.weibokong.http;

import java.util.HashMap;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.wzystal.weibokong.model.AppData;

import android.net.Uri;
import android.util.Log;

public abstract class DataManager<T> {
	private final static String BASE_URL = "https://api.weibo.com/2/";	
	
	public final static String ACCESS_TOKEN = "access_token";

	public abstract Class<T> getClassType();

	public void getData(Listener<T> listener, ErrorListener errorListener,
			String apiURL, HashMap<String, String> parameters, int method) {
		Uri.Builder uriBuilder = Uri.parse(BASE_URL + apiURL).buildUpon();
		//用户授权时，用户实例中没有token，需要使用OAuth2.0返回的token（该token存储到静态类AppData的authToken变量中）
		String token = (AppData.getLoginUser()!=null) ? AppData.getLoginUser().getToken() : AppData.getAuthToken();
		uriBuilder.appendQueryParameter(ACCESS_TOKEN, token);
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				uriBuilder.appendQueryParameter(key, parameters.get(key));
			}
		}
		String url = uriBuilder.build().toString();
		Log.i("wzy", "请求URL: " + url);
		GsonRequest<T> request = new GsonRequest<T>(method, url,
				getClassType(), listener, errorListener);
		RequestManager.getRequestQueue().add(request);
		RequestManager.getRequestQueue().start();
	}
}
