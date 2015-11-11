package cn.dotui.legu;

import org.json.JSONObject;

public class CommitResult {

	// {"returnCode":0,"retMsg":"ok","timestamp":"2015-11-11 12:59:50","eventId":"101","interfaceCmd":"serveSubmit","code":0}

	private int returnCode;

	private String retMsg;

	private String timestamp;

	private int eventId;

	private String interfaceCmd;

	private int code;

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getInterfaceCmd() {
		return interfaceCmd;
	}

	public void setInterfaceCmd(String interfaceCmd) {
		this.interfaceCmd = interfaceCmd;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isOK(){
		return code == 0;
	}
	
	/**
	 * {"returnCode":0,"retMsg":"ok","timestamp":"2015-11-11 12:59:50","eventId":"101","interfaceCmd":"serveSubmit","code":0}
	 * 
	 * @param json
	 * @return
	 */
	public boolean fromJSON(JSONObject json) {
		if (json.has("returnCode")) {
			this.returnCode = json.getInt("returnCode");
		}
		
		if (json.has("retMsg")) {
			this.retMsg = json.getString("retMsg");
		}

		if (json.has("timestamp")) {
			this.timestamp = json.getString("timestamp");
		}

		if (json.has("eventId")) {
			this.eventId = json.getInt("eventId");
		}
		
		if (json.has("interfaceCmd")) {
			this.interfaceCmd = json.getString("interfaceCmd");
		}
		
		if (json.has("code")) {
			this.code = json.getInt("code");
		}
	
		return true;
	}
}
