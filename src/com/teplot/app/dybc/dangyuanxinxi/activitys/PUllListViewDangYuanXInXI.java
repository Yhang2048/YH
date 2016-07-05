package com.teplot.app.dybc.dangyuanxinxi.activitys;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.escoder.pulllistview.OnListViewListener;
import com.escoder.pulllistview.PullListView;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.AdressAdapter;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.teplot.app.dybc.model.XiangQingRequest;
import com.trplot.app.dybs.R;
/**
 * 弃用
 * @author 杨航 
 *
 */
public class PUllListViewDangYuanXInXI extends FragmentActivity implements
		OnItemClickListener, OnListViewListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("基本信息");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	// 加载动画视图
	private ProgressBar mLoadingView = null;
	// 内容列表视图
	private PullListView mContentPlv = null;
	// adapter
	private AdressAdapter mDangYuanXinXiAdapter = null;

	// 当前数据索引
	private int mPageIndex = 1;

	private String url;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.pulllistview);
		initTitle();
		initView();

	}

	private void initView() {
		mLoadingView = (ProgressBar) findViewById(R.id.pb_loading);
		mContentPlv = (PullListView) findViewById(R.id.plv_common);
		mDangYuanXinXiAdapter = new AdressAdapter(
				getApplicationContext());
		mContentPlv.setAdapter(mDangYuanXinXiAdapter);
		mContentPlv.setOnItemClickListener(this);
		mContentPlv.setOnListViewListener(this);
		loadData(mPageIndex);

	}

	@Override
	public void onRefresh() {
		mPageIndex = 1;
		loadData(mPageIndex);

	}

	@Override
	public void onLoadMore() {
		mPageIndex++;
		loadData(mPageIndex);

	}

	private void loadData(int i) {
		String token = Preferences.getPrefer(getApplicationContext())
				.getString("token", "");
		url = "http://125.46.1.174:8080/5/api/dyxx/list.do?token=" + token;
		VollyHelper.create(getApplication()).addRequest(
				new XiangQingRequest(Method.GET, url,
						new Listener<List<DangYuanXinXiModel>>() {

							@Override
							public void onResponse(List<DangYuanXinXiModel> arg0) {
								// TODO Auto-generated method stub
								// Log.e("ffffffffffffffaaaaaaaaaaaaaaaaaafffffff",
								// arg0 + "");
//
//								if (mPageIndex == 1) {
//									mDangYuanXinXiAdapter.clear();
//								}
//
//								// 设置数据
//								mDangYuanXinXiAdapter.addItems(arg0);
//								// 停止刷新
//								mContentPlv.stopRefresh();
//								// loading
//								mLoadingView.setVisibility(View.GONE);
//
//								mContentPlv.stopLoadMore();
//								// Log.e("-----------", ""+arg0);

							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub

							}
						}), url);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(PUllListViewDangYuanXInXI.this,DetailsActivity.class);
		DangYuanXinXiModel data = mDangYuanXinXiAdapter.getItem(position-1);
		intent.putExtra("data", data);
		startActivity(intent);
	}
}
