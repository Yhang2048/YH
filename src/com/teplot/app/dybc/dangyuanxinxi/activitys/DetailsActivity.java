package com.teplot.app.dybc.dangyuanxinxi.activitys;

import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 弃用
 * @author 杨航 
 *
 */
public class DetailsActivity extends Activity {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("详细信息");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	private TextView mtv0, mtv1, mtv2, mtv3, mtv4, mtv5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pulllistview_select);
		initTitle();
		initView();
	}

	private void initView() {
		DangYuanXinXiModel data = (DangYuanXinXiModel) getIntent()
				.getSerializableExtra("data");

		mtv0 = (TextView) findViewById(R.id.xingming);
		mtv1 = (TextView) findViewById(R.id.tv1);
		mtv2 = (TextView) findViewById(R.id.tv2);
		mtv3 = (TextView) findViewById(R.id.tv3);
		mtv4 = (TextView) findViewById(R.id.tv4);
		mtv5 = (TextView) findViewById(R.id.tv5);

		Log.e(getClass().getSimpleName(), "load data");
		if (data != null) {
			mtv0.setText(data.getName());
			mtv1.setText("name:" + data.getSpelling());

			if (data.getGender().toString().equals("M")) {
				mtv2.setText("性别：男");
			} else {
				mtv2.setText("性别：女");
			}
			mtv3.setText("民族：" + data.getNation());
			mtv4.setText("电话:" + data.getPager());
			mtv5.setText("出生日期：" + "\n" + data.getBirthday());
		}
	}
}
