package com.teplot.app.dybc.model;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

/**
 * Volly网络访问住手
 * @author Administrator
 *
 */
public class VollyHelper {
	/**
	 * 应用级别的请求对列
	 */
	private RequestQueue mAppRequestQueue=null;
	
	private Gson mGsontace=null;
	private OkHttpClient okHttpClient;
	private VollyHelper(Context context) {
		okHttpClient=new OkHttpClient();
		okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
		okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
		okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
		mAppRequestQueue=Volley.newRequestQueue(context, new OkHttpStack(okHttpClient));
		mGsontace=new Gson();
	}
	private static VollyHelper mVollyHelper=null;
	
	public static VollyHelper create(Context context){
		if(mVollyHelper== null){
			synchronized (VollyHelper.class) {
				if(mVollyHelper == null){
					mVollyHelper=new VollyHelper(context.getApplicationContext());
				}
			}
		}
		return mVollyHelper;
	}
	/**
	 * 添加请求
	 * @param request
	 * @param tag
	 */
	public void addRequest(Request<?> request,Object tag){
		request.setTag(tag);
		mAppRequestQueue.add(request);
		
	}
	/**
	 * 取消所有以tag为标识的网络请求
	 * @param tag
	 */
	public void cancelRequest(Object tag){
		mAppRequestQueue.cancelAll(tag);
	}
	/**
	 * 获取JSON解析类
	 * @return
	 */
	public Gson getGsonInstance(){
		return mGsontace;
	}
	/**
	 * 获取OkHttpClient 实例对象
	 * @return
	 */
	public OkHttpClient getOkHttpClient(){
		return okHttpClient.clone();
	}
}
