package com.teplot.app.dybc.firstfragment.activitys;

import com.trplot.app.dybs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GongShiGongKaiActivity extends Activity implements OnClickListener{
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("公示公开");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private LinearLayout mLLPaiMing, mLLDangWu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstfragment_gongshi);
		initTitle();
		initView();
	}

	private void initView() {
		mLLPaiMing = (LinearLayout) findViewById(R.id.ll_paiming);
		mLLDangWu = (LinearLayout) findViewById(R.id.ll_dangwu_gongkai);
		
		mLLPaiMing.setOnClickListener(this);
		mLLDangWu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_paiming:
			Toast toast ;
			toast = Toast.makeText(this, "待开发...", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.ll_dangwu_gongkai:
			Toast toast1 ;
			toast1 = Toast.makeText(this, "待开发...", Toast.LENGTH_SHORT);
			toast1.setGravity(Gravity.CENTER, 0, 0);
			toast1.show();
			break;
		default:
			break;
		}
	}
}
