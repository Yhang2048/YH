package com.teplot.app.dybc;

import java.util.ArrayList;
import java.util.List;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.teplot.app.dybc.fragment.FirstFragment;
import com.teplot.app.dybc.fragment.ManngeFragment;
import com.teplot.app.dybc.fragment.PersonalFragment;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 主页面
 * @author 杨航 
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout mLLFirst, mLLManage, mLLPersonal;
	private ImageView mIvFirst, mIvManage, mIvPersonal;
	private FirstFragment mFirstFragment;
	private ManngeFragment mManngeFragment;
	private PersonalFragment mPersonalFragment;
	
	private long mExitTime;
	

	private android.app.FragmentManager mFragmentManager;

	private int position = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		mFragmentManager = getFragmentManager();
		setTab(0);
		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"U4YDSODFwRiRBxxK00lIFXfR");
	}
	/**
	 * 消除重影onRestoreInstanceState和onSaveInstanceState
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	  position = savedInstanceState.getInt("position");
	  setTab(position);
	  super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	  //记录当前的position
	  outState.putInt("position", position);
	}

	@SuppressWarnings("deprecation")
	private void initView() {
		mLLFirst = (LinearLayout) findViewById(R.id.tab_first);
		mLLManage = (LinearLayout) findViewById(R.id.tab_manage);
		mLLPersonal = (LinearLayout) findViewById(R.id.tab_personal);

		mIvFirst = (ImageView) findViewById(R.id.iv_first);
		mIvManage = (ImageView) findViewById(R.id.iv_manage);
		mIvPersonal = (ImageView) findViewById(R.id.iv_personal);
		

		mLLFirst.setOnClickListener(this);
		mLLManage.setOnClickListener(this);
		mLLPersonal.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		resetImgs();
		switch (v.getId()) {
		case R.id.tab_first:
			setTab(0);
			break;
		case R.id.tab_manage:
			setTab(1);
			break;
		case R.id.tab_personal:
			setTab(2);
			break;
		}
	}

	private void setTab(int i) {
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (i) {
		case 0:
			mIvFirst.setImageResource(R.drawable.shouyered);
			if (mFirstFragment == null) {
				mFirstFragment = new FirstFragment();
				transaction.add(R.id.content, mFirstFragment);
			} else {
				transaction.show(mFirstFragment);
			}

			break;
		case 1:
			mIvManage.setImageResource(R.drawable.adressred);
			if (mManngeFragment == null) {
				mManngeFragment = new ManngeFragment();
				transaction.add(R.id.content, mManngeFragment);
			} else {
				transaction.show(mManngeFragment);
			}
			break;
		case 2:
			mIvPersonal.setImageResource(R.drawable.gerenzhongxinred);
			if (mPersonalFragment == null) {
				mPersonalFragment = new PersonalFragment();
				transaction.add(R.id.content, mPersonalFragment);
			} else {
				transaction.show(mPersonalFragment);
			}
			break;
		}
		transaction.commit();
	}

	private void resetImgs() {
		mIvFirst.setImageResource(R.drawable.shouye);
		mIvManage.setImageResource(R.drawable.adress);
		mIvPersonal.setImageResource(R.drawable.gerenzhongxin);
	}

	private void hideFragments(FragmentTransaction transaction) {
		if (mFirstFragment != null) {
			transaction.hide(mFirstFragment);
		}
		if (mManngeFragment != null) {
			transaction.hide(mManngeFragment);
		}
		if (mPersonalFragment != null) {
			transaction.hide(mPersonalFragment);
		}
	}
	// 按两次实现退出应用
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK) {
              if ((System.currentTimeMillis() - mExitTime) > 2000) {
                      Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                      mExitTime = System.currentTimeMillis();

              } else {
                      finish();
              }
              return true;
      }
      return super.onKeyDown(keyCode, event);
}
}
