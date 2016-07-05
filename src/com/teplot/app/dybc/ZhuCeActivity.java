package com.teplot.app.dybc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.teplot.app.dybc.model.XiangQingRequest;
import com.trplot.app.dybs.R;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 注册页面，暂时停用
 * @author 杨航
 *
 */
public class ZhuCeActivity extends Activity {

	private SharedPreferences User;
	private SharedPreferences.Editor userEditor;

	private EditText mZhangHao;
	private EditText mMiMa;
	private EditText mQueRen;

	private Button mZhuCe;
	private ImageView mBack;

	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuce);
		initView();
		initData();
	}

	String Num;
	List<String> i = new ArrayList<>();

	private void initData() {
		String token = Preferences.getPrefer(getApplicationContext())
				.getString("token", "");
		url = "http://125.46.1.174:8080/5/api/dyxx/list.do?token=" + token;
		VollyHelper.create(getApplication()).addRequest(
				new XiangQingRequest(Method.GET, url,
						new Listener<List<DangYuanXinXiModel>>() {

							@Override
							public void onResponse(List<DangYuanXinXiModel> arg0) {
								for (int j = 0; j < arg0.size(); j++) {
									  Num = arg0.get(j).getPager();
										Log.e("111111111111111111111111111", ""+Num);
									i.add(Num);
								}
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {

							}
						}), url);

	}

	private void initView() {
		mZhangHao = (EditText) findViewById(R.id.et_num);
		mMiMa = (EditText) findViewById(R.id.et_mima);
		mQueRen = (EditText) findViewById(R.id.et_queren);
		mZhuCe = (Button) findViewById(R.id.bt_zhuce);
		mBack = (ImageView) findViewById(R.id.btn_back);
		User = getSharedPreferences("ZhangHao", MODE_PRIVATE);
		userEditor = User.edit();

		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mZhuCe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Map<String, String> map = (Map<String, String>) User.getAll();
				Set<String> set = map.keySet();
				String urname = null;
				for (String name : set) {
					if (mZhangHao.getText().toString().equals(name)) {
						urname = name;
						break;
					}
				}
				//手机号不匹配不能进行注册
				String Num = null;
				for (String num:i) {
					if (mZhangHao.getText().toString().equals(num)) {
						Num = num;
						break;
					}
				}
				if (TextUtils.isEmpty(mMiMa.getText().toString())
						&& TextUtils.isEmpty(mQueRen.getText().toString())) {
					Toast.makeText(ZhuCeActivity.this, "账户密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (!mMiMa.getText().toString().trim()
						.equals(mQueRen.getText().toString().trim())) {
					Toast.makeText(ZhuCeActivity.this, "两次密码输入不一致",
							Toast.LENGTH_SHORT).show();
				} else if (TextUtils.isEmpty(mZhangHao.getText().toString())) {
					Toast.makeText(ZhuCeActivity.this, "密码不能为空",
							Toast.LENGTH_SHORT).show();
				} else if (mZhangHao.getText().toString().equals(urname)) {
					Toast.makeText(ZhuCeActivity.this, "您注册的用户已存在",
							Toast.LENGTH_SHORT).show();
				} else if (!mZhangHao.getText().toString().equals(Num)) {
//					Log.e("22222222222222222222222222222", "" + Num);
					Toast.makeText(ZhuCeActivity.this, "您不能进行注册",
							Toast.LENGTH_SHORT).show();
				} else {
					userEditor.putString(mZhangHao.getText().toString(), mMiMa
							.getText().toString());
					userEditor.commit();
					Toast.makeText(ZhuCeActivity.this, "注册成功",
							Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	}
}
