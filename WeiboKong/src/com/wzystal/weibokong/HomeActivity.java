package com.wzystal.weibokong;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wzystal.weibokong.adapter.StatusDataAdapter;
import com.wzystal.weibokong.model.AppData;
import com.wzystal.weibokong.model.Status;
import com.wzystal.weibokong.model.User;
import com.wzystal.weibokong.widget.HomeStatusesFragment;
import com.wzystal.weibokong.widget.MentionStatusesFragment;
import com.wzystal.weibokong.widget.MoreFragment;
import com.wzystal.weibokong.widget.ProfileFragment;
import com.wzystal.weibokong.widget.XListView;

public class HomeActivity extends Activity implements OnClickListener {
	private User user = null;
	private TextView tvNickName;
	private ImageButton btnMenu;
	private ImageButton btnWriteStatus;
	
	private FragmentManager fragmentManager;
	private Fragment homeFragment;
	private Fragment mentionFragment;
	private Fragment profileFragment;
	private Fragment moreFragment;
	private ImageButton btnHome;
	private ImageButton btnMessage;
	private ImageButton btnProfile;
	private ImageButton btnMore;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		initData();
	}

	public void initView() {
		tvNickName = (TextView) findViewById(R.id.tv_home_nick_name);
		btnMenu = (ImageButton) findViewById(R.id.btn_home_menu);
		btnMenu.setOnClickListener(this);
		btnWriteStatus = (ImageButton) findViewById(R.id.btn_status_write);
		btnWriteStatus.setOnClickListener(this);
		btnHome = (ImageButton) findViewById(R.id.tab_home);
		btnMessage = (ImageButton) findViewById(R.id.tab_message);
		btnProfile = (ImageButton) findViewById(R.id.tab_profile);
		btnMore = (ImageButton) findViewById(R.id.tab_more);
		btnHome.setOnClickListener(this);
		btnMessage.setOnClickListener(this);
		btnProfile.setOnClickListener(this);
		btnMore.setOnClickListener(this);
		fragmentManager = getFragmentManager();
		showTab(0);
	}

	public void initData() {
		user = AppData.getLoginUser();
		tvNickName.setText(user.getNickName());
	}

	private void showTab(int index) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		clearTabSelection();
		switch (index) {
		case 0:
			btnHome.setImageResource(R.drawable.tab_home_disabled);
			if (null == homeFragment) {
				homeFragment = new HomeStatusesFragment();
				transaction.add(R.id.fragment_content, homeFragment);
			} else {
				transaction.show(homeFragment);
			}
			break;
		case 1:
			btnMessage.setImageResource(R.drawable.tab_message_disabled);
			if (null == mentionFragment) {
				mentionFragment = new MentionStatusesFragment();
				transaction.add(R.id.fragment_content, mentionFragment);
			} else {
				transaction.show(mentionFragment);
			}
			break;
		case 2:
			btnProfile.setImageResource(R.drawable.tab_profile_disabled);
//			if(null == profileFragment){
//				profileFragment = new ProfileFragment();
//				transaction.add(R.id.fragment_content, profileFragment);
//			}else {
//				transaction.show(profileFragment);
//			}
			profileFragment = new ProfileFragment();
			transaction.add(R.id.fragment_content, profileFragment);
			break;
		case 3:
			btnMore.setImageResource(R.drawable.tab_more_disabled);
			if(null == moreFragment){
				moreFragment = new MoreFragment();
				transaction.add(R.id.fragment_content, moreFragment);
			}else {
				transaction.show(moreFragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (null != homeFragment)
			transaction.hide(homeFragment);
		if (null != mentionFragment)
			transaction.hide(mentionFragment);
		if (null != profileFragment)
			transaction.hide(profileFragment);
		if (null != moreFragment)
			transaction.hide(moreFragment);
	}

	private void clearTabSelection() {
		btnHome.setImageResource(R.drawable.selector_tab_home);
		btnMessage.setImageResource(R.drawable.selector_tab_message);
		btnProfile.setImageResource(R.drawable.selector_tab_profile);
		btnMore.setImageResource(R.drawable.selector_tab_more);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_home_menu:
			showTab(3);
			break;
		case R.id.btn_status_write:
			startActivity(new Intent(this, StatusWriteActivity.class));
			break;
		case R.id.tab_home:
			showTab(0);
			break;
		case R.id.tab_message:
			showTab(1);
			break;
		case R.id.tab_profile:
			showTab(2);
			break;
		case R.id.tab_more:
			showTab(3);
			break;
		default:
			break;
		}
	}
	
}
