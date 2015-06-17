package com.example.activity;

import java.util.List;

import com.example.contentprovider.RecentSuggestionsProvider;
import com.example.dao.OperateDAO;
import com.example.model.PpVodItem;
import com.example.utils.ActivityVodAdapter;
import com.example.utils.MySysApplication;
import com.example.yuejiaoyun.R;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

public class SearchableActivity extends Activity {

	String queriedStr = null;
	ListView searchResultLV = null;
	SearchView searchView = null;
	List<PpVodItem> vodItems = null;
	OperateDAO dao = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		//��Ӹ�Activity��MySysApplication����ʵ��������
		MySysApplication.getInstance().addActivity(this);
		//��ʼ��ȫ�ֱ��������ݡ��ؼ��ȵ�
		searchResultLV = (ListView)findViewById(R.id.searchResultLV);
		dao = new OperateDAO(this);
		handleIntent(getIntent());  
	}
	
	//���activity��manifest�����ļ��е����� launchMode =singTop ���Ӧ 
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
		
	}
	//www.cnblogs.com/over140/archive/2012/01/06/2314114.html
	//���� onCreate ��onNewIntent �ṩ��intent Search ����
	private void handleIntent(Intent intent) {
		// ���û����������ڻس�ȷ������ĳ�����ݵ�ʱ�򣬽�ͨ��Intent.ACTION_SEARCH��������SearchableActivity
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
		      String query = intent.getStringExtra(SearchManager.QUERY);
		      //�ж� �˴β�ѯ���ϴβ�ѯ�Ƿ��ظ�
		      if(!query.equals(queriedStr)){
		    	  //ȥ��� ������ search ����
		    	  doEnterSearch(query);
		    	  //����query�ַ���Ϊ ��ѯ����queriedStr�ַ���
		    	  queriedStr = query;
		    	  //����һ��SearchRecentSuggestionsʵ��������saveRecentQuery�������յ��Ĳ�ѯ�ؼ��ʽ��б���
		    	  SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
		    			  RecentSuggestionsProvider.AUTHORITY, RecentSuggestionsProvider.MODE); 
		          suggestions.saveRecentQuery(query, null); 
		      }else return;
		    }else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
		        // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
		    	//���û� ѡ���� ������ �е�ĳһ��ʱ����ͨ��Intent.ACTION_VIEW������ת����
		    	//�ڴ� ��ȡ ѡ�е� ������ ��data���ݣ���ɡ������������ʾ�����ߡ���ת��
		    	System.out.println("��ת����SearchableActivity��ACTION_VIEW��֧��");
		        Uri data = intent.getData();
		        System.out.println("Uri Str--"+ data.toString());
		        String vod_id = data.getLastPathSegment();
		        doPointSearch(vod_id);
		    }
	}
	
	/**
	 * �����Enter����������������������õ��Ľ����ֵ��ȫ�ֱ��� List<PpVodItem> vodItems��
	 * @param query--��Ҫ�������ַ���
	 */
	private void doEnterSearch(String query) {
		// TODO Auto-generated method stub
		System.out.println("doMySearch's query--"+query);
		vodItems = dao.searchVodLikeStr(query);
		showSearchedVodsResult();
	}

	/**
	 * ��ɴӽ�������ѡ�е�ĳ�����ݵ���ʾ����ʵҲ��Ҫ���һ��������ֻ������������Ѿ�ͨ����ѡ�еĽ����֪�����Ǹ�ȷ����PpVodItem
	 * @param vod_id  �Ǹ�ָ���Ľ��������ṩ��vod_id
	 */
	private void doPointSearch(String vod_id) {
		System.out.println("SearchableActivity doPointSearch()��õ�vod_id--"+vod_id);
		vodItems = dao.searchVodByVod_id(vod_id);
		showSearchedVodsResult();
	}
	
	/**
	 * �����Ϊ�յĻ�����ʾȫ�ֱ���List<PpVodItem> vodItems�е�����
	 */
	private void showSearchedVodsResult() {
		
		if(!vodItems.isEmpty()){
			//�����ѯ���Ľ���ǿ�
			ActivityVodAdapter adapter = new ActivityVodAdapter(this, vodItems);
			searchResultLV.setAdapter(adapter);
			searchResultLV.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int arg2, long arg3) {
					// TODO Auto-generated method stub 
					System.out.println("data from tag--->"+view.getTag().toString());

					Intent intent = new Intent(view.getContext(), VideoPlayActivity.class);
					intent.putExtra("wantShowVod", (PpVodItem)view.getTag());
					startActivity(intent);
				}
			});
		}else{
			System.out.println("doMySearch's vodItems isEmpty");
			Toast.makeText(this, "û���ҵ�ƥ�����Ƶ", 2000 );
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the options menu from XML
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_search_activity, menu);
	    
	    // Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
	    
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    //�����������Ƿ����𣬴˴�false����������
	    searchView.setIconifiedByDefault(false);
	    //����HintĬ�ϵ�ֵ
	    searchView.setQueryHint(queriedStr);
	    /*searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				//Toast.makeText(this, "����ѡ����:" + query,Toast.LENGTH_SHORT).show();
				System.out.println("query--"+query);
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEARCH);
				intent.putExtra(SearchManager.QUERY, query);
				startActivity(intent);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(newText)){
					System.out.println("newText--"+newText);
				}
				return true;
			}
		});*/
	    return true;
	}
	
}
