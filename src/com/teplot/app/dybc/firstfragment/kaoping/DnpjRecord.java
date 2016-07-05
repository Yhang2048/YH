package com.teplot.app.dybc.firstfragment.kaoping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DnpjRecord implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private Date createTime;

    private Date updateTime;

    private Integer userId;

    private String mobile;

    private Integer pjUserId;

    private String pjMobile;

    private BigDecimal score;

    private Integer quarter;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getPjUserId() {
        return pjUserId;
    }

    public void setPjUserId(Integer pjUserId) {
        this.pjUserId = pjUserId;
    }

    public String getPjMobile() {
        return pjMobile;
    }

    public void setPjMobile(String pjMobile) {
        this.pjMobile = pjMobile == null ? null : pjMobile.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

	@Override
	public String toString() {
		return "DnpjRecord [ createTime=" + createTime
				+ ", updateTime=" + updateTime + ", userId=" + userId
				+ ", mobile=" + mobile + ", pjUserId=" + pjUserId
				+ ", pjMobile=" + pjMobile + ", score=" + score + ", quarter="
				+ quarter + "]";
	}
    
}