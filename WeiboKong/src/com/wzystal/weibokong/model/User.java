package com.wzystal.weibokong.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class User implements Serializable{	

	private static final long serialVersionUID = 7055773492586605155L;
    
	private String token;
	
    @SerializedName ("idstr")
	private String uid;
	
	@SerializedName ("name")
	private String nickName;
	
	@SerializedName ("profile_image_url")
	private String avatar;
	
	@SerializedName ("avatar_large")
	private String avatarLarge;
	
	@SerializedName ("avatar_hd")
	private String avatarHD;
	
	@SerializedName ("description")
	private String motto;
	
	@SerializedName ("gender")
	private String sex;
	
	@SerializedName ("location")
	private String location;
	
	@SerializedName ("followers_count")
	private int followerCount;
	
	@SerializedName ("friends_count")
	private int friendCount;
	
	@SerializedName ("statuses_count")
	private int statusCount;
	
	@SerializedName ("favourites_count")
	private int favouriteCount;
	
	@SerializedName ("status")
	private Status lastestStatus;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return (null==nickName) ? "" : nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMotto() {
		return (null==motto) ? "" : motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}
	
	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}

	public String getAvatarHD() {
		return avatarHD;
	}

	public void setAvatarHD(String avatarHD) {
		this.avatarHD = avatarHD;
	}

	public String getSex() {
		if(sex.equals("m")) return "男";
		if(sex.equals("f")) return "女";
		return "未知";
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return (null==location) ? "" : location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public int getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}

	public int getStatusCount() {
		return statusCount;
	}

	public void setStatusCount(int statusCount) {
		this.statusCount = statusCount;
	}

	public int getFavouriteCount() {
		return (favouriteCount<=0) ? 0 : favouriteCount;
	}

	public void setFavouriteCount(int favouriteCount) {
		this.favouriteCount = favouriteCount;
	}

	public Status getLastestStatus() {
		return lastestStatus;
	}

	public void setLastestStatus(Status lastestStatus) {
		this.lastestStatus = lastestStatus;
	}

	@Override
	public boolean equals(Object object) {
		if(object==this) return true;
		if(object==null) return false;
		User other = (User)object;
		return this.getUid().equals(other.getUid());
	}
	
}
