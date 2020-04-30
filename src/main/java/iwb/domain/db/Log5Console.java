package iwb.domain.db;

import java.io.Serializable;
import java.util.Map;

import iwb.util.GenericUtil;


public class Log5Console implements Serializable, Log5Base{
	private static final long serialVersionUID = 184252091816162873L;

	private int logId;
	private int userId;
	private int customizationId;  
	private String msg;  
	private String level;  
	private String projectUuid;  
	private	String transactionId;

	public String toInfluxDB() {
		StringBuilder s=new StringBuilder();
		s.append("console,project_uuid=").append(projectUuid).append(" user_id=").append(userId).append("i,level=\"").append(level).append("\",msg=\"").append(GenericUtil.stringToJS2(msg)).append("\"");
		if(!GenericUtil.isEmpty(transactionId))s.append(",trid=\"").append(transactionId).append("\"");
		return s.toString();
	}

	
	public Log5Console() {
	}
	
	public Log5Console(Map<String, Object> scd,String msg,String level, String transactionId) {
		this.customizationId = scd.containsKey("customizationId") ? (Integer)scd.get("customizationId") : 0;
		this.projectUuid = (String)scd.get("projectId");
		this.userId = scd.containsKey("userId") ? (Integer)scd.get("userId") : 0;
		this.msg = msg;
		this.level = GenericUtil.isEmpty(level) ? "info":level;
		this.transactionId = transactionId;
	}

 
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}

	public int getCustomizationId() {
		return customizationId;
	}
	public void setCustomizationId(int customizationId) {
		this.customizationId = customizationId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getProjectUuid() {
		return projectUuid;
	}
	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}
	
}
