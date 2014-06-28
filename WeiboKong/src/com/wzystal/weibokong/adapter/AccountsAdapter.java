package com.wzystal.weibokong.adapter;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.wzystal.weibokong.R;
import com.wzystal.weibokong.cache.ImageCacheManager;
import com.wzystal.weibokong.model.User;
import com.wzystal.weibokong.util.SharedPrefHelper;

public class AccountsAdapter extends BaseAdapter{
	private Context context = null;
	private ArrayList<User> userList = null;
	
	public AccountsAdapter(Context context, ArrayList<User> userList){
		this.context = context;
		this.userList = userList;
	}
	
	public static class ViewHolder{
		public User user;
		private NetworkImageView nivAvatar;
		public TextView tvNickName;
		public ImageView ivAccountDel;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		AccountDelListener accountDelListener = null;
		if(null == convertView){
			convertView = View.inflate(context, R.layout.list_item_account, null);
			holder = new ViewHolder();
			holder.nivAvatar = (NetworkImageView)convertView.findViewById(R.id.niv_account_avatar);
			holder.tvNickName = (TextView)convertView.findViewById(R.id.tv_account_nickName);
			holder.ivAccountDel = (ImageView)convertView.findViewById(R.id.iv_account_delete);
			accountDelListener = new AccountDelListener();
			holder.ivAccountDel.setOnClickListener(accountDelListener);
			convertView.setTag(holder);
			convertView.setTag(holder.ivAccountDel.getId(), accountDelListener);			
		}else {
			holder = (ViewHolder)convertView.getTag();
			accountDelListener = (AccountDelListener)convertView.getTag(holder.ivAccountDel.getId());
		}
		accountDelListener.setPosition(position);
		User user = userList.get(position);
		if(null != user){
			holder.user = user;
			holder.nivAvatar.setImageUrl(user.getAvatar(), ImageCacheManager.getInstance().getImageLoader());
			holder.tvNickName.setText(user.getNickName());
		}
		return convertView;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.userList.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.userList.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	class AccountDelListener implements OnClickListener{
		int position;
		public void setPosition(int position) {  
            this.position = position;  
        } 
		@Override
		public void onClick(View view) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("您确定要删除 " + userList.get(position).getNickName() + " ?")
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPrefHelper spHelper = new SharedPrefHelper(context, SharedPrefHelper.PS_WEIBOKONG);
						try {
							ArrayList<User> allUsers = spHelper.getUserList(SharedPrefHelper.USER_LIST);
							allUsers.remove(userList.get(position));
							spHelper.setUserList(SharedPrefHelper.USER_LIST, allUsers);
						} catch (StreamCorruptedException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						userList.remove(position);
						AccountsAdapter.this.notifyDataSetChanged();
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
}
