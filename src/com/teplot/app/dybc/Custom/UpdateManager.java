package com.teplot.app.dybc.Custom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.trplot.app.dybs.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 远程自动更新管理
 * @author GRR
 *
 */
public class UpdateManager {

	//是否取消下载	
	private boolean isCancelDowload=false;
	
	private static final int DOWNLOADING=1;
	private static final int COMPLETEDOWNLOAD=2;
	
	private Context context;
	
	private String version_code;//版本号
	private String version_name;//版本名称
	private String version_desc;//版本描述
	private String version_path;//APK文件下载路径
	
	private ProgressBar progress_download;
	private Dialog dialog_download;
	
	private int mProgress;
	
	//版本更新接口
	private String url="http://192.168.1.1/update.html";
	
	//下载的apk安装包保存地址
	private String savePath;
	
	public UpdateManager(Context context){
		this.context=context;
	}
	/**
	 * {
	 * 	"version_code":2.1
	 * 	"version_name":2
	 * }
	 */
	/**
	 * 处理从服务器获取到的数据
	 */
	private Handler checkHandler=new Handler(){
		public void handleMessage(Message msg) {
			JSONObject jsonObject=(JSONObject)msg.obj;
			switch (msg.what) {
			case 1://请求到数据
				try {
					version_code=jsonObject.getString("version_code");
					version_name=jsonObject.getString("version_name");
					version_desc=jsonObject.getString("version_desc");
					version_path=jsonObject.getString("version_path");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (isUpdate(version_code)) {
					//弹出dialog让用户选择是否进行更新
					showDialog();
				}else{
					Toast.makeText(context, "已是最新版本！", Toast.LENGTH_LONG).show();
				}
				break;
			case -1:
				Toast.makeText(context, "已是最新版本！", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		};
	};
	
	/**
	 * 更新下载进度条
	 */
	private Handler updateProgressHandler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOADING:
				//正在下载 更新进度条
				progress_download.setProgress(mProgress);
				break;
			case COMPLETEDOWNLOAD:
				//下载完成
				//取消对话框
				dialog_download.dismiss();
				//安装应用
				installapk();
			default:
				break;
			}
		};
	};
	
	/**
	 * 对话框提示用户是否更新
	 */
	private void showDialog(){
		AlertDialog.Builder dialog=new Builder(context);
		dialog.setTitle("新版本提示");
		dialog.setMessage("发现新版本，是否立即进行更新？");
		dialog.setNegativeButton("以后再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dialog.setPositiveButton("立即更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//弹出下载框
				showDownloadDialog();
			}
		});
		dialog.create().show();
	}
	
	/**
	 * 下载框
	 */
	private void showDownloadDialog(){
		//创建AlertDialog。Bulider对象
		AlertDialog.Builder downloadDialog=new AlertDialog.Builder(context);
		//标题
		downloadDialog.setTitle("正在下载，请稍后...");
		//view
		View view=LayoutInflater.from(context).inflate(
				R.layout.download_dialog, null);
		//获取ProgressBar
		progress_download=(ProgressBar)view.findViewById(R.id.pb_download);
		//将view添加到dialog
		downloadDialog.setView(view);
		downloadDialog.setNegativeButton("取消下载", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//取消框对话框
				dialog_download.dismiss();
				//停止下载数据
				isCancelDowload=true;
			}
		});
		//构建对话框
		dialog_download=downloadDialog.create();
		dialog_download.show();
		//下载安装包
		downLoadApk();
	}
	
	/**
	 * 下载安装包
	 */
	private void downLoadApk(){
		//开启子线程下载数据
		new Thread(){
			public void run() {
				//判断SD卡是否挂载
				if (Environment.getExternalStorageState()
						.equals(Environment.MEDIA_MOUNTED)) {
					//sd的根目录路径
					String sdPath=Environment.getExternalStorageDirectory()
							+"/";
					savePath=sdPath+"test";
					File file=new File(savePath);
					//判断文件夹是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					
					//下载数据
					try {
						HttpURLConnection conn=(HttpURLConnection)new URL(version_path)
						.openConnection();
						InputStream inputStream=conn.getInputStream();
						//文件长度
						int length=conn.getContentLength();
						File apkFile=new File(savePath,version_name);
						FileOutputStream outputStream=new FileOutputStream(apkFile);
						
						int count=0;
						byte buffer[]=new byte[1024*2];
						while(!isCancelDowload){
							int numread=inputStream.read(buffer);
							count+=numread;
							mProgress=(int)(((float)count/length)*100);
							//通知主线程更新UI
							updateProgressHandler.sendEmptyMessage(DOWNLOADING);
							//下载完成
							if (count<0) {
								updateProgressHandler.sendEmptyMessage(COMPLETEDOWNLOAD);
								break;
							}
							outputStream.write(buffer,0,numread);
						}
						//关闭流
						outputStream.close();
						inputStream.close();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
	/**
	 * 安装apk
	 */
	private void installapk(){
		File apkfile =new File(savePath,version_name);
		if(!apkfile.exists()){
			return;
		}
		//定位资源
		Uri uri =Uri.parse("file://"+apkfile.toString());
		Intent intent =new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	/**
	 * 判断是否需要升级
	 * @param version_code 从服务器获取的版本号
	 * @return
	 */
	private boolean isUpdate(String version_code){
		//服务器上的软件版本号
		int serverVersionCode=Integer.parseInt(version_code);
		//当前安装的软件的版本号
		int locationVersionCode=1;
		try {
			locationVersionCode=
					context.getPackageManager()
					.getPackageInfo("com.trplot.app.dybs", 0)
					.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//比较版本号 判断是否需要升级
		if (serverVersionCode>locationVersionCode) {
			//需要升级
			return true;
		}else{
			//不需要升级
			return false;
		}
	}
	
	/**
	 * 从远程服务器获取数据
	 */
	public void checkUpdate(){
		//使用Volley获取json数据
		RequestQueue mQueue=Volley.newRequestQueue(context);
		JsonObjectRequest mRequest=new JsonObjectRequest(
				url, null, new Listener<JSONObject>() {
			//请求成功
			@Override
			public void onResponse(JSONObject arg0) {
				//网络连接成功
				Message msg=new Message();
				msg.obj=arg0;
				msg.what=1;
				checkHandler.sendMessage(msg);
			}
			
		}, new Response.ErrorListener() {
			//请求失败
			@Override
			public void onErrorResponse(VolleyError arg0) {
				Message msg=new Message();
				msg.what=-1;
				checkHandler.sendMessage(msg);
			}
		});
		//把请求添加到队列
		mQueue.add(mRequest);
	}
}
