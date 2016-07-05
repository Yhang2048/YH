package com.teplot.app.dybc.firstfragment.kaoping;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.baidu.platform.comapi.map.s;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.find.SignIn.SingInModel;
import com.teplot.app.dybc.find.studyMetting.MettingModel;
import com.teplot.app.dybc.firstfragment.tongji.QianDaoActivity;
import com.teplot.app.dybc.firstfragment.tongji.XueXiActivity;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.HttpAsyncTask;
import com.teplot.app.dybc.http.NetUtil;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.DengLuModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.teplot.app.dybc.model.XiangQingRequest;
import com.trplot.app.dybs.R;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnContextClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DangYuangHuPingActivity extends Activity {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("党员互评");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private RadioGroup radioGroup;
	private TextView mTvLast, mTvNext, mTvName;
	private RadioButton mYouxiu, mLiang, mYiban, mCha;
	private Map<String, DnpjRecord> mMap = new HashMap<String, DnpjRecord>();

	private LinkedList<DnpjRecord> list = new LinkedList<DnpjRecord>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tongji_chaxun);
		initTitle();
		initView();
		NetUtil netUtil = new NetUtil();
		@SuppressWarnings("static-access")
		Boolean isNetWork = netUtil
				.isNetworkAvalible(DangYuangHuPingActivity.this);
		if (isNetWork) {
			initData();
		} else {
			Toast.makeText(DangYuangHuPingActivity.this, "网络连接不可用",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.huping_radioGroup);
		mTvLast = (TextView) findViewById(R.id.tv_last);
		mTvNext = (TextView) findViewById(R.id.tv_next);
		mTvName = (TextView) findViewById(R.id.tv_name);
		mYouxiu = (RadioButton) findViewById(R.id.huping_radio_you);
		mLiang = (RadioButton) findViewById(R.id.huping_radio_liang);
		mYiban = (RadioButton) findViewById(R.id.huping_radio_yiban);
		mCha = (RadioButton) findViewById(R.id.huping_radio_cha);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// TODO Auto-generated method stub
				if (checkedId == mYouxiu.getId()) {
					Log.e("checkedId", "" + checkedId);
					Toast.makeText(DangYuangHuPingActivity.this, "您选择了优秀",
							Toast.LENGTH_SHORT).show();

				} else if (checkedId == mLiang.getId()) {
					Toast.makeText(DangYuangHuPingActivity.this, "您选择了良好",
							Toast.LENGTH_SHORT).show();
				} else if (checkedId == mYiban.getId()) {
					Toast.makeText(DangYuangHuPingActivity.this, "您选择了一般",
							Toast.LENGTH_SHORT).show();
				} else if (checkedId == mCha.getId()) {
					Toast.makeText(DangYuangHuPingActivity.this, "您选择了差",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		mTvLast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (i == 0) {

					Toast.makeText(DangYuangHuPingActivity.this, "已是第一个",
							Toast.LENGTH_SHORT).show();
				} else if (i <= NameList.size() && i > 0) {
					BigDecimal f = list.getLast().getScore();
					int score = f.intValue();
					if (score == 100) {
						mYouxiu.setChecked(true);
					} else if (score == 80) {
						mLiang.setChecked(true);
					} else if (score == 60) {
						mYiban.setChecked(true);
					} else if (score == 0) {
						mCha.setChecked(true);
					}
					list.removeLast();
					i--;
					mTvName.setText(NameList.get(i));
					mTvNext.setText("下一个");
				}
			}
		});
		mTvNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (i < NameList.size()) {
					if (mMap.get(NameList.get(i)) == null) {
						DangYuanXinXiModel dangYuanXinXiModel = mModels.get(i);
						// Log.e("mModels", "" + mModels.toString());
						Log.e("", "");
						String pager = Preferences.getPrefer(
								getApplicationContext()).getString("account",
								"");
						int id = Preferences.getPrefer(getApplicationContext())
								.getInt("id", 0);
						DnpjRecord mDnpjRecord = new DnpjRecord();
						mDnpjRecord.setScore(ListPut());
						mDnpjRecord.setPjMobile(pager);
						mDnpjRecord.setPjUserId(id);
						mDnpjRecord.setUserId(dangYuanXinXiModel.getId());
						// mDnpjRecord.setId(dangYuanXinXiModel.getId());
						mDnpjRecord.setMobile(dangYuanXinXiModel.getPager());
						// mDnpjRecord.setPjMobile();
						Log.e("setId", "" + dangYuanXinXiModel.getId());
						Log.e("dangYuanXinXiModel.getPager()", ""
								+ dangYuanXinXiModel.getPager());

						// if (list.get(i).getPjUserId()!=null) {
						list.add(mDnpjRecord);
						// }
						i++;

					} else {
						// mMap.get(NameList.get(i)).setScore(ListPut());

						i++;
						mTvName.setText(NameList.get(i));

					}

				}
				if (i < NameList.size()) {
					mTvName.setText(NameList.get(i));
				}
				if (i == NameList.size() - 1) {
					Log.e("NameList.size()", "" + NameList.size());
					Log.e("i", "" + i);
					mTvNext.setText("保存并提交");
				}
				if (NameList.size() == i) {
					Log.e("idddd", "" + i);
					Gson gson = new Gson();
					String gsonlist = gson.toJson(list);
					String token = Preferences.getPrefer(
							getApplicationContext()).getString("token", "");
					Log.e("gsonlist", "" + gsonlist);
					PingJia(token, gsonlist);
				}

			}

		});

	}

	private BigDecimal ListPut() {
		BigDecimal bigDecimal = null;
		int redioid = radioGroup.getCheckedRadioButtonId();
		switch (redioid) {
		case R.id.huping_radio_you:
			bigDecimal = new BigDecimal(100);
			break;
		case R.id.huping_radio_liang:
			bigDecimal = new BigDecimal(80);
			break;
		case R.id.huping_radio_yiban:
			bigDecimal = new BigDecimal(60);
			break;
		case R.id.huping_radio_cha:
			bigDecimal = new BigDecimal(0);
			break;

		default:

			break;
		}
		return bigDecimal;

	}

	private int i = 0;
	private String url;
	private String Name;
	private List<String> NameList = new ArrayList<>();
	private List<DangYuanXinXiModel> mModels;

	private void initData() {

		String token = Preferences.getPrefer(getApplicationContext())
				.getString("token", "");
		int origId = Preferences.getPrefer(this).getInt("origId", 0);
		url = Url.AllParty + "token=" + token + "&origId=" + origId;
		VollyHelper.create(getApplication()).addRequest(
				new XiangQingRequest(Method.GET, url,
						new Listener<List<DangYuanXinXiModel>>() {

							@Override
							public void onResponse(List<DangYuanXinXiModel> arg0) {

								int id = Preferences.getPrefer(
										getApplicationContext())
										.getInt("id", 0);
								// 迭代清除相同数据项
								Iterator<DangYuanXinXiModel> iter = arg0
										.iterator();
								while (iter.hasNext()) {
									DangYuanXinXiModel a = iter.next();

									if (id == a.getId()) {
										iter.remove();
									}
									Log.e("idid", "" + id);
									Log.e("iter.next().getId()", "" + a.getId());
								}
								mModels = arg0;

								for (int j = 0; j < arg0.size(); j++) {
									Name = arg0.get(j).getName();
									NameList.add(Name);
								}
								mTvName.setText(NameList.get(0));

								ArrayList<DangYuanXinXiModel> xinXiModels = (ArrayList<DangYuanXinXiModel>) arg0;
								ACache aCache = ACache
										.get(DangYuangHuPingActivity.this);
								aCache.put("DangyuanHP", xinXiModels,
										ACache.TIME_HOUR);
							}

						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								@SuppressWarnings("unchecked")
								final ArrayList<DangYuanXinXiModel> dangYuanXinXiModels = (ArrayList<DangYuanXinXiModel>) ACache
										.get(DangYuangHuPingActivity.this)
										.getAsObject("DangyuanHP");
								if (dangYuanXinXiModels != null) {
									int id = Preferences.getPrefer(
											getApplicationContext()).getInt(
											"id", 0);
									// 迭代清除相同数据项
									Iterator<DangYuanXinXiModel> iter = dangYuanXinXiModels
											.iterator();
									while (iter.hasNext()) {
										DangYuanXinXiModel a = iter.next();

										if (id == a.getId()) {
											iter.remove();
										}
										Log.e("idid", "" + id);
										Log.e("iter.next().getId()",
												"" + a.getId());
									}
									mModels = dangYuanXinXiModels;

									for (int j = 0; j < dangYuanXinXiModels
											.size(); j++) {
										Name = dangYuanXinXiModels.get(j)
												.getName();
										NameList.add(Name);
									}
									mTvName.setText(NameList.get(0));
								}
								Toast.makeText(DangYuangHuPingActivity.this,
										"没有相关数据", Toast.LENGTH_SHORT).show();
							}
						}), url);

	}

	private void PingJia(String token, String array) {
		Log.e("wwww", "wwwwww");
		String httpUrl = Url.PingJia;
		Map<String, String> params = new HashMap<>();
		params.put("token", token);
		params.put("jsonList", array);
		HttpAsyncTask http = new HttpAsyncTask() {
			@Override
			public void onStart(String taskId) {
				// show dialog
			}

			@Override
			public void onFinish(String taskId, String response) {
				PingJiaModel loginInfo = new Gson().fromJson(response,
						new TypeToken<PingJiaModel>() {
						}.getType());
				if (loginInfo != null && loginInfo.isSucces()) {
					// login success
					String code = loginInfo.getCode().toString();
					String msg = loginInfo.getMsg();
					Toast.makeText(DangYuangHuPingActivity.this, msg,
							Toast.LENGTH_SHORT).show();
				} else {
					// login failure
					String code = loginInfo.getCode().toString();
					Log.e("codecode", "" + code);
					String msg = loginInfo.getMsg();
					Toast.makeText(DangYuangHuPingActivity.this, msg,
							Toast.LENGTH_SHORT).show();

				}
			}
		};

		http.excute(httpUrl, params, "POST");
	}

}
