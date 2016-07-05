package com.teplot.app.dybc.firstfragment.activitys;

import com.teplot.app.dybc.firstfragment.kaoping.DangYuangHuPingActivity;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DangYuanKaoPingActivity extends Activity implements OnClickListener{
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("党员考评");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private LinearLayout mLLHuPing, mLLXianShangKaoPing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstfragment_kaoping);
		initTitle();
		initView();
	}

	private void initView() {
		mLLHuPing = (LinearLayout) findViewById(R.id.ll_huiping);
		mLLXianShangKaoPing = (LinearLayout) findViewById(R.id.ll_kaoping);
		
		mLLHuPing.setOnClickListener(this);
		mLLXianShangKaoPing.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_huiping:
			Intent intent = new Intent();
			intent.setClass(this, DangYuangHuPingActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_kaoping:
			Toast toast ;
			toast = Toast.makeText(DangYuanKaoPingActivity.this, "待开发...", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		default:
			break;
		}
	}
}
