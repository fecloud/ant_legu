package cn.dotui.legu;

import org.json.JSONObject;

public class CommitResult {

	private  String res;
	
	private int time;

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public boolean resIsOK(){
		if (null != res && res.trim().equalsIgnoreCase("ok")){
			return true;
		}
		return false;
	}
	
	/**
	 * {"res":"ok", "time":"20"}
	 * @param json
	 * @return
	 */
	public boolean fromJSON(JSONObject json){
		if (json.has("res")) {
			this.res = json.getString("res");
		}
		
		if (json.has("time")) {
			this.time = json.getInt("time");
		}
		return true;
	}
}
