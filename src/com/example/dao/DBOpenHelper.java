package com.example.dao;

import com.example.constants.DBInfoConfig;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * DBOpenHelper �ǲ������ݿ�������࣬���ܣ�
 * 1��ͨ��getReadableDatabase(),getWritableDatabase()���Ի��SQLiteDatabse����ͨ���ö�����Զ����ݿ���в���
 * 2���ṩ��onCreate()��onUpgrade()�����ص����������������ڴ������������ݿ�ʱ�������Լ��Ĳ���
 *
 * 
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper {

	private static final int VERSION = DBInfoConfig.DB_VERSION;
	
	public DBOpenHelper(Context context, String name) {
		super(context, name, null, VERSION);
		// TODO Auto-generated constructor stub
	}
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//����pp_list��pp_vod �����Ի����WebService�õ��Ļ�������  create or replace table
		System.out.println("���� �������ݿ� �ڸ���");
		//db.execSQL("create table pp_list(list_id integer primary key,list_pid,list_oid,list_sid,list_name,list_skin,list_dir,list_keywords,list_title,list_description);");
		//db.execSQL("create table pp_vod(vod_id integer primary key,vod_cid,vod_name,vod_title,vod_keywords,vod_color,vod_actor,vod_director,vod_content,vod_pic,vod_area,vod_language,vod_year,vod_continu,vod_addtime,vod_hits,vod_stars,vod_del,vod_up,vod_down,vod_play,vod_server,vod_url,vod_inputer,vod_reurl,vod_letter,vod_skin,vod_gold,vod_golder)");
		
		db.execSQL(DBInfoConfig.CREATE_TABLE_PLAY_LIKES);
		db.execSQL(DBInfoConfig.CREATE_TABLE_PPLIST);
		db.execSQL(DBInfoConfig.CREATE_TABLE_PPVOD);
		db.execSQL(DBInfoConfig.CREATE_TABLE_PLAY_HISTORY);
		
		
		System.out.println("�������");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
