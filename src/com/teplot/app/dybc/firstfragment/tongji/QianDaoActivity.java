package com.teplot.app.dybc.firstfragment.tongji;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.find.SignIn.SingInAdapter;
import com.teplot.app.dybc.find.SignIn.SingInModel;
import com.teplot.app.dybc.find.SignIn.SingInRequest;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.VollyHelper;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QianDaoActivity extends Activity implements OnItemClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("签到签退");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private ListView mLvQiandao;
	private SingInAdapter mAdapter;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qiandao);
		initTitle();
		initView();
		LoadData();
	}

	private void LoadData() {

		String token = Preferences.getPrefer(this).getString("token", "");
		int id = Preferences.getPrefer(this).getInt("id", 0);
		url = Url.Sign + "token=" + token + "&id=" + id;
		Log.e("urlurl", ""+url);
		VollyHelper.create(getApplicationContext()).addRequest(
				new SingInRequest(Method.GET, url,
						new Listener<List<SingInModel>>() {
							@Override
							public void onResponse(List<SingInModel> arg0) {
								mAdapter.addItems(arg0);
								ArrayList<SingInModel> xinXiModels = (ArrayList<SingInModel>) arg0;
								ACache aCache = ACache
										.get(QianDaoActivity.this);
								aCache.put("QianDao", xinXiModels,
										ACache.TIME_DAY);
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								@SuppressWarnings("unchecked")
								final ArrayList<SingInModel> Model = (ArrayList<SingInModel>) ACache
										.get(QianDaoActivity.this).getAsObject(
												"QianDao");
								if (Model!=null) {
									mAdapter.addItems(Model);
								}else {
									Toast.makeText(QianDaoActivity.this, "没有相关数据", Toast.LENGTH_SHORT).show();
								}
							}
						}), url);

	}

	private void initView() {
		mLvQiandao = (ListView) findViewById(R.id.lv_qiandao);
		mAdapter = new SingInAdapter(this);
		mLvQiandao.setAdapter(mAdapter);
		mLvQiandao.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
