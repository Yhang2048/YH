package com.teplot.app.dybc.find.studyMetting;

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
import com.teplot.app.dybc.model.DangYuanXinXiModel;

public class StadyMettingRequest extends Request<List<MettingModel>> {

	private Listener<List<MettingModel>> mSuccessListener = null;

	public StadyMettingRequest(int method, String url,
			Listener<List<MettingModel>> SuccessListener, ErrorListener listener) {
		super(method, url, listener);
		this.mSuccessListener = SuccessListener;
	}

	@Override
	protected void deliverResponse(List<MettingModel> arg0) {
		if (mSuccessListener != null) {
			mSuccessListener.onResponse(arg0);
		}
	}

	@Override
	protected Response<List<MettingModel>> parseNetworkResponse(
			NetworkResponse arg0) {
		String result = new String(arg0.data);
		Log.e("StadyMettingRequest", ""+result);
		try {
			JSONObject resultObject = new JSONObject(result);
			String Str = resultObject.getString("list");
			Gson gson = new Gson();
			List<MettingModel> data = gson.fromJson(Str,
					new TypeToken<List<MettingModel>>() {
					}.getType());
			Log.e("datadata", ""+data);
			return Response.success(data,
					HttpHeaderParser.parseCacheHeaders(arg0));
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.error(new VolleyError(e));
		}
	}

}
