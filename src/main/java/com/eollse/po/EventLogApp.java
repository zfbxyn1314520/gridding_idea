package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EventLogApp implements Serializable {

	private Integer eventLogId;
	private Integer eventId;
	private String eventLogContent;
	private String editEventLogName;
	@DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date editEventLogDate;
	private Integer solveStatusId;

	public EventLogApp() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "EventLogApp [eventLogId=" + eventLogId + ", eventId=" + eventId
				+ ", eventLogContent=" + eventLogContent
				+ ", editEventLogName=" + editEventLogName
				+ ", editEventLogDate=" + editEventLogDate + ", solveStatusId="
				+ solveStatusId + "]";
	}


	public EventLogApp(Integer eventLogId, Integer eventId,
			String eventLogContent, String editEventLogName,
			Date editEventLogDate, Integer solveStatusId) {
		super();
		this.eventLogId = eventLogId;
		this.eventId = eventId;
		this.eventLogContent = eventLogContent;
		this.editEventLogName = editEventLogName;
		this.editEventLogDate = editEventLogDate;
		this.solveStatusId = solveStatusId;
	}


	public Integer getEventLogId() {
		return eventLogId;
	}

	public void setEventLogId(Integer eventLogId) {
		this.eventLogId = eventLogId;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventLogContent() {
		return eventLogContent;
	}

	public void setEventLogContent(String eventLogContent) {
		this.eventLogContent = eventLogContent;
	}

	public String getEditEventLogName() {
		return editEventLogName;
	}

	public void setEditEventLogName(String editEventLogName) {
		this.editEventLogName = editEventLogName;
	}

	public Date getEditEventLogDate() {
		return editEventLogDate;
	}

	public void setEditEventLogDate(Date editEventLogDate) {
		this.editEventLogDate = editEventLogDate;
	}

	public Integer getSolveStatusId() {
		return solveStatusId;
	}

	public void setSolveStatusId(Integer solveStatusId) {
		this.solveStatusId = solveStatusId;
	}

}
