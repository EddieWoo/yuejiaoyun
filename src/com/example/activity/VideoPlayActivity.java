package com.example.activity;

import org.apache.http.conn.ConnectTimeoutException;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.OutlineTextView;
import io.vov.vitamio.widget.VideoView;
import com.example.constants.WebInfoConfig;
import com.example.dao.OperateDAO;
import com.example.model.PpListItem;
import com.example.model.PpVodItem;
import com.example.utils.HttpUtil;
import com.example.utils.MD5EncryptUtil;
import com.example.utils.MySysApplication;
import com.example.yuejiaoyun.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class VideoPlayActivity extends Activity implements OnRatingBarChangeListener,OnClickListener{

	private String uriString = "";//"http://219.223.194.119:8080/mp3/MissPuff3DMV.flv";
	private VideoView mVideoView;
    private RatingBar ratingbar;
    
    int count=0;
//    ���޿ؼ�
    private Button buttonone;
	private TextView textViewone;
	private android.view.animation.Animation animation;
//  ���	
	private Button buttontwo;
    private TextView textViewtwo;
    private android.view.animation.Animation cai_anim;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videoplay);
		//��Ӹ�Activity��MySysApplication����ʵ��������
		MySysApplication.getInstance().addActivity(this);
		
//	 ���޶���
		   animation=AnimationUtils.loadAnimation(VideoPlayActivity.this,R.anim.nn);
	        buttonone=(Button)findViewById(R.id.bt_one);
	        buttonone.setOnClickListener(this);
	        textViewone=(TextView)findViewById(R.id.tv_one);
	        
