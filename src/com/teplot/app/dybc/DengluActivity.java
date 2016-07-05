package com.teplot.app.dybc;

import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.location.LLSInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.internal.http.RealResponseBody;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.HttpAsyncTask;
import com.teplot.app.dybc.http.NetUtil;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DengLuModel;
import com.trplot.app.dybs.R;

/**
 * 实现登录的功能
 * 
 * @author Administrator
 * 
 */
public class DengluActivity extends Activity implements OnCheckedChangeListener {
	public SharedPreferences User;
	public SharedPreferences.Editor UserEditor;
	public SharedPreferences LastUser;
	public SharedPreferences.Editor LastUserEditor;

	private EditText mZhangHao;
	private EditText mMiMa;
	private TextView mDengLu;

	private ToggleButton mToggleButton;

	private View mLoadingView;
	
	private long mExitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_denglu);
		initView();
		if (Preferences.getPrefer(getApplicationContext()).getBoolean("auto", false)) {
			NetUtil netUtil = new NetUtil();
			@SuppressWarnings("static-access")
			Boolean isNetWork = netUtil
					.isNetworkAvalible(getApplicationContext());
			if (isNetWork) {
			String account =Preferences.getPrefer(getApplicationContext()).getString("account", "");
			mZhangHao.setText(account);
			mLoadingView.setVisibility(View.VISIBLE);
			mDengLu.setText("自动登录中。。。");
			mDengLu.setOnClickListener(null);
			String password =Preferences.getPrefer(getApplicationContext()).getString("password", "");
			login(account, password);
			} else {
				Toast.makeText(DengluActivity.this, "网络不可用,无法登陆",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	String Num;

	private void initView() {
		mZhangHao = (EditText) findViewById(R.id.et_num);
		mMiMa = (EditText) findViewById(R.id.et_mima);
		mDengLu = (TextView) findViewById(R.id.bt_denglu);
		mToggleButton = (ToggleButton) findViewById(R.id.tgb_btn);
		
		mLoadingView = findViewById(R.id.pb_loading);
		
		User = getSharedPreferences("ZhangHao", MODE_PRIVATE);
		UserEditor = User.edit();
		LastUser = getSharedPreferences("LastUser", MODE_PRIVATE);
		LastUserEditor = LastUser.edit();
		mMiMa.setText("123456");

		// 登录按钮的监听事件
		mDengLu.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				// mThread = new Thread(runnable);
				// mThread.start();
				String UserPhone = mZhangHao.getText().toString();
				String Password = mMiMa.getText().toString();

				NetUtil netUtil = new NetUtil();
				Boolean isNetWork = netUtil
						.isNetworkAvalible(getApplicationContext());
				if (isNetWork) {
					login(UserPhone, Password);
				} else {
					Toast.makeText(DengluActivity.this, "网络连接不可用",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		mToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.e("isChecked", isChecked + "");
				Boolean Auto = isChecked;
				Preferences.getPrefer(getApplicationContext()).putBoolean("auto", Auto);
//				LastUserEditor.putBoolean("auto", Auto);
			}
		});
	}

	/**
	 * 密码输入框显示为密文格式
	 */
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {

			mMiMa.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

		} else {
			mMiMa.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
	}

	/**
	 * 登录请求
	 * 
	 * @param isAutoLogin
	 *            是否为自动登录
	 * @param account
	 *            账号
	 * @param passwd
	 *            密码
	 */
	private void login(String account, String passwd) {
		String httpUrl = Url.LogIn;
		Map<String, String> params = new HashMap<>();
		params.put("account", account);
		params.put("password", passwd);
		HttpAsyncTask http = new HttpAsyncTask() {
			@Override
			public void onStart(String taskId) {
				// show dialog
			}

			@Override
			public void onFinish(String taskId, String response) {
				DengLuModel loginInfo = new Gson().fromJson(response,
						DengLuModel.class);
				if (loginInfo != null && loginInfo.isSucces()) {
					// login success
					String token = loginInfo.getToken();
					String mag = loginInfo.getMsg();
					String code = loginInfo.getCode();
					String name = loginInfo.getObject().getName();
					String account = loginInfo.getObject().getPager();
					String password = loginInfo.getObject().getPassword();
					int UserType = loginInfo.getObject().getUserType();
					int id = loginInfo.getObject().getId();
					int origId = loginInfo.getObject().getOrigId();
					// 传输党员类型
					Preferences.getPrefer(getApplicationContext()).putInt(
							"UserType", UserType);
					// 传输账号
					Preferences.getPrefer(getApplicationContext()).putString(
							"account", account);
					// 传输密码
					Preferences.getPrefer(getApplicationContext()).putString(
							"password", password);
					// 传输名字
					Preferences.getPrefer(getApplicationContext()).putString(
							"name", name);
					// 传输id
					Preferences.getPrefer(getApplicationContext()).putInt("id",
							id);
					// 传输区域id
					Preferences.getPrefer(getApplicationContext()).putInt(
							"origId", origId);
					// 实现本地缓存，间接的数据传输
					Preferences.getPrefer(getApplicationContext()).putString(
							"token", token);

					Toast.makeText(DengluActivity.this, mag, Toast.LENGTH_SHORT)
							.show();

					LastUserEditor.commit();
					Intent intent = new Intent(DengluActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					// login failure
					// isSuc = new Boolean(false);

					String code = loginInfo.getCode();
					String msg = loginInfo.getMsg();
					Toast.makeText(DengluActivity.this, msg, Toast.LENGTH_SHORT)
							.show();
				}
			}
		};
		http.excute(httpUrl, params);
	}

	// 按两次实现退出应用
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
