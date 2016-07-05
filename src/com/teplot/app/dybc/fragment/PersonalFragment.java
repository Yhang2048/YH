package com.teplot.app.dybc.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.teplot.app.dybc.Custom.DialogBack;
import com.teplot.app.dybc.Custom.DialogPhoto;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.firstfragment.fengcai.Bimp;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.HttpAsyncTask;
import com.teplot.app.dybc.http.NetUtil;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.DengLuModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.teplot.app.dybc.model.XiangQingRequest;
import com.teplot.app.dybc.shezhi.activitys.GuanYuActivity;
import com.teplot.app.dybc.shezhi.activitys.XiaoXiTongZhiActivity;
import com.teplot.app.dybc.shezhi.activitys.XinXiCaiJiActivity;
import com.teplot.app.dybc.shezhi.activitys.ZhuangHuAnQuanActivity;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalFragment extends Fragment implements OnClickListener {
	private View view;

	private DangYuanXinXiModel mModel;
	private String Num;
	private String url;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_shezhi, container, false);
		initView();
		NetUtil netUtil = new NetUtil();
		Boolean isNetWork = netUtil.isNetworkAvalible(getActivity());
		if (isNetWork) {
			loadData();
		} else {
			Toast.makeText(getActivity(), "网络连接不可用", Toast.LENGTH_SHORT).show();
		}
		return view;
	}

	private RelativeLayout mRLTop, mRLAboutMe;
	private LinearLayout mLLXiaoXi, mLLXinXi;
	private TextView mTvBack, mTvBanBenMing;
	private TextView mName, mPhone;
	private ImageView iv_user;

	private void initView() {

		mRLTop = (RelativeLayout) view.findViewById(R.id.top);
		mLLXiaoXi = (LinearLayout) view.findViewById(R.id.ll_xiaoxi);
		mLLXinXi = (LinearLayout) view.findViewById(R.id.ll_xinxi);
		mRLAboutMe = (RelativeLayout) view.findViewById(R.id.ll_about_me);
		mTvBack = (TextView) view.findViewById(R.id.tv_back);
		mName = (TextView) view.findViewById(R.id.name);
		mPhone = (TextView) view.findViewById(R.id.callphone);
		iv_user = (ImageView) view.findViewById(R.id.iv_user);

		// 获取版本名
		mTvBanBenMing = (TextView) view.findViewById(R.id.tv_banbenming);
		mTvBanBenMing.setText("V." + getVersionName());

		mRLTop.setOnClickListener(this);
		mLLXiaoXi.setOnClickListener(this);
		mRLAboutMe.setOnClickListener(this);
		mTvBack.setOnClickListener(this);
		mLLXinXi.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		// 同步图片
		String photo = Preferences.getPrefer(getActivity()).getString(
				"picturePath", "");
		try {
			Bitmap b = Bimp.revitionImageSize(photo);
			iv_user.setImageBitmap(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadData() {

		// 通过本地缓存获得Token
		String token = Preferences.getPrefer(getActivity()).getString("token",
				"");
		int origId = Preferences.getPrefer(getActivity()).getInt("origId", 0);
		url = Url.AllParty + "token=" + token + "&origId=" + origId;
		Log.e("urlurlurl", "" + url);
		// 通过本地缓存获得用户登录的手机号
		Num = Preferences.getPrefer(getActivity()).getString("account", "");
		VollyHelper.create(getActivity()).addRequest(
				new XiangQingRequest(Method.GET, url,
						new Listener<List<DangYuanXinXiModel>>() {

							@Override
							public void onResponse(List<DangYuanXinXiModel> arg0) {
								setData(arg0);
								ArrayList<DangYuanXinXiModel> xinXiModels = (ArrayList<DangYuanXinXiModel>) arg0;
								ACache aCache = ACache.get(getActivity());
								aCache.put("Personal", xinXiModels,
										ACache.TIME_HOUR);
							}

						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								@SuppressWarnings("unchecked")
								final ArrayList<DangYuanXinXiModel> entities = (ArrayList<DangYuanXinXiModel>) ACache
										.get(getActivity()).getAsObject(
												"Personal");
								if (entities!=null) {
									setData(entities);
								}
									Toast.makeText(getActivity(), "没有相关数据",
											Toast.LENGTH_SHORT).show();
								
							}
						}), url);

	}

	protected void setData(List<DangYuanXinXiModel> Data) {
		// 得到手机号所对应得json数据

		for (DangYuanXinXiModel model : Data) {
			if (Num.equals(model.getPager())) {
				mModel = model;
			}
		}
		Log.e("mModel", "" + mModel);
		if (mModel != null) {
			mName.setText(mModel.getName());
			mPhone.setText("电话：" + mModel.getPager());
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top:
			Intent intent = new Intent();
			intent.setClass(getActivity(), ZhuangHuAnQuanActivity.class);
			intent.putExtra("mModel", mModel);
			startActivity(intent);
			break;
		case R.id.ll_xiaoxi:
			Intent intent1 = new Intent();
			intent1.setClass(getActivity(), XiaoXiTongZhiActivity.class);
			startActivity(intent1);
			break;
		case R.id.ll_about_me:
			Intent intent2 = new Intent();
			intent2.setClass(getActivity(), GuanYuActivity.class);
			startActivity(intent2);
			break;
		case R.id.tv_back:
			DialogBack dialogBack = new DialogBack(getActivity());
			dialogBack.show();
			break;
		case R.id.ll_xinxi:
			Intent intent3 = new Intent();
			intent3.setClass(getActivity(), XinXiCaiJiActivity.class);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}

	private String getVersionName() {
		try {
			// 获取packagemanager的实例
			PackageManager packageManager = getActivity().getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			PackageInfo packInfo = packageManager.getPackageInfo(getActivity()
					.getPackageName(), 0);
			String version = packInfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取Android系统的版本号
	 * 
	 * @return
	 */

	public static int getAndroidSDKVersion() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return version;
	}

}
