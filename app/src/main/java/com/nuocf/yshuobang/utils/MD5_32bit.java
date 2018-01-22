/**   
* @Title: MD5_32bit.java 
* @Package com.wendy.pad.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author luquan yebo0505@gmail.com   
* @date 2015-4-24 下午12:30:56 
* @version V1.0   
*/ 
package com.nuocf.yshuobang.utils;

import java.security.MessageDigest;

public class MD5_32bit {
	// MD5加密，32位
	public static String md5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
 