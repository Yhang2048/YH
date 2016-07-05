package com.teplot.app.dybc.firstfragment.tongji;

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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.teplot.app.dybc.find.studyMetting.MettingModel;

public class KaopingJiFenRequest extends Request<KaoPingJiFenModel>{
	private Listener<KaoPingJiFenModel> mSuccessListener;

	public KaopingJiFenRequest(int method, String url,Listener<KaoPingJiFenModel> listener, ErrorListener errorlistener) {
		super(method, url, errorlistener);
		this.mSuccessListener = listener;
	}

	@Override
	protected void deliverResponse(KaoPingJiFenModel arg0) {
		if (mSuccessListener !=null) {
			mSuccessListener.onResponse(arg0);
		}
	}

	@Override
	protected Response<KaoPingJiFenModel> parseNetworkResponse(
			NetworkResponse arg0) {
		String result = new String(arg0.data);
		Log.e("result", ""+result);
		try {
			JSONObject resultObject = new JSONObject(result);
			Log.e("resultObject", ""+resultObject);
			String Str = resultObject.toString();
			Log.e("StrStrStr", ""+Str);
			Gson gson = new Gson();
			KaoPingJiFenModel data = gson.fromJson(Str,new TypeToken<KaoPingJiFenModel>(){}.getType());
			Log.e("11111111111111111111111111111111", ""+data);
			return  Response.success(data,
					HttpHeaderParser.parseCacheHeaders(arg0));
			
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.error(new VolleyError(e));
		}
	}

}
