package com.teplot.app.dybc.shezhi.activitys;

import com.trplot.app.dybs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GongNengActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("功能消息免打扰");
		mSheZhi.setText("新消息通知");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private LinearLayout mLLKaiQi, mLLYeJian, mLLClose;
	private ImageView mIvKaiQi, mIvYeJian, mIvClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gongneng_xiaoxi);
		initTitle();
		initView();
	}

	private void initView() {
		mLLKaiQi = (LinearLayout) findViewById(R.id.ll_kaiqi);
		mLLYeJian = (LinearLayout) findViewById(R.id.ll_yejian);
		mLLClose = (LinearLayout) findViewById(R.id.ll_close);

		mIvKaiQi = (ImageView) findViewById(R.id.iv_kaiqi);
		mIvYeJian = (ImageView) findViewById(R.id.iv_yejian_kaiqi);
		mIvClose = (ImageView) findViewById(R.id.iv_close);

		mLLKaiQi.setOnClickListener(this);
		mLLYeJian.setOnClickListener(this);
		mLLClose.setOnClickListener(this);
	}
	private boolean visibility_Flag = false;
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.ll_kaiqi:
			if (visibility_Flag) {
				mIvKaiQi.setVisibility(View.INVISIBLE);
				visibility_Flag = false;
			} else {
				mIvKaiQi.setVisibility(View.VISIBLE);
				visibility_Flag = true;
			}
			
			break;
		case R.id.ll_yejian:
			if (visibility_Flag) {
				mIvYeJian.setVisibility(View.INVISIBLE);
				visibility_Flag = false;
			} else {
				mIvYeJian.setVisibility(View.VISIBLE);
				visibility_Flag = true;
			}
			
			break;

		case R.id.ll_close:
			if (visibility_Flag) {
				mIvClose.setVisibility(View.INVISIBLE);
				visibility_Flag = false;
			} else {
				mIvClose.setVisibility(View.VISIBLE);
				visibility_Flag = true;
			}
			
			break;

		default:
			break;
		}
	}
}