//	    �ȶ���
	        cai_anim=AnimationUtils.loadAnimation(VideoPlayActivity.this,R.anim.cai_anim);
	        buttontwo=(Button)findViewById(R.id.bt_two);
	        buttontwo.setOnClickListener(this);
	        textViewtwo=(TextView)findViewById(R.id.tv_two);
	        

	        
		//�õ�Intent�������������
		Intent intent = getIntent();
		final PpVodItem wantShowVod = (PpVodItem)intent.getSerializableExtra("wantShowVod");
		//��ȡxml�����ļ��ж���Ŀؼ�
		mVideoView = (VideoView)findViewById(R.id.videoView);
		TextView rootCategoryView = (TextView)findViewById(R.id.rootCategory);
		TextView subCategoryView = (TextView)findViewById(R.id.subCategory);
		TextView vod_nameView = (TextView)findViewById(R.id.vod_name);
		TextView vod_contentView = (TextView)findViewById(R.id.vod_content);
		final TextView vod_up= (TextView)findViewById(R.id.vod_up);
		final TextView vod_down= (TextView)findViewById(R.id.vod_down);




		
		
		//��ѯ���ݿ⣬�õ������ࡱ����,��Ϊ�������ࡱ�͡��ӷ��ࡱ
		OperateDAO dao = new OperateDAO(this);
		PpListItem subCategoryItem  = dao.getPpListItemByList_ID(wantShowVod.getVod_cid());
		PpListItem rootCategoryItem = null;
		if(!subCategoryItem.getList_pid().equals("0")){
			rootCategoryItem = dao.getPpListItemByList_ID(subCategoryItem.getList_pid());
		}
		//Ϊ�����ؼ��趨��ʾ����
		//��� ������ ����,Ϊ���趨��ʾֵ����������ڣ������Ƴ���GONEΪ���ò���ʾҲ��ռ�ÿռ�
		if(rootCategoryItem !=null){
			//�趨��ʾֵ
			rootCategoryView.setText(rootCategoryItem.getList_name()+" > ");
			rootCategoryView.setTag(rootCategoryItem);
			//�趨����������ת���÷���ҳ����
			rootCategoryView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(VideoPlayActivity.this,ShowVodsInSelectedPpList.class);
					PpListItem listItem =(PpListItem)view.getTag();
					intent.putExtra("list_id", Integer.parseInt(listItem.getList_id()));
					intent.putExtra("list_name", listItem.getList_name());
					startActivity(intent);
				}
			});
		}else{
			rootCategoryView.setVisibility(View.GONE);
		}
		subCategoryView.setText(subCategoryItem.getList_name());
		subCategoryView.setTag(subCategoryItem);
		subCategoryView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(VideoPlayActivity.this,ShowVodsInSelectedPpList.class);
				PpListItem listItem =(PpListItem)view.getTag();
				intent.putExtra("list_id", Integer.parseInt(listItem.getList_id()));
				intent.putExtra("list_name", listItem.getList_name());
				startActivity(intent);
			}
		});
		vod_nameView.setText(wantShowVod.getVod_name());
		vod_contentView.setText("������ܣ�"+wantShowVod.getVod_content());
		
		


		
		//
		String vodsource_id = wantShowVod.getVod_sourceid();
		System.out.println("VideoPlayActivity get vodsource_id-->" + vodsource_id);
		/*
		 * ͨ��souce_id����һ������ ���õ� �����Ĳ��ŵ�ַ
		 * 1������Ҫ�Ĳ���ƴ�ӳ��ַ���  ������ģ�������Ĭ�ϱ���Ĳ���(code��ҪMD5 32λ[Сд]����)��
		 * 		String time=Unixʱ�������ȷ���룩; String code=MD5[AccessID.time.do.sourceid]  ע��˳��
		 * 2��ƴ�ӳ������Url��ַ��get��ֵ������
		 * 		�ӿڵ�ַ host+ /service/api/?do= video(����task��) op=geturl;sourceid;type=http/m3u8
		 * 3������  2�� ��õ�Url
		 * 4���õ����
		 */
		//1���������������
		String unixTimeStamp = new WebInfoConfig().UnixTimeStamp;
		System.out.println("unixTimeStamp--"+unixTimeStamp);
		String doStr = "video";
		String preCode = WebInfoConfig.PKUsz_ACCESS_ID + unixTimeStamp + doStr + vodsource_id;
		System.out.println("preCode--"+preCode);
		String code = MD5EncryptUtil.getMD5(preCode);
		System.out.println("code--"+code);
		//�õ������ַ
		String requestUrl = WebInfoConfig.PKUsz_DEMAND_SERVER_API_URL+"service/api/?do="+doStr
				+"&op=geturl&type=http&sourceid="+vodsource_id+"&time="+unixTimeStamp+"&code="+code;
		System.out.println("requestUrl--"+requestUrl);
		//����http����
		String result = "";
		try {
			result = HttpUtil.getRequest(requestUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!result.equals("")){
			System.out.println("result--"+result);
			//uriString = parse from result;
			String temp1 = result.substring(result.indexOf("http"), result.indexOf(".flv")+".flv".length());
			//�� ����ַ����е� \/ �滻�� /
			temp1 = temp1.replace("\\", "");
			System.out.println("temp-->"+ temp1);
			uriString = temp1;
		}else System.out.println("http result is nothing");
		//�����ŵ�ַ�ǿգ��򲥷�֮
		if(!uriString.equals("")){
			
			if (!LibsChecker.checkVitamioLibs(this))
				System.out.println("check error");
			
			//mVideoView.setVideoURI(Uri.parse(uriString));
			mVideoView.setVideoPath(uriString);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();
			//mVideoView.start();
			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					// optional need Vitamio 4.0
					mediaPlayer.setPlaybackSpeed(1.0f);
				}
			});
			/*Vitamio ---VideoViewDemo �����
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 
			mVideoView.setVideoPath(path);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();

			mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					// optional need Vitamio 4.0
					mediaPlayer.setPlaybackSpeed(1.0f);
				}
			});*/
			
			

			
