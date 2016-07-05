package com.teplot.app.dybc.shezhi.activitys;

import com.teplot.app.dybc.Custom.ClearEditText;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WenTiActivity extends Activity {

	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	public static int RESULT_CODE = 1;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("问题反馈");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private ClearEditText mPhone;
	private ImageView mBtnTianjia;

	private ImageView mIvFirst, mIvSecond, mIvThred, mIvFouth;
	private FrameLayout mFlFirst, mFlSecond, mFlThred, mFlFouth;

	private TextView mTvTiJiao;
	private EditText mEtYiJian;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wenti_fankui);
		initTitle();
		initView();
	}

	private void initView() {
		mPhone = (ClearEditText) findViewById(R.id.cl_phone);
		mBtnTianjia = (ImageView) findViewById(R.id.iv_btn_tianjia);
		mIvFirst = (ImageView) findViewById(R.id.iv_first);
		mIvSecond = (ImageView) findViewById(R.id.iv_second);
		mIvThred = (ImageView) findViewById(R.id.iv_third);
		mIvFouth = (ImageView) findViewById(R.id.iv_fourth);

		mFlFirst = (FrameLayout) findViewById(R.id.fl_first);
		mFlSecond = (FrameLayout) findViewById(R.id.fl_second);
		mFlThred = (FrameLayout) findViewById(R.id.fl_third);
		mFlFouth = (FrameLayout) findViewById(R.id.fl_fourth);
		
		mTvTiJiao = (TextView) findViewById(R.id.tv_tianjiao);
		mEtYiJian = (EditText) findViewById(R.id.et_yijian);
		
		mTvTiJiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + "18538585924" ));
				intent.putExtra("sms_body", mEtYiJian.getText().toString()+"\n"
						+mPhone.getText().toString());
				startActivity(intent);
				Toast.makeText(WenTiActivity.this, "感谢您的意见", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		mBtnTianjia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, RESULT_CODE);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_CODE && resultCode == RESULT_OK
				&& data != null) {
			// 我们选择图片的Uri
			Uri selectImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectImage,
					filePathColumn, null, null, null);
			cursor.moveToNext();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			// 图片路径
			String picturePath = cursor.getString(columnIndex);
			
			
			cursor.close();
			mIvFirst.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			mFlFirst.setVisibility(View.VISIBLE);
//			mIvSecond.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//			mFlSecond.setVisibility(View.VISIBLE);
//			mIvThred.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//			mFlThred.setVisibility(View.VISIBLE);
//			mIvFouth.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//			mFlFouth.setVisibility(View.VISIBLE);
		}
	}
}
