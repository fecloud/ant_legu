package cn.dotui.legu;

import org.json.JSONObject;

public class UploadResult {

	//{"res":"ok", "appname":"OpenLog.apk", "name":"com.test.log", "ver":"1.0", "id":"5ab857bbcd88a660c20c03a5af96c7c9", "notice":"OpenLog.apk"}
	
	private  String res;
	
	private String appname;
	
	private String name;
	
	private String ver;
	
	private String id;
	
	private String notice;

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	public boolean resIsOK(){
		if (null != res && res.trim().equalsIgnoreCase("ok")){
			return true;
		}
		return false;
	}
	
	/**
	 * //{"res":"ok", "appname":"OpenLog.apk", "name":"com.test.log", "ver":"1.0", "id":"5ab857bbcd88a660c20c03a5af96c7c9", "notice":"OpenLog.apk"}
	 * @param json
	 * @return
	 */
	public boolean fromJSON(JSONObject json){
		if (json.has("res")) {
			this.res = json.getString("res");
		}
		
		if (json.has("appname")) {
			this.appname = json.getString("appname");
		}
		
		if (json.has("name")) {
			this.name = json.getString("name");
		}

		if (json.has("ver")) {
			this.ver = json.getString("ver");
		}
		
		if (json.has("id")) {
			this.id = json.getString("id");
		}
		
		if (json.has("notice")) {
			this.notice = json.getString("notice");
		}
		return true;
	}
	
}
