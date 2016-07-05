package com.teplot.app.dybc.firstfragment.tongji;

import java.io.Serializable;
/**
 * 党内互评，党外测评数据模型
 * @author Administrator
 *
 */
public class KaoPingJiFenModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double dwIntegral;
	private double dnIntegral;
	private double zongIntegral;
	

	public double getZongIntegral() {
		return zongIntegral;
	}

	public void setZongIntegral(double zongIntegral) {
		this.zongIntegral = zongIntegral;
	}

	public double getDwIntegral() {
		return dwIntegral;
	}

	public void setDwIntegral(double dwIntegral) {
		this.dwIntegral = dwIntegral;
	}

	public double getDnIntegral() {
		return dnIntegral;
	}

	public void setDnIntegral(double dnIntegral) {
		this.dnIntegral = dnIntegral;
	}

	@Override
	public String toString() {
		return "KaoPingJiFenModel [dwIntegral=" + dwIntegral + ", dnIntegral="
				+ dnIntegral + "]";
	}

}
