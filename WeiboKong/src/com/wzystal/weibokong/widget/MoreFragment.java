package com.wzystal.weibokong.widget;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import com.wzystal.weibokong.LoginActivity;
import com.wzystal.weibokong.R;
import com.wzystal.weibokong.adapter.AccountsAdapter;
import com.wzystal.weibokong.model.AppData;
import com.wzystal.weibokong.model.User;
import com.wzystal.weibokong.util.SharedPrefHelper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MoreFragment extends Fragment implements OnClickListener{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_more, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	private void initView(){
		View view = getView();
		LinearLayout ll_setting = (LinearLayout) view.findViewById(R.id.ll_more_setting);
		LinearLayout ll_account = (LinearLayout) view.findViewById(R.id.ll_more_account);
		LinearLayout ll_group = (LinearLayout) view.findViewById(R.id.ll_more_group);
		LinearLayout ll_theme = (LinearLayout) view.findViewById(R.id.ll_more_theme);
		LinearLayout ll_feedback = (LinearLayout) view.findViewById(R.id.ll_more_feedback);
		LinearLayout ll_check_update = (LinearLayout) view.findViewById(R.id.ll_more_check_update);
		LinearLayout ll_quit = (LinearLayout) view.findViewById(R.id.ll_more_quit);
		ll_setting.setOnClickListener(this);
		ll_account.setOnClickListener(this);
		ll_group.setOnClickListener(this);
		ll_theme.setOnClickListener(this);
		ll_feedback.setOnClickListener(this);
		ll_check_update.setOnClickListener(this);
		ll_quit.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_more_account:
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			break;
		case R.id.ll_more_quit:
			quit();
			break;
		default:
			break;
		}
	}
	
	private void quit(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("您确定要退出 " + AppData.APP_NAME + " ?")
			.setCancelable(false)
			.setPositiveButton("确定", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					getActivity().finish();
					AppData.userLogout();
					System.exit(0);
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
}
