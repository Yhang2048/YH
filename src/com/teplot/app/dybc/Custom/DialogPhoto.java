package com.teplot.app.dybc.Custom;

import com.teplot.app.dybc.http.Preferences;
import com.trplot.app.dybs.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation.ActivityResult;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 拍照的dialog弹框
 * @author 杨航
 *
 */
public class DialogPhoto extends Dialog implements OnClickListener,
		OnActivityResultListener {

	private Context mContext;

	/**
	 * 自定义Dialog监听器
	 */
	public interface PriorityListener {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
		 */
		public void refreshPriorityUI(String string);
	}

	private Activity activity;

	public DialogPhoto(Context context, Activity activity) {
		this(context, R.style.Theme_Dialog_From_Bottom, activity);
	}

	public DialogPhoto(Context context, int themeResId, Activity activity) {
		super(context, themeResId);
		this.mContext = context;
		this.activity = activity;
		init();
		initValuse();
	}

	private void init() {
		this.setCanceledOnTouchOutside(true);
		this.setCancelable(true);
	}

	private void initValuse() {
		Window window = this.getWindow();
		window.getDecorView().setPadding(0, 0, 0, 0);
		window.setBackgroundDrawableResource(R.drawable.ground_boders);
		WindowManager.LayoutParams lp = window.getAttributes();

		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.dimAmount = 0.3f;
		lp.gravity = Gravity.BOTTOM;
		window.setAttributes(lp);

	}

	private TextView mPaiZhao;
	private TextView mPhoto;
	private LinearLayout mClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pop_photo);
		initView();
	}

	private void initView() {
		mPaiZhao = (TextView) findViewById(R.id.tv_paizhao);
		mPhoto = (TextView) findViewById(R.id.tv_congshouji);
		mClose = (LinearLayout) findViewById(R.id.ll_cancel_photo);
		
		

		mPaiZhao.setOnClickListener(this);
		mPhoto.setOnClickListener(this);
		mClose.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_paizhao:
			Intent intent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			getContext().startActivity(intent);
			dismiss();
			break;
		case R.id.tv_congshouji:
			Intent intent1 = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			activity.startActivityForResult(intent1, RESULT_CODE);
			dismiss();
			break;
		case R.id.ll_cancel_photo:
			dismiss();
			break;

		default:
			break;
		}
	}
	
	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		activity.startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	public static int RESULT_CODE = 1;
	public static String picturePath;

	@Override
	public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_CODE && resultCode == -1 && data != null) {
			// 我们选择图片的Uri
			Uri selectImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContext().getContentResolver().query(
					selectImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			// 图片路径
			picturePath = cursor.getString(columnIndex);
//			Preferences.getPrefer(mContext).putString("picturePath",
//					picturePath);
			cursor.close();
		}else if (requestCode == TAKE_PICTURE &&  resultCode == activity.RESULT_OK) {
			String fileName = String.valueOf(System.currentTimeMillis());
			
			
		}

		return false;
	}

}
