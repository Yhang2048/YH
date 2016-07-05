package com.teplot.app.dybc.firstfragment.baibaoxiang.dangyuanjichu;

import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JiChuZhiShiActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("党员基础知识");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private LinearLayout mLLdanjianzhanlue, mLLdangji, mLLdangzhang, mLLxuexi,
			mLLzilv;
	private FrameLayout mFlDangZhang;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstfragment_baibaoxiang_jichuzhishi);
		initTitle();
		initView();
	}

	private void initView() {
		mLLdanjianzhanlue = (LinearLayout) findViewById(R.id.ll_sixiang);
		mLLdangji = (LinearLayout) findViewById(R.id.ll_dangji);
		mLLdangzhang = (LinearLayout) findViewById(R.id.ll_dangjian);
		mLLxuexi = (LinearLayout) findViewById(R.id.ll_xuexi);
		mLLzilv = (LinearLayout) findViewById(R.id.ll_zilv);

		mLLdanjianzhanlue.setOnClickListener(this);
		mLLdangji.setOnClickListener(this);
		mLLdangzhang.setOnClickListener(this);
		mLLxuexi.setOnClickListener(this);
		mLLzilv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_sixiang:
			Intent intent = new Intent(JiChuZhiShiActivity.this,
					DangjianZhanlueActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_dangji:
			Intent intent1 = new Intent(JiChuZhiShiActivity.this,
					DangjiActivity.class);
			startActivity(intent1);
			break;
		case R.id.ll_dangjian:
			Intent intent2 = new Intent(JiChuZhiShiActivity.this,
					DangjianActivity.class);
			startActivity(intent2);
			break;
		case R.id.ll_xuexi:
			Intent intent3 = new Intent(JiChuZhiShiActivity.this,
					XuexiActivity.class);
			startActivity(intent3);
			break;
		case R.id.ll_zilv:
			Intent intent4 = new Intent(JiChuZhiShiActivity.this,
					ZilvActivity.class);
			startActivity(intent4);
			break;

		default:
			break;
		}
	}
}
