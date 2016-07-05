package com.teplot.app.dybc.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.firstfragment.activitys.BaibaoXiangActivity;
import com.teplot.app.dybc.firstfragment.activitys.DangYuanKaoPingActivity;
import com.teplot.app.dybc.firstfragment.activitys.DangYuanXueXiActivity;
import com.teplot.app.dybc.firstfragment.activitys.FengCaiZhanShiActivity;
import com.teplot.app.dybc.firstfragment.activitys.GongShiGongKaiActivity;
import com.teplot.app.dybc.firstfragment.activitys.TongJiChaXunActivity;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.teplot.app.dybc.model.XiangQingRequest;
import com.trplot.app.dybs.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstFragment extends Fragment implements OnClickListener {
	private LinearLayout mTongXun, mDangYuanXinXi, mLLyuchuanXianFeng,
			mLLdangyuanFuwu, mLLTongjiChaxun, mLLJiFen;

	private View view;
	private TextView mName;
	protected static final int REQUEST_CONTACT = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_first_fragment, container,
				false);
		initView();
		loadData();
		return view;
	}

	private void initView() {
		mDangYuanXinXi = (LinearLayout) view
				.findViewById(R.id.ll_tongji_chaxun);
		mTongXun = (LinearLayout) view.findViewById(R.id.ll_baibaoxiang);
		mLLyuchuanXianFeng = (LinearLayout) view
				.findViewById(R.id.ll_dangyuan_kaoping);
		mLLdangyuanFuwu = (LinearLayout) view
				.findViewById(R.id.ll_gongshi_gongkai);
		mLLTongjiChaxun = (LinearLayout) view
				.findViewById(R.id.ll_fengcai_zhanshi);
		mLLJiFen = (LinearLayout) view.findViewById(R.id.ll_duangyuan_xuexi);
		mName = (TextView) view.findViewById(R.id.firstfragment_name);

		mTongXun.setOnClickListener(this);
		mDangYuanXinXi.setOnClickListener(this);
		mLLyuchuanXianFeng.setOnClickListener(this);
		mLLdangyuanFuwu.setOnClickListener(this);
		mLLTongjiChaxun.setOnClickListener(this);
		mLLJiFen.setOnClickListener(this);
	}

	private List<DangYuanXinXiModel> mData;
	private DangYuanXinXiModel mModel;
	private String Num;
	private String url;

	private void loadData() {

		// 通过本地缓存获得Token
		String token = Preferences.getPrefer(getActivity()).getString("token",
				"");
		int origId = Preferences.getPrefer(getActivity()).getInt("origId", 0);
		url = Url.AllParty + "token=" + token + "&origId=" + origId;
		// 通过本地缓存获得用户登录的手机号
		Num = Preferences.getPrefer(getActivity()).getString("account", "");

		VollyHelper.create(getActivity()).addRequest(
				new XiangQingRequest(Method.GET, url,
						new Listener<List<DangYuanXinXiModel>>() {

							@Override
							public void onResponse(List<DangYuanXinXiModel> arg0) {
								mData = arg0;
								setData(arg0);
								 ArrayList<DangYuanXinXiModel> xinXiModels =
								 (ArrayList<DangYuanXinXiModel>) arg0;
								 ACache aCache = ACache.get(getActivity());
								 aCache.put("firstFName",
								 xinXiModels,ACache.TIME_HOUR);
							}

						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								 @SuppressWarnings("unchecked")
								 final ArrayList<DangYuanXinXiModel> entities
								 = (ArrayList<DangYuanXinXiModel>) ACache
								 .get(getActivity()).getAsObject(
								 "firstFName");
								 if (entities!=null) {
									 setData(entities);
								}
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
			mName.setText("— — — " + mModel.getName());
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tongji_chaxun:
			Intent intent0 = new Intent();
			intent0.setClass(getActivity(), TongJiChaXunActivity.class);
			startActivity(intent0);
			break;
		case R.id.ll_baibaoxiang:
			Intent intent6 = new Intent();
			intent6.setClass(getActivity(), BaibaoXiangActivity.class);
			startActivity(intent6);
			break;
		case R.id.ll_dangyuan_kaoping:
			Intent intent2 = new Intent();
			intent2.setClass(getActivity(), DangYuanKaoPingActivity.class);
			startActivity(intent2);
			break;

		case R.id.ll_gongshi_gongkai:
			Intent intent3 = new Intent();
			intent3.setClass(getActivity(), GongShiGongKaiActivity.class);
			startActivity(intent3);
			break;

		case R.id.ll_fengcai_zhanshi:
			Intent intent4 = new Intent();
			intent4.setClass(getActivity(), FengCaiZhanShiActivity.class);
			startActivity(intent4);
			break;
		case R.id.ll_duangyuan_xuexi:
			Intent intent5 = new Intent();
			intent5.setClass(getActivity(), DangYuanXueXiActivity.class);
			startActivity(intent5);
			break;
		}
	}
}
