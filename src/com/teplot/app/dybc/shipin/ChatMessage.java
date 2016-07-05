package com.teplot.app.dybc.shipin;

import java.util.Date;
/**
 * 消息bean
 * @author Administrator
 *
 */

public class ChatMessage {
	private String name;
	private String msg;
	private Type type;
	private Date data;

	public enum Type {
		INCOMING, OUTCOMING

	}
	public ChatMessage() {
		
	}

	public ChatMessage(String msg, Type type, Date data) {
		super();
		this.msg = msg;
		this.type = type;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
