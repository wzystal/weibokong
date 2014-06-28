package com.wzystal.weibokong.model;

import java.io.Serializable;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class OAuthToken implements Serializable{
	private static final long serialVersionUID = -284202023864667986L;
	
	private String uid;
	private String token;
	private String refreshToken;
	private long expiresTime;
	private String avatar;
	private String nickName;

	public OAuthToken(Oauth2AccessToken accessToken){
		this.uid = accessToken.getUid();
		this.token = accessToken.getToken();
		this.refreshToken = accessToken.getRefreshToken();
		this.expiresTime = accessToken.getExpiresTime();
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
