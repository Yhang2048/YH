package com.teplot.app.dybc.firstfragment.baibaoxiang.dangyuanjichu;

import java.util.List;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.teplot.app.dybc.Custom.Url;
import com.teplot.app.dybc.firstfragment.baibaoxiang.BaiBaoXiangDetailRequest;
import com.teplot.app.dybc.firstfragment.baibaoxiang.BaibaoxiangDetailModel;
import com.teplot.app.dybc.http.Preferences;
import com.teplot.app.dybc.model.VollyHelper;
import com.trplot.app.dybs.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DangjiActivity extends Activity {
	private ImageView mBack;
	private TextView mTitle;
	private TextView mSheZhi;

	private void initTitle() {
		mBack = (ImageView) findViewById(R.id.btn_back);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mSheZhi = (TextView) findViewById(R.id.tv_shezhi);
		mTitle.setText("习近平党纪论述");
		mSheZhi.setText("");
		mBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	final String HTML_MIME_TYPE = "text/html;charset=utf-8";

	final String HTML_ENCODING = "utf-8";

	private BaibaoxiangDetailModel mDetailModel = null;

	private WebView mWebView = null;
	private ProgressBar mLoding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dangzhang);
		initTitle();
		initUI();
		initData();
	}

	private void initData() {
		String token = Preferences.getPrefer(getApplicationContext())
				.getString("token", "");
		String url = Url.BaibaoxiangDetail + "token="+ token + "&id="+14;
		VollyHelper.create(getApplicationContext()).addRequest(new BaiBaoXiangDetailRequest(Method.GET, url, new Listener<BaibaoxiangDetailModel>() {

			@Override
			public void onResponse(BaibaoxiangDetailModel arg0) {
				// TODO Auto-generated method stub
				// 组装数据
				String body = packageWebData(arg0);
				mWebView.loadData(body, 
						HTML_MIME_TYPE, 
						HTML_ENCODING);
				Log.e("bodybody", ""+body);
				mLoding.setVisibility(View.GONE);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		}), url);
	}
	/**
	 * 组装数据，产生webview可以使用的数据
	 * @param result
	 * @return 
	 */
	private String packageWebData(BaibaoxiangDetailModel result) {
		if (result != null) {
			StringBuffer body = new StringBuffer();
			String content = result.getContent();
			body.append(content);
			// 返回数据
			return body.toString();
		}
		return "";
	}
	/**
	 * 初始化界面
	 */
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initUI() {
		mLoding = (ProgressBar) findViewById(R.id.pb_loading);
		mWebView = (WebView) findViewById(R.id.webview);

		// 设置WebView
		WebSettings settings = mWebView.getSettings();
		// 设置可以运行JS脚本!!!!
		settings.setJavaScriptEnabled(true);
		settings.setDefaultFontSize(16);
		// 设置文本编码
		settings.setDefaultTextEncodingName("UTF-8");
		/*
		 * LayoutAlgorithm是一个枚举用来控制页面的布局，有三个类型：
		 * 1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度 2.NORMAL：正常显示不做任何渲染
		 * 3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中 用SINGLE_COLUMN类型可以设置页面居中显示，
		 * 页面可以放大缩小，但这种方法不怎么好， 有时候会让你的页面布局走样而且我测了一下，只能显示中间那一块，超出屏幕的部分都不能显示。
		 */
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		settings.setSupportZoom(false);// 用于设置webview放大
		settings.setBuiltInZoomControls(false);

		// 添加js交互接口类，并起别名 onImageClickListner，该名称可以在js代码中使用
		mWebView.addJavascriptInterface(this, "onImageClickListner");
		//
		mWebView.setWebViewClient(new NewsWebClient());

	}

	@SuppressLint("SetJavaScriptEnabled")
	class NewsWebClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			// 加载图片点击事件js方法
			loadOnImageClickListener();
		}

		private void loadOnImageClickListener() {
			// 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数
			// 函数的功能是在图片点击的时候调用本地java接口并传递url过去
			mWebView.loadUrl("javascript:(function(){"
					+ "var objs = document.getElementsByTagName(\"img\"); "
					+ "for(var i=0;i<objs.length;i++)  "
					+ "{"
					+ "    objs[i].onclick=function()  "
					+ "    {  "
					+ "        window.onImageClickListner.openImage(this.src);  "
					+ "        window.onImageClickListner.getImageAlt(this.alt);  "
					+ "    }  " + "}" + "})()");
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
		}
	}
}
