package com.example.contentprovider;

import android.content.SearchRecentSuggestionsProvider;

public class RecentSuggestionsProvider extends SearchRecentSuggestionsProvider {
	//����authority�������κ�Ψһ���ַ��������������content provider����ȫ�޶�����--������provider����
	public final static String AUTHORITY = "com.example.contentprovider.RecentSuggestionsProvider";
	/*���ݿ�ģʽ�������DATABASE_MODE_QUERIES����һ����ѡ��DATABASE_MODE_2LINES���ڽ������б������һ�У�ʹ��ÿ�����������ṩ�����ı���
		public final static int MODE = DATABASE_MODE_QUERIES | DATABASE_MODE_2LINES;*/
    public final static int MODE = DATABASE_MODE_QUERIES;
    
	public RecentSuggestionsProvider() {
		//���ò���Ϊ����authority�����ݿ�ģʽ
		setupSuggestions(AUTHORITY, MODE);
	}
}
