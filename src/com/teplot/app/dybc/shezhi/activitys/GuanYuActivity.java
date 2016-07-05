package com.teplot.app.dybc.shezhi.activitys;

import com.teplot.app.dybc.Custom.UpdateManager;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuanYuActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("掌上红云");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

//	private LinearLayout mWenTi;
	private LinearLayout mJianCha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guanyu);
		initTitle();
		initView();
	}

	private void initView() {
//		mWenTi = (LinearLayout) findViewById(R.id.ll_wenti_fankui);
		mJianCha = (LinearLayout) findViewById(R.id.ll_jiancha_genxin);
//		mWenTi.setOnClickListener(this);
		mJianCha.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.ll_wenti_fankui:
//			Intent intent = new Intent(GuanYuActivity.this,WenTiActivity.class);
//			startActivity(intent);
//			break;
		case R.id.ll_jiancha_genxin:
			new UpdateManager(getApplication()).checkUpdate();
			break;

		default:
			break;
		}

	}

}
