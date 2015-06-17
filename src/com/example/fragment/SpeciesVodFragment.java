package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.activity.ShowVodsInSelectedPpList;
import com.example.yuejiaoyun.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * ��ʾ ����Vod
 */
public class SpeciesVodFragment extends Fragment {

	//��ѧ �γ�GridView����������...��ʷ
	private GridView highSchoolGridView;
	//��ѧ �γ�GridView������IT��...��ʷ
	private GridView universityGridView;
	//����GridView��������¼Ƭ...���Ӿ�
	private GridView othersGridView;
	
	private String[] highSchoolTitles = new String[] {"����","��ѧ","Ӣ��","����","��ѧ","����","��ʷ"};
	private String[] universityTitles = new String[] {"IT��","����","����","����","��ʷ"};
	private String[] othersTitles = new String[] {"��¼Ƭ","ְҵ","����","��Ӱ","���Ӿ�"};
	
	private int[] highSchoolImages = new int[] { R.drawable.pp_list8, R.drawable.pp_list9, R.drawable.pp_list10, R.drawable.pp_list11, R.drawable.pp_list12, R.drawable.pp_list13, R.drawable.pp_list14};
	private int[] universityImages = new int[] { R.drawable.pp_list15, R.drawable.pp_list16, R.drawable.pp_list17, R.drawable.pp_list18, R.drawable.pp_list19};
	private int[] othersImages = new int[] { R.drawable.pp_list3, R.drawable.pp_list4, R.drawable.pp_list5, R.drawable.pp_list6, R.drawable.pp_list7};
	
	public SpeciesVodFragment() {
		// TODO Auto-generated constructor stub
	}

	// �÷����ķ���ֵ���Ǹ�Fragment��ʾ��View���
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//���� SpeciesVodFragment �Ĳ����ļ�,���ɵ����rootView���ǽ�Ҫ��ʾ��View
		View rootView = inflater.inflate(R.layout.fragment_vod_species, container, false);
		
		//��ȡ �����ļ��� ��GridView
		highSchoolGridView = (GridView) rootView.findViewById(R.id.highSchoolGridView);
		universityGridView = (GridView) rootView.findViewById(R.id.universityGridView);
		othersGridView = (GridView) rootView.findViewById(R.id.othersGridView);
		//���������� ��λGridView������Դ
		PictureAdapter highSchoolAdapter = new PictureAdapter(highSchoolTitles, highSchoolImages, this.getActivity());
		PictureAdapter universityAdapter = new PictureAdapter(universityTitles, universityImages, this.getActivity());
		PictureAdapter othersAdapter = new PictureAdapter(othersTitles, othersImages, this.getActivity());
		//ΪGridView���ö�Ӧ��������,����ʾͼƬ
		highSchoolGridView.setAdapter(highSchoolAdapter);
		universityGridView.setAdapter(universityAdapter);
		othersGridView.setAdapter(othersAdapter);
		//ΪGridView���õ����¼��ļ�����
		highSchoolGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentViews, View clickView, int position,
					long id) {
				//ΪIntent��Ϣ������ Ĭ�ϵ�Bundle���ݰ�
				Bundle bundle = new Bundle();
				//position��0��ʼ����8Ϊ����ѧgridview���µĵ�һ��Item"����"����List_idΪ8����position�Ͷ�Ӧ�����cid��ƫ����
				bundle.putInt("list_id", position+8);
				bundle.putString("list_name", highSchoolTitles[position]);
				Intent intent=new Intent(getActivity(), ShowVodsInSelectedPpList.class);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
				
			}
		});
		universityGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentViews, View clickView, int position,
					long id) {
				//ΪIntent��Ϣ������ Ĭ�ϵ�Bundle���ݰ�
				Bundle bundle = new Bundle();
				//position��0��ʼ����15Ϊ����ѧgridview���µĵ�һ��Item�ǡ�IT�ࡱ����List_idΪ15����position�Ͷ�Ӧ�����cid��ƫ����
				bundle.putInt("list_id", position+15);
				bundle.putString("list_name", universityTitles[position]);
				Intent intent=new Intent(getActivity(), ShowVodsInSelectedPpList.class);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
				
			}
		});
		othersGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parentViews, View clickView, int position,
					long id) {
				//ΪIntent��Ϣ������ Ĭ�ϵ�Bundle���ݰ�
				Bundle bundle = new Bundle();
				//position��0��ʼ����3Ϊ������gridview���µĵ�һ��Item��¼Ƭ����List_idΪ3����position�Ͷ�Ӧ�����cid��ƫ����
				bundle.putInt("list_id", position+3);
				bundle.putString("list_name", othersTitles[position]);
				Intent intent=new Intent(getActivity(), ShowVodsInSelectedPpList.class);
				intent.putExtras(bundle);
				getActivity().startActivity(intent);
				
			}
		});
		//TextView textView = (TextView)container.findViewById(R.id.);
		
		return rootView; 
	}
}
//�ڲ���
class PictureAdapter extends BaseAdapter {// ��������BaseAdapter������

