package com.teplot.app.dybc.Custom;
/**
 * URL工具类
 * @author 杨航
 *
 */
public class Url {
	/**
	 * 登录 表单域 account 必填 password 必填
	 * {"token":"DBA20F80048F3F891474FE9920B50377","code":"0000","msg":"登录成功！"}
	 */
	public static final String LogIn = "http://125.46.1.174:8080/5/api/login.do?";
	/**
	 * 退出 表单域 token 必填 {"code":"0000","object":[],"msg":"退出成功！"}
	 */
	public static final String LogOut = "http://125.46.1.174:8080/5/api/logout.do?";
	/**
	 * 党员列表查询 表单域 token 必填 origId 必填
	 * {"code":"0000","msg":"退出成功！","list":[{},{}]}
	 */
	public static final String AllParty = "http://125.46.1.174:8080/5/api/dyxx/list.do?";
	/**
	 * 会议安排列表查询 表单域 token 必填 origId 必填
	 * {"code":"0000","msg":"退出成功！","list":[{},{}]}
	 */
	public static final String Metting = "http://125.46.1.174:8080/5/api/hyap/list.do?";
	/**
	 * 会议详情查询 表单域 token 必填 origId 必填 {"code":"0000","msg":"退出成功！","object":{}}
	 */
	public static final String MeetingDetail = "http://125.46.1.174:8080/5/api/hyap/detail.do?";
	/**
	 * 签到信息列表查询 表单域 token 必填id 必填 {"code":"0000","msg":"退出成功！","list":[{},{}]}
	 */
	public static final String Sign = "http://125.46.1.174:8080/5/api/check/list.do?";
	/**
	 * 党内互评 表单域 token 必填 list 必填 返回json
	 * {"code":"0000","msg":"退出成功！","list":[{},{}]}
	 */
	public static final String PingJia = "http://125.46.1.174:8080/5/api/dnhp/insert.do?";
	/**
	 * 党内外评议积分
	 * 表单域 token 必填  id（用户id） 必填   quarter(季度) 必填
	 * 返回json {"dnIntegral":[{},{}]，"dwIntegral":[{},{}]}
	 */
	public static final String DangNeiWaiPingFen = "http://125.46.1.174:8080/5/api/dndw/list.do?";
	/**
	 * 百宝箱列表（没有内容只有主题）
	 * 表单域 token 必填 
	 * {"code":"0000","msg":"退出成功！","list":[{},{}]}
	 */
	public static final String Baibaoxiang = "http://125.46.1.174:8080/5/api/bbx/list.do?";
	/**
	 * 百宝箱详情（只有内容）
	 * 表单域 token 必填 id 必填
	 * {"object":{}}
	 */
	public static final String BaibaoxiangDetail = "http://125.46.1.174:8080/5/api/bbx/detail.do?";

}
