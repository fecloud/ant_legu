package cn.dotui.legu;

import org.json.JSONObject;

public class UploadResult {

	// {"code":0,"appName":"Dreamca...","fileName":"DreamCa...elease.apk","edition":"2.1","md5":"318D95D91107AC91C2B97050482D36B5","signMd5":"76:30:11:9A:D4:36:01:E9:BF:B8:28:38:49:25:98:47","pkgName":"com.zq.camera","logo":"http:\/\/szmain.fcloud.store.qq.com\/store_file_download?buid=17441&uin=413379493&dir_path=\/318D95D91107AC91C2B97050482D36B5\/&name=318D95D91107AC91C2B97050482D36B5Logo&key=2C4B0158E9D45F10F33C0712B983E684561C638DEC855519A9C9D12A98E7A11D"}

	private String appname;

	private int code;

	private String edition;

	private String fileName;

	private String logo;

	private String md5;

	private String pkgName;

	private String signMd5;

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getSignMd5() {
		return signMd5;
	}

	public void setSignMd5(String signMd5) {
		this.signMd5 = signMd5;
	}

	public boolean isOK(){
		return code == 0;
	}
	
	/**
	 * //{"res":"ok", "appname":"OpenLog.apk", "name":"com.test.log",
	 * "ver":"1.0", "id":"5ab857bbcd88a660c20c03a5af96c7c9",
	 * "notice":"OpenLog.apk"}
	 * 
	 * @param json
	 * @return
	 */
	public boolean fromJSON(JSONObject json) {
		if (json.has("appname")) {
			this.appname = json.getString("appname");
		}

		if (json.has("code")) {
			this.code = json.getInt("code");
		}

		if (json.has("edition")) {
			this.edition = json.getString("edition");
		}

		if (json.has("fileName")) {
			this.fileName = json.getString("fileName");
		}

		if (json.has("logo")) {
			this.logo = json.getString("logo");
		}

		if (json.has("md5")) {
			this.md5 = json.getString("md5");
		}

		if (json.has("pkgName")) {
			this.pkgName = json.getString("pkgName");
		}

		if (json.has("signMd5")) {
			this.signMd5 = json.getString("signMd5");
		}
		return true;
	}

}
