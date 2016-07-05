package com.teplot.app.dybc.firstfragment.tongji;

import java.text.DecimalFormat;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.VollyHelper;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JiFenActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("考评积分");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	// private TextView mTvNeiRong;

	private LinearLayout mLLYiJiDu;
	private LinearLayout mLLErJiDu;
	private LinearLayout mLLSanJiDu;
	private LinearLayout mLLSiJiDu;
	private String url2;
	private TextView mYiJiDu, mErJiDu, mSanJiDu, mSiJiDu, ShangBanNian,
			XiaBanNian, NianDu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jifen);
		initTitle();
		initView();
		loadData();
		initData();
	}

	private void initData() {
		if (TextUtils.isEmpty(mYiJiDu.getText().toString())) {
			mYiJiDu.setText(Preferences.getPrefer(getApplicationContext())
					.getString("yizongfen", ""));
		}
		if (TextUtils.isEmpty(mErJiDu.getText().toString())) {
			mErJiDu.setText(Preferences.getPrefer(getApplicationContext())
					.getString("erzongfen", ""));
		}
		if (TextUtils.isEmpty(mSanJiDu.getText().toString())) {
			mSanJiDu.setText(Preferences.getPrefer(getApplicationContext())
					.getString("sanzongfen", ""));
		}
		if (TextUtils.isEmpty(mSiJiDu.getText().toString())) {
			mSiJiDu.setText(Preferences.getPrefer(getApplicationContext())
					.getString("sizongfen", ""));
		}
		String Yi =  mYiJiDu.getText().toString();
		String Er =  mErJiDu.getText().toString();
		String San =  mSanJiDu.getText().toString();
		String Si =  mSiJiDu.getText().toString();

		if (!TextUtils.isEmpty(Yi) && !TextUtils.isEmpty(Er)) {
			Double yiDouble = Double.valueOf(Yi);
			Double erDouble = Double.valueOf(Er);
			ShangBanNian.setText(yiDouble + erDouble + "");
		}
		if (!TextUtils.isEmpty(San) && !TextUtils.isEmpty(Si)) {
			Double sanDouble = Double.valueOf(San);
			Double siDouble = Double.valueOf(Si);
			XiaBanNian.setText(sanDouble + siDouble + "");
		}
		if (!TextUtils.isEmpty(ShangBanNian.getText().toString())
				&& !TextUtils.isEmpty(XiaBanNian.getText().toString())) {
			Double shangDouble = Double.valueOf(ShangBanNian.getText()
					.toString());
			Double xiaDouble = Double.valueOf(XiaBanNian.getText().toString());
			NianDu.setText(shangDouble + xiaDouble + "");
		}

	}

	private void loadData() {
		String token = Preferences.getPrefer(this).getString("token", "");
		int id = Preferences.getPrefer(this).getInt("id", 0);
		url2 = Url.DangNeiWaiPingFen + "token=" + token + "&id=" + id
				+ "&quarter=" + 2;
		VollyHelper.create(getApplicationContext()).addRequest(
				new KaopingJiFenRequest(Method.GET, url2,
						new Listener<KaoPingJiFenModel>() {

							@Override
							public void onResponse(KaoPingJiFenModel arg0) {
								if (arg0 != null) {
									double zong = arg0.getZongIntegral();
									DecimalFormat decimalFormat3 = new DecimalFormat(
											"0.00");
									String zongfen = decimalFormat3
											.format(zong);
									// mErJiDu.setText(zongfen);
									Preferences.getPrefer(
											getApplicationContext()).putString(
											"zongfen", zongfen);

								}
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub
								Toast.makeText(JiFenActivity.this, "没有相关数据", Toast.LENGTH_SHORT).show();
							}
						}), url2);

	}

	private void initView() {
		mLLYiJiDu = (LinearLayout) findViewById(R.id.ll_yijidu);
		mLLErJiDu = (LinearLayout) findViewById(R.id.erjidu);
		mLLSanJiDu = (LinearLayout) findViewById(R.id.sanjidu);
		mLLSiJiDu = (LinearLayout) findViewById(R.id.sijidu);

		mYiJiDu = (TextView) findViewById(R.id.jifen_yi);
		mErJiDu = (TextView) findViewById(R.id.jifen_er);
		mSanJiDu = (TextView) findViewById(R.id.jifen_san);
		mSiJiDu = (TextView) findViewById(R.id.jifen_si);
		ShangBanNian = (TextView) findViewById(R.id.jifen_shangbannian);
		XiaBanNian = (TextView) findViewById(R.id.jifen_xiaobannian);
		NianDu = (TextView) findViewById(R.id.jifen_zhengnian);
		mLLYiJiDu.setOnClickListener(this);
		mLLErJiDu.setOnClickListener(this);
		mLLSanJiDu.setOnClickListener(this);
		mLLSiJiDu.setOnClickListener(this);

	}

	// 打开富媒体列表界面
//	private void openRichMediaList() {
//		// Push: 打开富媒体消息列表
//		Intent sendIntent = new Intent();
//		sendIntent.setClassName(getBaseContext(),
//				"com.baidu.android.pushservice.richmedia.MediaListActivity");
//		sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		JiFenActivity.this.startActivity(sendIntent);
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_yijidu:
			Intent intent = new Intent(JiFenActivity.this, JiFenDetail.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.erjidu:
			Intent intent1 = new Intent(JiFenActivity.this, JiFenErDetail.class);
			startActivityForResult(intent1, 2);
			break;
		case R.id.sanjidu:
			Intent intent2 = new Intent(JiFenActivity.this,
					JiFenSanDetail.class);
			startActivityForResult(intent2, 3);
			break;
		case R.id.sijidu:
			Intent intent3 = new Intent(JiFenActivity.this, JiFenSiDetail.class);
			startActivityForResult(intent3, 4);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			String Yijidu = data.getStringExtra("yzongfen");
			mYiJiDu.setText(Yijidu);
			break;
		case 2:
			String Erjidu = data.getStringExtra("ezongfen");
			mErJiDu.setText(Erjidu);
			break;

		case 3:
			String Sanjidu = data.getStringExtra("sazongfen");
			mSanJiDu.setText(Sanjidu);
			break;
		case 4:
			String Sijidu = data.getStringExtra("szongfen");
			mSiJiDu.setText(Sijidu);
			break;
		default:
			break;
		}
	}

}
