package com.wzystal.weibokong.widget;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.wzystal.weibokong.R;
import com.wzystal.weibokong.cache.ImageCacheManager;
import com.wzystal.weibokong.http.UserManager;
import com.wzystal.weibokong.model.AppData;
import com.wzystal.weibokong.model.Status;
import com.wzystal.weibokong.model.User;
import com.wzystal.weibokong.util.SharedPrefHelper;
import com.wzystal.weibokong.util.TimeHelper;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileFragment extends Fragment implements Listener<User>, ErrorListener{
	private LinearLayout pbLoading;
	private NetworkImageView nivAvatar;
	private TextView tvNickName;
	private TextView tvLocation;
	private TextView tvMotto;
	private TextView tvFollowerCount;
	private TextView tvFriendCount;
	private TextView tvStatusCount;	
	private TextView tvFavoriteCount;
	
	private TextView tv_status_text;
	private NetworkImageView niv_thumb_pic;
	private LinearLayout ll_retweet_status;
	private TextView tv_retweet_text;
	private NetworkImageView niv_retweet_thumbpic;
	private TextView tv_source;
	private TextView tv_repost;
	private TextView tv_comment;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_profile, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
	}
	
	private void initView(){
		View view = getView();
		pbLoading = (LinearLayout) view.findViewById(R.id.pb_loading);
		nivAvatar = (NetworkImageView) view.findViewById(R.id.niv_profile_avatar);
		tvNickName = (TextView) view.findViewById(R.id.tv_profile_nick_name);
		tvLocation = (TextView) view.findViewById(R.id.tv_profile_location);
		tvMotto = (TextView) view.findViewById(R.id.tv_profile_motto);
		tvFollowerCount = (TextView) view.findViewById(R.id.tv_profile_follower_count);
		tvFriendCount = (TextView) view.findViewById(R.id.tv_profile_friend_count);
		tvStatusCount = (TextView) view.findViewById(R.id.tv_profile_status_count);
		tvFavoriteCount = (TextView) view.findViewById(R.id.tv_profile_favorite_count);		
		
		tv_status_text = (TextView) view.findViewById(R.id.tv_status_text);
		niv_thumb_pic = (NetworkImageView) view.findViewById(R.id.niv_thumb_pic);
		ll_retweet_status = (LinearLayout) view.findViewById(R.id.ll_retweet_status);
		tv_retweet_text = (TextView) view.findViewById(R.id.tv_retweet_text);
		niv_retweet_thumbpic = (NetworkImageView) view.findViewById(R.id.niv_retweet_thumbpic);
		tv_source = (TextView) view.findViewById(R.id.tv_source);
		tv_repost = (TextView) view.findViewById(R.id.tv_repost);
		tv_comment = (TextView) view.findViewById(R.id.tv_comment);
	}
	
	private void initData(){
		UserManager.getInstance().getUserByUid(this, this, AppData.getLoginUser().getUid());
	}
	
	@Override
	public void onResponse(User user) {
		if(null != user){
			user.setToken(AppData.getLoginUser().getToken());
			AppData.userLogin(user);
			SharedPrefHelper spHelper = new SharedPrefHelper(getActivity(), SharedPrefHelper.PS_WEIBOKONG);
			List<User> userList = null;
			try {
				userList = spHelper.getUserList(SharedPrefHelper.USER_LIST);
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(null!=userList){
				if(userList.contains(user)) userList.remove(user);
				userList.add(user);
				try {
					spHelper.setUserList(SharedPrefHelper.USER_LIST, userList);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			ImageLoader imageLoader = ImageCacheManager.getInstance().getImageLoader();
			nivAvatar.setImageUrl(user.getAvatarLarge(), imageLoader);
			tvNickName.setText(user.getNickName());
			tvLocation.setText(user.getSex() + ", " + user.getLocation());
			tvMotto.setText(user.getMotto());
			tvFollowerCount.setText(""+user.getFollowerCount());
			tvFriendCount.setText(""+user.getFriendCount());
			tvStatusCount.setText(""+user.getStatusCount());
			tvFavoriteCount.setText(""+user.getFavouriteCount());
			Status status = user.getLastestStatus();
			tv_status_text.setText(status.getText());
			if (null != status.getThumbPic()) {
				niv_thumb_pic.setImageUrl(status.getThumbPic(), imageLoader);
				niv_thumb_pic.setVisibility(View.VISIBLE);
			}
			if(null != status.getRetweetedStatus()){
				ll_retweet_status.setVisibility(View.VISIBLE);
				tv_retweet_text.setText(status.getRetweetedStatus().getText());
				String imageUrl = status.getRetweetedStatus().getThumbPic();
				if(null != imageUrl){
					niv_retweet_thumbpic.setImageUrl(imageUrl, imageLoader);		
					niv_retweet_thumbpic.setVisibility(View.VISIBLE);
				}
			}
			String timeAndSource = TimeHelper.parseTime(status.getCreatedAt())
					+ " 来自" + Html.fromHtml(status.getSource());
			tv_source.setText(timeAndSource);
			tv_repost.setText("转发(" + status.getRepostCount() + ")");
			tv_comment.setText("评论(" + status.getCommentCount() + ")");
			pbLoading.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		error.printStackTrace();
	}
}
