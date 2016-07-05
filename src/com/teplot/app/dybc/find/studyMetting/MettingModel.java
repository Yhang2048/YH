package com.teplot.app.dybc.find.studyMetting;

import java.io.Serializable;

public class MettingModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String checkintime1;
	private String checkintime2;
	private String checkouttime1;
	private String checkouttime2;
	private String content;
	private String cycle;
	private int id;
	private int state;
	private int origId;
	private String studydate;
	private String studytime1;
	private String studytime2;
	private String theme;
	private int type;
	
	public int getOrigId() {
		return origId;
	}
	public void setOrigId(int origId) {
		this.origId = origId;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public String getCheckintime1() {
		return checkintime1;
	}
	public void setCheckintime1(String checkintime1) {
		this.checkintime1 = checkintime1;
	}
	public String getCheckintime2() {
		return checkintime2;
	}
	public void setCheckintime2(String checkintime2) {
		this.checkintime2 = checkintime2;
	}
	public String getCheckouttime1() {
		return checkouttime1;
	}
	public void setCheckouttime1(String checkouttime1) {
		this.checkouttime1 = checkouttime1;
	}
	public String getCheckouttime2() {
		return checkouttime2;
	}
	public void setCheckouttime2(String checkouttime2) {
		this.checkouttime2 = checkouttime2;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStudydate() {
		return studydate;
	}
	public void setStudydate(String studydate) {
		this.studydate = studydate;
	}
	public String getStudytime1() {
		return studytime1;
	}
	public void setStudytime1(String studytime1) {
		this.studytime1 = studytime1;
	}
	public String getStudytime2() {
		return studytime2;
	}
	public void setStudytime2(String studytime2) {
		this.studytime2 = studytime2;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MettingModel [checkintime1=" + checkintime1 + ", checkintime2="
				+ checkintime2 + ", checkouttime1=" + checkouttime1
				+ ", checkouttime2=" + checkouttime2 + ", content=" + content
				+ ", id=" + id + ", state=" + state + ", studydate="
				+ studydate + ", studytime1=" + studytime1 + ", studytime2="
				+ studytime2 + ", theme=" + theme + ", type=" + type + "]";
	}
	

}
