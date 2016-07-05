package com.teplot.app.dybc.model;

import java.util.List;

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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
public class DengLuRequest extends Request<DengLuModel> {
	private Listener<DengLuModel> mSuccessListener = null;

	public DengLuRequest(int method, String url,Listener<DengLuModel> SuccessListener, ErrorListener listener) {
		super(method, url, listener);
		this.mSuccessListener = SuccessListener;
	}


	@Override
	protected void deliverResponse(DengLuModel arg0) {
		if (mSuccessListener!=null) {
			mSuccessListener.onResponse(arg0);
		}
	}

	@Override
	protected Response<DengLuModel> parseNetworkResponse(NetworkResponse arg0) {
		String result = new String(arg0.data);
//		Log.e("11111111111111111111111111111111", ""+arg0);
		try {
			JSONObject resultObject = new JSONObject(result);
			String Str = resultObject.getString(result);
			Gson gson = new Gson();
			DengLuModel data = gson.fromJson(Str,new TypeToken<DengLuModel>(){}.getType());
			Log.e("11111111111111111111111111111111", ""+data);
			return  Response.success(data,
					HttpHeaderParser.parseCacheHeaders(arg0));
			
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.error(new VolleyError(e));
		}
	}

}
