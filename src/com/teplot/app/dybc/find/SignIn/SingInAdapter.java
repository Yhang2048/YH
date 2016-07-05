package com.teplot.app.dybc.find.SignIn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.squareup.okhttp.internal.http.RealResponseBody;
import com.trplot.app.dybs.R;

import android.content.Context;
import android.net.ParseException;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SingInAdapter extends BaseAdapter {
	private Context mContext;
	private List<SingInModel> mData;
	private LayoutInflater inflater;

	public SingInAdapter(Context context) {
		this.mContext = context;
		mData = new ArrayList<SingInModel>();
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public SingInModel getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addItem(SingInModel model) {
		mData.add(model);
		notifyDataSetChanged();

	}

	public void addItems(List<SingInModel> models) {
		mData.addAll(models);
		notifyDataSetChanged();

	}

	public void clear() {
		mData.clear();
		notifyDataSetChanged();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VollerHoder hoder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_singin, parent, false);
			hoder = new VollerHoder();
			hoder.mSinaInDate = (TextView) convertView
					.findViewById(R.id.tv_singin_date);
			hoder.mSinaInTime = (TextView) convertView
					.findViewById(R.id.tv_singin_singin);
			hoder.mSinaOutTime = (TextView) convertView
					.findViewById(R.id.tv_singin_singout);
			hoder.mReason = (TextView) convertView
					.findViewById(R.id.tv_singin_reason);
			hoder.mType = (TextView) convertView
					.findViewById(R.id.tv_singin_type);
			hoder.mRlTop = (RelativeLayout) convertView
					.findViewById(R.id.rl_top);
			hoder.mLine = (View) convertView.findViewById(R.id.line);
			convertView.setTag(hoder);
		} else {
			hoder = (VollerHoder) convertView.getTag();
		}

		final SingInModel inModel = getItem(position);

		if (inModel.getState() == 1) {
			hoder.mReason
					.setText(Html
							.fromHtml("状态：<font color=blue>正常</font>/<font color=blue>正常</font>"));
		} else if (inModel.getState() == 2) {
			hoder.mReason
					.setText(Html
							.fromHtml("状态：<font color=blue>正常</font>/<font color=red>早退</font>"));
		} else if (inModel.getState() == 3) {
			hoder.mReason
					.setText(Html
							.fromHtml("状态：<font color=red>迟到</font>/<font color=blue>正常</font>"));
		} else if (inModel.getState() == 4) {
			hoder.mReason
					.setText(Html
							.fromHtml("状态：<font color=red>迟到</font>/<font color=red>早退</font>"));
		} else if (inModel.getState() == 5) {
			hoder.mReason.setText(Html
					.fromHtml("状态：<font color=red>非正常到会</font>"));
		} else if (inModel.getState() == 6) {
			hoder.mReason
					.setText(Html.fromHtml("状态：<font color=red>旷会</font>"));
		}

		Pattern pattern = Pattern
				.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
		if (inModel.getArrange().getStudydate() != null) {
			Matcher matcher = pattern.matcher(inModel.getArrange()
					.getStudydate());
			while (matcher.find()) {
				hoder.mSinaInDate.setText("签到日期：" + matcher.group());
			}
		}

		// 学习类别
		if (inModel.getMeetingType() == 1) {
			hoder.mType.setText("学习");
			if (inModel.getChkInTime() != null) {
				String time = inModel.getChkInTime();
				String[] data = time.split(" ");
				String datas = data[1];
				hoder.mSinaInTime.setText("签到时间：" + datas);
			} else {
				hoder.mSinaInTime.setText("签到时间：无");
			}

			if (inModel.getChkOutTime() != null) {
				String time1 = inModel.getChkOutTime();
				String[] data = time1.split(" ");
				String datas = data[1];
				hoder.mSinaOutTime.setText("签退时间：" + datas);
			} else {
				hoder.mSinaOutTime.setText("签退时间：无");
			}
			// 活动类别
		}else if (inModel.getMeetingType() == 2) {
			hoder.mType.setText("活动");
			if (inModel.getChkInTime() != null) {
				String time = inModel.getChkInTime();
				String[] data = time.split(" ");
				String datas = data[1];
				hoder.mSinaInTime.setText("签到时间：" + datas);
			} else {
				hoder.mSinaInTime.setText("签到时间：无");
			}
		}else if (inModel.getMeetingType() == 3)
		{
		
		}
		return convertView;
	}

	static class VollerHoder {
		private TextView mSinaInDate;
		private TextView mSinaInTime;
		private TextView mSinaOutTime;
		private TextView mReason;
		private TextView mType;
		private RelativeLayout mRlTop;
		private View mLine;

	}

}
