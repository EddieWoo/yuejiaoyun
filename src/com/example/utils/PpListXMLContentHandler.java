package com.example.utils;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.model.PpListItem;

/**
 * ����PpListXML��������
 *
 */
public class PpListXMLContentHandler extends DefaultHandler {

	private PpListItem ppListItem = null;
	private List<PpListItem> ppListItems = null;
	private String tagName = null; //��ǰҪ������Ԫ�ر�ǩ
	
	public PpListXMLContentHandler() {
		// TODO Auto-generated constructor stub
		this.ppListItems = new ArrayList<PpListItem>();
	}
	public List<PpListItem> getPpListItems() {
		return ppListItems;
	}

	//�����ĵ���ʼ��֪ͨ���������ĵ��Ŀ�ͷ��ʱ�򣬵������������������������һЩԤ����Ĺ�����
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}
	/**
	 * ����Ԫ�ؿ�ʼ��֪ͨ��������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������������namespaceURI��ʾԪ�ص������ռ䣻
     * localName��ʾԪ�صı������ƣ�����ǰ׺����qName��ʾԪ�ص��޶�������ǰ׺����atts ��ʾԪ�ص����Լ���
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		this.tagName = localName;
		if(tagName.equals("pp_list")){
			this.ppListItem = new PpListItem();
		}
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
		String dataStr = new String(ch,start,length);
		if(tagName.equals("list_id")){
			this.ppListItem.setList_id(dataStr);
		}else if(tagName.equals("list_pid")){
			this.ppListItem.setList_pid(dataStr);
		}else if(tagName.equals("list_name")){
			this.ppListItem.setList_name(dataStr);
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if(localName.equals("pp_list")){
			this.ppListItems.add(ppListItem);
			ppListItem = null;
		}
		
		this.tagName = "";
	}
	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	
}
