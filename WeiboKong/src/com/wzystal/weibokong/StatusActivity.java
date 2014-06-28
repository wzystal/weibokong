package com.wzystal.weibokong;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.wzystal.weibokong.cache.ImageCacheManager;
import com.wzystal.weibokong.model.AppData;
import com.wzystal.weibokong.model.Status;
import com.wzystal.weibokong.util.TimeHelper;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatusActivity extends Activity {

	private Status mStatus;
	private NetworkImageView niv_avatar;
	private TextView tv_status_nick_name;
	private TextView tv_location;
	private TextView tv_status_text;
	private NetworkImageView niv_thumb_pic;
	private LinearLayout ll_retweet_status;
	private TextView tv_retweet_text;
	private NetworkImageView niv_retweet_thumbpic;
	private TextView tv_source;
	private TextView tv_repost;
	private TextView tv_comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status_detail);
		initView();
		initData();
	}

	public void initView() {
		niv_avatar = (NetworkImageView) findViewById(R.id.niv_status_avatar);
		tv_status_nick_name = (TextView) findViewById(R.id.tv_status_nick_name);
		tv_location = (TextView) findViewById(R.id.tv_location);
		tv_status_text = (TextView) findViewById(R.id.tv_status_text);
		niv_thumb_pic = (NetworkImageView) findViewById(R.id.niv_thumb_pic);
		tv_source = (TextView) findViewById(R.id.tv_source);
		tv_repost = (TextView) findViewById(R.id.tv_repost);
		tv_comment = (TextView) findViewById(R.id.tv_comment);
		ll_retweet_status = (LinearLayout) findViewById(R.id.ll_status_detail_retweet);
		tv_retweet_text = (TextView) findViewById(R.id.tv_status_detail_retweet_text);
		niv_retweet_thumbpic = (NetworkImageView) findViewById(R.id.niv__status_detail_retweet_thumbpic);
	}

	public void initData() {
		mStatus = (Status) getIntent().getSerializableExtra("status");
		ImageLoader imageLoader = ImageCacheManager.getInstance()
				.getImageLoader();
		niv_avatar.setImageUrl(mStatus.getUser().getAvatar(), imageLoader);
		tv_status_nick_name.setText(mStatus.getUser().getNickName());
		tv_location.setText(mStatus.getUser().getSex() + ", "
				+ mStatus.getUser().getLocation());
		tv_status_text.setText(mStatus.getText());
		if (null != mStatus.getOriginalPic()) {
			niv_thumb_pic.setImageUrl(mStatus.getOriginalPic(), imageLoader);
			niv_thumb_pic.setVisibility(View.VISIBLE);
		}else if(null != mStatus.getMiddlePic()){
			niv_thumb_pic.setImageUrl(mStatus.getMiddlePic(), imageLoader);
			niv_thumb_pic.setVisibility(View.VISIBLE);
		}else if (null != mStatus.getThumbPic()) {
			niv_thumb_pic.setImageUrl(mStatus.getThumbPic(), imageLoader);
			niv_thumb_pic.setVisibility(View.VISIBLE);
		}
		if(null != mStatus.getRetweetedStatus()){
			ll_retweet_status.setVisibility(View.VISIBLE);
			tv_retweet_text.setText(mStatus.getRetweetedStatus().getText());
			String imageUrl = mStatus.getRetweetedStatus().getOriginalPic();
			if(null != imageUrl){
				niv_retweet_thumbpic.setImageUrl(imageUrl, imageLoader);		
				niv_retweet_thumbpic.setVisibility(View.VISIBLE);
			}else if (null != (imageUrl=mStatus.getRetweetedStatus().getMiddlePic())) {
				niv_retweet_thumbpic.setImageUrl(imageUrl, imageLoader);		
				niv_retweet_thumbpic.setVisibility(View.VISIBLE);
			}else if (null != (imageUrl=mStatus.getRetweetedStatus().getThumbPic())) {
				niv_retweet_thumbpic.setImageUrl(imageUrl, imageLoader);		
				niv_retweet_thumbpic.setVisibility(View.VISIBLE);
			}
		}
		String timeAndSource = TimeHelper.parseTime(mStatus.getCreatedAt())
				+ " 来自" + Html.fromHtml(mStatus.getSource());
		tv_source.setText(timeAndSource);
		tv_repost.setText("转发(" + mStatus.getRepostCount() + ")");
		tv_comment.setText("评论(" + mStatus.getCommentCount() + ")");
	}

}
