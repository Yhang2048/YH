package com.teplot.app.dybc.Custom;

import com.trplot.app.dybs.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
/**
 * 当输入框输入时，输入框最后的叉号
 * @author 杨航
 *
 */
public class ClearEditText extends EditText implements OnFocusChangeListener {

	private Drawable mClearDrawable;

	private boolean hasFoucs;

	public boolean showType;

	public boolean showMobileType;

	private boolean isRun = false;

	private String inputStr = "";

	public ClearEditText(Context context) {
		this(context, null);
	}

	public ClearEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		mClearDrawable = getCompoundDrawables()[2];
		if (mClearDrawable == null) {
			mClearDrawable = getResources().getDrawable(
					R.drawable.delete_selector);
		}
		// 设置图标的宽高
		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
				mClearDrawable.getIntrinsicHeight());
		setClearIconVisible(false);
		setOnFocusChangeListener(this);

		addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (hasFoucs) {
					setClearIconVisible(s.length() > 0);
				}
				show(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setClearIconVisible(boolean visible) {
		Drawable right = visible ? mClearDrawable : null;
		// 设置右图标
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		this.hasFoucs = hasFocus;
		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	/**
	 * showType能够实现银行卡号形式的隔断输入 showMobileType能够实现手机号形式的隔断输入
	 * 
	 * @param s
	 */
	private void show(CharSequence s) {
		if (showType) {
			if (isRun) {
				isRun = false;
				return;
			}
			isRun = true;
			inputStr = "";
			String newStr = s.toString();
			newStr = newStr.replace(" ", "");
			int index = 0;
			if (showMobileType) {
				if ((index + 3) < newStr.length()) {
					inputStr += (newStr.substring(index, index + 3) + " ");
					index += 3;
				}
			}
			while ((index + 4) < newStr.length()) {
				inputStr += (newStr.substring(index, index + 4) + " ");
				index += 4;
			}
			inputStr += (newStr.substring(index, newStr.length()));
			this.setText(inputStr);
			this.setSelection(inputStr.length());

		}
	}

	/**
	 * 重写onTouchEvent方法，调用点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (getCompoundDrawables()[2] != null) {
				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
						&& (event.getX() < ((getWidth() - getPaddingRight())));
				if (touchable) {
					this.setText("");
				}
			}
		}
		return super.onTouchEvent(event);
	}
}
