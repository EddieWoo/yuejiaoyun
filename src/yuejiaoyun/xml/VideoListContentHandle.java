package yuejiaoyun.xml;


import java.util.List;


import info.info;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*�������ݴ�����   ʵ��defaulthandler��һЩ������override methods����  start document��������
 defaulthandler�����ڽ���xml�ļ�����
 */
public class VideoListContentHandle extends DefaultHandler {

	private List<info> list=null;
	String tagName=null;
	private info infos=null;
	//�����������ȫ����
	StringBuffer currentValue = new StringBuffer();
	
	public VideoListContentHandle(List<info> list) {
		super();
		this.list = list;
	}

	public List<info> getList() {
		return list;
	}

	public void setList(List<info> list) {
		this.list = list;
	}	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp=new String(ch,start,length);
		if(tagName.equals("vod_id")){
			infos.setId(temp);
		}else if(tagName.equals("vod_name")){
			infos.setVideoName(temp);
		}else if(tagName.equals("vod_http")){
			
			//�����������ȫ����
			 currentValue.append(ch, start, length);
			 temp=currentValue.toString();
			infos.setVideoURL(temp);
		
		}
	}
	
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equals("pp_vod")){
			list.add(infos); 
		 }
		tagName = "";
		//�����������ȫ����
			//	currentValue.delete(0, currentValue.length());
	}



	@Override
	public void startDocument() throws SAXException {

		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.tagName=localName;
		if(tagName.equals("pp_vod")){
			//�����������ȫ����
		//	currentValue.delete(0, currentValue.length());
			infos=new info();
		}
	}
	
}
