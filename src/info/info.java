package info;

import java.io.Serializable;

public class info implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vod_id;
	private String vod_name;
	private String vod_http;
//    private String vod_pic;
	 
	 
	//�Ҽ� resourceȻ��  generate getters and setters�Զ����ɣ���ݼ�Ϊ ctrl alt s��
	//���ɹ��캯��resource constructer 
	//��һ�����캯��Ϊto string 
	public String getId() {
		return vod_id;
	}
	public void setId(String id) {
		this.vod_id = id;
	}
	public String getVideoName() {
		return vod_name;
	}
	public void setVideoName(String Videoname) {
		this.vod_name = Videoname;
	}
	public info() {
		super();
	}
	public String getVideoURL() {
		return vod_http;
	}
	public void setVideoURL(String http) {
		this.vod_http = http;
	}
	
/*	public String getVideoPic() {
		return vod_pic;
	}

	public void setVideoPic(String pic) {
		this.vod_pic = pic;
	}
	*/
	@Override
	
	//�Զ�����
	public String toString() {
		return "VideoInfo [id=" + vod_id + ", VideoName=" + vod_name + ", VideoURL="
				+ vod_http + ", toString()=" + super.toString() + "]";
	}
	public info(String id, String videoname, String http,String pic) {
		super();
		this.vod_id = id;
		this.vod_name = videoname;
		this.vod_http = http;
//		this.vod_pic=pic;
	}	
	
}

