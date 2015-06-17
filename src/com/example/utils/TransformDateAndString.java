package com.example.utils;

import java.util.Locale;

/**
 * ʵ��Date��String ת���� ������
 */
public class TransformDateAndString {

	/**
	 * ��Unixʱ����ַ�������ȷ���룩ת��Ϊ������formats��ʽ��ʾ���ַ���
	 * @param timestampString
	 * @param formats
	 * @return formateDate
	 */
	public static String TimeStamp2Date(String timestampString, String formats){    
		  Long timestamp = Long.parseLong(timestampString)*1000;    
		  String formateDate = new java.text.SimpleDateFormat(formats,Locale.getDefault()).format(new java.util.Date(timestamp));    
		  return formateDate;    
		} 
}
