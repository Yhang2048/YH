package com.teplot.app.dybc.fragment.adress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teplot.app.dybc.fragment.adress.PinnedSectionListView.PinnedSectionListAdapter;
import com.teplot.app.dybc.model.DangYuanXinXiModel;
import com.trplot.app.dybs.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ContactAdapter extends BaseAdapter implements PinnedSectionListAdapter{
	
	private static final int VIEW_TYPE_CONTACT = 0;
	private static final int VIEW_TYPE_LETTER = 1;

	private Context context;
	private List<DangYuanXinXiModel> contacts;
	
	private List<Object> datas;
	private Map<String, Integer> letterPosition;
	
	private void initList() {
		datas = new ArrayList<Object>();
		letterPosition = new HashMap<String, Integer>();
		Collections.sort(contacts, new Comparator<DangYuanXinXiModel>() {

			@Override
			public int compare(DangYuanXinXiModel lhs, DangYuanXinXiModel rhs) {
				String lhsName = PinYinUtils.trans2PinYin(lhs.getName()).toUpperCase();
				String rhsName = PinYinUtils.trans2PinYin(rhs.getName()).toUpperCase();
				return lhsName.compareTo(rhsName);
			}
		});
		
		for(int i=0; i<contacts.size(); i++) {
			DangYuanXinXiModel contact = contacts.get(i);
			
			String firstLetter = getFirstLetter(contact.getName());
			if(!letterPosition.containsKey(firstLetter)) {
				letterPosition.put(firstLetter, datas.size());
				datas.add(firstLetter);
			}
			
			datas.add(contact);
		}
		
	}
	
	private String getFirstLetter(String name) {
		String firstLetter = "";
		char c = PinYinUtils.trans2PinYin(name).toUpperCase().charAt(0);
		if(c >= 'A' && c <= 'Z') {
			firstLetter = String.valueOf(c);
		}
		return firstLetter;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	@Override
	public int getItemViewType(int position) {
		return datas.get(position) instanceof DangYuanXinXiModel ? 
				VIEW_TYPE_CONTACT : VIEW_TYPE_LETTER;
	}
	
	public int getLetterPosition(String letter) {
		Integer positoin = letterPosition.get(letter);
		return positoin == null ? -1 : positoin;
	}
	
	public void updateList() {
		initList();
		notifyDataSetChanged();
	}
	
	public ContactAdapter(Context context, List<DangYuanXinXiModel> contacts) {
		this.context = context;
		this.contacts = contacts;
		initList();
	}
	
	@Override 
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int itemViewType = getItemViewType(position);
		return itemViewType == VIEW_TYPE_CONTACT ? 
				getContactView(position, convertView) : getLetterView(position, convertView);
	}

	private View getContactView(int position, View convertView) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_contact, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
			holder.ll_adress = (LinearLayout) convertView.findViewById(R.id.ll_adress);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final DangYuanXinXiModel contact = (DangYuanXinXiModel) getItem(position);
		Log.e("contactcontact", ""+contact);
		if (contact.toString() == null&&contact.toString().equals("")) {
			
		}else {
		holder.tv_name.setText(contact.getName());
		holder.tv_phone.setText(contact.getPager());
		}
		if (contact.getPager() == null) {
//			holder.ll_adress.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Intent.ACTION_DIAL);
				Uri data = Uri.parse("tel:" + ""+contact.getPager());
				intent.setData(data);
				context.startActivity(intent);
				
			}
		});
		
		return convertView;
	}
	
	private View getLetterView(int position, View convertView) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_letter, null);
			holder.tv_letter = (TextView) convertView.findViewById(R.id.tv_letter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String letter = (String) getItem(position);
		
		holder.tv_letter.setText(letter);
		holder.tv_letter.setTextColor(context.getResources().getColor(R.color.black));
		
		return convertView;
	}
	
	
	static class ViewHolder {
		TextView tv_letter;
		TextView tv_name;
		TextView tv_phone;
		LinearLayout ll_adress;
	}


	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == VIEW_TYPE_LETTER;
	}

}
