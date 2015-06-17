package com.example.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.example.constants.DBInfoConfig;
import com.example.constants.WebInfoConfig;
import com.example.dao.DBOpenHelper;
import com.example.model.PpListItem;
import com.example.model.PpVodItem;
import com.example.utils.HttpUtil;
import com.example.utils.PpListXMLContentHandler;
import com.example.utils.PpVodXMLContentHandler;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;


public class LoadPpListService extends Service {

	DBOpenHelper dbOpenHelper = null;
	SQLiteDatabase db = null;
	
	public LoadPpListService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		//��ȡ���ݿ����������
		dbOpenHelper = new DBOpenHelper(this, DBInfoConfig.DB_NAME);
		//�����ݿ�
		openDB();
		
		System.out.println("service begin-->"+new WebInfoConfig().UnixTimeStamp);
		
		String getPpListUrlStr = WebInfoConfig.GET_PPLIST_URL;
		String ppListResult = null;
		//����WebService, �õ�PpList
		try {
			ppListResult = HttpUtil.getRequest(getPpListUrlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//����PpListXML,�õ�����ppList
		List<PpListItem> ppList = parsePpListXML(ppListResult);
		//�����pp_List���ݱ��е�ԭ������
		cleanTablePpList();
		//��ppList�������ݿ�
		putPpList2DB(ppList);

		
		//�����ݿ��ж�ȡ������𡱵�pp_list_ids
		List<String> list_ids = null;
		list_ids = getRootPpListsFromDB();
		//�����pp_Vod���ݱ��е�ԭ������
		cleanTablePpVod();
		//����ÿһ�������ȥ��ȡ������µ����� vods
		for(String list_id : list_ids) {
			//System.out.println(list_id);
			//�����ݿ�
			//openDB();
			String getPpVodUrlStr = WebInfoConfig.GET_PPVOD_URL + "?cid=" + list_id;
			//��ȡָ������cid�µ�������Ƶ��xml���
			String ppVodResult = loadPpVodFromWeb(getPpVodUrlStr);
			//����xml��� �õ���ƵItem�б�
			List<PpVodItem> vodItems = parsePpVodXML(ppVodResult);
			//System.out.println("���ν��������ܹ�����ô���VodItem-->"+vodItems.size());

			//����ƵItem�б� �־û� �������ݿ�
			putPpVod2DB(vodItems);

		}
		//���Ž�DB�е�vod��Ϣȡ������ӡ
		//printAllVodFromDB();
		
		//�ر����ݿ�����db
		closeDB();
		System.out.println("service end-->"+new WebInfoConfig().UnixTimeStamp);
		//��ɲ����󣬽�����ǰ��Service
        stopSelf();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private List<PpListItem> parsePpListXML(String ppListResult){
		List<PpListItem> ppList = null;
		//����������
		PpListXMLContentHandler ppListXMLContentHandler;
		try {
			//����������
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			
			ppListXMLContentHandler = new PpListXMLContentHandler();
			xmlReader.setContentHandler(ppListXMLContentHandler);
			xmlReader.parse(new InputSource(new StringReader(ppListResult)));
			
			ppList = ppListXMLContentHandler.getPpListItems();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 		
		return ppList;
	}
	//��ppList�������ݿ�
	private void putPpList2DB(List<PpListItem> ppList){
		
		//ѭ����������
		for (PpListItem ppListItem : ppList) {
			 //System.out.println(ppListItem.toString());
			String sql = DBInfoConfig.INSERT_INTO_PPLIST;
			String bindArgs[] = new String[]{ppListItem.getList_id(),ppListItem.getList_pid(),ppListItem.getList_name()};
			db.execSQL(sql, bindArgs);
		}

	}
	
	/**
	 * �����ݿ��ж������洢��pp_list
	 * @return
	 */
	private List<String> getRootPpListsFromDB(){
		List<String> list_ids = new ArrayList<String>();
		String sql = DBInfoConfig.SELECT_ROOT_LIST_ID;
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			list_ids.add(cursor.getString(0));
			
		}
		return list_ids;
	}
	
	/**
	 * ��ָ��URL��load����pp_vod
	 * @param urlStr
	 */
	private String loadPpVodFromWeb(String urlStr){
		
		String ppVodResult = null;
		//����WebService, �õ�PpList
		try {
			ppVodResult = HttpUtil.getRequest(urlStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return ppVodResult;
	}
	
	/**
	 * ��������õĵ�PpVodXML
	 * @param ppVodResult
	 * @return List<PpVodItem>
	 */
	private List<PpVodItem> parsePpVodXML(String ppVodResult){
	
		List<PpVodItem> vodList = null;
	
		try {
			//����������
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
			
			PpVodXMLContentHandler ppVodXMLContentHandler = new PpVodXMLContentHandler();
			xmlReader.setContentHandler(ppVodXMLContentHandler);
			xmlReader.parse(new InputSource(new StringReader(ppVodResult)));
			
			vodList = ppVodXMLContentHandler.getPpVodItems();
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vodList;
	}
	
	//��ppVod�������ݿ�
	private void putPpVod2DB(List<PpVodItem> vodList){
		//�������Ϊ�գ��������ݲ������
		if(vodList.size() == 0) return;
		String sql = DBInfoConfig.INSERT_INTO_PPVOD;
		//ѭ����������
		for (PpVodItem ppVodItem : vodList) {
			
//		�¼Ӳ���������������ݿ�
			String bindArgs[] = new String[]{ppVodItem.getVod_id(),ppVodItem.getVod_cid(),ppVodItem.getVod_name(),ppVodItem.getVod_sourceid(),ppVodItem.getVod_pic(),ppVodItem.getVod_addtime(),ppVodItem.getVod_hits(),ppVodItem.getVod_content(),ppVodItem.getVod_up(),ppVodItem.getVod_down(),ppVodItem.getVod_gold()};
			//System.out.println("bindArgs-->"+ppVodItem.getVod_id());
			db.execSQL(sql, bindArgs);
			//System.out.println("��ɱ���insert");
		}

	}
	
	private void openDB(){

		db = dbOpenHelper.getWritableDatabase();
	}
	
	private void closeDB(){
		//�ر����ݿ�
		if(db != null && db.isOpen()){
			db.close();
		}
	}
	private void cleanTablePpList(){
		//�Ƚ�����������յ�
		db.execSQL(DBInfoConfig.DELETE_TABLE_PPLIST_RECORDS);
	}
	private void cleanTablePpVod(){
		//�Ƚ�����������յ�
		db.execSQL(DBInfoConfig.DELETE_TABLE_PPVOD_RECORDS);
		
	}
	/*
	private void printAllVodFromDB(){
		Cursor cursor = db.rawQuery("select * from pp_vod", null);
		while(cursor.moveToNext()){
			System.out.println(cursor.getInt(0)+"  "+cursor.getString(1)+"  "+cursor.getString(2)+"  "+cursor.getString(3)+"  "+cursor.getString(4));
		}
	}*/
	
	
}
