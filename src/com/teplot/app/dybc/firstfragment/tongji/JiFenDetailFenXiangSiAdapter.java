package com.teplot.app.dybc.firstfragment.tongji;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.teplot.app.dybc.find.SignIn.SingInModel;
import com.trplot.app.dybs.R;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JiFenDetailFenXiangSiAdapter extends BaseAdapter {
	private Context mContext;
	private List<SingInModel> mModels;
	private LayoutInflater mInflater;

	public JiFenDetailFenXiangSiAdapter(Context context) {
		this.mContext = context;
		mModels = new ArrayList<SingInModel>();
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mModels.size();
	}

	@Override
	public SingInModel getItem(int position) {
		// TODO Auto-generated method stub
		return mModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addItem(SingInModel model) {
		mModels.add(model);
		notifyDataSetChanged();
	}

	public void addItems(List<SingInModel> models) {
		mModels.addAll(models);
		notifyDataSetChanged();
	}

	public void clear() {
		mModels.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VollerHoder hoder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_jifen_si_detail, parent,
					false);
			hoder = new VollerHoder();
			hoder.mDate = (TextView) convertView
					.findViewById(R.id.jifen_delail_date);
			hoder.mType = (TextView) convertView
					.findViewById(R.id.jifen_delail_type);
			hoder.mSorcce = (TextView) convertView
					.findViewById(R.id.jifen_delail_sorce);
			hoder.mLLJiFen = (LinearLayout) convertView
					.findViewById(R.id.ll_jifen_detail);
			hoder.mLine = (View) convertView.findViewById(R.id.linejifen);
			convertView.setTag(hoder);
		} else {
			hoder = (VollerHoder) convertView.getTag();
		}
		final SingInModel inModel = getItem(position);
		int quarter = inModel.getQuarter();
		if (quarter == 4) {

			int i = inModel.getMeetingType();
			Integer MeetType = new Integer(i);
			if (MeetType != null) {
				if (inModel.getMeetingType() == 1) {
					hoder.mType.setText("学习");

				} else if (inModel.getMeetingType() == 2) {
					hoder.mType.setText("活动");
				} else {
					hoder.mLLJiFen.setVisibility(View.GONE);
					hoder.mLine.setVisibility(View.GONE);
				}
			}
			Pattern pattern = Pattern
					.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
			if (inModel.getArrange().getStudydate() != null) {
				Matcher matcher = pattern.matcher(inModel.getArrange()
						.getStudydate());
				while (matcher.find()) {
					hoder.mDate.setText(matcher.group());
				}
			}
			float s = inModel.getScore();
			String Sorce = String.valueOf(s);
			if (Sorce != null) {
				hoder.mSorcce.setText(s + "");
			}
		}
		return convertView;
	}

	static class VollerHoder {
		private TextView mType;
		private TextView mDate;
		private TextView mSorcce;
		private LinearLayout mLLJiFen;
		private View mLine;
	}

}
