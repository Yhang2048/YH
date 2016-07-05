package com.teplot.app.dybc.shipin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.teplot.app.dybc.shipin.ChatMessage.Type;
import com.trplot.app.dybs.R;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ShiPinActivity extends Activity {
	private ListView mListView;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mData;

	private EditText mEtMsg;
	private Button mBtnSeng;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 等待接收。子线程完成数据的返回
			ChatMessage fromMsg = (ChatMessage) msg.obj;
			mData.add(fromMsg);
			mAdapter.notifyDataSetChanged();
			mListView.setSelection(mData.size() - 1);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shipin);
		initView();
		initData();
		initListener();

	}

	private void initListener() {
		mBtnSeng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String toMsg = mEtMsg.getText().toString();
				if (TextUtils.isEmpty(toMsg)) {
					Toast.makeText(ShiPinActivity.this, "输入内容为空！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				ChatMessage toMessage = new ChatMessage();
				toMessage.setData(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(Type.OUTCOMING);
				mData.add(toMessage);
				mAdapter.notifyDataSetChanged();

				mEtMsg.setText("");

				new Thread() {
					public void run() {
						// 网络请求不能放到主线程
						ChatMessage fromMsg = HttpUtils.sendMessage(toMsg);
						Message message = Message.obtain();
						message.obj = fromMsg;
						mHandler.sendMessage(message);
					};
				}.start();

			}
		});
	}

	private void initData() {
		mData = new ArrayList<ChatMessage>();
		mData.add(new ChatMessage("Hi,我是北辰红云 小管家", Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(this, mData);
		mListView.setAdapter(mAdapter);
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mEtMsg = (EditText) findViewById(R.id.et_msg);
		mBtnSeng = (Button) findViewById(R.id.btn_send);

	}

}
