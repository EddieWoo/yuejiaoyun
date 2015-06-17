package com.example.utils;

import java.util.List;

import com.example.dao.OperateDAO;
import com.example.model.PlayHistoryItem;
import com.example.model.PpVodItem;
import com.example.yuejiaoyun.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayHistoryItemAdapter extends BaseAdapter {

	public Context context;
	public List<PlayHistoryItem> historyItems;
	private OperateDAO dao;
	
	public PlayHistoryItemAdapter(Context context,
			List<PlayHistoryItem> historyItems) {
		super();
		this.context = context;
		this.historyItems = historyItems;
		dao = new OperateDAO(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return historyItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return historyItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return Integer.parseInt(historyItems.get(arg0).getHis_id());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		PlayHistoryItem wantShowHistoryItem = historyItems.get(position);
		PpVodItem wantShowVod = dao.getVodByVod_id(wantShowHistoryItem.getVod_id());
		convertView = LayoutInflater.from(context).inflate(R.layout.adapter_show_playhistory_info_item, null);
		//��ȡplaytime
		TextView playtimeView = (TextView)convertView.findViewById(R.id.playtime);
		playtimeView.setText("����ʱ�䣺"+TransformDateAndString.TimeStamp2Date(wantShowHistoryItem.getPlaytime(), "yyyy-MM-dd HH:mm:ss"));
		
		//��ȡ�����ؼ� �� ��ʾ vod,��ActivityVodAdapter�еĹ�������
		TextView vod_idView=(TextView)convertView.findViewById(R.id.vod_id);
		TextView vod_cidView=(TextView)convertView.findViewById(R.id.vod_cid);
		TextView vod_nameView=(TextView)convertView.findViewById(R.id.vod_name);
		TextView vod_sourceidView=(TextView)convertView.findViewById(R.id.vod_sourceid);
		ImageView vod_picView = (ImageView)convertView.findViewById(R.id.vod_pic);
		TextView vod_addtimeView=(TextView)convertView.findViewById(R.id.vod_addtime);
		TextView vod_hitsView=(TextView)convertView.findViewById(R.id.vod_hits);
		TextView vod_contentView=(TextView)convertView.findViewById(R.id.vod_content);
		//Ϊ�����ؼ�����ֵ
		vod_idView.setText("id:"+wantShowVod.getVod_id());
		vod_cidView.setText("cid:"+wantShowVod.getVod_cid());
		vod_nameView.setText(wantShowVod.getVod_name());
		vod_sourceidView.setText("sourceid:"+wantShowVod.getVod_sourceid());
		/*
		 * �����Ҫ����һ���쳣���������pic����ֵ��picUrlString��Ϊ�յ�ʱ���ǲ��ܽ�ͼƬ������������ʹ�õģ���ΪkeyΪ�ն�
		 *������ԣ�1��������������ж��Ƿ�Ϊ��  2���ڽϵײ��AsyncImageLoader�д���
		 * ���� ����������ж� ��ȽϺ�
		 */
		if(wantShowVod.getVod_pic() == null ||wantShowVod.getVod_pic().equals("")){
			vod_picView.setImageResource(R.drawable.img404);
		}else{
			AsyncImageLoader loader = new AsyncImageLoader();
			VodPicCallbackImpl callbackImpl = new VodPicCallbackImpl(vod_picView);
	    	Drawable cacheImage = 
	    		loader.loadDrawable(wantShowVod.getVod_pic(), callbackImpl);
			if (cacheImage != null) {
				vod_picView.setImageDrawable(cacheImage);
			}
		}
		vod_addtimeView.setText("ʱ�䣺"+TransformDateAndString.TimeStamp2Date(wantShowVod.getVod_addtime(), "yyyy-MM-dd HH:mm:ss"));
		vod_hitsView.setText("�������"+wantShowVod.getVod_hits());
		
		/**
		 * ͬ���ģ�����vod_contentҲ���� ����Ϊ�� �������������
		 */
		if(wantShowVod.getVod_content() == null || wantShowVod.getVod_content().equals("")){
			vod_contentView.setText("���鹣�ţ�����");
		}else{
			vod_contentView.setText("���鹣�ţ�"+wantShowVod.getVod_content());
		}
		//Ϊ��ǰItem��View�趨Tag��ǩΪ�����л���PpVodItem�����Թ����Ϊ���趨OnClick�¼�����ʱ �ܸ�������ṩSourceId����
		convertView.setTag(wantShowVod);
		return convertView;
	}

}
