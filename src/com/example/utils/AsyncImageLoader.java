package com.example.utils;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.example.yuejiaoyun.R;

import android.R.drawable;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

//�������Ҫ������ʵ��ͼƬ���첽����
public class AsyncImageLoader {
	//ͼƬ�������
	//����ͼƬ��URL��ֵ��һ��SoftReference���󣬸ö���ָ��һ��Drawable����
	private Map<String, SoftReference<Drawable>> imageCache = 
		new HashMap<String, SoftReference<Drawable>>();
	
	//ʵ��ͼƬ���첽����
	public Drawable loadDrawable(final String imageUrl,final ImageCallback callback){
		/* ������ �ڴ� ����Ӧ�� �ղ��� �������Ǻ������� ���������������DrawableͼƬ�����Ƕ�Ӧ��KeyֵΪ�գ�ͬ�������
		 * ���Ը�Ϊֱ���ڽϸ߼���adapter����Բ���wantShowVod.getVod_pic()���жϲ�����������δ���������
		 //���imageUrl����Ϊ�յĻ������س������õġ�Ĭ��ͼƬ��
		if(imageUrl.equals("") || imageUrl == null){
			return Resources.getSystem().getDrawable(R.drawable.img404);
		}
		 */
		
		//��ѯ���棬�鿴��ǰ��Ҫ���ص�ͼƬ�Ƿ��Ѿ������ڻ��浱��
		if(imageCache.containsKey(imageUrl)){
			SoftReference<Drawable> softReference=imageCache.get(imageUrl);
			if(softReference.get() != null){//�����û�δ�����������
				return softReference.get();
			}
		}
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				callback.imageLoaded((Drawable) msg.obj);
			}
		};
		//�¿���һ���̣߳����߳����ڽ���ͼƬ������
		new Thread(){
			public void run() {
				Drawable drawable=loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			};
		}.start();
		return null;
	}
	//�÷������ڸ���ͼƬ��URL��������������ͼƬ
	protected Drawable loadImageFromUrl(String imageUrl) {
		try {
			//����ͼƬ��URL������ͼƬ��������һ��Drawable����		
				return Drawable.createFromStream(new URL(imageUrl).openStream(), "src");
			
		} catch (Exception e) {
//		throw new RuntimeException(e);
		
//      	return Resources.getSystem().getDrawable(R.drawable.push);

//	���ܴ������ȡ��ӦͼƬʱ�����������ƹ����ϵ�ͼ��		
			final String yuejiaoyun =
		       "http://ecloud.edugd.cn/images/global/logo.png";		
//			return Drawable.createFromStream(yuejiaoyun.openStream(), "src");
			Drawable yuejiaoyundraw=loadImageFromUrl(yuejiaoyun);
			return yuejiaoyundraw;
		}
	}
	
	//�ص��ӿ�
	public interface ImageCallback{
		public void imageLoaded(Drawable imageDrawable);
	}
}
