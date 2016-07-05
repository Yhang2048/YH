package com.teplot.app.dybc.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.fragment.adress.ContactAdapter;
import com.teplot.app.dybc.fragment.adress.LetterBar;
import com.teplot.app.dybc.fragment.adress.LetterBar.OnLetterSelectedListener;
import com.teplot.app.dybc.fragment.adress.PinnedSectionListView;
import com.teplot.app.dybc.http.ACache;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.teplot.app.dybc.model.VollyHelper;
import com.teplot.app.dybc.model.XiangQingRequest;
import com.trplot.app.dybs.R;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ManngeFragment extends Fragment {
	private View view;
	private LetterBar lb;
	private TextView tv_overlay;

	private PinnedSectionListView lv_contacts;

	private ContactAdapter adapter;
	private List<DangYuanXinXiModel> contacts = new ArrayList<DangYuanXinXiModel>();
	private String url;

	private List<DangYuanXinXiModel> mModel = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_manage_fragment, container,
				false);
		initView();
		LoadData();
		return view;
	}

	private void initView() {
		lb = (LetterBar) view.findViewById(R.id.lb);
		tv_overlay = (TextView) view.findViewById(R.id.tv_overlay);

		lv_contacts = (PinnedSectionListView) view
				.findViewById(R.id.lv_contacts);
		lv_contacts.setShadowVisible(false);

		adapter = new ContactAdapter(getActivity(), contacts);
		lv_contacts.setAdapter(adapter);

		lb.setOnLetterSelectedListener(new OnLetterSelectedListener() {

			@Override
			public void onLetterSelected(String letter) {
				if (TextUtils.isEmpty(letter)) {
					tv_overlay.setVisibility(View.GONE);
				} else {
					tv_overlay.setVisibility(View.VISIBLE);
					tv_overlay.setText(letter);

					int position = adapter.getLetterPosition(letter);
					if (position != -1) {
						lv_contacts.setSelection(position);
					}
				}
			}
		});
	}

	private void LoadData() {
		String token = Preferences.getPrefer(getActivity()).getString("token",
				"");
		int origId = Preferences.getPrefer(getActivity()).getInt("origId", 0);
		url = Url.AllParty + "token=" + token + "&origId=" + origId;
		VollyHelper.create(getActivity()).addRequest(
				new XiangQingRequest(Method.GET, url,
						new Listener<List<DangYuanXinXiModel>>() {

							@Override
							public void onResponse(List<DangYuanXinXiModel> arg0) {
								mModel = arg0;
								Log.e("mModelmModel", "" + mModel);
								List<DangYuanXinXiModel> mModel = arg0;
								contacts.clear();
								contacts.addAll(mModel);
								adapter.updateList();

								ArrayList<DangYuanXinXiModel> xinXiModels = (ArrayList<DangYuanXinXiModel>) arg0;
								ACache aCache = ACache.get(getActivity());
								aCache.put("adress", xinXiModels,
										ACache.TIME_HOUR);
								Log.e("xinXiModels", "" + xinXiModels);

							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								@SuppressWarnings("unchecked")
								final ArrayList<DangYuanXinXiModel> entities = (ArrayList<DangYuanXinXiModel>) ACache
										.get(getActivity()).getAsObject(
												"adress");
								if (entities!=null) {
								mModel = entities;

								List<DangYuanXinXiModel> mModel = entities;
								contacts.clear();
								contacts.addAll(mModel);
								adapter.updateList();
								}
									Toast.makeText(getActivity(), "没有相关数据",
											Toast.LENGTH_SHORT).show();
								
								
							}
						}), url);

	}
}
