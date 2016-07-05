package com.teplot.app.dybc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.teplot.app.dybc.firstfragment.tongji.JiFenActivity;
/**
 * 百度云推送
 * @author 杨航
 *
 */
public class PushTestReceiver extends PushMessageReceiver{

	@Override
	public void onBind(Context arg0, int errorCode, String appid, String userId,
			String channelId, String requestId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDelTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListTags(Context arg0, int arg1, List<String> arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}
public String message;
    /**
     * 接收透传消息的函数。
     *
     * @param context
     *            上下文
     * @param message
     *            推送的消息
     * @param customContentString
     *            自定义内容,为空或者json字符串
     */
	@Override
	public void onMessage(Context context, String message, String customContentString) {
		this.message = message;
        String messageString = "透传消息 message=\"" + message
                + "\" customContentString=" + customContentString;
        Log.d(TAG, messageString);

        // 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值
        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String myvalue = null;
                if (!customJson.isNull("北辰红云")) {
                    myvalue = customJson.getString("北辰红云");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        updateContent(context, messageString);
    }

	@Override
	public void onNotificationArrived(Context arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNotificationClicked(Context arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetTags(Context arg0, int arg1, List<String> arg2,
			List<String> arg3, String arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnbind(Context arg0, int arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}
	 private void updateContent(Context context, String content) {

	        Intent intent = new Intent();
	        intent.setClass(context.getApplicationContext(), JiFenActivity.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.putExtra("message",message);
	        context.getApplicationContext().startActivity(intent);
	    }

}
