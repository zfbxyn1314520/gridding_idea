package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
	private Integer logId;
	private Integer userId;
	private String logIP;
	private String useClass;
	private String useMethod;
	private String logLevel;
	private String logDate;
	private String logMsg;
	private User user;
	
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Log(Integer logId, Integer userId, String logIP, String useClass,
			String useMethod, String logLevel, String logDate, String logMsg,
			User user) {
		super();
		this.logId = logId;
		this.userId = userId;
		this.logIP = logIP;
		this.useClass = useClass;
		this.useMethod = useMethod;
		this.logLevel = logLevel;
		this.logDate = logDate;
		this.logMsg = logMsg;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Log [logId=" + logId + ", userId=" + userId + ", logIP="
				+ logIP + ", useClass=" + useClass + ", useMethod=" + useMethod
				+ ", logLevel=" + logLevel + ", logDate=" + logDate
				+ ", logMsg=" + logMsg + ", user=" + user + "]";
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLogIP() {
		return logIP;
	}
	public void setLogIP(String logIP) {
		this.logIP = logIP;
	}
	public String getUseClass() {
		return useClass;
	}
	public void setUseClass(String useClass) {
		this.useClass = useClass;
	}
	public String getUseMethod() {
		return useMethod;
	}
	public void setUseMethod(String useMethod) {
		this.useMethod = useMethod;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public String getLogMsg() {
		return logMsg;
	}
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


}
