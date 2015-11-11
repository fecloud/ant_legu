package cn.dotui.legu;

import org.json.JSONObject;

public class CheckResult {

	private int uin;
	private String appName;
	private String fileName;
	private String edition;
	private String md5;
	private String time;
	private int state;
	private int totalLeak;
	private int piracyCnt;
	private int jiagu;
	private int health;
	private String logo;
	private String appNameDisp;
	private String fileNameDisp;

	public int getUin() {
		return uin;
	}

	public void setUin(int uin) {
		this.uin = uin;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getTotalLeak() {
		return totalLeak;
	}

	public void setTotalLeak(int totalLeak) {
		this.totalLeak = totalLeak;
	}

	public int getPiracyCnt() {
		return piracyCnt;
	}

	public void setPiracyCnt(int piracyCnt) {
		this.piracyCnt = piracyCnt;
	}

	public int getJiagu() {
		return jiagu;
	}

	public void setJiagu(int jiagu) {
		this.jiagu = jiagu;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAppNameDisp() {
		return appNameDisp;
	}

	public void setAppNameDisp(String appNameDisp) {
		this.appNameDisp = appNameDisp;
	}

	public String getFileNameDisp() {
		return fileNameDisp;
	}

	public void setFileNameDisp(String fileNameDisp) {
		this.fileNameDisp = fileNameDisp;
	}

	/**
	 * {"uin":413379493,"appName":"QQLock","fileName":
	 * "com.apkol.qqlock_350000-release(1).apk"
	 * ,"edition":"1.5","md5":"605D6B725CF237FF9929B29FC0AC51D7"
	 * ,"time":"2015-11-11 13:45:02"
	 * ,"state":0,"totalLeak":21,"piracyCnt":-1,"jiagu":1,"health":74,"logo":
	 * "http:\/\/szmain.fcloud.store.qq.com\/store_file_download?buid=17441&uin=413379493&dir_path=\/605D6B725CF237FF9929B29FC0AC51D7\/&name=605D6B725CF237FF9929B29FC0AC51D7Logo&key=AAA86C7811D8DC99340847644905CCDBA87C0F9C980D393156E679ECCF2C832F","appNameDisp":"QQLock","fileNameDisp":"com.apk...ase(1).apk"
	 * }
	 * 
	 * @param json
	 * @return
	 */
	public boolean fromJSON(JSONObject json) {
		if (json.has("uin")) {
			this.uin = json.getInt("uin");
		}
		if (json.has("appName")) {
			this.appName = json.getString("appName");
		}

		if (json.has("fileName")) {
			this.fileName = json.getString("fileName");
		}
		if (json.has("edition")) {
			this.edition = json.getString("edition");
		}

		if (json.has("md5")) {
			this.md5 = json.getString("md5");
		}

		if (json.has("time")) {
			this.time = json.getString("time");
		}
		if (json.has("state")) {
			this.state = json.getInt("state");
		}
		if (json.has("totalLeak")) {
			this.totalLeak = json.getInt("totalLeak");
		}
		if (json.has("piracyCnt")) {
			this.piracyCnt = json.getInt("piracyCnt");
		}
		if (json.has("jiagu")) {
			this.jiagu = json.getInt("jiagu");
		}
		if (json.has("health")) {
			this.health = json.getInt("health");
		}
		if (json.has("logo")) {
			this.logo = json.getString("logo");
		}
		if (json.has("appNameDisp")) {
			this.appNameDisp = json.getString("appNameDisp");
		}

		if (json.has("fileNameDisp")) {
			this.fileNameDisp = json.getString("fileNameDisp");
		}

		return true;
	}
}
