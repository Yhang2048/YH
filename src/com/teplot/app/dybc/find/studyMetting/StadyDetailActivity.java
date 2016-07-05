package com.teplot.app.dybc.find.studyMetting;

import com.trplot.app.dybs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class StadyDetailActivity extends Activity {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mSheZhi.setText("");
		mTitle.setText("党员信息");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	
	private TextView mType,mTheme,mStudytime,mCheckintime,mCheckouttime,mContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study_select);
		initTitle();
		initView();
	}

	private void initView() {
		MettingModel model = (MettingModel) getIntent().getSerializableExtra("data");
		
		mType = (TextView) findViewById(R.id.study_select_type);
		mTheme = (TextView) findViewById(R.id.study_select_theme);
		mStudytime = (TextView) findViewById(R.id.study_select_studytime);
		mCheckintime = (TextView) findViewById(R.id.study_select_checkintime);
		mCheckouttime = (TextView) findViewById(R.id.study_select_checkouttime);
		mContent = (TextView) findViewById(R.id.study_select_content);
		
		
		if (model.getType() == 1) {
			mType.setText("类别：学习");
		}else if (model.getType() == 2) {
			mType.setText("类别：活动");
		}else if (model.getType() == 3) {
			mType.setText("类别：会议");
		}
//		mType.setText("类别："+model.getType());
		mTheme.setText("主题："+model.getTheme());
		
		
		mStudytime.setText("时间："+model.getStudydate().split(" ")[0]+"  "+model.getStudytime1()+"-"+model.getStudytime2());
		mCheckintime.setText("开始签到时间："+model.getCheckintime1()+"-"+model.getCheckintime2());
		mCheckouttime.setText("结束签到时间："+model.getCheckouttime1()+"-"+model.getCheckouttime2());
		mContent.setText("内容："+model.getContent());
		
	}
}
