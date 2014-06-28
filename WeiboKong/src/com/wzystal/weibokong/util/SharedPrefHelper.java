package com.wzystal.weibokong.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.wzystal.weibokong.model.User;

public class SharedPrefHelper {
	public static final String PS_WEIBOKONG = "weibokong";
	public static final String USER_LIST = "user_list";
	
	private Context context = null;
	private static SharedPreferences sp = null;
	private static Editor editor = null;

	public SharedPrefHelper(Context context) {
		this(context, PreferenceManager.getDefaultSharedPreferences(context));
	}

	public SharedPrefHelper(Context context, String filename) {
		this(context, context.getSharedPreferences(filename,
				Context.MODE_WORLD_WRITEABLE));
	}

	public SharedPrefHelper(Context context, SharedPreferences sp) {
		this.context = context;
		this.sp = sp;
		editor = sp.edit();
	}

	public void setUserList(String key, List<User> list)
			throws IOException {
		// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 然后将得到的字符数据装载到ObjectOutputStream
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		// writeObject 方法负责写入特定类的对象的状态，以便相应的readObject可以还原它
		oos.writeObject(list);
		// 最后，用Base64.encode将字节文件转换成Base64编码，并以String形式保存
		String value = new String(Base64.encode(baos.toByteArray(),
				Base64.DEFAULT));
		editor.putString(key, value);
		editor.commit();
		oos.close();
	}

	public ArrayList<User> getUserList(String key)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		String value = sp.getString(key, null);
		if (null != value) {
			byte[] mByte = Base64.decode(value.getBytes(), Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(mByte);
			ObjectInputStream ois = new ObjectInputStream(bais);
			ArrayList<User> list = (ArrayList<User>) ois.readObject();
			return list;
		}
		return null;
	}

}
