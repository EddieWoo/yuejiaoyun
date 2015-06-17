package com.example.guide;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.yuejiaoyun.R;
import com.example.activity.WelcomeActivity;
import com.example.activity.MainActivity;
/**
 * 
 * @{# SplashActivity.java Create on 2013-5-2 ����9:10:01
 * 
 *     class desc: �������� (1)�ж��Ƿ����״μ���Ӧ��--��ȡ��ȡSharedPreferences�ķ���
 *     (2)�ǣ������GuideActivity���������MainActivity (3)3s��ִ��(2)����
 * 
 *     <p>
 *     Copyright: Copyright(c) 2013
 *     </p>
 * @Version 1.0
 * @Author <a href="mailto:gaolei_xj@163.com">Leo</a>
 * 
 * 
 */
public class SplashActivity extends Activity {
	boolean isFirstIn = false;

	private static final int GO_HOME = 3000;
	private static final int GO_GUIDE = 1001;
	// �ӳ�3��
	private static final long SPLASH_DELAY_MILLIS = 4000;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	/**
	 * Handler:��ת����ͬ����
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

	}
	

	@Override
	protected void onStart() {
		ConnectivityManager manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			Toast.makeText(SplashActivity.this, "����������", 0).show();
			
/*		�������磬�����welcomeactivity
	         ������������������ж�����û������ʱ������������logcat��ʾnullpointerexception��cannot create 
	    LoadPplistService�������ڽ������֮ǰ�ͽ��������жϣ���ӽ��û���������������*/
			init();
		} else {
			AlertDialog.Builder builder = new Builder(SplashActivity.this);
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
							
//							�����ȡ�����Ƴ�����
							finish();
						}
					});
			builder.show();
		}
		super.onStart();
		
	}



	private void init() {
		// ��ȡSharedPreferences����Ҫ������
		// ʹ��SharedPreferences����¼�����ʹ�ô���
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);

		// ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ
		isFirstIn = preferences.getBoolean("isFirstIn", true);

		// �жϳ�����ڼ������У�����ǵ�һ����������ת���������棬������ת��������
		if (!isFirstIn) {
			// ʹ��Handler��postDelayed������3���ִ����ת��MainActivity
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}

	}

	public void goHome() {
		Intent intent = new Intent(SplashActivity.this,WelcomeActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(SplashActivity.this,WelcomeActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}
}
