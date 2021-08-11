package com.sdmx.framework.util;

public class TestAscii {
	public static void main(String[] args) {
		String str = "aacsdfe 		d	ddd";  //包含了不间断空格的字符串
		str = str.replace("\u00A0", "123");
		if (str.contains("\u0009")){
			str = str.replace("\u0009", "");
		}
		//str = str.replace("	","");
		System.out.println(str);
		String a1 = "	";
		System.out.println(getCnASCII(a1));
		String a2 = " ";
		System.out.println(getCnASCII(a2));
		String a3 = " ";
		System.out.println(getCnASCII(a3));
		String a4 = "\u00A0";
		System.out.println(getCnASCII(a4));
	}
	 public static String getCnASCII(String str) {
		  StringBuffer stringBuffer = new StringBuffer();
		   //将字符串转换成字节序列
		  byte[] bGBK = str.getBytes();
		  for (int i = 0; i < bGBK.length; i++) {
		   //将每个字符转换成ASCII码
		   stringBuffer.append(Integer.toHexString(bGBK[i]&0xff));
		  }
		  return stringBuffer.toString();
	}
}
