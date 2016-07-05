package com.teplot.app.dybc.find.SignIn;

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
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.teplot.app.dybc.find.studyMetting.MettingModel;

public class SingInRequest extends Request<List<SingInModel>> {
	private Listener<List<SingInModel>> mSuccessListener;
	public SingInRequest(int method, String url,Listener<List<SingInModel>> SuccessListener ,ErrorListener listener) {
		super(method, url, listener);
		this.mSuccessListener = SuccessListener;
	}

	@Override
	protected void deliverResponse(List<SingInModel> arg0) {
		if (mSuccessListener!=null) {
			mSuccessListener.onResponse(arg0);
		}
	}

	@Override
	protected Response<List<SingInModel>> parseNetworkResponse(
			NetworkResponse arg0) {
		String result = new String(arg0.data);
		try {
			
			JSONObject resultObject = new JSONObject(result);
			String Str = resultObject.getString("list");
			Gson gson = new Gson();
			List<SingInModel> data = gson.fromJson(Str,
					new TypeToken<List<SingInModel>>() {
					}.getType());
			return Response.success(data,
					HttpHeaderParser.parseCacheHeaders(arg0));
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.error(new VolleyError(e));
		}
	}

}
