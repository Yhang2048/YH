package com.teplot.app.dybc.Custom;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.teplot.app.dybc.DengluActivity;
import com.teplot.app.dybc.http.HttpAsyncTask;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DengLuModel;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 退出的Dialog弹框
 * @author 杨航
 *
 */
public class DialogBack extends Dialog implements
		android.view.View.OnClickListener {

	private Activity mContext;

	public DialogBack(Activity context) {
		this(context, R.style.Theme_Dialog_From_Bottom);
	}

	public DialogBack(Context context, int themeResId) {
		super(context, themeResId);
		this.mContext = (Activity) context;
		init();
		initValues();
	}

	private void initValues() {
		// 获取Dialog所在的窗口
		Window window = this.getWindow();
		// 设置其默认的padding值为0
		window.getDecorView().setPadding(0, 0, 0, 0);
		window.setBackgroundDrawableResource(R.drawable.ground_boders);
		WindowManager.LayoutParams lp = window.getAttributes();
		// 设置使其宽·高度沾满屏幕
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// 设置其透明度
		lp.dimAmount = 0.3f;
		// 位于底部
		lp.gravity = Gravity.BOTTOM;
		window.setAttributes(lp);

	}

	private void init() {
		this.setCanceledOnTouchOutside(true);
		this.setCancelable(true);

	}

	private TextView mZhuXiao;
	private TextView mClose;
	private LinearLayout mCancle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_back);
		initView();
	}

	private void initView() {
		mZhuXiao = (TextView) findViewById(R.id.tv_zhuxiao);
		mClose = (TextView) findViewById(R.id.tv_close);
		mCancle = (LinearLayout) findViewById(R.id.ll_cancel);

		mZhuXiao.setOnClickListener(this);
		mClose.setOnClickListener(this);
		mCancle.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_zhuxiao:
			String token1 = Preferences.getPrefer(getContext()).getString("token",
					"");
			logout(token1);
			//把表数据清空实现自动登录

			Preferences.getPrefer(mContext).remove("auto");
			
			Intent intent = new Intent();
			intent.setClass(mContext, DengluActivity.class);
			mContext.startActivity(intent);
			((Activity) mContext).finish();
			break;
		case R.id.tv_close:
			System.exit(0);
			break;
		case R.id.ll_cancel:
			dismiss();
			break;
		default:
			break;
		}
	}

	/**
	 * 退出时网络请求
	 */
	public void logout(String token) {
		
		String httpUrl = Url.LogOut;
		Map<String, String> map = new HashMap<>();
		map.put("token", token);
		HttpAsyncTask asyncTask = new HttpAsyncTask() {
			@Override
			public void onStart(String taskId) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onFinish(String taskId, String response) {
				DengLuModel loginInfo = new Gson().fromJson(response,
						DengLuModel.class);
				if (loginInfo.isSucces()) {
					String mag = loginInfo.getMsg();
					Toast.makeText(getContext(), mag,
							Toast.LENGTH_SHORT).show();
				}else {
					String mag = loginInfo.getMsg();
					Toast.makeText(getContext(), mag,
							Toast.LENGTH_SHORT).show();
				}
			}
		};
		asyncTask.excute(httpUrl, map);
	}
}
