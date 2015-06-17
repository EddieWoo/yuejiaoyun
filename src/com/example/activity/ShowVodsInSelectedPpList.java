package com.example.activity;

import info.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.constants.DBInfoConfig;
import com.example.dao.DBOpenHelper;
import com.example.dao.OperateDAO;
import com.example.model.PpVodItem;
import com.example.yuejiaoyun.R;
import com.example.utils.ActivityVodAdapter;
import com.example.utils.MySysApplication;
import com.example.dao.OperateDAO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class ShowVodsInSelectedPpList extends Activity {
	//�����첽���ص�Drawable ����keyΪvod_pic
	Map<String,Drawable> drawableMap = new HashMap<String, Drawable>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showvods_in_selectedpplist);
		//��Ӹ�Activity��MySysApplication����ʵ��������
		MySysApplication.getInstance().addActivity(this);
		
		//�����ת������Intent���󣬿���ֱ�Ӵ�Intent��ȡ����Я����Ĭ��bundle���ݰ�
		Intent intent = getIntent();
		int list_id = intent.getIntExtra("list_id", -1);
		//if(list_id == -1) error
		String list_name = intent.getStringExtra("list_name");
		//���ݿ��������list_id����÷����µ�����vod
		OperateDAO dao = new OperateDAO(this);
		
		List<PpVodItem> vodItems = dao.getVodsByPpList(String.valueOf(list_id));
		if(!vodItems.isEmpty()){//���������ǿ�
			//��ӡһ�½����
			/*for (PpVodItem ppVodItem : vodItems) {
				System.out.println(ppVodItem.toString());
			}*/
			
			/*ע�͵� ----�����ÿ��vod��Drawalbeͼ��
			for (PpVodItem ppVodItem : vodItems) {
				String vod_id = ppVodItem.getVod_id();
				String vod_pic = ppVodItem.getVod_pic();
				AsyncImageLoader loader = new AsyncImageLoader();
				//loader.loadDrawable(vod_pic, this);
				Drawable tempDrawable  = loader.loadImageFromUrl(vod_pic);
				drawableMap.put(vod_pic, tempDrawable);
			}*/
			/*����Map���鿴�� �Ƿ������� ʹ��adapter֮ǰ���Ѿ���drawableMap������
			 Set<String> key = drawableMap.keySet();
		        for (Iterator it = key.iterator(); it.hasNext();) {
		            String s = (String) it.next();
		            System.out.println("mapKey--"+s);
		            //System.out.println("drawableMap-->"+s+"---"+drawableMap.get(s).toString());
		        }*/
			System.out.println("ΪListView��������");
			ListView vodsList = (ListView)findViewById(R.id.vodsList);
			/* ��adapter��������֮ǰ����Ҫ������Դ��װ��List<Map>����
			List<Map<String,Object>> adapterData = new ArrayList<Map<String,Object>>();
			for (PpVodItem ppVodItem : vodItems) {
				Map<String,Object> map = new HashMap<String, Object>();
				//vod��ṹ vod_id integer primary key,vod_cid,vod_name,vod_sourceid,vod_pic
				map.put("vod_id", ppVodItem.getVod_id());
				map.put("vod_cid", ppVodItem.getVod_cid());
				map.put("vod_name", ppVodItem.getVod_name());
				map.put("vod_sourceid", ppVodItem.getVod_sourceid());
				map.put("vod_pic", ppVodItem.getVod_pic());
				adapterData.add(map);
			}
			
			���Է���simpleadapter���ܲ����Խ��ͼƬ���첽�������⣬�ʼ̳�BaseAdapter�������Լ���adapter
			SimpleAdapter adapter = new SimpleAdapter(this, adapterData, R.layout.simple_showvods_info_item,
					new String[]{"vod_id","vod_cid","vod_name","vod_sourceid","vod_pic"}, 
					new int[]{R.id.vod_id,R.id.vod_cid,R.id.vod_name,R.id.vod_sourceid,R.id.vod_pic});
					*/
			ActivityVodAdapter activityVodAdapter = new ActivityVodAdapter(this, vodItems);
			vodsList.setAdapter(activityVodAdapter);
			
			
//����ѡ���ղ�
			vodsList.setOnItemLongClickListener(new OnItemLongClickListener(){  
				
			    public boolean onItemLongClick(AdapterView<?> arg0, View view,  
			            int arg2, long arg3) {  
			        // TODO Auto-generated method stub  
			    	System.out.print("�ղ���Ŀ");
			        islikes("ȷ��Ҫ�ղ���");
			        
// �����ղز���
			    	PpVodItem selectedlikes=(PpVodItem)view.getTag();  
			    	OperateDAO newdao =new OperateDAO(getBaseContext());
  				    newdao.insertVodIntoPlayLikes(selectedlikes);
  				  System.out.print("�ղ�����Ŀ");
			        return true;  
			    }  
			  });
				
//	�̰��������		
			vodsList.setOnItemClickListener(new OnItemClickListener() {
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
		}else System.out.println("���˰���ɶ��û��");		

		TextView textView = (TextView)findViewById(R.id.ppListInfo);
		textView.setText(list_name + list_id);
		
	}

	/*ʵ�� �ӿ��ж���ķ���, Ŀǰ�ô��벻����Ҫ��ʹ��
	@Override
	public void addLoadedImage(String vod_pic,Drawable imageDrawable) {
		// TODO Auto-generated method stub
		drawableMap.put(vod_pic, imageDrawable);
	}*/

	
//	����Ҫ���õĺ���
  private void islikes(String msg) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage(msg).setCancelable(false)
              .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

                  @Override
                  public void onClick(DialogInterface arg0, int arg1) {
                      // TODO Auto-generated method stub
                	  
                	  
/*                      �����ղر���Ӧ��������У����������������������������
                	  PpVodItem selectedlikes=(PpVodItem)view.getTag();  
  			    	OperateDAO newdao =new OperateDAO(getBaseContext());
    				    newdao.insertVodIntoPlayLikes(selectedlikes);
    				  System.out.print("�ղ�����Ŀ");
             */        

                       return;
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
