package com.atguigu.gmall.util;

import java.security.MessageDigest;

public class Md5 {

	public static boolean stringCheck(String source) {
		return source != null && !"".equals(source);
	}
	
	public static String toMd(String source) {
		
		boolean result = stringCheck(source);
		if(!result) {
			throw new RuntimeException(ErrorMessage.MD5_SOURCE_MISSING);
		}
		byte[] inputBytes = source.getBytes();
		String algorithm = "md5";
		byte[] outputBytes = null;
		
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			outputBytes = messageDigest.digest(inputBytes);
		
		StringBuilder stringBuilder = new StringBuilder();
		char[] chars = new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		for(int i = 0; i < outputBytes.length; i++) {
			byte b = outputBytes[i];
			int lowValue = b & 15;
			int highValue = (b>>4) & 15;
			char lowChar = chars[lowValue];
			char highChar = chars[highValue];
			stringBuilder.append(highChar).append(lowChar);
		}
		return stringBuilder.toString();
	}catch (Exception e) {
		return null;
		}
	}
}
