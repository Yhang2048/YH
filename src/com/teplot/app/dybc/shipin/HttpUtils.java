package com.teplot.app.dybc.shipin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.teplot.app.dybc.shipin.ChatMessage.Type;

/**
 * 网络请求的工具类
 * 
 * @author Administrator
 * 
 */
public class HttpUtils {
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "09d6cbad79c68c1580111c31f3c9ba33";

	/**
	 * 发送一个消息，得到返回的消息
	 * 
	 * @param msg
	 *            用户输入的信息
	 * @return
	 */
	public static ChatMessage sendMessage(String msg) {
		ChatMessage chatMessage = new ChatMessage();
		String jsonRes = doGet(msg);// 服务器返回的json字符串结果
		Gson gson = new Gson();
		// 把得到的数据转换成Result
		Result result = null;
		// 加入try catch机制 即便没有转化成功，也能保证程序的运行
		try {
			result = gson.fromJson(jsonRes, Result.class);
			chatMessage.setMsg(result.getText());
		} catch (JsonSyntaxException e) {
			chatMessage.setMsg("服务器繁忙，请稍后再试");
		}
		// 设置时间和类型
		chatMessage.setData(new Date());
		chatMessage.setType(Type.INCOMING);
		return chatMessage;

	}

	public static String doGet(String msg) {
		String result = "";

		String url = setParams(msg);

		InputStream inputStream = null;
		ByteArrayOutputStream baos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlNet
					.openConnection();
			connection.setReadTimeout(5 * 1000);// 读取超时
			connection.setConnectTimeout(5 * 1000);// 链接超时
			connection.setRequestMethod("GET");// 请求方式
			// 通过服务器拿到用户的Stream
			inputStream = connection.getInputStream();

			int len = -1;
			byte[] buf = new byte[128];

			// 把流转化为String的类
			baos = new ByteArrayOutputStream();

			while ((len = inputStream.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();// 清除缓冲区,把流读到本地
			result = new String(baos.toByteArray());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;

	}

	/**
	 * 拼接完整的url
	 * 
	 * @param msg
	 *            用户输入的信息
	 * @return url
	 */
	private static String setParams(String msg) {
		String url = "";
		try {
			url = URL + "?key=" + API_KEY + "&info="
					+ URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

}
