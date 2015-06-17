package download;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class HttpDownloader {

private URL url = null;
	
	/**
	 * ����URL�����ļ���ǰ��������ļ����ı��ģ������ķ���ֵ�����ļ����е�����
	 * 1.����һ��URL����
	 * 2.ͨ��URL���󣬴���һ��HttpURLConnection����
	 * 3.�õ�InputStream
	 * 4.��InputStream�õ�����
	 */
	public String download(String urlStr){
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try{
			//����һ��url����
			url = new URL(urlStr);
			//����һ��http����
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			//ʹ��IO��ȡ����
			buffer  = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while((line = buffer.readLine()) != null){
				sb.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * �ú����������Σ�-1���������ļ�����0���������ļ��ɹ���1�����ļ��Ѿ�����
	 */
	public int downFile(String urlStr, String path,String fileName){
		InputStream inputStream = null;
		try{
			FileUtils fu = new FileUtils();
			
			if(fu.isFileExist(fileName,path)){
				return 1;
			}else{
				
				inputStream = getInputStreamFromUrl(urlStr);
				System.out.println("test");
				File resultFile = fu.write2SDFromInput(path, fileName, inputStream);
				if(resultFile == null){
					return -1;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * ����URL�õ�������
	 */
	public InputStream getInputStreamFromUrl(String urlStr) throws MalformedURLException, IOException{
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}
}