//			 Log.d("�����õ��޵�����Ϊ��", wantShowVod.getVod_up());
			
			
			
			//��ɲ��ţ�����WebService�ӿڷ��� addHits������
			String addHitsUrl = WebInfoConfig.ADD_HITS_URL + wantShowVod.getVod_id();
			
			try {
				HttpUtil.getRequest(addHitsUrl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ͬʱ���������ݿ���ɸ���
			if(dao.addVodHits(wantShowVod) ==1){
				System.out.println("DB addHits success");
			}else{
				System.out.println("DB addHits fail");
			}
			//��ɲ��ţ���play_history������� ��ʷ������Ϣ
			if(dao.insertVodIntoPlayHistory(wantShowVod)>0){
				//����²����Row_id��Ч�Ļ�
				System.out.println("DB play_history insert success");
			}else{
				System.out.println("DB play_history insert fail");
			}
			
		}else{
			System.out.println("VideoPlayActivity uriString error");
		}
		
		

//		��ѯ���ݿ⣬�õ����� ��ȵ�������
		String totalzan="�޵�����: "+wantShowVod.getVod_up();
		vod_up.setText(totalzan);
		String totalcai="�ȵ�����: "+wantShowVod.getVod_down();
		vod_down.setText(totalcai);
		
		
		

		
//	����	
	buttonone.setOnClickListener(new OnClickListener()
		{
			
			public void onClick(View v)
			{
//���޷������ӿڣ���������onClick����ͱ������������е�һ��������wanShowvod������Ϊfinal
				String addLikeUrl= WebInfoConfig.LIKE_HITS_URL+ wantShowVod.getVod_id();
				textViewone.setVisibility(View.VISIBLE);
				textViewone.startAnimation(animation);
//				���Ŷ���			
				new Handler().postDelayed(new Runnable(){
		            public void run() {
		            	textViewone.setVisibility(View.GONE);
		            } 
				}, 1000);
				
				
				try {
					if(count==0){
					HttpUtil.getRequest(addLikeUrl);
//					��������
					int temnum= Integer.parseInt(wantShowVod.getVod_up()) +1;
					vod_up.setText("�޵�������"+ temnum);

					Toast.makeText(getBaseContext(), "�Ѿ�����", Toast.LENGTH_SHORT).show();
					
					count++;
					}
					else{
						Toast.makeText(getBaseContext(), "�����ظ����", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}	
			}	
		});

	
	
//		��ȵĲ���
	buttontwo.setOnClickListener(new OnClickListener()
		{		
			public void onClick(View v)
			{
//��ȷ������ӿڣ���������onClick����ͱ������������е�һ��������wanShowvod������Ϊfinal
				String addDislikeUrl= WebInfoConfig.DISLIKE_HITS_URL+ wantShowVod.getVod_id();
				textViewtwo.setVisibility(View.VISIBLE);
				textViewtwo.startAnimation(cai_anim);
			//  ���Ŷ���
				new Handler().postDelayed(new Runnable(){
		            public void run() {
		            	textViewtwo.setVisibility(View.GONE);
		            } 
				}, 1000);
				
				
				try {
					if(count==0){
					HttpUtil.getRequest(addDislikeUrl);
//					��������
					int temnum1= Integer.parseInt(wantShowVod.getVod_down()) +1;
					vod_down.setText("�ȵ�������"+ temnum1);
					Toast.makeText(getBaseContext(), "�Ѿ��ȹ�", Toast.LENGTH_SHORT).show();

					
					count++;}
					else{
						Toast.makeText(getBaseContext(), "�����ظ����", Toast.LENGTH_SHORT).show();
				} 
				}
					catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}	
			}	
		});

	
	
	//����  ratingbar
	/* ����������
	   �������½ӿ�
http://219.223.192.21/testTv/service/goldupdate.php?vodid=95&vodgold=1
���룺 vodid=  ��Ŀ��ID�������ݿ��е���ĿID��
  vodgold= �û������֣�1-10֮���һ�����֣��ͻ���Ҫ�����ֿ����������Χ��*/

			final RatingBar rb = (RatingBar)findViewById(R.id.ratingBar1);
			 rb.setOnRatingBarChangeListener( new OnRatingBarChangeListener(){
				public void onRatingChanged(RatingBar arg0,float rating, boolean fromUser)
				{
				rb.setAlpha((int) (rating*255/5) );	
				String score="http://219.223.192.21/testTv/service/goldupdate.php?vodid="+ wantShowVod.getVod_id()
						+ "&vodgold="+ rating;
				 Toast.makeText(getBaseContext(), "��л��������", Toast.LENGTH_SHORT).show();
				try {
					HttpUtil.getRequest(score);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();}	
				}				
	               });
			 		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		// TODO Auto-generated method stub
	
	}



	}


