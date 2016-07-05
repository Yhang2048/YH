package com.teplot.app.dybc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.trplot.app.dybs.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdressAdapter extends BaseAdapter {

	private Context mContext;
	private List<DangYuanXinXiModel> mData;
	private LayoutInflater inflater;

	public AdressAdapter(Context context) {
		this.mContext = context;
		mData = new ArrayList<DangYuanXinXiModel>();
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public DangYuanXinXiModel getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addItem(DangYuanXinXiModel news) {
		mData.add(news);
		notifyDataSetChanged();
	}

	public void addItems(List<DangYuanXinXiModel> news) {
		mData.addAll(news);
		notifyDataSetChanged();
	}

	public void clear() {
		mData.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoder hoder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_xiangqing, parent,
					false);
			hoder = new ViewHoder();
			hoder.mName = (TextView) convertView.findViewById(R.id.tv_adress_name);
			hoder.mDianHua = (TextView) convertView.findViewById(R.id.tv_adress_num);
			
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		final DangYuanXinXiModel model = getItem(position);
		hoder.mName.setText(model.getName());
		hoder.mDianHua.setText(model.getPager());
		return convertView;
	}

	static class ViewHoder {
		public TextView mName = null;
		public TextView mDianHua = null;

	}

}
