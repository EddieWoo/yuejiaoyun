package com.example.contentprovider;

import java.util.HashMap;

import com.example.constants.DBInfoConfig;
import com.example.dao.OperateDAO;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * ������SearchView�ṩ ���ƵĽ���������
 */
public class CustomSuggestionsProvider extends ContentProvider {

	/**
	 * ����Ϊ CustomSuggestionsProvider �趨 ��Ҫ�õ��ĸ��� ����ֵ
	 */
	//AUTHORITY����Ҫ���provider��manifest�����ļ��е�AUTHORITY����ֵ��һ��
	public static String AUTHORITY = "com.example.contentprovider.CustomSuggestionsProvider";
	//CONTENT_URI Ϊ ȡ�����provider����Ҫ�ṩ��Ӧ��URIֵ��
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pp_vod");
	//���ݿ��� yjy.db
	public static final String DATABASE_NAME = DBInfoConfig.DB_NAME;
	//����
	public static final String TABLE_NAME = "pp_vod";
	/*MIME types used for searching vods or looking up a single definition
		VODS_MIME_TYPE Ϊȡ������vods�� �������� ��DEFINITION_MIME_TYPEΪĳһ��vod���ݵ�����*/
    public static final String VODS_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                                                  "/vnd.example.android.searchablevod";
    public static final String VOD_DEFINITION_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                                                       "/vnd.example.android.searchablevod";
    //The columns we'll include in the pp_vod table
    public static final String KEY_VOD_NAME = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String KEY_VOD_DEFINITION = SearchManager.SUGGEST_COLUMN_TEXT_2;
    //Ҫʹ�õ� �������ݿ� ��dao�࣬��onCreate�ص������б�ʵ����
    private OperateDAO dao;
    
    //UriMatcher stuff  һ����һ������ �� UriMatcher�е�ĳ���ֶζ�Ӧ���Ա��� ƥ��
    private static final int SEARCH_SUGGEST = 0;
    private static final UriMatcher sURIMatcher = buildUriMatcher();
    /**
     * ��buildUriMatcher()���ڳ�ʼ�����涨���UriMatcherȫ�ֱ�����
     * �����ж����˺ܶ��ֵ�ԣ�������query()�ж��ύ������Uri���� ƥ���б�
     */
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
        // to get suggestions...  '/*' ����˼�ǿ���ƥ������text�� �� '/#'����˼��ֻ��ƥ������
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);

        return matcher;
    }
    
    private static final HashMap<String,String> mColumnMap = buildColumnMap();
    /**
     * ����ִ�в�ѯ��ʱ�� ���õ� SQLiteQueryBuilder����ȫ�ֱ��� mColumnMap ����Ϊ���ṩ����
     * mColumnMap��Ӧ��buildColumnMap()������һ�������ͱ�����ӳ���ϵ��
     * �� ����ġ���������ֶκ�ʵ�ʵġ�pp_vod�����ֶ� ֮���ӳ��
     */
    private static HashMap<String,String> buildColumnMap() {
        HashMap<String,String> map = new HashMap<String,String>();
        //CustomSuggestionProvider��Ҫ�õ������⡰������е�_ID
        map.put(BaseColumns._ID, "vod_id AS " +BaseColumns._ID);
        //������е�   suggest_text_1 �ֶΣ���ÿһ��������� �����ı�
        map.put(KEY_VOD_NAME, "vod_name AS "+KEY_VOD_NAME);
        //������е�   suggest_text_2 �ֶΣ���ÿһ��������� �ڶ����ı�
        map.put(KEY_VOD_DEFINITION, "vod_content AS "+KEY_VOD_DEFINITION);
        /* ������� �洢�� ���ǿɼ����ݡ�������Ϊÿһ���������¼����һ��INTENT_DATA��
         * �� �û����ĳ������������Intent��תʱ�����ֶ�Ϊ�����ո�intent��SearchableActivity���ṩdata */
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA, "vod_id AS " +
                SearchManager.SUGGEST_COLUMN_INTENT_DATA);
        /*��ѯ��ݷ�ʽ��ĿǰΪֹ���ò��������Ĺ���
         * map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);*/
        return map;
    }
    
    
    //======================================================
	@Override
	public boolean onCreate() {
		dao = new OperateDAO(getContext());
		return false;
	}

	/**
	 * �û���ʼ�������Ի��������widget�������ı�ʱ��ÿ����һ���ַ���ϵͳ��������һ��query()��
	 * ����content provider�м��������
	 * ��query()�У�����ʵ�ֶ�content provider���������ݵļ�����������һ��ָ����ѽ����������е�Cursor��
	 * ���� query()�൱��provider�ĺ���
	 * @param 
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
		// Use the UriMatcher to see what kind of query we have and format the db query accordingly
		//���� �������� uri����Match�жϣ��Դ˾�����ִ�������Ĳ���
        switch (sURIMatcher.match(uri)) {
            case SEARCH_SUGGEST:
            	if (selectionArgs == null) {
                    throw new IllegalArgumentException(
                        "selectionArgs must be provided for the Uri: " + uri);
                  }
                  return getSuggestions(selectionArgs[0]);
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
	}
    /**
     * ��Cursor��ϵͳ���ؽ�����ʱ��ÿһ�����ݵ��и�ʽ����ϵͳ�涨�ġ�
     * ��ˣ�������Ҫ�ѽ��������ݴ洢�ڱ��ػ�Web��������SQLite���ݿ��У�����Ҫ�Ա��ػ�web��������ʽ�洢��
     * ������ѽ������ʽ��Ϊ���һ�����ݣ�����Cursor����ʾ��
     * ϵͳ����ʶ�����У����������Ǳ���ģ������ǿ�ѡ��
     * 	1���������͵���ID��Ψһ��ʶ�������ListView����ʾ������ʱ��ϵͳ���õ���ֵ
     * 	2��SUGGEST_COLUMN_TEXT_1 ��  ����������ַ�����
     */
	private Cursor getSuggestions(String query) {
		String[] columns = new String[] {
		          BaseColumns._ID,
		          KEY_VOD_NAME,
		          KEY_VOD_DEFINITION,
		          SearchManager.SUGGEST_COLUMN_INTENT_DATA
		};//���ڴ����� intent_data  �ο� dictionaryProvider
		String selection = KEY_VOD_NAME + " LIKE ? OR "+ KEY_VOD_DEFINITION + " LIKE ?";
        String[] selectionArgs = new String[] {query+"%","%"+query+"%"};
        //��SQLiteQueryBuilder��������ѯ���
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        //�趨��ѯ�ĸ���
        builder.setTables(TABLE_NAME);
        // �����趨ColumnMap�����԰��ȸ�API��Ҫ��  ʵ�� ������ӳ�䣬������ ��vod_id AS�� ���
        builder.setProjectionMap(mColumnMap);

       /*����ģ�������β�ѯ���Ϊ �� �Ĳ��Է�������ͷ���Ż������� ʹ�ùȸ�API�Ƽ���SQLiteQueryBuilder����ѯΪ��
        String sql = "select vod_id AS _id,vod_name AS suggest_text_1,vod_content AS suggest_text_2 from pp_vod where vod_name like ? or vod_content like ?";
		String likeStr = "%"+ query +"%";
		String bindArgs[] = new String[]{likeStr,likeStr};
		Cursor cursor = dao.getReadableDB().rawQuery(sql, bindArgs);
        */
        //ִ�в�ѯ �˴� groupBy��having��sortOrder��ʱΪ��
        Cursor cursor = builder.query(dao.getReadableDB(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
