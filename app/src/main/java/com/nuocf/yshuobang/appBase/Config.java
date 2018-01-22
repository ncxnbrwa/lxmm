/**
 * 
 */
package com.nuocf.yshuobang.appBase;

/**
 * @author luquan
 *
 * 2014-6-25 上午10:15:04
 * 一些基础不变的参数
 */


public class Config {

	//应用最低SDK支持版本
	public static int VERSION_SDK_INT = 14;
	//客户端版本
	public static String CLIENT_VERSION = "1.0.0";
	//接口协议版本
	public static String API_VERSION = "1.0";
	//客户端系统类型android/ios
	public static final String MOBILE_TYPE = "ANDROID";
	//应用文件基础目录
	public static final String ROOTPATH = "/yshuoban/";

	//病例图片
	public static final String FILE_IMAGE = "0";
	//用户头像
	public static final String FILE_HEAD = "1";

	//全部预约
	public static final int ORDER_ALL = 0;
	//手术预约
	public static final int ORDER_SHOUSHU = 1;
	//精准预约
	public static final int ORDER_JINGZHUN = 2;
	//海外预约
	public static final int ORDER_HAIWAI = 3;
	//绿色通道
	public static final int ORDER_LVSE = 4;

	//获取手机验证码-注册
	public static final String SMS_CODE_REGISTE = "1";
	//获取手机验证码-找回密码
	public static final String SMS_CODE_FORGETPWD = "2";

	//排序-综合
	public static final String SORT_SYN="0";
	//排序-三甲优先
	public static final String SORT_THREE_A ="1";
	//排序-职位高低
	public static final String SORT_POSTION="2";

	//预约全部
	public static final String RESERVE_ALL = "0";
	//待安排
	public static final String RESERVE_ARRANGE = "1";
	//待确认
	public static final String RESERVE_CONFIRM = "2";
	//已完成
	public static final String RESERVE_DONE = "3";
	//已评价
	public static final String RESERVE_COMMENT = "4";
	//已取消
	public static final String RESERVE_CANCEL = "5";

	//手机正则判断
	//"[1]"代表第1位为数字1，
	//"[34578]"代表第二位可以为3、4、5、7、8中的一个，
	//"\\d{9}"代表后面是可以是0～9的数字，有9位。
	public static final String telRegex = "[1][34578]\\d{9}";
}