    private LayoutInflater inflater;// ����LayoutInflater����
    private List<Picture> pictures;// ����List���ͼ���

    // Ϊ�ഴ�����캯��
    public PictureAdapter(String[] titles, int[] images, Context context) {
        super();
        pictures = new ArrayList<Picture>();// ��ʼ�����ͼ��϶���
        inflater = LayoutInflater.from(context);// ��ʼ��LayoutInflater����
        for (int i = 0; i < images.length; i++)// ����ͼ������
        {
            Picture picture = new Picture(titles[i], images[i]);// ʹ�ñ����ͼ������Picture����
            pictures.add(picture);// ��Picture������ӵ����ͼ�����
        }
    }

    @Override
    public int getCount() {// ��ȡ���ͼ��ϵĳ���
        if (null != pictures) {// ������ͼ��ϲ�Ϊ��
            return pictures.size();// ���ط��ͳ���
        } else {
            return 0;// ����0
        }
    }

    @Override
    public Object getItem(int arg0) {
        return pictures.get(arg0);// ��ȡ���ͼ���ָ������������
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;// ���ط��ͼ��ϵ�����
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder viewHolder;// ����ViewHolder����
        if (arg1 == null) {// �ж�ͼ���ʶ�Ƿ�Ϊ��

            arg1 = inflater.inflate(R.layout.gridview_item, null);// ����ͼ���ʶ
            viewHolder = new ViewHolder();// ��ʼ��ViewHolder����
            viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);// ����ͼ�����
            viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);// ����ͼ��Ķ�����ֵ
            arg1.setTag(viewHolder);// ������ʾ
        } else {
            viewHolder = (ViewHolder) arg1.getTag();// ������ʾ
        }
        viewHolder.title.setText(pictures.get(arg0).getTitle());// ����ͼ�����
        viewHolder.image.setImageResource(pictures.get(arg0).getImageId());// ����ͼ��Ķ�����ֵ
        return arg1;// ����ͼ���ʶ
    }
}

class ViewHolder {// ����ViewHolder��

    public TextView title;// ����TextView����
    public ImageView image;// ����ImageView����
}

class Picture {// ����Picture��

    private String title;// �����ַ�������ʾͼ�����
    private int imageId;// ����int��������ʾͼ��Ķ�����ֵ

    public Picture() {// Ĭ�Ϲ��캯��

        super();
    }

    public Picture(String title, int imageId) {// �����вι��캯��

        super();
        this.title = title;// Ϊͼ����⸳ֵ
        this.imageId = imageId;// Ϊͼ��Ķ�����ֵ��ֵ
    }

    public String getTitle() {// ����ͼ�����Ŀɶ�����
        return title;
    }

    public void setTitle(String title) {// ����ͼ�����Ŀ�д����
        this.title = title;
    }

    public int getImageId() {// ����ͼ�������ֵ�Ŀɶ�����
        return imageId;
    }

    public void setimageId(int imageId) {// ����ͼ�������ֵ�Ŀ�д����
        this.imageId = imageId;
    }
}