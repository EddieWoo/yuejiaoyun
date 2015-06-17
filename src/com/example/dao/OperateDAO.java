package com.example.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.constants.DBInfoConfig;
import com.example.model.PlayHistoryItem;
import com.example.model.PlayLikesItem;
import com.example.model.PpListItem;
import com.example.model.PpVodItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class OperateDAO {

    public DBOpenHelper helper;// ����DBOpenHelper����
    public SQLiteDatabase db;// ����SQLiteDatabase����
    
	public OperateDAO(Context context) {
		helper = new DBOpenHelper(context, DBInfoConfig.DB_NAME);// ��ʼ��DBOpenHelper����
	}
	
	//���getReadableDB()���� ��Ϊ�� ӭ�� CustomSuggestionProvider��getSuggestions() ���������ʱ����
	public SQLiteDatabase getReadableDB(){
		return helper.getReadableDatabase();
	}
	/**
	 * �õ� ָ��list_id�µ�����vods���������ӷ����µ�����vods
	 * @param list_id
	 * @return
	 */
	public List<PpVodItem> getVodsByPpList(String list_id){
		List<PpVodItem> vodItems = new ArrayList<PpVodItem>();
		
		System.out.println("sql֮ǰ��list_id--" + list_id);
		//�������жϣ�������ѯ��List_id�Ƿ��Ǹ��б�����Ǹ��б�����Ҫ��������µ����з���
		//����Ŀǰ���ݽṹֱ���ϲ�ȡ��¼�ķ��༶��Ϊ�������ʼ򻯱���Ϊһ�������ѯ
		PpListItem testItem = getPpListItemByList_ID(list_id);
		db = helper.getReadableDatabase();
		Cursor cursor = null;
		if(testItem.getList_pid().equals("0")){
			//ǡ��Ϊ�����ࡾ�磺��ѧ����ѧ��,����Ҫ������������������ѯ
			String sql = DBInfoConfig.SELECT_VOD_BY_LIST_PID;
			cursor = db.rawQuery(sql, new String[]{list_id});
			while(cursor.moveToNext()){
				PpVodItem vodItem = new PpVodItem();
//vod��ṹ vod_id integer primary key,vod_cid,vod_name,vod_sourceid,vod_pic,vod_addtime,vod_hits,vod_content
				vodItem.setVod_id(cursor.getInt(cursor.getColumnIndex("vod_id")) +"");
				vodItem.setVod_cid(cursor.getString(cursor.getColumnIndex("vod_cid")));
				vodItem.setVod_name(cursor.getString(cursor.getColumnIndex("vod_name")));
				vodItem.setVod_sourceid(cursor.getString(cursor.getColumnIndex("vod_sourceid")));
				vodItem.setVod_pic(cursor.getString(cursor.getColumnIndex("vod_pic")));
				vodItem.setVod_addtime(cursor.getString(cursor.getColumnIndex("vod_addtime")));
				vodItem.setVod_hits(cursor.getString(cursor.getColumnIndex("vod_hits")));
				vodItem.setVod_content(cursor.getString(cursor.getColumnIndex("vod_content")));
				vodItem.setVod_up(cursor.getString(cursor.getColumnIndex("vod_up")) +"");
				vodItem.setVod_down(cursor.getString(cursor.getColumnIndex("vod_down")) +"");
				vodItem.setVod_gold(cursor.getString(cursor.getColumnIndex("vod_gold")) +"");
				vodItems.add(vodItem);
			}
		}else{
			String sql = DBInfoConfig.SELECT_VOD_BY_PPLIST;
			cursor = db.rawQuery(sql, new String[]{list_id});
			while(cursor.moveToNext()){
				PpVodItem vodItem = new PpVodItem();
				//vod��ṹ vod_id integer primary key,vod_cid,vod_name,vod_sourceid,vod_pic,vod_addtime,vod_hits,vod_content
				vodItem.setVod_id(cursor.getInt(cursor.getColumnIndex("vod_id")) +"");
				vodItem.setVod_cid(cursor.getString(cursor.getColumnIndex("vod_cid")));
				vodItem.setVod_name(cursor.getString(cursor.getColumnIndex("vod_name")));
				vodItem.setVod_sourceid(cursor.getString(cursor.getColumnIndex("vod_sourceid")));
				vodItem.setVod_pic(cursor.getString(cursor.getColumnIndex("vod_pic")));
				vodItem.setVod_addtime(cursor.getString(cursor.getColumnIndex("vod_addtime")));
				vodItem.setVod_hits(cursor.getString(cursor.getColumnIndex("vod_hits")));
				vodItem.setVod_content(cursor.getString(cursor.getColumnIndex("vod_content")));
				vodItem.setVod_up(cursor.getString(cursor.getColumnIndex("vod_up")) +"");
				vodItem.setVod_down(cursor.getString(cursor.getColumnIndex("vod_down")) +"");
				vodItem.setVod_gold(cursor.getString(cursor.getColumnIndex("vod_gold")) +"");
				vodItems.add(vodItem);
			}
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return vodItems;
	}
	
	/**
	 * �õ�count��������Ƶ����vod_addtime  DESC ����
	 * @param count
	 * @return
	 */
	public List<PpVodItem> getLatestVods(int count){
		List<PpVodItem> vodItems = new ArrayList<PpVodItem>();
		db = helper.getReadableDatabase();
		String sql = DBInfoConfig.SELECT_LATEST_VOD;
		Cursor cursor = db.rawQuery(sql, null);
		//cursorָ���count˫��Լ������
		while(cursor.moveToNext() && count>0){
			PpVodItem vodItem = new PpVodItem();
			//vod��ṹ vod_id integer primary key,vod_cid,vod_name,vod_sourceid,vod_pic,vod_addtime,vod_hits,vod_content
			vodItem.setVod_id(cursor.getInt(cursor.getColumnIndex("vod_id")) +"");
			vodItem.setVod_cid(cursor.getString(cursor.getColumnIndex("vod_cid")));
			vodItem.setVod_name(cursor.getString(cursor.getColumnIndex("vod_name")));
			vodItem.setVod_sourceid(cursor.getString(cursor.getColumnIndex("vod_sourceid")));
			vodItem.setVod_pic(cursor.getString(cursor.getColumnIndex("vod_pic")));
			vodItem.setVod_addtime(cursor.getString(cursor.getColumnIndex("vod_addtime")));
			vodItem.setVod_hits(cursor.getString(cursor.getColumnIndex("vod_hits")));
			vodItem.setVod_content(cursor.getString(cursor.getColumnIndex("vod_content")));
			vodItem.setVod_up(cursor.getString(cursor.getColumnIndex("vod_up")) +"");
			vodItem.setVod_down(cursor.getString(cursor.getColumnIndex("vod_down")) +"");
			vodItem.setVod_gold(cursor.getString(cursor.getColumnIndex("vod_gold")) +"");
			vodItems.add(vodItem);			
			count = count-1;
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return vodItems;
	}
	/**
	 * �õ�count����ѡ��Ƶ ,��vod_hits DESC ����
	 * @param count
	 * @return
	 */
	public List<PpVodItem> getChosenVods(int count){
		List<PpVodItem> vodItems = new ArrayList<PpVodItem>();
		db = helper.getReadableDatabase();
		String sql = DBInfoConfig.SELECT_CHOSEN_VOD;
		Cursor cursor = db.rawQuery(sql, null);
		//cursorָ���count˫��Լ������
		while(cursor.moveToNext() && count>0){
			PpVodItem vodItem = new PpVodItem();
			//vod��ṹ vod_id integer primary key,vod_cid,vod_name,vod_sourceid,vod_pic,vod_addtime,vod_hits,vod_content
			vodItem.setVod_id(cursor.getInt(cursor.getColumnIndex("vod_id")) +"");
			vodItem.setVod_cid(cursor.getString(cursor.getColumnIndex("vod_cid")));
			vodItem.setVod_name(cursor.getString(cursor.getColumnIndex("vod_name")));
			vodItem.setVod_sourceid(cursor.getString(cursor.getColumnIndex("vod_sourceid")));
			vodItem.setVod_pic(cursor.getString(cursor.getColumnIndex("vod_pic")));
			vodItem.setVod_addtime(cursor.getString(cursor.getColumnIndex("vod_addtime")));
			vodItem.setVod_hits(cursor.getString(cursor.getColumnIndex("vod_hits")));
			vodItem.setVod_content(cursor.getString(cursor.getColumnIndex("vod_content")));
			vodItem.setVod_up(cursor.getString(cursor.getColumnIndex("vod_up")) +"");
			vodItem.setVod_down(cursor.getString(cursor.getColumnIndex("vod_down")) +"");
			vodItem.setVod_gold(cursor.getString(cursor.getColumnIndex("vod_gold")) +"");
			vodItems.add(vodItem);			
			count = count-1;
			//Ӧ����select top X * from table_name
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return vodItems;
	}
	
	/**
	 * list_id ��  vod_cid������list_id��ѯ�õ�һ�� PpListItem
	 * @param vod_cid
	 * @return
	 */
	public PpListItem getPpListItemByList_ID(String list_id){
		PpListItem item = new PpListItem();
		db = helper.getReadableDatabase();
		String sql = DBInfoConfig.SELECT_PPLIST_ITEM_BY_LISTID;
		String bindArgs[] = new String[]{list_id};
		Cursor cursor = db.rawQuery(sql, bindArgs);
		if(cursor.moveToNext()){
			item.setList_id(cursor.getInt(cursor.getColumnIndex("list_id"))+"");
			item.setList_name(cursor.getString(cursor.getColumnIndex("list_name")));
			item.setList_pid(cursor.getString(cursor.getColumnIndex("list_pid")));
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return item;
	}
	
	/**
	 * ����pp_vod���е�vod_hits�ֶΣ�ʹ�� ���Լ�һ��
	 * @param ���޸ĵ�ԭ����vodItem
	 * @return ���ݿ��б��ø��²���Ӱ���˵ļ�¼����
	 */
	public int addVodHits(PpVodItem vodItem){
		int result = 0;
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("vod_hits", vodItem.getVod_hits()+1);
		result = db.update("pp_vod", values, "vod_id=?", new String[]{vodItem.getVod_id()});
		if(db !=null && db.isOpen()){
			db.close();
		}
		return result;
	}
	
	/**
	 * ��pp_vod���� like ������queryStr�ַ�����صļ�¼������ƥ��vod_name��vod_content
	 * @param queryStr
	 * @return
	 */
	public List<PpVodItem> searchVodLikeStr(String queryStr){
		List<PpVodItem> vodItems = new ArrayList<PpVodItem>();
		db = helper.getReadableDatabase();
		String sql = DBInfoConfig.SELECT_VODS_LIKE_STRING;
		String likeStr = "%"+ queryStr +"%";
		String bindArgs[] = new String[]{likeStr,likeStr};
		Cursor cursor = db.rawQuery(sql, bindArgs);
		while(cursor.moveToNext()){
			PpVodItem vodItem = new PpVodItem();
			//vod��ṹ vod_id integer primary key,vod_cid,vod_name,vod_sourceid,vod_pic,vod_addtime,vod_hits,vod_content
			vodItem.setVod_id(cursor.getInt(cursor.getColumnIndex("vod_id")) +"");
			vodItem.setVod_cid(cursor.getString(cursor.getColumnIndex("vod_cid")));
			vodItem.setVod_name(cursor.getString(cursor.getColumnIndex("vod_name")));
			vodItem.setVod_sourceid(cursor.getString(cursor.getColumnIndex("vod_sourceid")));
			vodItem.setVod_pic(cursor.getString(cursor.getColumnIndex("vod_pic")));
			vodItem.setVod_addtime(cursor.getString(cursor.getColumnIndex("vod_addtime")));
			vodItem.setVod_hits(cursor.getString(cursor.getColumnIndex("vod_hits")));
			vodItem.setVod_content(cursor.getString(cursor.getColumnIndex("vod_content")));
			vodItem.setVod_up(cursor.getString(cursor.getColumnIndex("vod_up")) +"");
			vodItem.setVod_down(cursor.getString(cursor.getColumnIndex("vod_down")) +"");
			vodItem.setVod_gold(cursor.getString(cursor.getColumnIndex("vod_gold")) +"");
			vodItems.add(vodItem);
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return vodItems;
	}
	
	/**
	 * �� ָ��vod_id ���� List<PpVodItem>����ĺ���
	 * ����SearchableActivity�е�ACTION_VIEW.doPointSearch()
	 * @param vod_id
	 * @return ����ָ��PpVodItem��List��sizeΪ1
	 */
	public List<PpVodItem> searchVodByVod_id(String vod_id){
		List<PpVodItem> vodItems = new ArrayList<PpVodItem>();
		PpVodItem vodItem = getVodByVod_id(vod_id);
		if(vodItem!=null){
			vodItems.add(vodItem);
		}else{
			System.out.println("OperateDAO��searchVodByVod_id() �����쳣");
		}
		return vodItems;
	}
	/**
	 * ִ�нϵײ�����ݿ�select����
	 * @param vod_id
	 * @return
	 */
	public PpVodItem getVodByVod_id(String vod_id){
		PpVodItem vodItem = null;
		db = helper.getReadableDatabase();
		String sql = DBInfoConfig.SELECT_VODS_BY_VOD_ID;
		String bindArgs[] = new String[]{vod_id};
		Cursor cursor = db.rawQuery(sql, bindArgs);
		if(cursor.moveToNext()){
			vodItem = new PpVodItem();
			//vod��ṹ vod_id integer primary key,vod_cid,vod_name,vod_sourceid,vod_pic,vod_addtime,vod_hits,vod_content
			vodItem.setVod_id(cursor.getInt(cursor.getColumnIndex("vod_id")) +"");
			vodItem.setVod_cid(cursor.getString(cursor.getColumnIndex("vod_cid")));
			vodItem.setVod_name(cursor.getString(cursor.getColumnIndex("vod_name")));
			vodItem.setVod_sourceid(cursor.getString(cursor.getColumnIndex("vod_sourceid")));
			vodItem.setVod_pic(cursor.getString(cursor.getColumnIndex("vod_pic")));
			vodItem.setVod_addtime(cursor.getString(cursor.getColumnIndex("vod_addtime")));
			vodItem.setVod_hits(cursor.getString(cursor.getColumnIndex("vod_hits")));
			vodItem.setVod_content(cursor.getString(cursor.getColumnIndex("vod_content")));
			vodItem.setVod_up(cursor.getString(cursor.getColumnIndex("vod_up")) +"");
			vodItem.setVod_down(cursor.getString(cursor.getColumnIndex("vod_down")) +"");
			vodItem.setVod_gold(cursor.getString(cursor.getColumnIndex("vod_gold")) +"");
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return vodItem;
	}
	/**
	 * �����Ź���Vod��¼��ӵ�play_history���У�play_history��ֻ����vod_id�ֶ�
	 * @param vodItem
	 * @return newlyInsertRowID �½������RowID���������ʧ�ܣ��򷵻�-1,û�з�Ӧ�򷵻�0
	 */
	public int insertVodIntoPlayHistory(PpVodItem vodItem){
		int newlyInsertRowID = 0;
		//����play_history��(his_id AUTOINCREMENT,vod_id,playtime)
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("vod_id", vodItem.getVod_id());
		values.put("playtime", Long.toString(new Date().getTime()/1000));
		newlyInsertRowID = (int)db.insert("play_history","nullColumnHack",values);
		if(db !=null && db.isOpen()){
			db.close();
		}
		//return�����the row ID of the newly inserted row, or -1 if an error occurred 
		return newlyInsertRowID;
	}
	
	
//	����ղ���Ŀ
	public int insertVodIntoPlayLikes(PpVodItem vodItem){
		int newlyInsertRowID = 0;
		int likesid=0;
		//����play_history��(his_id AUTOINCREMENT,vod_id,playtime)
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("vod_id", vodItem.getVod_id());
//		values.put("likes_id", ++likesid);
		newlyInsertRowID = (int)db.insert("play_likes","nullColumnHack",values);
		if(db !=null && db.isOpen()){
			db.close();
		}
		//return�����the row ID of the newly inserted row, or -1 if an error occurred 
		return newlyInsertRowID;
	}
	
	
	/**
	 * ����ָ�������� ��ʷ���ż�¼�����ղ����¼�playtime DESC ����
	 * @param count
	 * @return
	 */
	public List<PlayHistoryItem> getPlayHistory(int count){
		List<PlayHistoryItem> historyItems = new ArrayList<PlayHistoryItem>();
		db = helper.getReadableDatabase();
		String sql = DBInfoConfig.SELECT_HISTORY_VOD;
		Cursor cursor = db.rawQuery(sql, null);
		//cursorָ���count˫��Լ������
		while(cursor.moveToNext() && count>0){
			PlayHistoryItem historyItem = new PlayHistoryItem();
			//play_history�ı�ṹ(his_id integer primary key AUTOINCREMENT,vod_id,playtime)
			historyItem.setHis_id(cursor.getInt(cursor.getColumnIndex("his_id"))+"");
			historyItem.setVod_id(cursor.getString(cursor.getColumnIndex("vod_id")));
			historyItem.setPlaytime(cursor.getString(cursor.getColumnIndex("playtime")));
			historyItems.add(historyItem);
			count = count-1;
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return historyItems;
	}
	
	
	
	public List<PlayLikesItem> getPlayLikes(int count){
		List<PlayLikesItem> likesItems = new ArrayList<PlayLikesItem>();
		db = helper.getReadableDatabase();
		String sql = DBInfoConfig.SELECT_LIKES_VOD;
		Cursor cursor = db.rawQuery(sql, null);
		//cursorָ���count˫��Լ������
		while(cursor.moveToNext() && count>0){
			PlayLikesItem likesItem = new PlayLikesItem();
			//play_history�ı�ṹ(his_id integer primary key AUTOINCREMENT,vod_id,playtime)
			likesItem.setLikes_id(cursor.getInt(cursor.getColumnIndex("likes_id"))+"");
			likesItem.setVod_id(cursor.getString(cursor.getColumnIndex("vod_id")));
			likesItems.add(likesItem);
			count = count-1;
		}
		if(cursor != null){
			cursor.close();
		}
		if(db !=null && db.isOpen()){
			db.close();
		}
		return likesItems;
	}
	
}

