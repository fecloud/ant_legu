package cn.dotui.legu;

import org.json.JSONObject;

public class CheckResult {

	private String res;

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public boolean resIsOK() {
		if (null != res && res.trim().equalsIgnoreCase("ok")) {
			return true;
		}
		return false;
	}

	/**
	 * {"res":"ok", "time":"20"}
	 * 
	 * @param json
	 * @return
	 */
	public boolean fromJSON(JSONObject json) {
		if (json.has("res")) {
			this.res = json.getString("res");
		}
		return true;
	}
}
