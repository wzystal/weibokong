package com.wzystal.weibokong.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.wzystal.weibokong.R;
import com.wzystal.weibokong.cache.ImageCacheManager;
import com.wzystal.weibokong.model.AppData;
import com.wzystal.weibokong.model.Status;
import com.wzystal.weibokong.util.TimeHelper;

import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusDataAdapter extends BaseAdapter {
	private Fragment fragment;
	private List<Status> dataList;

	public StatusDataAdapter(Fragment fragment, List<Status> dataList) {
		this.fragment = fragment;
		this.dataList = dataList;
	}

	public static class ViewHolder {
		public Status status;
		public NetworkImageView niv_avatar;
		public TextView tv_nick_name;
		public TextView tv_created_at;
		public TextView tv_status_text;
		public NetworkImageView niv_thumb_pic;
		public TextView tv_source;
		public TextView tv_repost;
		public TextView tv_comment;
		public LinearLayout ll_retweet_status;
		public TextView tv_retweet_text;
		public NetworkImageView niv_retweet_thumbpic;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
//			LayoutInflater mInflater = LayoutInflater.from(AppData.getContext());
			LayoutInflater mInflater = LayoutInflater.from(fragment.getActivity());
			convertView = mInflater.inflate(R.layout.list_item_status, null);
			holder = new ViewHolder();
			holder.niv_avatar = (NetworkImageView) convertView
					.findViewById(R.id.niv_avatar);
			holder.tv_nick_name = (TextView) convertView
					.findViewById(R.id.tv_nick_name);
			holder.tv_created_at = (TextView) convertView
					.findViewById(R.id.tv_created_at);
			holder.tv_status_text = (TextView) convertView
					.findViewById(R.id.tv_status_text);
			holder.niv_thumb_pic = (NetworkImageView) convertView
					.findViewById(R.id.niv_thumb_pic);
			holder.tv_source = (TextView) convertView
					.findViewById(R.id.tv_source);
			holder.tv_repost = (TextView) convertView
					.findViewById(R.id.tv_repost);
			holder.tv_comment = (TextView) convertView
					.findViewById(R.id.tv_comment);
			holder.ll_retweet_status = (LinearLayout) convertView.findViewById(R.id.ll_retweet_status);
			holder.tv_retweet_text = (TextView) convertView
					.findViewById(R.id.tv_retweet_text);
			holder.niv_retweet_thumbpic = (NetworkImageView) convertView
					.findViewById(R.id.niv_retweet_thumbpic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Status status = (Status) dataList.get(position);
		holder.status = status;
		ImageLoader imageLoader = ImageCacheManager.getInstance()
				.getImageLoader();
		holder.niv_avatar
				.setImageUrl(status.getUser().getAvatar(), imageLoader);
		holder.tv_nick_name.setText(status.getUser().getNickName());
		holder.tv_created_at
				.setText(TimeHelper.parseTime(status.getCreatedAt()));
		holder.tv_status_text.setText(status.getText());
		if (null != status.getThumbPic()) {
			holder.niv_thumb_pic.setImageUrl(status.getThumbPic(), imageLoader);
			holder.niv_thumb_pic.setVisibility(View.VISIBLE);
		}
		if(null != status.getRetweetedStatus()){
			holder.ll_retweet_status.setVisibility(View.VISIBLE);
			holder.tv_retweet_text.setText(status.getRetweetedStatus().getText());
			String imageUrl = status.getRetweetedStatus().getThumbPic();
			if(null != imageUrl){
				holder.niv_retweet_thumbpic.setImageUrl(imageUrl, imageLoader);		
				holder.niv_retweet_thumbpic.setVisibility(View.VISIBLE);
			}
		}else{
			holder.ll_retweet_status.setVisibility(View.GONE);
			holder.niv_retweet_thumbpic.setVisibility(View.GONE);
		}
		holder.tv_source.setText("来自:" + Html.fromHtml(status.getSource()));
		holder.tv_repost.setText("转发(" + status.getRepostCount() + ")");
		holder.tv_comment.setText("评论(" + status.getCommentCount() + ")");
		return convertView;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
