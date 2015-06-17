package com.example.activity;


import java.util.Timer;
import java.util.TimerTask;

import com.example.utils.MySysApplication;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.yuejiaoyun.R;
import com.igexin.sdk.PushManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	//��ȡ���������ơ�����ͼƬ
	ImageView welcome_background = null;
	//�趨 Alpha���ֶ�������ʱ�䣨�༴ ҳ����תʱ�䣩
	private final static long DurationMillis = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//�� http://blog.csdn.net/wangjinyu501/article/details/8140588 ��ѧ��
		//���� �˵���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		
		
		network();
		
		
		
		PushManager.getInstance().initialize(this.getApplicationContext());
		//��Ӹ�Activity��MySysApplication����ʵ��������
		MySysApplication.getInstance().addActivity(this);
		
		//ʹȫ����ʾ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//System.out.println("welcomeActivity begin-->"+new WebInfoConfig().UnixTimeStamp);;
		/*
		 * ����Service������ݸ���
		 */
		System.out.println("welcomeactivity--service--begin");
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.example.service.LOAD_PPLIST");
     	startService(serviceIntent);
		System.out.println("welcomeactivity--service--end");
		/**
		 * ==============����1�����Alpha����==================
		
		welcome_background = (ImageView)findViewById(R.id.welcome_background);
		//�ô����趨 Alpha�������½�һ���������ϣ�����ʢ��AlphaAnimation
		AnimationSet animationSet = new AnimationSet(true);
		//�趨�仯���̣��ӿɼ���Ϊ0.5 ��� ��ȫ�ɼ� 1  //���������ֱ��ʾ��ʼ͸���Ⱥ�Ŀ��͸���ȣ�1��ʾ��͸����0��ʾ��ȫ͸��
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1);
		//���ö�������ʱ��
		alphaAnimation.setDuration(DurationMillis);
		//���뵽����������
		animationSet.addAnimation(alphaAnimation);
		//��ʼ����Ч��
		welcome_background.startAnimation(animationSet);
		//==============����1  ����==================
		
		
		*/
		/**
		 * ����ʾAlpha������ͬʱ����ɳ�����Լ죺
		 * 1�����汾��
		 * 2����û���ҵ���������ݿ⣬����Ҫ��������������
		 */
		//����ɣ���Ҫ�õ����ݿ���� ��  ʹ�� Apache HttpClient ����WebService��ð汾��
		
		
		/*�����Ƿ�߱�SD��Ȩ��
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			System.out.println("�߱�����SD��Ȩ��");
		}*/
		
		
		/**
		 * �Լ����ݿ� OK  
		 * ���ǳ��򱨴�The application may be doing too much work on its main thread.
		 * ���º���Ķ���Ч��û�ˣ����Դ����ڴ� �¿��߳�����Լ�
		 * 2014-3-28  16:53
		 
		DBOpenHelper dbOpenHelper = new DBOpenHelper(this, "yjy.db", null, 1);
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into pp_list values(1,0,2,1,'zhongxue','pp_vodchannel','movie',null,null,null)");
		System.out.println("�������ݳɹ�");
		*/
		
		/**
		 * ������LoadDBAsyncTask�����첽����������Լ�
		 */
		//LoadDBAsyncTask loadDBAsyncTask = new LoadDBAsyncTask();

		//loadDBAsyncTask.execute();


		
	
		//System.out.println("welcomeActivity end-->"+new WebInfoConfig().UnixTimeStamp);;
		System.out.println("beigin timerTask");
		/**
		 * =====����Timer��ʱ���� �������ת=====
	
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent mainActivityIntent = new Intent();
				mainActivityIntent.setClass(WelcomeActivity.this, MainActivity.class);
				startActivity(mainActivityIntent);
				
			}
		}, DurationMillis*2);*/
		
	
	  new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// �����ת
				Intent mainActivityIntent = new Intent();
				mainActivityIntent.setClass(WelcomeActivity.this, MainActivity.class);
				startActivity(mainActivityIntent);
				//�ص���ǰActivity
				finish();
			}
		}, DurationMillis*2);

		//=====����Timer��ʱ���� �������ת  over===== 
	}
	

	protected void network() {
		ConnectivityManager manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			Toast.makeText(WelcomeActivity.this, "������������", 0).show();
		} else {
			AlertDialog.Builder builder = new Builder(WelcomeActivity.this);
			builder.setTitle("�����������");
			builder.setMessage("����û�����ӣ��뵽���ý����������ã�");
			builder.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (android.os.Build.VERSION.SDK_INT > 10) {
								// 3.0���ϴ����ý��棬Ҳ����ֱ����ACTION_WIRELESS_SETTINGS�򿪵�wifi����
								startActivity(new Intent(
										android.provider.Settings.ACTION_SETTINGS));
							} else {
								startActivity(new Intent(
										android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							}
							dialog.cancel();
						}
					});

			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.show();
		}
		super.onStart();
	}
	

	
}
