package com.teplot.app.dybc.firstfragment.tongji;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.teplot.app.dybc.firstfragment.tongji.JiFenDetailFenXiangAdapter;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.find.SignIn.SingInModel;
import com.teplot.app.dybc.find.SignIn.SingInRequest;
import com.teplot.app.dybc.http.NetUtil;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.VollyHelper;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader.ForceLoadContentObserver;
import android.os.Bundle;
import android.renderscript.Float2;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class JiFenErDetail extends Activity {

	private ListView mLvFenXiang;
	private JiFenDetailFenXiangErAdapter mAdapter;
	private String url;
	private String url2;
	// 基准分值
	private TextView JiZhunFenZhi;
	private TextView ShiJiFenZhiRiChang;
	private TextView ShiJiFenZhiDangNei;
	private TextView ShiJiFenZhiDangWai;
	private TextView RiChangZong;
	private TextView DangNeiZong;
	private TextView DangWaiZong;
	private TextView ZongFenZhi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jifen_detail);
		initView();

		NetUtil netUtil = new NetUtil();
		@SuppressWarnings("static-access")
		Boolean isNetWork = netUtil.isNetworkAvalible(JiFenErDetail.this);
		if (isNetWork) {
			loadDate();
		} else {
			Toast.makeText(JiFenErDetail.this, "网络连接不可用", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void initView() {
		mLvFenXiang = (ListView) findViewById(R.id.lv_fenxiang_leibie);
		mAdapter = new JiFenDetailFenXiangErAdapter(this);
		mLvFenXiang.setAdapter(mAdapter);
		JiZhunFenZhi = (TextView) findViewById(R.id.jizhun_fenzhi);
		ShiJiFenZhiRiChang = (TextView) findViewById(R.id.shiji_fenzhi_richang);
		RiChangZong = (TextView) findViewById(R.id.richang_zongfenzhi);
		ShiJiFenZhiDangNei = (TextView) findViewById(R.id.er_huping_shiji);
		ShiJiFenZhiDangWai = (TextView) findViewById(R.id.er_dangwai_shiji);
		DangNeiZong = (TextView) findViewById(R.id.er_huping_zongfenzhi);
		DangWaiZong = (TextView) findViewById(R.id.er_dangwai_zong);
		ZongFenZhi = (TextView) findViewById(R.id.zongfenzhi);
	}

	private void loadDate() {

		String token = Preferences.getPrefer(this).getString("token", "");
		int id = Preferences.getPrefer(this).getInt("id", 0);
		url = Url.Sign + "token=" + token + "&id=" + id;
		VollyHelper.create(getApplicationContext()).addRequest(
				new SingInRequest(Method.GET, url,
						new Listener<List<SingInModel>>() {

							@Override
							public void onResponse(List<SingInModel> arg0) {
								float Scores = 0;
								int quarter = 0;
								for (int i = 0; i < arg0.size(); i++) {
									float score = arg0.get(i).getScore();
									Scores = Scores + score;
									quarter = arg0.get(i).getQuarter();
								}
								SetData(Scores, quarter);
								mAdapter.addItems(arg0);
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(), "没有相关数据", Toast.LENGTH_SHORT).show();
							}
						}), url);

		url2 = Url.DangNeiWaiPingFen + "token=" + token + "&id=" + id
				+ "&quarter=" + 2;
		VollyHelper.create(getApplicationContext()).addRequest(
				new KaopingJiFenRequest(Method.GET, url2,
						new Listener<KaoPingJiFenModel>() {

							@Override
							public void onResponse(KaoPingJiFenModel arg0) {
								Log.e("arg0", "" + arg0);
								if (arg0 != null) {
									double DangNei = arg0.getDnIntegral();
									double shang = DangNei * 1 / 5;
									
									DecimalFormat decimalFormat = new DecimalFormat(
											"0.00");
									String Shang = decimalFormat.format(shang);
									String DangNeiFen = decimalFormat.format(DangNei);
									ShiJiFenZhiDangNei.setText(DangNeiFen + "");
									DangNeiZong.setText(Shang);

									double DangWai = arg0.getDwIntegral();
									double shang1 = DangWai * 1 / 5;
									DecimalFormat decimalFormat1 = new DecimalFormat(
											"0.00");
									String Shang1 = decimalFormat1.format(shang1);
									String DangWaiFen = decimalFormat1.format(DangWai);
									ShiJiFenZhiDangWai.setText(DangWaiFen + "");
									DangWaiZong.setText(Shang1);

									String text = Preferences.getPrefer(
											JiFenErDetail.this).getString(
											"text", "");
									double zong = arg0.getZongIntegral();
									DecimalFormat decimalFormat3 = new DecimalFormat(
											"0.00");
									String zongfen = decimalFormat3.format(zong);
									ZongFenZhi.setText(zongfen);
									
									Intent intent = getIntent();
									intent.putExtra("ezongfen", zongfen);
									setResult(1, intent);
									
									Preferences.getPrefer(getApplicationContext()).putString("erzongfen", zongfen);
									
								}
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub
								Intent intent = getIntent();
								intent.putExtra("zongfen", Preferences.getPrefer(getApplicationContext()).getString("erzongfen", ""));
								setResult(0,intent);
								Toast.makeText(getApplicationContext(), "没有相关数据", Toast.LENGTH_SHORT).show();
							}
						}), url2);

	}
	private void SetData(float Scores, int quarter) {
		int UserType = Preferences.getPrefer(getApplicationContext()).getInt(
				"UserType", 1);
		Log.e("UserType", "" + UserType);
		if (quarter == 2) {
			if (UserType == 1) {
				float sum = Scores + 40;
				float shang = sum * 3 / 5;
				DecimalFormat decimalFormat = new DecimalFormat(".00");
				String Shang = decimalFormat.format(shang);
				RiChangZong.setText(Shang);
				ShiJiFenZhiRiChang.setText(sum + "");
				JiZhunFenZhi.setText(40 + "");
			} else if (UserType == 2) {
				float sum = Scores + 50;
				float shang = sum * 3 / 5;
				DecimalFormat decimalFormat = new DecimalFormat("0.00");
				String Shang = decimalFormat.format(shang);
				RiChangZong.setText(Shang);
				ShiJiFenZhiRiChang.setText(sum + "");
				JiZhunFenZhi.setText(50 + "");
			} else if (UserType == 3) {
				float sum = Scores + 60;
				float shang = sum * 3 / 5;
				DecimalFormat decimalFormat = new DecimalFormat(".00");
				String Shang = decimalFormat.format(shang);
				RiChangZong.setText(Shang);
				ShiJiFenZhiRiChang.setText(sum + "");
				JiZhunFenZhi.setText(60 + "");
			}

		}

	}

}
