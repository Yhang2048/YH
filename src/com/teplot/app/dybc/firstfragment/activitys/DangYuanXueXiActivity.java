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

public class DangYuanXueXiActivity extends Activity implements OnClickListener{
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("党员学习");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private LinearLayout mLLShiPin, mLLXueXi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstfragment_xuexi);
		initTitle();
		initView();
	}

	private void initView() {
		mLLShiPin = (LinearLayout) findViewById(R.id.ll_dangjian_shipin);
		mLLXueXi = (LinearLayout) findViewById(R.id.ll_xianshang_xuexi);
		
		mLLShiPin.setOnClickListener(this);
		mLLXueXi.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_dangjian_shipin:
			Toast toast ;
			toast = Toast.makeText(this, "待开发...", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			break;
		case R.id.ll_xianshang_xuexi:
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
