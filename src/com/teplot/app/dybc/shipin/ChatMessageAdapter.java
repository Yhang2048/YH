package com.teplot.app.dybc.shipin;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

import com.teplot.app.dybc.shipin.ChatMessage.Type;
import com.trplot.app.dybs.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMessageAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ChatMessage> mData;

	public ChatMessageAdapter(Context context, List<ChatMessage> Data) {
		mInflater = LayoutInflater.from(context);
		mData = Data;

	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public ChatMessage getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * getItemViewType和getViewTypeCount的复写是因为有多个Item
	 */
	@Override
	public int getItemViewType(int position) {
		ChatMessage chatMessage = mData.get(position);
		if (chatMessage.getType() == Type.INCOMING) {
			return 0;
		}

		return 1;
	}

	@Override
	public int getViewTypeCount() {

		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMessage chatMessage = mData.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			//通过ItemViewType设置不同的布局
			if (getItemViewType(position) == 0) {
				convertView = mInflater.inflate(R.layout.item_from_msg, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView
						.findViewById(R.id.id_form_msg_date);
				viewHolder.mMsg = (TextView) convertView
						.findViewById(R.id.from_msg_info);

			}else{
				convertView = mInflater.inflate(R.layout.item_to_msg, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.mDate = (TextView) convertView
						.findViewById(R.id.id_to_msg_date);
				viewHolder.mMsg = (TextView) convertView
						.findViewById(R.id.to_msg_info);
				
			}
			convertView.setTag(viewHolder);
	}else {
		viewHolder = (ViewHolder) convertView.getTag();
	}
		//设置数据
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		viewHolder.mDate.setText(sf.format(chatMessage.getData()));
		viewHolder.mMsg.setText(chatMessage.getMsg());
		return convertView;
	}

	private final class ViewHolder {
		TextView mDate;
		TextView mMsg;

	}
}
