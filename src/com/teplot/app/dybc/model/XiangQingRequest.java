package com.teplot.app.dybc.model;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class XiangQingRequest extends Request<List<DangYuanXinXiModel>> {

	private Listener<List<DangYuanXinXiModel>> mSuccessListener = null;
	
	public XiangQingRequest(int method, String url,Listener<List<DangYuanXinXiModel>> SuccessListener, ErrorListener listener) {
		super(method, url, listener);
		this.mSuccessListener = SuccessListener;
	}

	@Override
	protected void deliverResponse(List<DangYuanXinXiModel> arg0) {
			if (mSuccessListener!=null) {
				mSuccessListener.onResponse(arg0);
			}
	}
	@Override
	protected Response parseNetworkResponse(NetworkResponse arg0) {
		String result = new String(arg0.data);
//		Log.e("result", ""+result);
		
		try {
			JSONObject resultObject = new JSONObject(result);
			String Str = resultObject.getString("list");
			Gson gson = new Gson();
			List<DangYuanXinXiModel> data = gson.fromJson(Str,new TypeToken<List<DangYuanXinXiModel>>(){}.getType());
//			Log.e("data", ""+data);
			return  Response.success(data,
					HttpHeaderParser.parseCacheHeaders(arg0));
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.error(new VolleyError(e));
		}
	}


}
