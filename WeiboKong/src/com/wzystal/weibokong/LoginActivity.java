package com.wzystal.weibokong;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wzystal.weibokong.adapter.AccountsAdapter;
import com.wzystal.weibokong.model.AppData;
import com.wzystal.weibokong.model.User;
import com.wzystal.weibokong.util.SharedPrefHelper;

public class LoginActivity extends Activity {
	private ArrayList<User> userList = null;
	private LinearLayout llAddAccount = null;
	private ListView lvAccounts = null;
	private SharedPrefHelper spHelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		try {
			initData();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void initView() {
		llAddAccount = (LinearLayout) findViewById(R.id.llAddAccount);
		llAddAccount.setOnClickListener(new AccountAddListener());
		lvAccounts = (ListView) findViewById(R.id.lvAccounts);
	}

	public void initData() throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		spHelper = new SharedPrefHelper(this,
				SharedPrefHelper.PS_WEIBOKONG);
		if (null != spHelper) {
			userList = spHelper.getUserList(SharedPrefHelper.USER_LIST);
		}
		if (null != userList) {
			AccountsAdapter accountsAdapter = new AccountsAdapter(this,
					userList);
			lvAccounts.setAdapter(accountsAdapter);
			lvAccounts.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					AccountsAdapter.ViewHolder holder = (AccountsAdapter.ViewHolder) view.getTag();
					AppData.userLogin(holder.user);
					Intent intent = new Intent(LoginActivity.this,
							HomeActivity.class);
					LoginActivity.this.startActivity(intent);
//					LoginActivity.this.finish();
				}
			});
		}
	}

	class AccountAddListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(LoginActivity.this, AuthActivity.class);
			LoginActivity.this.startActivity(intent);
			LoginActivity.this.finish();
		}
	}

	// 再按一次退出程序
	private static Boolean isExit = false;
	Timer tExit = new Timer();
	MyTimerTask task = null;

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			isExit = false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT)
						.show();
				if (task != null)
					task.cancel(); // 将原任务从队列中移除
				task = new MyTimerTask();// 新建一个任务
				tExit.schedule(task, 2000);
			} else {
				this.finish();
				AppData.userLogout();
				System.exit(0);
			}
		}
		return false;
	}
}
