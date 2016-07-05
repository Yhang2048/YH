package com.teplot.app.dybc.firstfragment.activitys;

import com.teplot.app.dybc.firstfragment.tongji.JiFenActivity;
import com.teplot.app.dybc.firstfragment.tongji.QianDaoActivity;
import com.teplot.app.dybc.firstfragment.tongji.XueXiActivity;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TongJiChaXunActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("统计查询");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private LinearLayout mLLqiandao, mLLkaoping, mLLhuiyi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstfragment_tongji);
		initTitle();
		initView();
	}

	private void initView() {
		mLLqiandao = (LinearLayout) findViewById(R.id.ll_qiandao);
		mLLkaoping = (LinearLayout) findViewById(R.id.ll_kaoping);
		mLLhuiyi = (LinearLayout) findViewById(R.id.ll_huiyi);

		mLLqiandao.setOnClickListener(this);
		mLLkaoping.setOnClickListener(this);
		mLLhuiyi.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_qiandao:
			Intent intent2 = new Intent();
			intent2.setClass(this, QianDaoActivity.class);
			startActivity(intent2);
			break;
		case R.id.ll_kaoping:
			Intent intent5 = new Intent();
			intent5.setClass(this, JiFenActivity.class);
			startActivity(intent5);
			break;
		case R.id.ll_huiyi:
			Intent intent6 = new Intent();
			intent6.setClass(this, XueXiActivity.class);
			startActivity(intent6);
			break;
		default:
			break;
		}
	}
}
