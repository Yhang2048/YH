package com.teplot.app.dybc.firstfragment.tongji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.find.SignIn.SingInModel;
import com.teplot.app.dybc.find.studyMetting.MeetingAdapter;
import com.teplot.app.dybc.find.studyMetting.MettingModel;
import com.teplot.app.dybc.find.studyMetting.StadyDetailActivity;
import com.teplot.app.dybc.find.studyMetting.StadyMettingRequest;
import com.teplot.app.dybc.firstfragment.kaoping.DangYuangHuPingActivity;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.HttpAsyncTask;
import com.teplot.app.dybc.http.NetUtil;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DengLuModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.trplot.app.dybs.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class XueXiActivity extends FragmentActivity implements
		OnItemClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("会议活动");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private ListView mLvStudy;
	private MeetingAdapter mAdapter;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xuexi);
		initTitle();
		initView();
		LoadData();
	}

	private void initView() {
		mLvStudy = (ListView) findViewById(R.id.lv_study);
		mAdapter = new MeetingAdapter(getApplicationContext());
		mLvStudy.setAdapter(mAdapter);
		mLvStudy.setOnItemClickListener(this);

	}

	private void LoadData() {
		String token = Preferences.getPrefer(getApplicationContext())
				.getString("token", "");
		int origid = Preferences.getPrefer(this).getInt("origId", 0);
		url = Url.Metting + "token=" + token + "&origId=" + origid;
		Log.e("urlurlurlurl", "" + url);
		VollyHelper.create(getApplicationContext()).addRequest(
				new StadyMettingRequest(Method.GET, url,
						new Listener<List<MettingModel>>() {

							@Override
							public void onResponse(List<MettingModel> arg0) {
								mAdapter.addItems(arg0);
								ArrayList<MettingModel> xinXiModels = (ArrayList<MettingModel>) arg0;
								ACache aCache = ACache.get(XueXiActivity.this);
								aCache.put("Metting", xinXiModels,
										ACache.TIME_HOUR);
							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								@SuppressWarnings("unchecked")
								final ArrayList<MettingModel> mettingModels = (ArrayList<MettingModel>) ACache
										.get(XueXiActivity.this).getAsObject(
												"Metting");
								if (mettingModels != null) {
									mAdapter.addItems(mettingModels);
								}else {
									Toast.makeText(getApplicationContext(), "没有相关数据", Toast.LENGTH_SHORT).show();
								}
							}
						}), url);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(XueXiActivity.this,
				StadyDetailActivity.class);
		MettingModel data = mAdapter.getItem(position);
		intent.putExtra("data", data);
		startActivity(intent);
	}

}
