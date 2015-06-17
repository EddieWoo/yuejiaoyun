package com.example.activity;


import KeywordsView.KeywordsView;
import info.info;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import voicesearch.MyStt1Activity;
import com.example.yuejiaoyun.R;
import download.HttpDownloader;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import voicesearch.MyStt1Activity;


public class search extends Activity implements View.OnClickListener {
	private String[] totalKeys = null;
	private String[] key_words = new String[15];
	protected static final String TAG = "SearchPage";

	private KeywordsView showKeywords = null;
	private LinearLayout searchLayout = null;
	public String mykeywords=" ";
	

	/**
	 * ������
	 */

	private GestureDetector mggd;
	/**
	 * �ж�������ҳ�滹����ҳ��
	 */
	private boolean isOutter;

	
	

	public EditText keywords;
	 Button searchbutton;
	 private Button voicesearch =null;
	 TextView  result;
	 
	    HttpPost httppost;
	    StringBuffer buffer;
	    HttpResponse response;
	    HttpClient httpclient;
	    List<NameValuePair> nameValuePairs;
	  
	     
	      
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.searchpage);
		
		
		
		
	       final EditText editText1 =(EditText)findViewById(R.id.search_Keywords);
			//	  String s1="http://219.223.192.21/testTv/service/searchvod.php?keyword="+mykeywords;
					searchbutton=(Button)findViewById(R.id.search_button);
					
					voicesearch=(Button)findViewById(R.id.voicesearch);
					voicesearch.setOnClickListener(new voiceButtonListener());
				
				
					
		searchLayout = (LinearLayout) this.findViewById(R.id.searchContent);

		showKeywords = (KeywordsView) this.findViewById(R.id.word);
		showKeywords.setDuration(2000l);
		showKeywords.setOnClickListener(this);
		this.mggd = new GestureDetector(new Mygdlinseter());
	
		
		searchbutton=(Button)findViewById(R.id.search_button);
		
		//���ü�����
	//	searchbutton.setOnClickListener(new Button1Listener());
		
		showKeywords.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return mggd.onTouchEvent(event); // ע�����¼�
			}
		});
		isOutter = true;

		handler.sendEmptyMessage(Msg_Start_Load);	
		
		
		searchbutton.setOnClickListener(new OnClickListener() {
	    	  String s0="http://219.223.192.21/testTv/service/searchvod.php?keyword=";
             
		  
           public void onClick(View v) {
	                 
          //  String s1="http://219.223.192.21/testTv/service/searchvod.php?keyword="+mykeywords;
        	                    mykeywords=editText1.getText().toString();
        	                    try {
        	                    	
									String urlstr=URLEncoder.encode(mykeywords,"UTF-8");
									String s1=s0+urlstr;
								
        	                    
	                        	Intent intent = new Intent();
	                        	
	                        	// intent.putExtra("searchurl",s1);  
	                        	Bundle bundle=new Bundle();
	                        	bundle.putString("searchurl",s1);
	                 			//��Intent���������һ����ֵ��
	                 			//����Intent����Ҫ������Activity
	                 			intent.setClass(search.this,getsearchresult.class);
	                 			intent.putExtras(bundle);
	                 			//ͨ��Intent������������һ��Activity
	                 			startActivity(intent);
	                 			
	                 			// Log.d("�����������ؼ���",s1);
	                 			// Log.d("�����Ĺؼ���",mykeywords);
	                 			finish();
        	                    } catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
   }
		});
		 }
	
	

	   
	 class voiceButtonListener implements OnClickListener{
	    	//���ɸ���Ķ��󣬲�����ע�ᵽ�ؼ��ϡ�����ÿؼ����û����£��ͻ�ִ��onClick���� 
			@Override
			public void onClick(View v) {
				//����һ��Intent����
				Intent intent = new Intent();
				//��Intent���������һ����ֵ��
				//����Intent����Ҫ������Activity
				intent.setClass(search.this,MyStt1Activity.class);
				//ͨ��Intent������������һ��Activity
				search.this.startActivity(intent);}
	 }    
	       
	    
		
		//�������ܵ�ʵ��
		

	private String[] getRandomArray() {
		if (totalKeys != null && totalKeys.length > 0) {
			String[] keys = new String[15];
			List<String> ks = new ArrayList<String>();
			for (int i = 0; i < totalKeys.length; i++) {
				ks.add(totalKeys[i]);
			}
			for (int i = 0; i < keys.length; i++) {
				int k = (int) (ks.size() * Math.random());
				keys[i] = ks.remove(k);
				if (keys[i] == null)
					System.out.println("nulnulnulnulnul");
			}
			System.out.println("result's length = " + keys.length);
			return keys;
		}
		return new String[] { "���Ҽ�", "��Ӱ", "English", "����", "����ɲɣ���¶δ��", "���ɶ���", "����", "�����غ�",
				"������ɫ", "", "English", "ţ�ٶ���", "�˴Ź���", "��ʫ", "�������ٶ�", "����" };
	}

	private static final int Msg_Start_Load = 0x0102;
	private static final int Msg_Load_End = 0x0203;

	private LoadKeywordsTask task = null;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Msg_Start_Load:

				task = new LoadKeywordsTask();
				new Thread(task).start();

				break;
			case Msg_Load_End:
				showKeywords.rubKeywords();
				feedKeywordsFlow(showKeywords, key_words);
				showKeywords.go2Shwo(KeywordsView.ANIMATION_IN);
				break;
			}

		}
	};

	private class LoadKeywordsTask implements Runnable {
		@Override
		public void run() {
			try {

				key_words = getRandomArray();
				if (key_words.length > 0)
					handler.sendEmptyMessage(Msg_Load_End);
			} catch (Exception e) {
			}
		}
	}

	private void feedKeywordsFlow(KeywordsView keyworldFlow, String[] arr) {
		for (int i = 0; i < KeywordsView.MAX; i++) {
			String tmp = arr[i];
			keyworldFlow.feedKeyword(tmp);
		}
	}

	class Mygdlinseter implements OnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e2.getX() - e1.getX() > 100) { // �һ�
				key_words = getRandomArray();
				showKeywords.rubKeywords();
				feedKeywordsFlow(showKeywords, key_words);
				showKeywords.go2Shwo(KeywordsView.ANIMATION_OUT);
				return true;
			}
			if (e2.getX() - e1.getX() < -100) {// ��
				key_words = getRandomArray();
				showKeywords.rubKeywords();
				feedKeywordsFlow(showKeywords, key_words);
				showKeywords.go2Shwo(KeywordsView.ANIMATION_IN);
				return true;
			}
			if (e2.getY() - e1.getY() < -100) {// �ϻ�
				key_words = getRandomArray();
				showKeywords.rubKeywords();
				feedKeywordsFlow(showKeywords, key_words);
				showKeywords.go2Shwo(KeywordsView.ANIMATION_IN);
				return true;
			}
			if (e2.getY() - e1.getY() > 100) {// �»�
				key_words = getRandomArray();
				showKeywords.rubKeywords();
				feedKeywordsFlow(showKeywords, key_words);
				showKeywords.go2Shwo(KeywordsView.ANIMATION_OUT);
				return true;
			}
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		System.out.println("V" + v);
		// TODO Auto-generated method stub
		if (isOutter) {
			isOutter = false;

			String kw = ((TextView) v).getText().toString();
			Log.i(TAG, "keywords = " + kw);
			if (!kw.trim().equals("")) {
				searchLayout.removeAllViews();

			}
			Toast.makeText(this,
					"ѡ�е������ǣ�" + ((TextView) v).getText().toString(), 1).show();
		}

	}

	/**
	 * �����ذ����¼�
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			if (!isOutter) {
				isOutter = true;
				searchLayout.removeAllViews();
				searchLayout.addView(showKeywords);
				/**
				 * ��������Ϊ���ɶ���
				 */

				/**
				 * �����������
				 */

			} else {
				search.this.finish();
				/**
				 * ִ�з��ذ�������
				 */

			}

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}


	
	
}
