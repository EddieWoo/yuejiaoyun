package com.example.activity;

import java.lang.reflect.Method;
import com.example.fragment.ChosenVodFragment;
import com.example.fragment.LatestVodFragment;
import com.example.fragment.SpeciesVodFragment;
import com.example.utils.MySysApplication;
import com.example.yuejiaoyun.R;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.ActionBar.Tab;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import com.igexin.sdk.PushManager;
import setting.setting;

public class MainActivity extends FragmentActivity 
	implements ActionBar.TabListener{
	
	ViewPager viewPager;
	ActionBar actionBar;
	
	//�����жϡ����ΰ����ؼ����˳����ĵ�ǰʱ��
	private long mExitTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
     	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PushManager.getInstance().initialize(this.getApplicationContext());
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		//���ݿؼ�ID�õ��ؼ�
		//��Ӹ�Activity��MySysApplication����ʵ��������
		MySysApplication.getInstance().addActivity(this);
		
		// ��ȡActionBar����
		actionBar = getActionBar();
		// ��ȡViewPager
		viewPager = (ViewPager) findViewById(R.id.pager);
		// ����һ��FragmentPagerAdapter���󣬸ö�����ΪViewPager�ṩ���Fragment
		FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(
				getSupportFragmentManager())
		{
			// ��ȡ��positionλ�õ�Fragment
			@Override
			public Fragment getItem(int position) {
				// TODO Auto-generated method stub
				switch(position)
				{//˳�������� �����ࡱ������ѡ���������¡�
					case 0:
						SpeciesVodFragment speciesVodFragment = new SpeciesVodFragment();
						return speciesVodFragment;
					case 1:
						ChosenVodFragment chosenVodFragment = new ChosenVodFragment();
						return chosenVodFragment;
					case 2:
						LatestVodFragment latestVodFragment = new LatestVodFragment();
						return latestVodFragment;
				}
				return null;
		}

			// �÷����ķ���ֵi������Adapter�ܹ��������ٸ�Fragment
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 3;
			}
		};
		// ����ActionBarʹ��Tab������ʽ
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Ϊÿ��Fragment��Ӧ����Tab��ǩ
		Tab tab = actionBar.newTab().setText("����").setTabListener(this);
		actionBar.addTab(tab);
		tab = actionBar.newTab().setText("��ѡ").setTabListener(this);
		actionBar.addTab(tab);
		tab = actionBar.newTab().setText("����").setTabListener(this);
		actionBar.addTab(tab);
		// ΪViewPager�������FragmentPagerAdapter
		viewPager.setAdapter(pagerAdapter);
		// ΪViewPager������¼�������
		viewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener()
				{
					// ��ViewPager��ʾ��Fragment�����ı�ʱ�����÷���
					@Override
					public void onPageSelected(int position)
					{
						actionBar.setSelectedNavigationItem(position);
					}
				});
		
		
		
		Log.d("��������", "initializing sdk...");
     	PushManager.getInstance().initialize(this.getApplicationContext());
		
		

	}//onCreate()����
	
	
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	// ��ָ��Tab��ѡ��ʱ�����÷���
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
 
	//��дonKeyDown���� 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//���� BACK�����¼�����
		if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
            	MySysApplication.getInstance().exit();
            }
            return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	//�����˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//��reflect������� ���� menu item ������ʾ��Icon
		setIconEnable(menu, true);
		getMenuInflater().inflate(R.menu.menu_main_activity, menu);
		
		// Get the SearchView and set the searchable configuration  searchButton
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_view).getActionView();
	    
	    // Assumes current activity is the searchable activity 
	    //ΪSearchView�趨searchable.xml�ж���ĸ�����
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    //�趨 ������ һ��ʼ �� ��Ϊͼ�� �����
	    searchView.setIconifiedByDefault(true);
		/*
		//��ȡ��SearchView
		SearchView searchView = (SearchView)menu.findItem(R.id.search_menu).getActionView();
		//���� ��SearchView ��Ĭ����ʾ����ʾ�ı�
		searchView.setQueryHint("����");
		//���ø�SearchView��ʾ ���� ��ť
		searchView.setSubmitButtonEnabled(true);
		
		
		View mainView = getCurrentFocus();
		//Ϊ��SearchView��������¼�������
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			View searchContentView = getLayoutInflater().inflate(R.layout.activity_showvods_in_selectedpplist, null);
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				System.out.println("submit--"+ query);
				//actionBar.setDisplayShowCustomEnabled(true);
				//actionBar.setCustomView(searchContentView);
				//searchContentView.setAlpha(0.5f);
				//setContentView(searchContentView);
				LayoutParams params = searchContentView.getLayoutParams();
				params.
				searchContentView.setLayoutParams(params);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(newText)){
					System.out.println("newText--"+newText);
				}

				return false;
			}
		});*/
		return true;
	}
	//�˵���������Ļص�����
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		/*�ܺõĲ˵���ѡ����Ӧ����ʾ�� http://blog.sina.com.cn/s/blog_798c1d510101d5ln.html*/
		switch(item.getItemId()){
			/*case R.id.menu_search_view:
				Intent intent = new Intent(this,SearchableActivity.class);
				startActivity(intent);
				break; */
			case R.id.menu_exit:
				exitAlert("���Ҫ�˳���");
				break;
			case R.id.menu_playHistory: //��ת��PlayHistoryActivity
				Intent intent = new Intent(this,PlayHistoryActivity.class);
				startActivity(intent);
				break;
			case R.id.menu_share:
				 Intent intent001=new Intent(Intent.ACTION_SEND);
			      
				 intent001.setType("text/plain");
			      intent001.putExtra(Intent.EXTRA_SUBJECT, "����");
			      intent001.putExtra(Intent.EXTRA_TEXT, "С����ǣ��������������ƿͻ��ˣ���������������Ҳ���õ����ҵ�ѧϰ");
			      startActivity(Intent.createChooser(intent001, getTitle()));
			      break;
			      
			case R.id.menu_search:
				 Intent intent4=new Intent();
				   intent4.setClass(MainActivity.this, search.class);
				   MainActivity.this.startActivity(intent4);
					break;
			case R.id.menu_test:
				 Intent intent003=new Intent();
				   intent003.setClass(MainActivity.this, webpageloader.class);
				   MainActivity.this.startActivity(intent003);
					break;
					
			case R.id.menu_likes:
				 Intent intent004=new Intent();
				   intent004.setClass(MainActivity.this, PlayLikesActivity.class);
				   MainActivity.this.startActivity(intent004);
		          break;
		          
		          
			case R.id.menu_setting:
				 Intent intent005=new Intent();
				   intent005.setClass(MainActivity.this, setting.class);
				   MainActivity.this.startActivity(intent005);
					break;
		}
		//super.onOptionsItemSelected(item)
		return super.onOptionsItemSelected(item);
	}
	//enableΪtrueʱ���˵����ͼ����Ч��enableΪfalseʱ��Ч��4.0ϵͳĬ����Ч
	/**
	 * ���android4.0ϵͳ�в˵�(Menu)���Icon��Ч���⣬�ο���  http://blog.csdn.net/stevenhu_223/article/details/9705173
	 * ��Android4.0ϵͳ�У������˵�Menu��ͨ��setIcon�������˵����ͼ������Ч�� ,��Ϊ �˵���Դ���� MenuBuilder���˸ı䣬Ĭ��private boolean mOptionalIconsVisible = false;  
	 * �����÷����ˣ��ڴ������д����˵���ʱ��ͨ���������setOptionalIconsVisible��������mOptionalIconsVisibleΪtrue��
	 * Ȼ���ڸ��˵����Icon�������Ϳ����ڲ˵�����ʾ��ӵ�ͼ����
	 * @param menu
	 * @param enable
	 */
    private void setIconEnable(Menu menu, boolean enable)
    {
    	try 
    	{
			Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
			m.setAccessible(true);
			
			//MenuBuilderʵ��Menu�ӿڣ������˵�ʱ����������menu��ʵ����MenuBuilder����(java�Ķ�̬����)
			m.invoke(menu, enable);
    		
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
    }
    
    private void exitAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        ShareSDK.stopSDK(this);
        builder.setMessage(msg).setCancelable(false)
                .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

                	
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        //finish(); �������Ժ��ָ㲻����
                        //System.exit(0);  �������Ժ��ָ㲻����
                    	MySysApplication.getInstance().exit();
                    	
                    }
                })
                .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        return;
                    }
                }).create().show();
    }
}