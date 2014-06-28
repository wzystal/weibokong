package com.wzystal.weibokong.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Status implements Serializable{
	
	private static final long serialVersionUID = -406934736438136999L;
	
	@SerializedName("idstr")// 微博ID
	private String id;
	@SerializedName("user")// 发布人
	private User user;
	@SerializedName("text")// 微博内容
	private String text;
	@SerializedName("created_at")// 发布时间
	private String createdAt;
	@SerializedName ("source")//发布来源
	private String source;
	@SerializedName ("thumbnail_pic")//缩略图
	private String thumbPic;
	@SerializedName ("bmiddle_pic")
	private String middlePic;
	@SerializedName ("original_pic")//原始图片
	private String originalPic;
	@SerializedName ("reposts_count")
	private int repostCount;
	@SerializedName ("comments_count")
	private int commentCount;
	@SerializedName ("retweeted_status")
	private Status retweetedStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return (null==text) ? "" : text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getThumbPic() {
		return thumbPic;
	}

	public void setThumbPic(String thumbPic) {
		this.thumbPic = thumbPic;
	}

	public String getMiddlePic() {
		return middlePic;
	}

	public void setMiddlePic(String middlePic) {
		this.middlePic = middlePic;
	}
	
	public String getOriginalPic() {
		return originalPic;
	}

	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRepostCount() {
		return repostCount;
	}

	public void setRepostCount(int repostCount) {
		this.repostCount = repostCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Status getRetweetedStatus() {
		return retweetedStatus;
	}

	public void setRetweetedStatus(Status retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}

}
