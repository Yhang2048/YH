package com.teplot.app.dybc.shezhi.activitys;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.teplot.app.dybc.DengluActivity;
import com.teplot.app.dybc.Custom.DialogPhoto;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.firstfragment.fengcai.Bimp;
import com.teplot.app.dybc.http.HttpAsyncTask;
import com.teplot.app.dybc.http.NetUtil;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.DengLuModel;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ZhuangHuAnQuanActivity extends Activity implements OnClickListener {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("账号与安全");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private RelativeLayout mTouXiang;
	private ImageView mIvUser;
	private TextView mDetailName, mDetailjiguan, mDetailMinZu, mDetailPhone,
			mDetailSex, mDetailxueli, mDetailchusheng, mDetailrudang,
			mDetailzhuangzheng, mDetailSpace, mDetailTime, mDetailId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhanghu_anquan);
		initTitle();
		initView();

	}

	private void initView() {
		mTouXiang = (RelativeLayout) findViewById(R.id.rl_top);
		mIvUser = (ImageView) findViewById(R.id.iv_user);

		mDetailName = (TextView) findViewById(R.id.detail_name);
		mDetailjiguan = (TextView) findViewById(R.id.detail_jiguan);
		mDetailMinZu = (TextView) findViewById(R.id.detail_minzu);
		mDetailPhone = (TextView) findViewById(R.id.detail_num);
		mDetailSex = (TextView) findViewById(R.id.detail_sex);
		mDetailxueli = (TextView) findViewById(R.id.detail_xueli);
		mDetailchusheng = (TextView) findViewById(R.id.detail_shengri);
		mDetailrudang = (TextView) findViewById(R.id.detail_rudang);
		mDetailzhuangzheng = (TextView) findViewById(R.id.detail_zhuangzheng);
		mDetailSpace = (TextView) findViewById(R.id.detail_space);
		mDetailTime = (TextView) findViewById(R.id.detail_worktime);
		mDetailId = (TextView) findViewById(R.id.detail_id);

		mTouXiang.setOnClickListener(this);
		setData();

		String photo = Preferences.getPrefer(getApplication()).getString(
				"picturePath", "");
		try {
			Bitmap b = Bimp.revitionImageSize(photo);
			mIvUser.setImageBitmap(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setData() {
		// TODO Auto-generated method stub
		DangYuanXinXiModel model = (DangYuanXinXiModel) getIntent()
				.getSerializableExtra("mModel");
if (model!=null) {

		mDetailName.setText(model.getName());

		mDetailMinZu.setText(model.getNation());
		mDetailPhone.setText(model.getPager());

		if (model.getGender().equals("M")) {
			mDetailSex.setText("男");
		} else {
			mDetailSex.setText("女");
		}
		Pattern pattern = Pattern
				.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
		Matcher matcher = pattern.matcher(model.getBirthday());
		while (matcher.find()) {
			mDetailchusheng.setText(matcher.group());
		}

		if (model.getNativeplace() != null) {
			mDetailjiguan.setText(model.getNativeplace());
		} else {
			mDetailjiguan.setText("无");
		}

		if (model.getDegreeofeducation() != null&&model.getDegreeofeducation()!="") {
			mDetailxueli.setText(model.getDegreeofeducation());
		} else {
			mDetailxueli.setText("无");
		}

		if (model.getPartytime() != null) {
			Matcher matcher1 = pattern.matcher(model.getPartytime());
			while (matcher1.find()) {
				mDetailrudang.setText(matcher1.group());
			}
		} else {
			mDetailrudang.setText("无");
		}

		if (model.getPositivetime() != null) {
			Matcher matcher2 = pattern.matcher(model.getPositivetime());
			while (matcher2.find()) {
				mDetailzhuangzheng.setText(matcher2.group());
			}
		} else {
			mDetailzhuangzheng.setText("无");
		}
		if (model.getWorkpost() != null&&model.getWorkpost()!="") {
			mDetailSpace.setText(model.getWorkpost());
		} else {
			mDetailSpace.setText("无");
		}

		if (model.getWorktime() != null) {
			Matcher matcher3 = pattern.matcher(model.getWorktime());
			while (matcher3.find()) {
				mDetailTime.setText(matcher3.group());
			}
		} else {
			mDetailTime.setText("无");
		}

		if (model.getIdNum() != null) {
			mDetailId.setText(model.getIdNum());
		} else {
			mDetailId.setText("无");
		}
		
	}else {
		NetUtil netUtil = new NetUtil();
		@SuppressWarnings("static-access")
		Boolean isNetWork = netUtil
				.isNetworkAvalible(getApplicationContext());
		if (isNetWork) {
		String account =Preferences.getPrefer(getApplicationContext()).getString("account", "");
		String password =Preferences.getPrefer(getApplicationContext()).getString("password", "");
		login(account, password);
		} else {
			Toast.makeText(ZhuangHuAnQuanActivity.this, "网络连接不可用",
					Toast.LENGTH_SHORT).show();
		}
	 }
	}
	private void login(String account, String passwd) {
		String httpUrl = Url.LogIn;
		Map<String, String> params = new HashMap<>();
		params.put("account", account);
		params.put("password", passwd);
		HttpAsyncTask http = new HttpAsyncTask() {
			@Override
			public void onStart(String taskId) {
				// show dialog
			}

			@Override
			public void onFinish(String taskId, String response) {
				DengLuModel loginInfo = new Gson().fromJson(response,
						DengLuModel.class);
				if (loginInfo != null && loginInfo.isSucces()) {
					// login success
				} else {
					// login failure
					// isSuc = new Boolean(false);
				}
			}
		};
		http.excute(httpUrl, params);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_top:
			DialogPhoto dialogPhoto = new DialogPhoto(
					ZhuangHuAnQuanActivity.this, this);
			dialogPhoto.show();
			break;
		}
	}

	public static int RESULT_CODE = 1;

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
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			// 图片路径
			String picturePath = cursor.getString(columnIndex);
			Preferences.getPrefer(getApplicationContext()).putString(
					"picturePath", picturePath);
			try {
				Bitmap b = Bimp.revitionImageSize(picturePath);
				mIvUser.setImageBitmap(b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cursor.close();
		}
	}

}
