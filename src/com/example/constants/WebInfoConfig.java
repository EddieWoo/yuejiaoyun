package com.example.constants;

import java.util.Date;

/**
 * �����������Web������Ϣ����Ϊ��̬����
 * HM5049B42NT0044D0DBBD
 */
public class WebInfoConfig {
	
	public static final String TEST_FLV_URL =
			"http://219.223.199.96:5080/flvseek/data/userdata/vod/transcode/201401/zKEXnF7U_R3.flv";
	public static final String TEST_SOURCE_ID = "XUIRjNGP";
	
	
	//������ �㲥������ ��ַ
	public static final String YJY_DEMAND_SERVER_API_URL = "http://202.116.39.44/";
	//����PKUsz�㲥������   
	public static final String PKUsz_DEMAND_SERVER_API_URL = "http://219.223.199.96/";	
	//��ΰ�� �������Ƶ㲥�������� http://202.116.39.44/ �ṩ�� ����ӿ���Ϣ
	public static final String YJY_ACCESS_ID = "6RDdRsrzAn2ciUCG";
	//����PKUsz�㲥������ http://219.223.199.96/�ṩ�� ����ӿ���Ϣ
	public static final String PKUsz_ACCESS_ID = "pUsPqQJXwj6XAnzn";
	public static final String GET_PPLIST_URL = "http://219.223.192.21/testTv/service/getlist.php";
	public static final String GET_PPVOD_URL = "http://219.223.192.21/testTv/service/getvod.php";
	/**���µ�����Ľӿ�
	 * http://219.223.192.21/testTv/service/hitsupdate.php?vodid=68
	 */
	public static final String ADD_HITS_URL = "http://219.223.192.21/testTv/service/hitsupdate.php?vodid=";
	
	
//	����Ŀ���½ӿ�
	public static final String LIKE_HITS_URL="http://219.223.192.21/testTv/service/upupdate.php?vodid=";
//�Ƚ�Ŀ���½ӿ�
	public static final String DISLIKE_HITS_URL="http://219.223.192.21/testTv/service/downupdate.php?vodid=";
//	�������½ӿ�
	public static final String SCORE_URL="http://219.223.192.21/testTv/service/goldupdate.php?vodid=95&vodgold=";
	
	
	//��ȡUnixʱ���,��ȷ����
	public String UnixTimeStamp = Long.toString(new Date().getTime()/1000);
	
}
