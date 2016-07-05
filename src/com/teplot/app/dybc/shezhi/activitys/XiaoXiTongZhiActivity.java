package com.teplot.app.dybc.shezhi.activitys;

import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class XiaoXiTongZhiActivity extends Activity {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;
	
	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("新消息通知");
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private LinearLayout mGongNeng;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaoxi_tongzhi);
		initTitle();
		mGongNeng = (LinearLayout) findViewById(R.id.ll_gongneng_xiaoxi);
		mGongNeng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(XiaoXiTongZhiActivity.this,GongNengActivity.class);
				startActivity(intent);
			}
		});
	}
}
