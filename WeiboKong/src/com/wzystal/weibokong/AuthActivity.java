package com.wzystal.weibokong;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.wzystal.weibokong.http.UserManager;
import com.wzystal.weibokong.model.AppData;
import com.wzystal.weibokong.model.User;
import com.wzystal.weibokong.util.SharedPrefHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class AuthActivity extends Activity {
	public static final String APP_KEY = "412393153";
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
	private WeiboAuth mWeiboAuth = null;// 微博授权类实例
	private Oauth2AccessToken mAccessToken = null;// 经授权获取的token信息
	private SharedPrefHelper spHelper = null;
	private List<User> userList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		onAuthorize();
	}

	public void onAuthorize() {
		mWeiboAuth = new WeiboAuth(AuthActivity.this, APP_KEY, REDIRECT_URL, SCOPE);
		mWeiboAuth.anthorize(new AuthListener());
	}

	class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle values) {
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				AppData.setAuthToken(mAccessToken.getToken());
				UserManager.getInstance().getUserByUid(new GetUserListener(),
						new GetUserErrorListener(), mAccessToken.getUid());
			} else {
				onCancel();
			}
		}

		@Override
		public void onCancel() {
			Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
			AuthActivity.this.finish();
			AuthActivity.this.startActivity(intent);
		}

		@Override
		public void onWeiboException(WeiboException e) {
			e.printStackTrace();
		}
	}

	class GetUserListener implements Listener<User> {
		@Override
		public void onResponse(User user) {
			spHelper = new SharedPrefHelper(AuthActivity.this,
					SharedPrefHelper.PS_WEIBOKONG);
			user.setToken(mAccessToken.getToken());
			try {
				userList = spHelper.getUserList(SharedPrefHelper.USER_LIST);
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(userList!=null){
				if(userList.contains(user)){	
					AppData.userLogin(user);
					Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
					AuthActivity.this.startActivity(intent);
					AuthActivity.this.finish();
				}else{
					userList.add(user);
				}
			}else{
				userList = new ArrayList<User>();
				userList.add(user);
			}
			try {
				spHelper.setUserList(SharedPrefHelper.USER_LIST, userList);
				Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
				AuthActivity.this.finish();
				AuthActivity.this.startActivity(intent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class GetUserErrorListener implements ErrorListener {
		@Override
		public void onErrorResponse(VolleyError error) {
			error.printStackTrace();
		}
	}

}
