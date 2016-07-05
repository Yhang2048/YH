package com.teplot.app.dybc.model;

import java.io.Serializable;

public class DangYuanXinXiBean implements Serializable {
private DangYuanXinXiModel list;
private String code;
private String msg;
public DangYuanXinXiModel getList() {
	return list;
}
public void setList(DangYuanXinXiModel list) {
	this.list = list;
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

}
