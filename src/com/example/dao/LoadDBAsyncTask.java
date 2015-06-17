package com.example.dao;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.example.constants.WebInfoConfig;
import com.example.utils.HttpUtil;
import com.example.utils.PpListXMLContentHandler;

import android.os.AsyncTask;

public class LoadDBAsyncTask extends AsyncTask<Void, Void, String> {

	
	public LoadDBAsyncTask() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		System.out.println("��̨doInBackground ��ɶԲ����б������ �� �����ݿ�ĸ���");
		/**
		 * ��̨doInBackground ��ɶԲ����б������ �� �����ݿ�ĸ��£����裺
		 * 1����ָ��Url��ȡ���
		 * 2��SAX����Xml���
		 * 3�����ݳ־û�����Sqlite���ݿ�
		 */
		String getPpListUrlStr = WebInfoConfig.GET_PPLIST_URL;
		String ppListResult = null;
		 try {
			ppListResult = new HttpUtil().getRequest(getPpListUrlStr);
			//System.out.println("ppListResult-->"+ ppListResult);
			//System.out.println("ppListResult-->"+ppListResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			//����������
			 SAXParserFactory spf = SAXParserFactory.newInstance();
			 SAXParser saxParser = spf.newSAXParser();
			 //����������
			 /*PpListXMLContentHandler ppListXMLContentHandler = new PpListXMLContentHandler();
			 saxParser.parse(ppListResult, ppListXMLContentHandler);
			 System.out.println("xml�������1 :"+ ppListXMLContentHandler.getPpListItems().toString());
			 System.out.println("xml�������2 :"+ ppListXMLContentHandler.getPpListItems().get(1).getList_name());
			 */
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/**
		 * ��Unixʱ���  1396090240����ȷ���룩 ���������ϵ�MD5���룺
		 * 32λ[Сд]��4094a3f81cc5f26980974d10d292d91c
		 * �ڲ鿴����� �õ� Unicode�ͺ��ֵĻ�ת  �����߹�����վhttp://www.guabu.com/zhuanma/
		 * 
		 * 1396165673
		 * 6RDdRsrzAn2ciUCG1396165673video
		 * f644ae2ed8b1ee77d4fdf3262a768343
		 * 
		 * String unixTimeStamp = WebInfoConfig.UnixTimeStamp;
		 * System.out.println("Unixʱ�����"+unixTimeStamp);
		 * System.out.println("ƴ���������ַ���Ϊ��"+ WebInfoConfig.SEWISE_ACCESS_ID + unixTimeStamp + "video" +"zKEXnF7U");
		 */
		//HttpGet get = new HttpGet(demandServerUrl + "service/api/?time=1396142496"+"&code=30acf57b7a1071c870409c4219115279"+"&do=video&op=geturl&sourceid=XUIRjNGP&type=http");
		
		//String finalUrlString = demandServerUrl + "service/api/?time=1396165673&code=f644ae2ed8b1ee77d4fdf3262a768343&do=video";
		//System.out.println("����Http���ʵ�ַ  "+finalUrlString);
		//HttpGet get = new HttpGet(finalUrlString);//�г�������Ƶ�ļ��б�
		String result = "";
		
		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}


}
