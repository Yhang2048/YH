package com.teplot.app.dybc.find.SignIn;

import java.io.Serializable;

public class SingInModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private arrange arrange;
	private String chkInTime;
	private String chkOutTime;
	private String createTime;
	private int id;
	private int meetingId;
	private int meetingType;
	private String mobile;
	private float score;
	private int state;
	private int quarter;
	private String updateTime;
	private int userId;
	
	public arrange getArrange() {
		return arrange;
	}
	public void setArrange(arrange arrange) {
		this.arrange = arrange;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}
	public int getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(int meetingType) {
		this.meetingType = meetingType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getChkInTime() {
		return chkInTime;
	}
	public void setChkInTime(String chkInTime) {
		this.chkInTime = chkInTime;
	}
	public String getChkOutTime() {
		return chkOutTime;
	}
	public void setChkOutTime(String chkOutTime) {
		this.chkOutTime = chkOutTime;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "SingInModel [chkInTime=" + chkInTime + ", chkOutTime="
				+ chkOutTime + ", createTime=" + createTime + ", id=" + id
				+ ", meetingId=" + meetingId + ", meetingType=" + meetingType
				+ ", mobile=" + mobile + ", score=" + score + ", state="
				+ state + ", updateTime=" + updateTime 
				+ ", userId=" + userId + "]";
	}
	
}
