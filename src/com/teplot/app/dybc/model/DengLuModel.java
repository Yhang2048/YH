package com.teplot.app.dybc.model;

import java.io.Serializable;

public class DengLuModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String token;
	private String code;
	private String msg;
	private object object;

	public object getObject() {
		return object;
	}

	public void setObject(object object) {
		this.object = object;
	}

	public boolean isSucces() {
		if (code != null && code.equalsIgnoreCase("0000")) {
			return true;
		}
		return false;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "DengLuModel [token=" + token + ", code=" + code + ", msg="
				+ msg + "]";
	}

}
