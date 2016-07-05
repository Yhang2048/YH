package com.teplot.app.dybc.firstfragment.activitys;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.teplot.app.dybc.firstfragment.fengcai.AlbumActivity;
import com.teplot.app.dybc.firstfragment.fengcai.Bimp;
import com.teplot.app.dybc.firstfragment.fengcai.FileUtils;
import com.teplot.app.dybc.firstfragment.fengcai.GalleryActivity;
import com.teplot.app.dybc.firstfragment.fengcai.ImageItem;
import com.teplot.app.dybc.firstfragment.fengcai.PublicWay;
import com.teplot.app.dybc.firstfragment.fengcai.Res;
import com.trplot.app.dybs.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class FengCaiZhanShiActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;

	public LocationClient mLocationClient = null;
	public MyLocationListener mMyLocationListener = null;
	private TextView mLocation;
	private ImageView mback;
	private TextView mSend;
	private ArrayList<String> datas = new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_selectimg,
				null);
		setContentView(parentView);
		Init();

		PackageManager pkm = getPackageManager();
		boolean has_permission = (PackageManager.PERMISSION_GRANTED == pkm
				.checkPermission("android.permission.ACCESS_FINE_LOCATION",
						"com.trplot.app.dybs"));
		if (!has_permission) {
			Toast.makeText(FengCaiZhanShiActivity.this, "定位权限被禁用",
					Toast.LENGTH_SHORT).show();
		} else {
			// 定位权限已开启
			Intent intent = new Intent("com.baidu.location.f");
			intent.setPackage(getPackageName());
			startService(intent);
			mMyLocationListener = new MyLocationListener();
			location(mMyLocationListener);
		}

	}

	public void Init() {

		mLocation = (TextView) findViewById(R.id.tv_location);
		mback = (ImageView) findViewById(R.id.iv_back);
		mSend = (TextView) findViewById(R.id.activity_selectimg_send);
		mback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < PublicWay.activityList.size(); i++) {
					if (null != PublicWay.activityList.get(i)) {
						PublicWay.activityList.get(i).finish();
					}
				}
				finish();
			}
		});
		mSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		pop = new PopupWindow(FengCaiZhanShiActivity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(FengCaiZhanShiActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							FengCaiZhanShiActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(FengCaiZhanShiActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
					finish();
				}
			}
		});

	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
				String path = Bimp.tempSelectBitmap.get(position)
						.getImagePath();

				datas.add(path);
				ArrayList<Object> arrayList = new ArrayList<Object>(
						new LinkedHashSet<>(datas));
				Log.e("path222222222222222", "" + arrayList);
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);

				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for (int i = 0; i < PublicWay.activityList.size(); i++) {
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			finish();
		}
		return true;
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			// appSession.setBdLocation(location);
			Log.i("baidu", "baidu");
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers:");
				sb.append(location.getOperators());
			}
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			mLocation.setText("" + location.getAddrStr());
		}
	}

	public void location(BDLocationListener listener) {
		mLocationClient = new LocationClient(getApplicationContext());

		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll");
		option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
		option.setScanSpan(5 * 60000);
		mLocationClient.setLocOption(option);
		// Log.i("2", "2");
		mLocationClient.registerLocationListener(listener);
		mLocationClient.start();
	}

	public void location(Context ct) {
		mLocationClient = new LocationClient(ct);
		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll");
		option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
		option.setScanSpan(5 * 60000);
		mLocationClient.setLocOption(option);
		Log.i("2", "2");
		mLocationClient.registerLocationListener(new MyLocationListener());
		mLocationClient.start();
	}
}
