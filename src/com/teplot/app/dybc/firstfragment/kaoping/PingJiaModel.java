package com.teplot.app.dybc.firstfragment.kaoping;

public class PingJiaModel {
	private String code;
	private String msg;
	
	public boolean isSucces() {
		if (code != null && code.equalsIgnoreCase("0000")) {
			return true;
		}
		return false;
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
		return "PingJiaModel [code=" + code + ", msg=" + msg
				+ "]";
	}
	
	
	
}
