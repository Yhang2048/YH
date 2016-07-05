package com.teplot.app.dybc.find.studyMetting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.trplot.app.dybs.R;

import android.R.integer;
import android.R.mipmap;
import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.Telephony.Sms.Conversations;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeetingAdapter extends BaseAdapter {
	private Context mContext;
	private List<MettingModel> mModel;
	private LayoutInflater mInflater;

	public MeetingAdapter(Context context) {
		this.mContext = context;
		mModel = new ArrayList<MettingModel>();
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mModel.size();
	}

	@Override
	public MettingModel getItem(int position) {
		// TODO Auto-generated method stub
		Log.e("mModel.get(position)", ""+mModel.get(position));
		return mModel.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addItem(MettingModel news) {
		mModel.add(news);
		notifyDataSetChanged();
	}

	public void addItems(List<MettingModel> news) {
		mModel.addAll(news);
		notifyDataSetChanged();
	}

	public void clear() {
		mModel.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VollyHoder hoder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_study_metting,
					parent, false);
			hoder = new VollyHoder();
			hoder.mLine = (RelativeLayout) convertView
					.findViewById(R.id.rl_top);
			hoder.mXian = (View) convertView.findViewById(R.id.view_xian);
			hoder.mType = (TextView) convertView
					.findViewById(R.id.tv_metting_type);
			hoder.mContent = (TextView) convertView
					.findViewById(R.id.tv_metting_content);
			hoder.mStudydata = (TextView) convertView
					.findViewById(R.id.tv_metting_studydate);

			convertView.setTag(hoder);
		} else {
			hoder = (VollyHoder) convertView.getTag();
		}

		final MettingModel model = getItem(position);
	
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		long now=ca.getTimeInMillis();
//		Log.e("nownow", ""+now);
		
		String MeetingTime = model.getStudydate();
		long meet = 0l;
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sDateFormat.parse(MeetingTime));
			 meet = c.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		if (meet > now) {
			// Log.e("origIdorigId", "" + origId);
			if (model.getType() == 1) {
				hoder.mType.setText("类别：学习");
			} else if (model.getType() == 2) {
				hoder.mType.setText("类别：活动");
			} else if (model.getType() == 3) {
				hoder.mType.setText("类别：会议");
			}
			// hoder.mType.setText("类别："+model.getType());
			hoder.mContent.setText("主题：" + model.getTheme());
			Pattern pattern = Pattern
					.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
			Matcher matcher = pattern.matcher(model.getStudydate());
			while (matcher.find()) {
				hoder.mStudydata.setText("时间：" + matcher.group() + "  "
						+ model.getStudytime1());
			}
//		} else {
//			
//			if (model.getType() == 1) {
//				hoder.mType.setText("类别：学习");
//			} else if (model.getType() == 2) {
//				hoder.mType.setText("类别：活动");
//			} else if (model.getType() == 3) {
//				hoder.mType.setText("类别：会议");
//			}
//			// hoder.mType.setText("类别："+model.getType());
//			hoder.mContent.setText("主题：" + model.getTheme());
//			Pattern pattern = Pattern
//					.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
//			Matcher matcher = pattern.matcher(model.getStudydate());
//			while (matcher.find()) {
//				hoder.mStudydata.setText("时间：" + matcher.group() + "  "
//						+ model.getStudytime1());
//			}
//			
////			hoder.mStudydata.setText(model.getStudydate()+"111111111111111111111111");
////			hoder.mLine.setVisibility(View.GONE);
////			hoder.mXian.setVisibility(View.GONE);
//			
//		}
		return convertView;
	}

	static class VollyHoder {
		public TextView mType;
		public TextView mContent;
		public TextView mStudydata;
		public RelativeLayout mLine;
		public View mXian;
	}
}
