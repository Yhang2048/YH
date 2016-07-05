package com.teplot.app.dybc.firstfragment.baibaoxiang;

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

public class BaiBaoXiangDetailRequest extends Request<BaibaoxiangDetailModel>{
private Listener<BaibaoxiangDetailModel> mSuccessListener;
	public BaiBaoXiangDetailRequest(int method, String url,Listener<BaibaoxiangDetailModel> successListener,
			ErrorListener listener) {
		super(method, url, listener);
		this.mSuccessListener = successListener;
	}

	@Override
	protected void deliverResponse(BaibaoxiangDetailModel arg0) {
		if (mSuccessListener!=null) {
			mSuccessListener.onResponse(arg0);
		}
	}

	@Override
	protected Response<BaibaoxiangDetailModel> parseNetworkResponse(
			NetworkResponse arg0) {
		String result = new String(arg0.data);
		try {
			JSONObject jsonObject = new JSONObject(result);
			String str = jsonObject.getString("object");
			Gson gson = new Gson();
			BaibaoxiangDetailModel data = gson.fromJson(str,
					new TypeToken<BaibaoxiangDetailModel>() {
					}.getType());
			return Response.success(data,
					HttpHeaderParser.parseCacheHeaders(arg0));
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.error(new VolleyError(e));
		}
	}

}
