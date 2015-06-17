package setting;


import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.activity.MainActivity;
import com.example.activity.webpageloader;
import com.example.yuejiaoyun.R;
import setting.ReportOval;

public class setting extends Activity {
	
    private Button cachebutton =null;
//	private Button updatebutton = null;
	private Button cleansetting=null;
	private Button traffic=null;
    private Button about=null;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		 
		
	
		//���ü�����
		      cachebutton=(Button)findViewById(R.id.cache);
			  cachebutton.setOnClickListener(new ButtonListener());
		 
//			  updatebutton=(Button)findViewById(R.id.update);
//				���ü�����
//		      updatebutton.setOnClickListener(new MyButtonListener());	
		      
		      cleansetting=(Button)findViewById(R.id.cleansetting);
		      cleansetting.setOnClickListener(new cleanListener());
		      
		      traffic=(Button)findViewById(R.id.traffic);
		      traffic.setOnClickListener(new trafficListener());
		      
		      about=(Button)findViewById(R.id.about);
		      about.setOnClickListener(new aboutListener());
		      
		      final ActivityManager activityManager = (ActivityManager)   getSystemService(ACTIVITY_SERVICE);    
		      
		        ActivityManager.MemoryInfo   info = new ActivityManager.MemoryInfo();   
		   
		          activityManager.getMemoryInfo(info);    
		   
//		          Log.i(ACCESSIBILITY_SERVICE,"ϵͳʣ���ڴ�:"+(info.availMem >> 10)+"k");    
//		          Log.i(ACCESSIBILITY_SERVICE,"ϵͳ�Ƿ��ڵ��ڴ����У�"+info.lowMemory);
//		          Log.i(ACCESSIBILITY_SERVICE,"��ϵͳʣ���ڴ����"+info.threshold+"ʱ�Ϳ��ɵ��ڴ�����");
		          
		          long lowmm=info.availMem >>20;
		          long allmm=info.totalMem>>20;
		          
		    ReportOval myOval = (ReportOval) findViewById(R.id.oval);
		  	myOval.setWeights(new float[]{lowmm,1000-lowmm});
		  	myOval.setValues(new String[]{"�����ڴ�"+lowmm+"M","ȫ���ڴ�"+allmm+"M"});
		  	myOval.setColors(new int[]{Color.YELLOW,Color.GREEN});
		  	myOval.setTopOvalColor(Color.WHITE);
//		  	myOval.setLineWidth(0);
		  	
	}

	class ButtonListener extends DataCleanManager implements OnClickListener{
    	
		//���ɸ���Ķ��󣬲�����ע�ᵽ�ؼ��ϡ�����ÿؼ����û����£��ͻ�ִ��onClick���� 
		@Override
		public void onClick(View v) {
			
			  cleanInternalCache(getBaseContext());
			  cleanExternalCache(getBaseContext());
		      cleanFiles(getBaseContext());
		       
		      
		      Toast toast=Toast.makeText(getApplicationContext(),"���������",Toast.LENGTH_SHORT);
		      toast.show();
				}
		}
	
	
	
	class MyButtonListener implements OnClickListener{
    	//���ɸ���Ķ��󣬲�����ע�ᵽ�ؼ��ϡ�����ÿؼ����û����£��ͻ�ִ��onClick���� 
		@Override
		public void onClick(View v) {
//������
			  Toast toast=Toast.makeText(getApplicationContext(),"�������°�",Toast.LENGTH_SHORT);
		      toast.show();
		}
	}
	
	
	
	class trafficListener implements OnClickListener{
		
		public void onClick(View v) {
//����ͳ��
		   Intent intent001=new Intent();		
		   intent001.setClass(setting.this, trafficstats.class);
		   setting.this.startActivity(intent001);
					}
		
	}
	
	
//	����ҳ�������
   class aboutListener implements OnClickListener{
		
		public void onClick(View v) {
//����ͳ��
		   Intent intent0011=new Intent();		
		   intent0011.setClass(setting.this, about.class);
		   setting.this.startActivity(intent0011);
					}
		
	}
	
	
	
	
	class cleanListener extends DataCleanManager implements OnClickListener{
    	//���ɸ���Ķ��󣬲�����ע�ᵽ�ؼ��ϡ�����ÿؼ����û����£��ͻ�ִ��onClick���� 
		@Override
		public void onClick(View v) {
			
		      cleanDatabases(getBaseContext());
		      cleanSharedPreference(getBaseContext());
		      
		      Toast toast=Toast.makeText(getApplicationContext(),"�ղغ����������",Toast.LENGTH_SHORT);
		      toast.show();
			
		}
	}
	

	
	
	
	public class DataCleanManager {
	    /** * �����Ӧ���ڲ�����(/data/data/com.xxx.xxx/cache) * * @param context */
	    public void cleanInternalCache(Context context) {
	        deleteFilesByDirectory(context.getCacheDir());
	    }

	    /** * �����Ӧ���������ݿ�(/data/data/com.xxx.xxx/databases) * * @param context */
	    public void cleanDatabases(Context context) {
	        deleteFilesByDirectory(new File("/data/data/"
	                + context.getPackageName() + "/databases"));
	    }

	    /**
	     * * �����Ӧ��SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
	     * context
	     */
	    public void cleanSharedPreference(Context context) {
	        deleteFilesByDirectory(new File("/data/data/"
	                + context.getPackageName() + "/shared_prefs"));
	    }

	    /** * �����������Ӧ�����ݿ� * * @param context * @param dbName */
	    public void cleanDatabaseByName(Context context, String dbName) {
	        context.deleteDatabase(dbName);
	    }

	    /** * ���/data/data/com.xxx.xxx/files�µ����� * * @param context */
	    public void cleanFiles(Context context) {
	        deleteFilesByDirectory(context.getFilesDir());
	    }

	    /**
	     * * ����ⲿcache�µ�����(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
	     * context
	     */
	    public void cleanExternalCache(Context context) {
	        if (Environment.getExternalStorageState().equals(
	                Environment.MEDIA_MOUNTED)) {
	            deleteFilesByDirectory(context.getExternalCacheDir());
	        }
	    }

	    /** * ����Զ���·���µ��ļ���ʹ����С�ģ��벻Ҫ��ɾ������ֻ֧��Ŀ¼�µ��ļ�ɾ�� * * @param filePath */
	    public void cleanCustomCache(String filePath) {
	        deleteFilesByDirectory(new File(filePath));
	    }

	    /** * �����Ӧ�����е����� * * @param context * @param filepath */
	    public void cleanApplicationData(Context context, String... filepath) {
	        cleanInternalCache(context);
	        cleanExternalCache(context);
	        cleanDatabases(context);
	        cleanSharedPreference(context);
	        cleanFiles(context);
	        
	        for (String filePath : filepath) {
	            cleanCustomCache(filePath);
	        }
	    }

	    
	    /** * ɾ������ ����ֻ��ɾ��ĳ���ļ����µ��ļ�����������directory�Ǹ��ļ������������� * * @param directory */
	    public void deleteFilesByDirectory(File directory) {
	        if (directory != null && directory.exists() && directory.isDirectory()) {
	            for (File item : directory.listFiles()) {
	                item.delete();
	            }
	        }
	    }
	}
	

}

