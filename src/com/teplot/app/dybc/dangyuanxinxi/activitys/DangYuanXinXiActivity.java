package com.teplot.app.dybc.dangyuanxinxi.activitys;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.teplot.app.dybc.firstfragment.kaoping.DangYuangHuPingActivity;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.teplot.app.dybc.model.XiangQingRequest;
import com.trplot.app.dybs.R;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 党员信息Activity,弃用
 * @author 杨航
 *
 */
public class DangYuanXinXiActivity extends Activity {
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

	private TextView mtv0, mtv1, mtv2, mtv3, mtv4, mtv5;

	private TextView mJiguan, mXueli, mRudang, mZhuanzheng, mWorkspace,
			mWorktime, mId;

	private List<DangYuanXinXiModel> mData;
	private DangYuanXinXiModel mModel;
	private String Num;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dangyuan_xinxi);
		initTitle();
		initView();
		loadData();
	}

	private void loadData() {

		// 通过本地缓存获得Token
		String token = Preferences.getPrefer(getApplicationContext())
				.getString("token", "");
		url = "http://125.46.1.174:8080/5/api/dyxx/list.do?token=" + token;
		// 通过本地缓存获得用户登录的手机号
		Num = Preferences.getPrefer(getApplicationContext()).getString("pager",
				"");
		VollyHelper.create(getApplicationContext()).addRequest(
				new XiangQingRequest(Method.GET, url,
						new Listener<List<DangYuanXinXiModel>>() {

							@Override
							public void onResponse(List<DangYuanXinXiModel> arg0) {
								mData = arg0;
								setData(mModel);

							}

						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {

							}
						}), url);
	}

	/**
	 * 设置内容
	 * 
	 * @param Data
	 *            添加参数 以备调用
	 */
	public void setData(DangYuanXinXiModel Data) {
		// 得到手机号所对应得json数据

		for (DangYuanXinXiModel model : mData) {
			if (Num.equals(model.getPager())) {
				mModel = model;
			}
		}
		mtv0.setText(mModel.getName());
		
		mtv2.setText("民族：" + mModel.getNation());
		mtv3.setText("电话：" + mModel.getPager());

		if (mModel.getMarital() != null) {
			mtv1.setText("婚配：" + mModel.getMarital());
		} else {
			mtv1.setText("籍贯：无");
		}
		
		if (mModel.getGender().equals("M")) {
			mtv4.setText("性别：男");
		} else {
			mtv4.setText("性别：女");
		}
		Pattern pattern = Pattern
				.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
		Matcher matcher = pattern.matcher(mModel.getBirthday());
		while (matcher.find()) {
			mtv5.setText("出生日期：" + matcher.group());
		}

		if (mModel.getNativeplace() != null) {
			mJiguan.setText("籍贯：" + mModel.getNativeplace());
		} else {
			mJiguan.setText("籍贯：无");
		}

		if (mModel.getDegreeofeducation() != null) {
			mXueli.setText("学历：" + mModel.getDegreeofeducation());
		} else {
			mXueli.setText("学历：无");
		}

		
		if (mModel.getPartytime() != null) {
			Matcher matcher1 = pattern.matcher(mModel.getPartytime());
			while (matcher1.find()) {
				mRudang.setText("入党时间："+ "\n"+"        "+ matcher1.group());
			}
		} else {
			mRudang.setText("入党时间：无");
		}
		
		if (mModel.getPositivetime() != null) {
			Matcher matcher2 = pattern.matcher(mModel.getPositivetime());
			while (matcher2.find()) {
				mZhuanzheng.setText("转正时间：" + "\n"+"        "+ matcher2.group());
			}
		} else {
			mZhuanzheng.setText("转正时间：无");
		}
		if (mModel.getWorkpost() != null) {
			mWorkspace.setText("工作地点：" + "\n" + mModel.getWorkpost());
		}else{
			mWorkspace.setText("工作地点：无");
		}
		
		if (mModel.getWorktime() != null) {
			Matcher matcher3 = pattern.matcher(mModel.getWorktime());
			while (matcher3.find()) {
				mWorktime.setText("工作时间：" + "\n"+"        "+ matcher3.group());
			}
		}else {
			mWorktime.setText("工作时间：无");
		}
		
		if (mModel.getIdNum() != null) {
			mId.setText("身份证：" + mModel.getIdNum());
		} else {
			mId.setText("身份证：无");
		}

	}

	private void initView() {
		mtv0 = (TextView) findViewById(R.id.name);
		mtv1 = (TextView) findViewById(R.id.sname);
		mtv2 = (TextView) findViewById(R.id.sex);
		mtv3 = (TextView) findViewById(R.id.minzu);
		mtv4 = (TextView) findViewById(R.id.phone);
		mtv5 = (TextView) findViewById(R.id.chusheng);

		mJiguan = (TextView) findViewById(R.id.jiguan);
		mXueli = (TextView) findViewById(R.id.xueli);
		mRudang = (TextView) findViewById(R.id.rudang);
		mZhuanzheng = (TextView) findViewById(R.id.zhuanzheng);
		mWorkspace = (TextView) findViewById(R.id.workspace);
		mWorktime = (TextView) findViewById(R.id.worktime);
		mId = (TextView) findViewById(R.id.id);

	}
}
