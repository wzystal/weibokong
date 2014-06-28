package com.wzystal.weibokong.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StatusData {
	
	@SerializedName ("statuses")
	private List<Status> statuses;

	public List<Status> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

}
