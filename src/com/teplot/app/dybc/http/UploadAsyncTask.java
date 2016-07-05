package com.teplot.app.dybc.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;


/**
 * Depiction:异步上传文件
 * <p>
 * Author: Kevin Lynn
 * <p>
 * Create Date：2014年5月3日 下午9:06:56
 * <p>
 * Modify:
 * <p>
 * 
 * @version 1.0
 * @since 1.0
 */
public abstract class UploadAsyncTask extends Thread {
	private final static String	TAG		 = "UploadAsyncTask";
	private final Handler		handler	 = new Handler();
	private final static int	TIME_OUT = 20000;
	private String				uploadUrl;
	private HttpURLConnection	conn	 = null;
	private boolean				cancel;
	private String				formName;
	private String				tag;
	
	private List<String>		paths;
	
	public UploadAsyncTask(String path, String uploadUrl) {
		this(new File(path), uploadUrl);
	}
	
	public UploadAsyncTask(File file, String uploadUrl) {
		if (file == null || !file.exists()) {
			throw new RuntimeException("the file is null or not exist");
		}
		
		paths = new ArrayList<String>();
		paths.add(file.getPath());
		this.uploadUrl = uploadUrl;
	}
	
	public UploadAsyncTask(List<String> paths, String uploadUrl) {
		if (paths == null || paths.size() == 0) {
			throw new RuntimeException("the files is null or not exist");
		}
		
		this.paths = paths;
		this.uploadUrl = uploadUrl;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	protected abstract void onPreExcute(String tag);
	
	protected abstract void onPostExcute(String tag, List<Result> results);
	
	public void run() {
		if (tag == null) {
			tag = getName();
		}
		
		handler.post(new Runnable() {
			@Override
			public void run() {
				onPreExcute(tag);
			}
		});
		
		final List<Result> results = new ArrayList<UploadAsyncTask.Result>();
		for (String path : paths) {
			Result result = upload(path, formName);
			results.add(result);
		}
		
		handler.post(new Runnable() {
			@Override
			public void run() {
				onPostExcute(tag, results);
			}
		});
	}
	
	public void excute(String formName) {
		this.formName = formName;
		this.start();
	}
	
	/**
	 * 开始上传
	 * 
	 * @param formName
	 *            表单名称
	 * @return 响应结果，如果取消的话，返回结果为字符串“cancel”
	 */
	private Result upload(String path, String formName) {
		Result result = new Result();
		
		BufferedReader buffered = null;
		try {
			conn = (HttpURLConnection) new URL(uploadUrl).openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Android");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setConnectTimeout(TIME_OUT);
			conn.setReadTimeout(TIME_OUT);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			String BOUNDARY = "--------------";
			String MULTIPART_FORM_DATA = "multipart/form-data";
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY);
			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
			File file = new File(path);
			byte[] content = getBytesFromFile(file);
			if (content == null) {
				result.message = "the file is not exist";
				return result;
			}
			StringBuilder split = new StringBuilder();
			split.append("--");
			split.append(BOUNDARY);
			split.append("\r\n");
			split.append("Content-Disposition: form-data;name=\"" + formName + "\";filename=\"" + file.getName() + "\"\r\n");
			split.append("Content-Type: application/octet-stream; charset=UTF-8\r\n");
			split.append("Content-Transfer-Encoding: binary" + "\r\n\r\n");
			outStream.write(split.toString().getBytes("UTF-8"));
			outStream.write(content, 0, content.length);
			outStream.write("\r\n".getBytes());
			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();
			outStream.write(end_data);
			outStream.flush();
			outStream.close();
			
			StringBuffer response = new StringBuffer();
			int code = conn.getResponseCode();
			if (code == 200) {
				buffered = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String readData = null;
				while ((readData = buffered.readLine()) != null) {
					response.append(readData);
				}
			} else {
				response.append(conn.getResponseMessage());
			}
			
			result.httpCode = code;
			result.message = response.toString();
		} catch (MalformedURLException e1) {
			Log.e(TAG, e1.toString());
		} catch (UnsupportedEncodingException e2) {
			Log.e(TAG, e2.toString());
		} catch (IOException e3) {
			Log.e(TAG, e3.toString());
		} finally {
			try {
				if (buffered != null) {
					buffered.close();
				}
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		if (cancel) {
			result.httpCode = HttpURLConnection.HTTP_GONE;
			result.message = "cancel";
		}
		return result;
	}
	
	/**
	 * 取消上传
	 */
	public void cancel() {
		try {
			cancel = true;
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}
	}
	
	/**
	 * 文件转化为字节数组
	 * 
	 * @param file
	 *            文件对象
	 * @return byte[]
	 */
	private byte[] getBytesFromFile(File file) {
		byte[] data = null;
		try {
			if (null == file || !file.exists()) {
				Log.e(TAG, "get bytes from file error! the file is null or not exist");
				return null;
			}
			
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] buf = new byte[4096];
			int n;
			while ((n = in.read(buf)) != -1) {
				out.write(buf, 0, n);
			}
			in.close();
			out.close();
			data = out.toByteArray();
		} catch (IOException e) {
			Log.e(TAG, "get bytes from file process error!");
		}
		return data;
	}
	
	public class Result {
		public boolean flag		= false;
		public int	   httpCode	= HttpURLConnection.HTTP_NOT_FOUND;
		public String  message	= "upload fail";
		
		@Override
		public String toString() {
			return "Result [flag=" + flag + ", httpCode=" + httpCode + ", message=" + message + "]";
		}
		
	}
}
