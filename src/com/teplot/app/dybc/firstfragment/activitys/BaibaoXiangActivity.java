package com.teplot.app.dybc.firstfragment.activitys;

import com.teplot.app.dybc.firstfragment.activitys.FengCaiZhanShiActivity.MyLocationListener;
import com.teplot.app.dybc.firstfragment.baibaoxiang.DangZhangActivity;
import com.teplot.app.dybc.firstfragment.baibaoxiang.LiangXueYiZuoActivity;
import com.teplot.app.dybc.firstfragment.baibaoxiang.dangyuanjichu.JiChuZhiShiActivity;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BaibaoXiangActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("百宝箱");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private LinearLayout mLLDangYuanFanZhan, mLLDangYuanJiChu, mLLDangZhiBu,
			mLLDangWei, mLLLiangXue;
	private FrameLayout mFlDangZhang;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstfragment_baibaoxiang);
		initTitle();
		initView();
	}

	private void initView() {
		mLLDangYuanFanZhan = (LinearLayout) findViewById(R.id.ll_dangyuan_fazhan);
		mLLDangYuanJiChu = (LinearLayout) findViewById(R.id.ll_dangyuan);
		mLLDangZhiBu = (LinearLayout) findViewById(R.id.ll_dangzhibu);
		mLLDangWei = (LinearLayout) findViewById(R.id.ll_dangwei);
		mLLLiangXue = (LinearLayout) findViewById(R.id.ll_liangxue);
		mFlDangZhang = (FrameLayout) findViewById(R.id.fl_dangzhang);

		mLLDangYuanFanZhan.setOnClickListener(this);
		mLLDangYuanJiChu.setOnClickListener(this);
		mLLDangZhiBu.setOnClickListener(this);
		mLLDangWei.setOnClickListener(this);
		mLLLiangXue.setOnClickListener(this);
		mFlDangZhang.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_dangyuan_fazhan:
			Toast toast;
			toast = Toast.makeText(this, "待开发...", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.ll_dangyuan:
			Intent intent3 = new Intent(BaibaoXiangActivity.this,
					JiChuZhiShiActivity.class);
			startActivity(intent3);
			break;
		case R.id.ll_dangzhibu:
			Toast toast2;
			toast2 = Toast.makeText(this, "待开发...", Toast.LENGTH_SHORT);
			toast2.setGravity(Gravity.CENTER, 0, 0);
			toast2.show();
			break;
		case R.id.ll_dangwei:
			Toast toast3;
			toast3 = Toast.makeText(this, "待开发...", Toast.LENGTH_SHORT);
			toast3.setGravity(Gravity.CENTER, 0, 0);
			toast3.show();
			break;
		case R.id.ll_liangxue:
			Intent intent2 = new Intent(BaibaoXiangActivity.this,
					LiangXueYiZuoActivity.class);
			startActivity(intent2);
			break;
		case R.id.fl_dangzhang:
			Intent intent = new Intent(BaibaoXiangActivity.this,
					DangZhangActivity.class);
			startActivity(intent);
			break;
		}
	}
}
