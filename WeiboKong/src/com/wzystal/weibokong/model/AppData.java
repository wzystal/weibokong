package com.wzystal.weibokong.model;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;

import com.wzystal.weibokong.cache.ImageCacheManager;
import com.wzystal.weibokong.cache.ImageCacheManager.CacheType;
import com.wzystal.weibokong.http.RequestManager;

public class AppData extends Application{
	public static final String APP_NAME = "微博控";
	
	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100; // PNG is lossless so
	
	private static Context context;
	
	private static String authToken = null;
	private static User loginUser = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
		context = getApplicationContext();
	}

	public static Context getContext() {
		return AppData.context;
	}
	
	/**
	 * Intialize the request manager and the image cache
	 */
	private void init() {
		RequestManager.init(this);
		createImageCache();
	}

	public static String getAuthToken() {
		return authToken;
	}

	public static void setAuthToken(String authToken) {
		AppData.authToken = authToken;
	}

	public static User getLoginUser(){
		return loginUser;
	}
	
	public static void userLogin(User user){
		loginUser = user;
	}
	public static void userLogout(){
		loginUser = null;
	}
	
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for
	 * a Disk based LRU implementation.
	 */
	private void createImageCache() {
		ImageCacheManager.getInstance().init(this, this.getPackageCodePath(),
				DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT,
				DISK_IMAGECACHE_QUALITY, CacheType.MEMORY);
	}
}
