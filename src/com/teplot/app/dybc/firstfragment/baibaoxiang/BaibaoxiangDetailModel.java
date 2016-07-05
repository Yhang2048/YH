package com.teplot.app.dybc.firstfragment.baibaoxiang;

import java.io.Serializable;
/**
 * 百宝箱Model
 * @author Administrator
 *
 */
public class BaibaoxiangDetailModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;
	private String createtime;
	private int id;
	private int state;
	private String theme;
	private String updatetime;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "BaibaoxiangModel [content=" + content + ", createtime="
				+ createtime + ", id=" + id + ", state=" + state + ", theme="
				+ theme + ", updatetime=" + updatetime + "]";
	}

}
